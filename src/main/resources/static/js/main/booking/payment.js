import {
  getPromotionsVoucher,
  getPromotionsEvent,
  calculateTotalPrice,
} from "../../API/PromotionAPI.js";
import { AddBooking } from "../../API/BookingAPI.js";
import { getCustomerByEmail } from "../../API/UserAPI.js";
import { getMovieByID } from "../../API/MovieAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";

$(document).ready(function () {
  async function getPromotionData() {
    let data = await getPromotionsEvent("../../");
    return data["list"];
  }
  async function getPromotionDiscount(promotionID) {
    let data = await getPromotionData();
    for (const datavoucher of data) {
      if (datavoucher.PromotionID == promotionID) {
        return datavoucher.Discount;
      }
    }
    return 0;
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
    let PriceTotal = sessionStorage.getItem("TotalPrice");
    let menuprice = sessionStorage.getItem("PriceMenu");
    let movieid = sessionStorage.getItem("movieid");
    let dataMovie = await getMovieByID("../..", movieid);
    dataMovie = dataMovie.data[0];
    console.log(dataMovie);
    $(".movie-content").text(dataMovie.name);
    $(".menu-content").text(toVndCurrencyFormat(menuprice));
    $(".price-ticket").text(toVndCurrencyFormat(pricetorender));
    $(".all-price-content").text(toVndCurrencyFormat(PriceTotal));
    $(".after-price-content").text(toVndCurrencyFormat(PriceTotal));
    $(".time-content").text(sessionStorage.getItem("TimeChoosing"));
  }
  renderInformation();
  async function ChangePrice(PromotionPrice, newPrice) {
    $(".discount-content").text("- " + toVndCurrencyFormat(PromotionPrice));
    $(".after-price-content").text(toVndCurrencyFormat(newPrice));
  }
  $(".btn-grad").click(async () => {
    const data = await getPromotionsEvent(
      "../..",
      $(".form-discount-content").val()
    );
    if (data.data[0]) {
      let PromotionPrice =
        JSON.parse(sessionStorage.getItem("TotalPrice")) /
        data.data[0].discount;
      let newPrice =
        JSON.parse(sessionStorage.getItem("TotalPrice")) - PromotionPrice;
      ChangePrice(PromotionPrice, newPrice);
    }
    // let PromtionPrice = await calculateTotalPrice(
    //   "../../../.",
    //   $(".form-discount-content").val(),
    //   JSON.parse(sessionStorage.getItem("TotalPrice"))
    // );
    // if (PromtionPrice.success) {
    //   let newPrice = PromtionPrice.totalPrice;
    //   let PromotionPrice =
    //     JSON.parse(sessionStorage.getItem("TotalPrice")) - newPrice;
    //   ChangePrice(PromotionPrice, newPrice);
    // }
  });
  $("#method-momo").click(function () {
    if ($("#method-atm p").css("color") == "rgb(255, 60, 172)") {
      $(this).css("border", "1px solid #ff3cac");
      $("#method-momo p").css("color", "#ff3cac");
      $("#method-atm").css("border", "0.8px solid rgb(154, 159, 174)");
      $("#method-atm p").css("color", "rgb(154, 159, 174)");
      $("#method-atm svg").css("fill", "rgb(154, 159, 174)");
    } else {
      if ($(this).css("border") == "0.8px solid rgb(154, 159, 174)") {
        $(this).css("border", "1px solid #ff3cac");
        $("#method-momo p").css("color", "#ff3cac");
      } else {
        $(this).css("border", "0.8px solid rgb(154, 159, 174)");
        $("#method-momo p").css("color", "rgb(154, 159, 174)");
      }
    }
  });
  $("#method-atm").click(function () {
    if ($("#method-momo p").css("color") == "rgb(255, 60, 172)") {
      $(this).css("border", "1px solid #ff3cac");
      $("#method-atm p").css("color", "#ff3cac");
      $("#method-momo").css("border", "0.8px solid rgb(154, 159, 174)");
      $("#method-momo p").css("color", "rgb(154, 159, 174)");
      $("#method-atm svg").css("fill", "rgb(154, 159, 174)");
    } else {
      if ($(this).css("border") == "0.8px solid rgb(154, 159, 174)") {
        $(this).css("border", "1px solid #ff3cac");
        $("#method-atm p").css("color", "#ff3cac");
      } else {
        $(this).css("border", "0.8px solid rgb(154, 159, 174)");
        $("#method-atm p").css("color", "rgb(154, 159, 174)");
      }
    }
  });
  async function FindingUserByEmailFunc(email) {
    let data = await getCustomerByEmail("../..", email);
    return data;
  }
  $(".confirm-btn").click(async () => {
    const EmailUser = sessionStorage.getItem("email");
    let UserData = await FindingUserByEmailFunc(EmailUser);
    console.log(UserData.data[0]);
    let UserDataRender = UserData.data[0];
    console.log(UserDataRender);
    let customer_id = UserDataRender.customerId;
    let ChoosingSeat = sessionStorage.getItem("ChoosingSeat");
    let RoomID = sessionStorage.getItem("RoomIDChoosing");
    let dayChoosing = sessionStorage.getItem("DayChoosing");
    let totalPrice = sessionStorage.getItem("TotalPrice");
    let ShowTimeID = sessionStorage.getItem("ShowTimeID");
    let TheaterName = sessionStorage.getItem("TheaterName");
    let MenuInfo = sessionStorage.getItem("MenuInfo");
    let NumofTicket = sessionStorage.getItem("SelectedTicketNum");
    var currentDate = new Date();
    if (!NumofTicket) {
      NumofTicket = 1;
    }
    console.log(NumofTicket);
    // get the current date and time
    var date = currentDate.getDate();
    var month = currentDate.getMonth() + 1;
    var year = currentDate.getFullYear();
    var hours = currentDate.getHours();
    var minutes = currentDate.getMinutes();
    var seconds = currentDate.getSeconds();

    // add leading zeros to the minutes and seconds
    if (minutes < 10) {
      minutes = "0" + minutes;
    }

    if (seconds < 10) {
      seconds = "0" + seconds;
    }
    const dateNoew = new Date();
    // display the current date and time
    const timetoadd =
      year +
      "-" +
      month +
      "-" +
      date +
      " " +
      hours +
      ":" +
      minutes +
      ":" +
      seconds;
    let getVoucher = $(".form-discount-content").val();
    let resulttoadd = await AddBooking(
      "../..",
      NumofTicket,
      dateNoew,
      getVoucher,
      customer_id,
      ShowTimeID,
      totalPrice,
      JSON.parse(ChoosingSeat),
      JSON.parse(MenuInfo)
    );
    console.log(resulttoadd);
    sessionStorage.removeItem("ChoosingSeat");
    sessionStorage.removeItem("RoomIDChoosing");
    sessionStorage.removeItem("DayChoosing");
    sessionStorage.removeItem("ShowTimeID");
    sessionStorage.removeItem("TimeChoosing");
    sessionStorage.removeItem("TheaterName");
    sessionStorage.removeItem("Pricing");
    sessionStorage.removeItem("MenuInfo");
    sessionStorage.removeItem("movieid");
    sessionStorage.removeItem("TotalPrice");
    sessionStorage.removeItem("PriceMenu");
    window.location.href = "/booking/success-payment";
  });
});
