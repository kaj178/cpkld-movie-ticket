import {
  getAllCustomer,
  getAllManager,
  addManager,
  updateCustomer,
  updateManager,
} from "../../API/UserAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";
import { RegisterAPI } from "../../API/LoginAPI.js";

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

  $(".all-user").click(() => loadAllUser().then(() => showData(currentData)));
  $(".customer").click(() => loadCustomer().then(() => showData(currentData)));
  $(".manager").click(() => loadManager().then(() => showData(currentData)));

  $("#btn-search").click(() => {
    let query = $(".input-place input").val().trim().toUpperCase();
    currentData = allData.filter((element) =>
      element.managerId
        ? element.managerId.search(query) != -1
        : element.customerId.search(query) != -1
    );
    $(".item-choosing-block .divider-mini").remove();
    $(".search-result").parent().append("<div class=divider-mini></div>");
    showData();
  });

  $("#ModalAddUser .type").change(() => {
    let type = $("#ModalAddUser .type").val();
    if (type === "1") $("#ModalAddUser .address").removeAttr("disabled");
    else {
      $("#ModalAddUser .address").val("");
      $("#ModalAddUser .address").attr("disabled", true);
    }
  });

  // Add form
  $("#btn-add").click(() => {
    let fullname = $("#ModalAddUser .fullName").val().trim();
    let email = $("#ModalAddUser .email").val().trim();
    let password = $("#ModalAddUser .password").val().trim();
    let address = $("#ModalAddUser .address").val();
    let phone = $("#ModalAddUser .phone").val();
    let type = $("#ModalAddUser .type").val();
    addManager("../../..", email, password, fullname, phone, address).then(
      (res) => {
        if (res.status !== 200)
          $("#ModalAddUser .message")
            .text("Thêm thất bại")
            .removeClass("success");
        else {
          $("#ModalAddUser .message")
            .text("Thêm thành công")
            .addClass("success");
          $(".all-user").trigger("click");
        }
      }
    );
  });

  // Edit form
  $("#btn-edit").click(() => {
    let fullname = $("#ModalEditUser .fullName").val().trim();
    let email = $("#ModalEditUser .email").val().trim();
    let password = $("#ModalEditUser .password").val().trim();
    let phone = $("#ModalEditUser .phone").val();
    let id = $("#ModalEditUser .id").val();
    let type = $("#ModalEditUser .type").val();

    if (type === "1") {
      let address = $("#ModalEditUser .address").val();
      updateCustomer(
        "../../..",
        id,
        email,
        password,
        fullname,
        address,
        phone
      ).then((res) => {
        if (res.success == false) {
          $("#ModalEditUser .message")
            .text("Sửa thất bại")
            .removeClass("success");
        } else {
          $("#ModalEditUser .message")
            .text("Sửa thành công")
            .addClass("success");
          $(".all-user").trigger("click");
        }
      });
    } else {
      updateManager("../../..", id, email, password, fullname, phone).then(
        (res) => {
          if (res.success == false)
            $("#ModalEditUser .message")
              .text("Sửa thất bại")
              .removeClass("success");
          else {
            $("#ModalEditUser .message")
              .text("Sửa thành công")
              .addClass("success");
            $(".all-user").trigger("click");
          }
        }
      );
    }
  });

  loadAllUser().then(() => showData(currentData)); // page load
});

function showData(currentData) {
  table.clear().draw();
  let data = currentData;
  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    table.row
      .add([
        data[i].managerId ? data[i].managerId : data[i].customerId,
        // i + 1,
        data[i].fullname,
        data[i].email,
        data[i].phone,
        data[i].password,
        data[i].address ? data[i].address : "",
        data[i].managerId ? "Quản lý" : "Khách hàng",
      ])
      .draw();
  }
}

async function loadAllUser() {
  currentData = [];
  let page = 1;
  let data;
  do {
    data = await getAllCustomer("../..", page);
    // console.log(data)
    currentData.push(...data.data);
    page++;
  } while (data.data.length == 0);
  page = 1;

  do {
    data = await getAllManager("../..", page);
    currentData.push(...data.data);
    page++;
  } while (data.data.length == 0);
  allData = [...currentData];
  console.log();
}

async function loadCustomer() {
  currentData = [];
  let page = 1;
  let data;
  data = await getAllCustomer("../..", page);
  for (let i = 0; i < data.data.length; i++) {
    currentData.push(data.data[i]);
  }
  console.log(currentData);
  return currentData;
}

async function loadManager() {
  currentData = [];
  let page = 1;
  let data;
  data = await getAllManager("../..", page);
  currentData.push(...data.data);
  console.log(currentData);
  return currentData;
}

function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.managerId === id || e.customerId === id);
  editModal.find(".id").val(id);
  editModal.find(".type").val(data.managerId ? "2" : "1");
  editModal.find(".id").val(id);
  editModal.find(".fullName").val(data.fullname);
  editModal.find(".email").val(data.email);
  editModal.find(".phone").val(data.phone);
  editModal.find(".password").val(data.password);
  editModal
    .find(".address")
    .val(data.address)
    .attr("disabled", data.managerId ? true : false);
  editModal.modal("show");
}
