import { getAllBooking, changeStatus } from "../../API/BookingAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";
let allData = [];
let currentData = [];
let table = $("#table-content").DataTable({
  select: {
    style: "single",
    info: false,
  },
  searching: false,
  language: {
    lengthMenu: "Số kết quả / Trang _MENU_",
    zeroRecords: "Không tìm thấy dữ liệu",
    info: "Hiển thị trang _PAGE_ trên _PAGES_",
    infoEmpty: "Đang tìm kiếm dữ liệu",
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

$(document).ready(() => {
  table.on("select", function (e, dt, type, indexes) {
    if (type === "row") {
      var data = table.rows(indexes).data();
      fillEditData(data[0][0]);
    }
  });
  $(".item-choosing-block").click(function () {
    $(".item-choosing-block .divider-mini").remove();
    $(this).append("<div class=divider-mini></div>");
  });
  $(".all-ticket").click(() => loadAllBooking().then(() => showData()));
  $("#btn-search").click(() => {
    let query = $(".input-place input").val().trim().toUpperCase();
    currentData = allData.filter(
      (element) => element.BookingID.search(query) != -1
    );
    $(".item-choosing-block .divider-mini").remove();
    $(".search-result").parent().append("<div class=divider-mini></div>");
    showData();
  });

  // Edit form
  $("#btn-edit").click(() => {
    let id = $("#ModalEditUser #id").val();
    let status = $("#ModalEditUser #status").val();

    changeStatus("../../", id, status).then((res) => {
      if (res.success == false) {
        $("#ModalEditUser .message")
          .text("Sửa thất bại")
          .removeClass("success");
      } else {
        $("#ModalEditUser .message").text("Sửa thành công").addClass("success");
        $(".all-ticket").trigger("click");
      }
    });
  });

  loadAllBooking().then(() => showData()); // page load
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
  for (let i = 0; i < numRow && i < 1000; i++) {
    table.row
      .add([
        data[i].bookingId,
        data[i].amountItem,
        toVndCurrencyFormat(data[i].totalPrice),
        data[i].startTime,
        data[i].promotionName,
        data[i].customerId,
        data[i].status == 1 ? "Đã thanh toán" : "Chưa thanh toán",
      ])
      .draw();
  }
}

async function loadAllBooking() {
  currentData = [];
  let page = 1;
  let data;
  data = await getAllBooking("../..");
  currentData.push(...data.data);
  allData = [...currentData];
}

function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.bookingId === id);
  editModal.find("#id").val(id);
  editModal.find("#number").val(data.amountItem);
  editModal.find("#total").val(data.totalPrice);
  editModal.find("#time").val(data.startTime);
  editModal.find("#voucher").val(data.promotionName);
  editModal.find("#customerID").val(data.customerId);
  editModal.find("#status").val(data.status);
  editModal.modal("show");
}
