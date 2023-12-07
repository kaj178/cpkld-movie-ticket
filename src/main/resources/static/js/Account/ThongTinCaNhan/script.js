import { getCustomerByEmail, changePassword } from "../../API/UserAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";
let accountID;
$(document).ready(function () {
  // if (sessionStorage.getItem("Email")) {
  //   let email = XORDecrypt(window.sessionStorage.getItem("Email"));
  //   getCustomerByEmail("../../..", email).then((res) => {
  //     let data = JSON.parse(res.user);
  //     let name = data.customer.FullName;
  //     accountID = data.customer.account_id;
  //     $("#create-accout").addClass("visually-hidden");
  //     $("#login-accout").addClass("visually-hidden");
  //     $("#user-account")
  //       .removeClass("visually-hidden")
  //       .find("a")
  //       .text(name);
  //     $("#signout").removeClass("visually-hidden");
  //   });
  // } else {
  //   window.location.href = "../../Login_Modal/LoginModal.html";
  // }

  $("#signout a").click(() => {
    sessionStorage.removeItem("Email");
    window.location.href = ".";
  });

  //   $("#btn-change-password").click(() => {
  //     let oldPass = $("#password-old").val();
  //     let newPass = $("#password-new").val();
  //     let newPassConfirm = $("#password-new-confirm").val();
  //     if (newPass !== newPassConfirm) {
  //       $(".message")
  //         .text("Mật khẩu xác nhận không khớp")
  //         .show(1)
  //         .delay(3000)
  //         .hide(1);
  //       return;
  //     }
  //     changePassword("../../..", accountID, newPass, oldPass).then((res) => {
  //       if (res.success)
  //         $(".message")
  //           .text("Đổi mật khẩu thành công")
  //           .addClass("text-success")
  //           .removeClass("text-danger");
  //       else
  //         $(".message")
  //           .text("Mật khẩu cũ không đúng")
  //           .addClass("text-danger")
  //           .removeClass("text-success");
  //     });
  //   });
  // });

  (async function TakeUserInformation() {
    const EmailUser = sessionStorage.getItem("email");
    let UserData = await getCustomerByEmail("../..", EmailUser);
    let UserDataRender = UserData.data[0];
    $(".name-display").text(UserDataRender.fullname);
    $(".email-display").text(EmailUser);
    $(".user-display").text(EmailUser);
    $(".phone-display").text(UserDataRender.phone);
  })();
});
