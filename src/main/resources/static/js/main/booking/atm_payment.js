import { XORDecrypt } from "../../Util/EncryptXOR.js";
import { getCustomerByEmail } from "../../API/UserAPI.js";

$(document).ready(function () {
  function checkEnterInfo() {
    if (
      $(".atm-info").css("display") == "block" &&
      $("#atm-number").val() != "" &&
      $("#user-name").val() != "" &&
      $("#year").val() != "" &&
      $("#flexCheckDefault").is(":checked")
    ) {
      return true;
    }
    return false;
  }
  $(".method").click(function () {
    let all_method = $(document).find(".method");
    for (let i = 0; i < all_method.length; i++) {
      if ($(this).find("p").text() != $(all_method[i]).find("p").text()) {
        $(all_method[i]).css("border", "1px solid white");
        $(all_method[i]).find("p").css("color", "white");
      }
    }
    if (
      $(this).css("border") == "0.8px solid rgb(255, 60, 172)" &&
      $(".atm-info").css("display") == "block"
    ) {
      $(this).css("border", "1px solid white");
      $(this).find("p").css("color", "white");
      $(".atm-info").css("display", "none");
    } else {
      $(this).css("border", "1px solid #ff3cac");
      $(this).find("p").css("color", "#ff3cac");
      $(".atm-info").css("display", "block");
    }
  });
  $("#bill-btn").click(function () {
    if (checkEnterInfo()) {
      $("#ModalPayment").modal("show");
      $("#check-bill").text("");
    } else {
      $("#check-bill").text("Vui lòng nhập đầy đủ phương thức thanh toán");
      $("#check-bill").css("color", "red");
    }
  });
});
