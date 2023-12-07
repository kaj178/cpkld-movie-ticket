import { getAllMenu, addMenu, updateMenu } from "../../API/MenuAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";

let allData = [];
let currentData = [];

let table = $("#table-content").DataTable({
  pagelength: 5,
  lengthMenu: [5, 10],
  select: {
    style: "single",
    info: false,
  },
  searching: true,
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

  $(".all-combo").click(() => loadAllMenu().then(() => showData(currentData)));

  $("#btn-search").click(() => {
    let query = $(".input-place input").val().trim().toUpperCase();
    currentData = allData.filter(
      (element) => element.ItemID.search(query) != -1
    );
    $(".item-choosing-block .divider-mini").remove();
    $(".search-result").parent().append("<div class=divider-mini></div>");
    showData();
  });

  $("#btn-add").click(() => {
    let Name = $("#ModalAddUser #Name").val().trim();
    let Price = $("#ModalAddUser #Price").val().trim();
    let status = $("#ModalAddUser #status").val();
    let imageFiles = $("#ModalAddUser #image")[0].files[0];
    if (!imageFiles) return;
    $("#ModalAddUser #image")[0]
      .files[0].convertToBase64()
      .then((res) => {
        addMenu("../../..", Name, res.result, Price, status).then((res) => {
          if (res.success == false)
            $("#ModalAddUser .message")
              .text("Thêm thất bại")
              .removeClass("success");
          else
            $("#ModalAddUser .message")
              .text("Thêm thành công")
              .addClass("success");
          $(".all-combo").trigger("click");
        });
      });
  });

  $("#btn-edit").click(() => {
    let ItemID = $("#ModalEditUser #ItemID").val().trim();
    let Name = $("#ModalEditUser #Name").val().trim();
    let Price = $("#ModalEditUser #Price").val().trim();
    let status = $("#ModalEditUser #status").val();
    let ImageURL = $("#ModalEditUser #image").val().trim();
    updateMenu("../../..", Name, ImageURL, Price, status, ItemID).then(
      (res) => {
        if (res.success == false)
          $("#ModalEditUser .message")
            .text("Sửa thất bại")
            .removeClass("success");
        else
          $("#ModalEditUser .message")
            .text("Sửa thành công")
            .addClass("success");
        $(".all-combo").trigger("click");
      }
    );
  });
  loadAllMenu().then(() => showData(currentData));
});

async function loadAllMenu() {
  currentData = [];
  let data;
  data = await getAllMenu("../..");
  for (let i = 0; i < data.data.length; i++) {
    currentData.push(data.data[i]);
  }
  console.log(currentData);
  return currentData;
}

function toVndCurrencyFormat(number) {
  const currencyFormat = new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    minimumFractionDigits: 0,
  });

  return currencyFormat.format(number);
}

function showData(currentData) {
  table.clear().draw();
  let data = currentData;
  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    table.row
      .add([
        data[i].itemId,
        data[i].name,
        data[i].price,
        "http://localhost:8080/public/img/menu/" + data[i].imgUrl,
        data[i].status,
      ])
      .draw();
  }
}

function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.itemId === id);
  console.log(data);
  editModal.find("#ItemID").val(data.itemId);
  editModal.find("#Name").val(data.name);
  editModal.find("#Price").val(data.price);
  editModal
    .find("#image")
    .val("http://localhost:8080/public/img/menu/" + data.imgUrl);
  editModal.find("#status").val(data.status);
  editModal.modal("show");
}

File.prototype.convertToBase64 = function () {
  return new Promise(
    function (resolve, reject) {
      var reader = new FileReader();
      reader.onloadend = function (e) {
        resolve({
          fileName: this.name,
          result: e.target.result,
          error: e.target.error,
        });
      };
      reader.readAsDataURL(this);
    }.bind(this)
  );
};
