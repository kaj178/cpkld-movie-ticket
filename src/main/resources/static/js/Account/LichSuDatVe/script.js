import { XORDecrypt } from "../../Util/EncryptXOR.js";
import { getCustomerByEmail } from "../../API/UserAPI.js";
import { getAllBookingsByCustomerID } from "../../API/BookingAPI.js";

let currentData = [];
let table = $("#table-content").DataTable({
  searching: false,
  language: {
    lengthMenu: "Số kết quả / Trang _MENU_",
    zeroRecords: "Không tìm thấy dữ liệu",
    info: "Hiển thị trang _PAGE_ trên _PAGES_",
    infoEmpty: "Không tìm thấy dữ liệu",
    infoFiltered: "(filtered from _MAX_ total records)",
    paginate: {
      first: "Trang đầu",
      last: "Trang cuối",
      next: "Trang sau",
      previous: "Trang trước",
    },
  },
});
$("#table-content_filter").hide();

let id;

$(document).ready(function () {
  // if (sessionStorage.getItem("Email")) {
  //   let email = XORDecrypt(window.sessionStorage.getItem("Email"));
  //   getCustomerByEmail("../../..", email).then((res) => {
  //     let data = JSON.parse(res.user);
  //     id = data.customer.CustomerID;
  //     let name = data.customer.FullName;
  //     $("#create-accout").addClass("visually-hidden");
  //     $("#login-accout").addClass("visually-hidden");
  //     $("#user-account").removeClass("visually-hidden").find("a").text(name);
  //     $("#signout").removeClass("visually-hidden");
  //     loadAllBooking(id).then(() => showData());
  //   });
  // } else {
  //   window.location.href = "../../Login_Modal/LoginModal.html";
  // }

  $("#signout a").click(() => {
    sessionStorage.removeItem("Email");
    window.location.href = ".";
  });
});
function toVndCurrencyFormat(number) {
  const currencyFormat = new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    minimumFractionDigits: 0,
  });

  return currencyFormat.format(number);
}

function showData() {
  table.clear().draw();
  let data = currentData;

  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    table.row
      .add([
        data[i].booking.BookingID,
        data[i].booking.NumberOfTickets,
        toVndCurrencyFormat(data[i].booking.TotalPrice),
        data[i].booking.BookingTime,
        data[i].booking.Voucher,
        data[i].booking.status == 1 ? "Đã thanh toán" : "Chưa thanh toán",
      ])
      .draw();
  }
}
async function loadAllBooking(id) {
  currentData = [];
  let page = 1;
  let data;
  do {
    data = await getAllBookingsByCustomerID("../../..", page, id);
    console.log(data);
    currentData.push(...data);
    page++;
  } while (data.length != 0);
}
