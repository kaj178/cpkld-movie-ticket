import {
  updateShowTime,
  addShowTime,
  getAllShowtimeByMovieID,
  getAllShowTime,
} from "../../API/ShowTimeAPI.js";
import { getPremierMovie, getUpcomingMovie } from "../../API/MovieAPI.js";
import { getAllRooms } from "../../API/RoomAPI.js";
import { getAllFormats } from "../../API/FormatAPI.js";
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
$(document).ready(() => {
  // authenticate
  // let authFlag = true;
  // if (sessionStorage.getItem('Email')) {
  //   let email = XORDecrypt(sessionStorage.getItem('Email'));
  //   getUserByEmail("../../..", email).then(res => {
  //     if (res.role !== '2') authFlag = false;
  //   })
  // }
  // else authFlag = false;

  // if (!authFlag) {
  //   window.location.href = "../../Login_Modal/LoginModal.html";
  // }

  // $('.logout-container').click(() => {
  //   sessionStorage.removeItem('Email');
  //   window.location.href = "../../../";
  // })

  table.on("select", function (e, dt, type, indexes) {
    if (type === "row") {
      var data = table.rows(indexes).data();
      fillEditData(data[0][0]);
    }
  });
  $("#table-content_filter").hide();
  loadAllShowtime().then(() => showData());
  $(".item-choosing-block").click(function () {
    $(".item-choosing-block .divider-mini").remove();
    $(this).append("<div class=divider-mini></div>");
  });
  $(".all-showtime").click(() => loadAllShowtime().then(() => showData()));
  $("#btn-search").click(() => {
    let query = $(".input-place input").val().trim().toUpperCase();
    let MovieID = $("#select-movie").val();
    let RoomID = $("#select-room").val();
    let FormatID = $("#select-format").val();
    currentData = allData.filter(
      (element) =>
        element.ShowtimeID.search(query) != -1 &&
        (MovieID === "" || element.MovieID === MovieID) &&
        (RoomID === "" || element.RoomID === RoomID) &&
        (FormatID === "" || element.FormatID === FormatID)
    );
    $(".item-choosing-block .divider-mini").remove();
    $(".search-result").parent().append("<div class=divider-mini></div>");
    showData();
  });
  // Add form
  $("#btn-add").click(() => {
    let StartTime = $("#ModalAddUser .StartTime").val();
    let EndTime = $("#ModalAddUser .EndTime").val();
    let Price = $("#ModalAddUser .Price").val();
    let MovieID = $("#ModalAddUser .MovieID").val();
    let RoomID = $("#ModalAddUser .RoomID").val();
    let FormatID = $("#ModalAddUser .FormatName").val();
    addShowTime(
      "../../..",
      Price,
      StartTime,
      MovieID,
      EndTime,
      RoomID,
      FormatID
    ).then((res) => {
      if (res.success == false)
        $("#ModalAddUser .message")
          .text("Thêm thất bại")
          .removeClass("success");
      else
        $("#ModalAddUser .message").text("Thêm thành công").addClass("success");
      $(".all-showtime").trigger("click");
    });
  });
  // Edit form
  $("#btn-edit").click(() => {
    let showtimeID = $("#ModalEditUser .showtimeID").val();
    let StartTime = $("#ModalEditUser .StartTime").val();
    let EndTime = $("#ModalEditUser .EndTime").val();
    let Price = $("#ModalEditUser .Price").val();
    let MovieID = $("#ModalEditUser .MovieID").val();
    let RoomID = $("#ModalEditUser .RoomID").val();
    let FormatID = $("#ModalEditUser .FormatName").val();
    updateShowTime(
      "../../..",
      Price,
      StartTime,
      MovieID,
      EndTime,
      RoomID,
      FormatID,
      showtimeID
    ).then((res) => {
      if (res.success == false)
        $("#ModalEditUser .message")
          .text("Sửa thất bại")
          .removeClass("success");
      else {
        $("#ModalEditUser .message").text("Sửa thành công").addClass("success");
        $(".all-showtime").trigger("click");
      }
    });
  });
  loadAllMovie();
  loadAllRoom();
  loadAllFormat();
});
async function loadAllShowtime() {
  currentData = [];

  let page = 1;
  let data;
  do {
    data = await getAllShowTime("../../..", page);
    currentData.push(...data.ShowtimeList);
    page++;
  } while (data.ShowtimeList.length != 0);
  page = 1;
  allData = [...currentData];
}
async function loadAllRoom() {
  let data;
  data = await getAllRooms("../../..");
  data.list.forEach((element) => {
    $("#select-room").append(
      `<option value=${element.RoomID}>${element.RoomID}</option>`
    );
  });
}
async function loadAllFormat() {
  let data;
  data = await getAllFormats("../../..");
  data.formats.forEach((element) => {
    $("#select-format").append(
      `<option value=${element.FormatID}>${element.NameFormat}</option>`
    );
  });
}
async function loadAllMovie() {
  let page = 1;
  let data1, data2;
  do {
    data1 = await getUpcomingMovie("../../..", page);
    data1.forEach((element) => {
      $("#select-movie").append(
        `<option value=${element.MovieID}>${element.MovieID} - ${element.MovieName}</option>`
      );
    });
    page++;
  } while (data1.length != 0);
  page = 1;
  do {
    data2 = await getPremierMovie("../../..", page);
    data2.forEach((element) => {
      $("#select-movie").append(
        `<option value=${element.MovieID}>${element.MovieID} - ${element.MovieName}</option>`
      );
    });
    page++;
  } while (data2.length != 0);
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
        data[i].ShowtimeID,
        data[i].StartTime,
        data[i].EndTime,
        toVndCurrencyFormat(data[i].Price),
        data[i].MovieID,
        data[i].RoomID,
        data[i].FormatID,
      ])
      .draw();
  }
}
function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.ShowtimeID === id);
  editModal.find(".showtimeID").val(data.ShowtimeID);
  editModal.find(".StartTime").val(data.StartTime);
  editModal.find(".EndTime").val(data.EndTime);
  editModal.find(".Price").val(data.Price);
  editModal.find(".MovieID").val(data.MovieID);
  editModal.find(".RoomID").val(data.RoomID);
  editModal.find(".FormatName").val(data.FormatID);
  editModal.modal("show");
}
