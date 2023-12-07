import { XOREncrypt, XORDecrypt } from "../../Util/EncryptXOR.js";
import {
  postLocalStorage,
  setLocalStorage,
} from "../../Util/LocalstorageInterFace.js";
import { getShowTimeByID } from "../../API/ShowTimeAPI.js";
import { getSeatByRoomID, getseatByID } from "../../API/SeatAPI.js";
import { getAllTicketsByShowTimeId } from "../../API/TicketAPI.js";
import { getCustomerByEmail } from "../../API/UserAPI.js";

$(document).ready(function () {
  $(".select-container-info").change(function () {
    var selectedOption = $(this).val();
    sessionStorage.setItem("SelectedTicketNum", selectedOption);
  });
  $(".navbar-toggler").click(function () {
    var color = $(".navbar").css("background-color");
    if (color == "rgba(0, 0, 0, 0.01)") {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.85)");
    } else {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.01)");
    }
  });
  async function getAllTicketsByShowTimeIdFunc() {
    const ShowTimeID = sessionStorage.getItem("ShowTimeID");
    let datas = await getAllTicketsByShowTimeId("../..", ShowTimeID);
    let dataShowTimeIDToLoop = datas.data;
    return dataShowTimeIDToLoop;
  }

  async function getSeatByRoomIDFunc() {
    let RoomID = sessionStorage.getItem("RoomIDChoosing");
    let seatData = await getSeatByRoomID("../..", RoomID);
    return seatData.data;
  }
  function CountingHowManySeat(seatData, numofRow) {
    let count = 0;

    for (let i = 0; i < seatData.length / numofRow; i++) {
      if (seatData[i].seatName.charAt(0) === "A") {
        count++;
      }
    }
    return count;
  }
  function createAlphabetString(lastChar) {
    let alphabetString = "";
    let startChar = "A";
    for (let i = startChar.charCodeAt(0); i <= lastChar.charCodeAt(0); i++) {
      alphabetString += String.fromCharCode(i);
    }

    return alphabetString;
  }
  async function getSeatChar() {
    let seatdata = await getSeatByRoomIDFunc();
    let charofseat = seatdata[seatdata.length - 1].seatName[0];
    let rowofchars = await createAlphabetString(charofseat);
    let numofCol = await CountingHowManySeat(seatdata, rowofchars.length);
    return [rowofchars, rowofchars.length, numofCol, seatdata];
  }
  (async function renderSeat() {
    let resultofchars = await getSeatChar();
    let seatChars = resultofchars[0];
    let numCol = resultofchars[2];
    let seatdata = resultofchars[3];
    let numRow = resultofchars[1];
    let ticketID = await getAllTicketsByShowTimeIdFunc();
    let flagtoloop = 0;
    // for (let j = 0; j <= numCol * i; j++) {
    //   console.log(seatdata[j]);
    // }
    const row_chars = seatChars.slice(0, numRow);
    const availableSeats = seatdata.filter((seat) => {
      const isTaken = ticketID.some((t) => t.seatsId === seat.seatId);
      return !isTaken;
    });

    const fullSeats = seatdata.filter((seat) => {
      const isTaken = ticketID.some((t) => t.seatsId === seat.seatId);
      return isTaken;
    });
    for (const char of row_chars) {
      let row = document.createElement("div");
      row.className = "seat-row";
      $(row).append('<div class="letter">' + char + "</div>");
      let charCode = char.charCodeAt(0);
      let charRow = 0;

      if (charCode >= 65 && charCode <= 90) {
        // Check if the character is A-Z
        let newChar = charCode - 65;
        charRow = newChar;
      }
      for (let i = 1; i <= numRow; i++) {
        const seattoadd = availableSeats.find((seat) => {
          return seat.seatName === `${char}${i}`;
        });
        if (fullSeats.length == 0) {
          if (seattoadd) {
            if (seattoadd.typeId == 2) {
              $(row).append(
                new Seat(charRow, i, 3, seattoadd.seatId).getHTML()
              );
              continue;
            } else {
              $(row).append(
                new Seat(charRow, i, 0, seattoadd.seatId).getHTML()
              );
            }
          }
        } else {
          if (seattoadd) {
            if (fullSeats.seatId != seattoadd.seatId) {
              if (seattoadd.typeId == 2) {
                $(row).append(
                  new Seat(charRow, i, 3, seattoadd.seatId).getHTML()
                );
                continue;
              } else {
                $(row).append(
                  new Seat(charRow, i, 0, seattoadd.seatId).getHTML()
                );
              }
            }
          } else {
            let booked = fullSeats.find((seat) => {
              return seat.seatName === `${char}${i}`;
            });
            if (booked) {
              $(row).append(new Seat(charRow, i, -1, booked.seatId).getHTML());
            }
          }
        }
      }

      $(".seats-container").append(row);
    }
    renderInformation();
    function toVndCurrencyFormat(number) {
      const currencyFormat = new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
        minimumFractionDigits: 0,
      });

      return currencyFormat.format(number);
    }
    async function renderInformation() {
      $(".date-content").text(sessionStorage.getItem("DayChoosing"));
      $(".room-content").text(sessionStorage.getItem("RoomIDChoosing"));
      $(".hour-content").text(sessionStorage.getItem("TimeChoosing"));
      $(".theater-content").text(sessionStorage.getItem("TheaterName"));
    }
    let bookingNumber = 0;
    let arrtemptransaction = [];
    let price = 0;

    $(".seat").click(async function () {
      let row = $(this).attr("row");
      let col = $(this).attr("col");
      let stateid = $(this).attr("stateId");
      let idSeat = $(this).attr("seatId");

      function numberToChar(number) {
        return String.fromCharCode(parseInt(number) + "A".charCodeAt(0));
      }

      if (stateid == 1) {
        unselectSeat(row, col);
        let ShowTimeID = sessionStorage.getItem("ShowTimeID");
        let pricing = await getShowTimeByID("../..", ShowTimeID);
        console.log(pricing);
        let pricetocal = pricing.data[0].price;
        let seatIndentify = await getseatByID("../..", idSeat);
        console.log(seatIndentify.data);
        arrtemptransaction = arrtemptransaction.filter(
          (e) => e.seatId !== seatIndentify.data[0].seatId
        );
        console.log(arrtemptransaction);
        sessionStorage.setItem(
          "ChoosingSeat",
          JSON.stringify(arrtemptransaction)
        );
        // Cập nhật thông tin Seat
        let seatdata = $(".seat-content").text();
        let seatList = seatdata
          .split(" ")
          .filter((e) => e !== seatIndentify.data[0].seatName);
        seatdata = seatList.join(" ");
        $(".seat-content").text(seatdata);
        // Cập nhật thông tin giá
        if (seatIndentify.data[0].typeId === "2") price -= 2;
        else price -= 1;

        let pricetorender = toVndCurrencyFormat(pricetocal * price);
        $(".ticket-content").text(pricetorender);
        $(".total-content").text(pricetorender);
        sessionStorage.setItem("Pricing", pricetocal * price);
        bookingNumber--;
        return;
      }
      const selectedValue = $(".form-select option:selected").val();
      if (selectedValue <= bookingNumber) {
        return;
      }

      if (stateid == 0) {
        stateid = 1;
        price += 1;
      } else if (stateid == 3) {
        stateid = 2;
        price += 2;
      } else return;

      let ShowTimeID = sessionStorage.getItem("ShowTimeID");
      let pricing = await getShowTimeByID("../..", ShowTimeID);
      let pricetocal = pricing.data[0].price;
      let seatIndentify = await getseatByID("../..", idSeat);
      selectSeat(row, col);
      arrtemptransaction = [...arrtemptransaction, seatIndentify.data[0]];
      console.log(arrtemptransaction);
      sessionStorage.setItem(
        "ChoosingSeat",
        JSON.stringify(arrtemptransaction)
      );
      // Cập nhật thông tin Seat
      let seatdata = $(".seat-content").text();
      seatdata += seatIndentify.data[0].seatName + " ";
      $(".seat-content").text(seatdata);
      // Cập nhật thông tin giá
      let pricetorender = toVndCurrencyFormat(pricetocal * price);
      $(".ticket-content").text(pricetorender);
      $(".total-content").text(pricetorender);
      sessionStorage.setItem("Pricing", pricetocal * price);
      bookingNumber++;
    });
  })();
});

