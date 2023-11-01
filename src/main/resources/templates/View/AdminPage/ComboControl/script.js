import { getAllMenu, addMenu, updateMenu } from "../../API/MenuAPI.js";
import { getUserByEmail } from "../../API/UserAPI.js";
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
  let authFlag = true;
  if (sessionStorage.getItem("Email")) {
    let email = XORDecrypt(sessionStorage.getItem("Email"));
    getUserByEmail("../../..", email).then((res) => {
      if (res.role !== "2") authFlag = false;
    });
  } else authFlag = false;

  if (!authFlag) {
    window.location.href = "../../Login_Modal/LoginModal.html";
  }

  $(".logout-container").click(() => {
    sessionStorage.removeItem("Email");
    window.location.href = "../../../";
  });

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
  $(".all-combo").click(() => loadAllMenu().then(() => showData()));
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
  loadAllMenu().then(() => showData());
});
async function loadAllMenu() {
  currentData = [];
  let page = 1;
  let data;
  do {
    data = await getAllMenu("../../..", page);
    currentData.push(...data.menus);
    console.log(currentData);
    page++;
  } while (data.menus.length != 0);
  page = 1;
  allData = [...currentData];
}
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
        data[i].ItemID,
        data[i].Name,
        toVndCurrencyFormat(data[i].Price),
        data[i].ImageURL,
        data[i].status,
      ])
      .draw();
  }
}
function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.ItemID === id);
  console.log(data);
  editModal.find("#ItemID").val(data.ItemID);
  editModal.find("#Name").val(data.Name);
  editModal.find("#Price").val(data.Price);
  editModal.find("#image").val(data.ImageURL);
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
