import { getAllMenu, getDetailMenuByID } from "../../API/MenuAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";
import { getCustomerByEmail } from "../../API/UserAPI.js";

$(document).ready(function () {
  async function getDetailMenuByIDFunc(id) {
    let data = await getDetailMenuByID("../..", id);
    return data;
  }
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
    let seatdata = JSON.parse(sessionStorage.getItem("ChoosingSeat"));
    let textseatdata = "";
    for (var i = 0; i < seatdata.length; i++) {
      textseatdata += seatdata[i].seatName + " ";
    }
    $(".seat-content").text(textseatdata);
    // Cập nhật thông tin giá
    let pricetorender = sessionStorage.getItem("Pricing");
    $(".ticket-content").text(toVndCurrencyFormat(pricetorender));
    $(".total-content").text(toVndCurrencyFormat(pricetorender));
  }
  renderInformation();
  async function getAllMenuFunc() {
    let datas = await getAllMenu("../..", 1);
    for (const data of datas.data) {
      let VNDPrice = await toVndCurrencyFormat(data.price);
      const htmls = `<div class="col-lg-6 col-md-12 col-xs-12">
          <div class="card mb-5">
            <div class="row g-0">
              <div class="col-md-4">
                <img
                  src="../public/img/menu/${data.imgUrl}"
                  class="img-fluid rounded-start"
                  alt="..."
                />
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title">${data.name}</h5>
                  <p class="card-text">
                    <small class="text-muted"
                      >Giá bán: <span>${VNDPrice}</span></small
                    >
                  </p>
                  <p
                    class="card-text"
                    style="display: flex; align-items: center"
                  >
                    <button itemid=${data.itemId} type="button" class="btn-minus">-</button>
                    <span
                      style="
                        margin-right: 10px;
                        margin-left: 10px;
                        color: white;
                        font-weight: normal;
                      "
                    >
                      0
                    </span>
                    <button itemid=${data.itemId} type="button" class="btn-plus">+</button>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>`;
      $(".container-menu").append(htmls);
    }
    const objectstore = [];
    $(".btn-minus").click(async function () {
      const comboNumber = $(this).parent().find("span");
      if (comboNumber.text() > 0) {
        comboNumber.text(parseInt(comboNumber.text()) - 1);
        let numbertoadd = parseInt(comboNumber.text());
        let itemid = $(this).attr("itemid");
        let data = await getDetailMenuByIDFunc(itemid);
        let datatoadd = data.data[0];
        let existingItem = objectstore.find(function (item) {
          return (
            item.name === datatoadd.name && item.imgUrl === datatoadd.imgUrl
          );
        });
        if (existingItem) {
          if (!existingItem.hasOwnProperty("Number")) {
            existingItem.Number = 1;
          } else {
            existingItem.Number -= 1;
          }
        }
      }
      sessionStorage.setItem("MenuInfo", JSON.stringify(objectstore));
      const itemTotalPrices = objectstore.map((item) => {
        const totalPrice = Number(item.price) * item.Number;
        return { ...item, totalPrice };
      });

      const totalPrice = itemTotalPrices.reduce((total, item) => {
        return total + item.totalPrice;
      }, 0);
      $(".combo-content").text(toVndCurrencyFormat(totalPrice));
      sessionStorage.setItem("PriceMenu", totalPrice);
      let pricetocal = sessionStorage.getItem("Pricing");
      $(".total-content").text(
        toVndCurrencyFormat(totalPrice + parseInt(pricetocal))
      );
      sessionStorage.setItem("TotalPrice", totalPrice + parseInt(pricetocal));
      sessionStorage.setItem("PriceMenu", totalPrice);
    });
    $(".btn-plus").click(async function () {
      const comboNumber = $(this).parent().find("span");
      comboNumber.text(parseInt(comboNumber.text()) + 1);
      let numbertoadd = parseInt(comboNumber.text());
      let itemid = $(this).attr("itemid");
      let data = await getDetailMenuByIDFunc(itemid);
      let datatoadd = data.data[0];
      let existingItem = objectstore.find(function (item) {
        return item.name === datatoadd.name && item.imgUrl === datatoadd.imgUrl;
      });
      if (existingItem) {
        if (!existingItem.hasOwnProperty("Number")) {
          existingItem.Number = 1;
        } else {
          existingItem.Number += 1;
        }
      } else {
        datatoadd.Number = 1;
        objectstore.push(datatoadd);
      }
      sessionStorage.setItem("MenuInfo", JSON.stringify(objectstore));
      const itemTotalPrices = objectstore.map((item) => {
        const totalPrice = Number(item.price) * item.Number;
        return { ...item, totalPrice };
      });

      // Get the total price of all items
      const totalPrice = itemTotalPrices.reduce((total, item) => {
        return total + item.totalPrice;
      }, 0);
      $(".combo-content").text(toVndCurrencyFormat(totalPrice));
      let pricetocal = sessionStorage.getItem("Pricing");
      $(".total-content").text(
        toVndCurrencyFormat(totalPrice + parseInt(pricetocal))
      );
      sessionStorage.setItem("TotalPrice", totalPrice + parseInt(pricetocal));
      sessionStorage.setItem("PriceMenu", totalPrice);
    });
  }

  getAllMenuFunc();
});