function selectSeat(row, col) {
  let seat = $(`.seat[row=${row}][col=${col}]`);
  if (seat.hasClass("booked") || seat.hasClass("selected")) return;
  seat.addClass("selecting").removeClass("available").attr("stateid", 1);
}

function unselectSeat(row, col) {
  let seat = $(`.seat[row=${row}][col=${col}]`);
  seat.removeClass("selecting").addClass("available");
  if (seat.hasClass("couple")) seat.attr("stateid", 3);
  else seat.attr("stateid", 0);
}

class Seat {
  constructor(row, col, state, seatId) {
    this.row = row;
    this.col = col;
    this.state = state;
    this.seatId = seatId;
  }

  getHTML() {
    let stateId = this.state;
    let stateName;
    let row_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    switch (this.state) {
      case -1:
        stateName = "booked";
        break;
      case 0:
        stateName = "available";
        break;
      case 1:
        stateName = "selecting";
        break;
      case 2:
        stateName = "selected";
        break;
      case 3:
        stateName = "couple";
        break;
    }

    return (
      `<div class="seat ${stateName}" seatId="${this.seatId}" stateId=${stateId} row=${this.row} col=${this.col}><svg width="25" height="18" viewBox="0 0 25 18" fill="#343434" xmlns="http://www.w3.org/2000/svg">` +
      '<path d="M3.75 4.66875V2.25C3.75 1.0125 4.875 0 6.25 0H18.75C20.125 0 21.25 1.0125 21.25 2.25V4.68C19.8 5.14125 18.75 6.37875 18.75 7.84125V10.125H6.25V7.83C6.25 6.37875 5.2 5.13 3.75 4.66875ZM22.5 5.625C21.125 5.625 20 6.6375 20 7.875V11.25H5V7.875C5 6.6375 3.8875 5.625 2.5 5.625C1.1125 5.625 0 6.6375 0 7.875V13.5C0 14.7375 1.125 15.75 2.5 15.75V18H5V15.75H20V18H22.5V15.75C23.875 15.75 25 14.7375 25 13.5V7.875C25 6.6375 23.875 5.625 22.5 5.625Z" />' +
      "</svg>" +
      '<div class="seat-tooltip">' +
      `<div>HÀNG<b>${row_chars[this.row]}</b></div>` +
      `<div>GHẾ<b>${this.col}</b></div>` +
      "</div>" +
      "</div>"
    );
  }
}
