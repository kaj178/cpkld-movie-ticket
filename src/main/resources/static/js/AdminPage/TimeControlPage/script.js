import {
  updateShowTime,
  addShowTime,
  getAllShowtimeByMovieID,
  getAllShowTime,
} from "../../API/ShowTimeAPI.js";
import { getAllMovies } from "../../API/MovieAPI.js";
import { getAllRooms } from "../../API/RoomAPI.js";
import { getAllFormats } from "../../API/FormatAPI.js";
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
      "../..",
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
      "../..",
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
  Promise.all([loadAllRoom(), loadAllFormat(), loadAllMovie()]);
});
async function loadAllShowtime() {
  currentData = [];
  let page = 1;
  let data;
  data = await getAllShowTime("../..", page);
  currentData.push(...data.data);
  allData = [...currentData];
}
async function loadAllRoom() {
  let data;
  data = await getAllRooms("../..");
  data.data.forEach((element) => {
    $("#select-room").append(
      `<option value=${element.roomID}>${element.roomID}</option>`
    );
  });
}
async function loadAllFormat() {
  let data;
  data = await getAllFormats("../..");
  data.data.forEach((element) => {
    $("#select-format").append(
      `<option value=${element.formatId}>${element.formatName}</option>`
    );
  });
}
async function loadAllMovie() {
  const data = await getAllMovies("../..");
  data.data.forEach((element) => {
    $("#select-movie").append(
      `<option value=${element.movieId}>${element.movieId} - ${element.name}</option>`
    );
  });
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
  console.log(currentData);
  for (let i = 0; i < numRow; i++) {
    table.row
      .add([
        data[i].showTimeId,
        data[i].startTime,
        data[i].endTime,
        toVndCurrencyFormat(data[i].price),
        data[i].movie.movieId,
        data[i].roomId,
        data[i].formatId,
      ])
      .draw();
  }
}

function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.showTimeId === id);
  editModal.find(".showtimeID").val(data.showTimeId);
  editModal.find(".StartTime").val(data.startTime);
  editModal.find(".EndTime").val(data.endTime);
  editModal.find(".Price").val(data.price);
  editModal.find(".MovieID").val(data.movie.movieId);
  editModal.find(".RoomID").val(data.roomId);
  editModal.find(".FormatName").val(data.formatId);
  editModal.modal("show");
}
