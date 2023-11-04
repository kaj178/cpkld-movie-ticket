import {
  getAllCustomer,
  getAllManager,
  addManager,
  updateCustomer,
  updateManager,
} from "../../API/UserAPI.js";
import { getUserByEmail } from "../../API/UserAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";
import { RegisterAPI } from "../../API/LoginAPI.js";

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
  // let authFlag = true;
  // if (sessionStorage.getItem("Email")) {
  //   let email = XORDecrypt(sessionStorage.getItem("Email"));
  //   getUserByEmail("../../..", email).then((res) => {
  //     if (res.role !== "2") authFlag = false;
  //   });
  // } else authFlag = false;

  // if (!authFlag) {
  //   window.location.href = "/login";
  // }

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
  $(".all-user").click(() => loadAllUser().then(() => showData()));
  $(".customer").click(() => loadCustomer().then(() => showData()));
  $(".manager").click(() => loadManager().then(() => showData()));
  $("#btn-search").click(() => {
    let query = $(".input-place input").val().trim().toUpperCase();
    currentData = allData.filter((element) =>
      element.ManagerID
        ? element.ManagerID.search(query) != -1
        : element.CustomerID.search(query) != -1
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
    let phone = $("#ModalAddUser .phone").val();
    let type = $("#ModalAddUser .type").val();
    if (type === "1") {
      let address = $("#ModalAddUser .address").val();
      RegisterAPI("../../..", email, password, fullname, address, phone).then(
        (res) => {
          if (res.success == false) {
            $("#ModalAddUser .message")
              .text("Thêm thất bại")
              .removeClass("success");
          } else {
            $("#ModalAddUser .message")
              .text("Thêm thành công")
              .addClass("success");
            $(".all-user").trigger("click");
          }
        }
      );
    } else {
      addManager("../../..", email, password, fullname, phone).then((res) => {
        if (res.success == false)
          $("#ModalAddUser .message")
            .text("Thêm thất bại")
            .removeClass("success");
        else {
          $("#ModalAddUser .message")
            .text("Thêm thành công")
            .addClass("success");
          $(".all-user").trigger("click");
        }
      });
    }
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

  loadAllUser().then(() => showData()); // page load
});

function showData() {
  table.clear().draw();
  let data = currentData;

  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    table.row
      .add([
        data[i].ManagerID ? data[i].ManagerID : data[i].id,
        data[i].fullname,
        data[i].email,
        data[i].phone,
        data[i].password,
        data[i].address ? data[i].address : "",
        data[i].ManagerID ? "Quản lý" : "Khách hàng",
      ])
      .draw();
  }
}
async function loadAllUser() {
  currentData = [];
  let page = 1;
  let data;
  do {
    data = await getAllCustomer("../../..", page);
    // console.log(data)
    currentData.push(...data.data);
    page++;
  } while (data.data.length != 0);
  page = 1;

  do {
    data = await getAllManager("../../..", page);
    currentData.push(...data.data);
    page++;
  } while (data.data.length != 0);
  allData = [...currentData];
}

async function loadCustomer() {
  currentData = [];
  let page = 1;
  let data;
  do {
    data = await getAllCustomer("../../..", page);
    currentData.push(...data.data);
    page++;
  } while (data.data.length != 0);
  console.log(currentData);
}

async function loadManager() {
  currentData = [];
  let page = 1;
  let data;
  do {
    data = await getAllManager("../../..", page);
    currentData.push(...data.data);
    page++;
  } while (data.data.length != 0);
  console.log(currentData);
}

function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.ManagerID === id || e.CustomerID === id);
  editModal.find(".id").val(id);
  editModal.find(".type").val(data.ManagerID ? "2" : "1");
  editModal.find(".id").val(id);
  editModal.find(".fullName").val(data.FullName);
  editModal.find(".email").val(data.Email);
  editModal.find(".phone").val(data.Phone);
  editModal.find(".password").val(data.password);
  editModal
    .find(".address")
    .val(data.Address)
    .attr("disabled", data.ManagerID ? true : false);
  editModal.modal("show");
}
