import {
  getAllMovies,
  getHotMovieAPI,
  getPremiereMovies,
  getUpcomingMovies,
  addMovie,
  updateMovie,
} from "../../API/MovieAPI.js";
import { getAllGenres } from "../../API/GenreAPI.js";
import { getAllStudios } from "../../API/StudioAPI.js";
import { getAllLanguages } from "../../API/LanguageAPI.js";
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

  $(".hot-film").click(() => loadHotMovie().then(() => showData(currentData)));
  $(".premiere-film").click(() =>
    loadPremiereMovies().then(() => showData(currentData))
  );
  $(".upcoming-film").click(() =>
    loadUpcomingMovie().then(() => showData(currentData))
  );
  $(".all-film").click(() => loadAllMovies().then(() => showData(currentData)));
  $("#btn-search").click(() => {
    let query = $(".input-place input").val().trim().toUpperCase();
    let languageID = $("#select-language").val();
    let genreID = $("#select-genre").val();
    let studioID = $("#select-studio").val();
    currentData = allData.filter(
      (element) =>
        element.MovieID.search(query) != -1 &&
        (genreID === "" ||
          element.ListGenre.find((x) => x.GenreID === genreID)) &&
        (studioID === "" || element.StudioID === studioID) &&
        (languageID === "" || element.LanguageID === languageID)
    );
    $(".item-choosing-block .divider-mini").remove();
    $(".search-result").parent().append("<div class=divider-mini></div>");
    showData();
  });

  // Add form
  $("#btn-add").click(() => {
    let MovieName = $("#ModalAddUser #name").val().trim();
    let Time = $("#ModalAddUser #time").val().trim();
    let LanguageID = $("#ModalAddUser #language").val().trim();
    let StudioID = $("#ModalAddUser #studio").val();
    let posterFile = $("#ModalAddUser #poster").val();
    let imageFiles = $("#ModalAddUser #image").val();

    let Director = $("#ModalAddUser #director").val().trim();
    let listActor = $("#ModalAddUser #actor").val().trim();

    let age = $("#ModalAddUser #age").val();
    let listGenre = $("#ModalAddUser #genre").val();
    let story = $("#ModalAddUser #story").val().trim();
    let Year = $("#ModalAddUser #year").val().trim();
    let Premiere = $("#ModalAddUser #premiere").val();
    let URLTrailer = $("#ModalAddUser #trailer").val();
    if (!posterFile || !imageFiles) return;

    listActor = listActor.split(",").map((e) => e.trim());
    let listImage = [];
    $("#ModalAddUser #poster")[0]
      .files[0].convertToBase64()
      .then((res) => {
        listImage.push({ file: res.result, type: 1 });
      });
    $("#ModalAddUser #image")[0]
      .files.convertAllToBase64(/\.(png|jpeg|jpg|gif)$/i)
      .then(function (res) {
        listImage.push(
          ...res.map((e) => {
            return { file: e.result, type: 2 };
          })
        );
        console.log(listImage);
        addMovie(
          "../..",
          MovieName,
          Director,
          Year,
          Premiere,
          URLTrailer,
          Time,
          StudioID,
          LanguageID,
          story,
          age,
          listActor,
          listGenre,
          listImage
        ).then((res) => {
          if (res.success == false)
            $("#ModalAddUser .message")
              .text("Thêm thất bại")
              .removeClass("success");
          else
            $("#ModalAddUser .message")
              .text("Thêm thành công")
              .addClass("success");
        });
      });
  });

  // Edit form
  $("#btn-edit").click(() => {
    let MovieID = $("#ModalEditUser #id").val().trim();
    let MovieName = $("#ModalEditUser #name").val().trim();
    let Time = $("#ModalEditUser #time").val().trim();
    let LanguageID = $("#ModalEditUser #language").val().trim();
    let StudioID = $("#ModalEditUser #studio").val();
    let Director = $("#ModalEditUser #director").val().trim();
    let age = $("#ModalEditUser #age").val();
    let story = $("#ModalEditUser #story").val().trim();
    let Year = $("#ModalEditUser #year").val().trim();
    let Premiere = $("#ModalEditUser #premiere").val();
    let URLTrailer = $("#ModalEditUser #trailer").val();

    updateMovie(
      "../..",
      MovieID,
      MovieName,
      Director,
      Year,
      Premiere,
      URLTrailer,
      Time,
      StudioID,
      LanguageID,
      story,
      age
    ).then((res) => {
      if (res.success == false)
        $("#ModalEditUser .message")
          .text("Sửa thất bại")
          .removeClass("success");
      else {
        $("#ModalEditUser .message").text("Sửa thành công").addClass("success");
      }
    });
  });

  loadAllMovies().then(() => showData(currentData)); // page load
  loadAllGenre();
  loadAllStudio();
  loadAllLanguage();
});

function showData(currentData) {
  table.clear().draw();
  let data = currentData;
  console.log(currentData);
  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    let genreList = [];
    data[i].movieGenres.forEach((genre) => {
      genreList.push(genre);
    });
    table.row
      .add([
        data[i].movieId,
        data[i].name,
        data[i].studio.name,
        genreList.join(", "),
        data[i].premiere,
        data[i].time,
        data[i].language,
        data[i].director,
        data[i].rating,
        data[i].story,
      ])
      .draw();
  }
}

async function loadPremiereMovies() {
  currentData = [];
  let data;
  data = await getPremiereMovies("../..");
  for (let i = 0; i < data.data.length; i++) {
    currentData.push(data.data[i]);
  }
  // allData = [...currentData]
  console.log(currentData);
  return currentData;
}

async function loadHotMovie() {
  currentData = [];
  let data = await getHotMovieAPI("../..");
  for (let i = 0; i < data.data.length; i++) {
    currentData.push(data.data[i]);
  }
  return currentData;
}

async function loadUpcomingMovie() {
  currentData = [];
  let data = await getUpcomingMovies("../..");
  for (let i = 0; i < data.data.length; i++) {
    currentData.push(data.data[i]);
  }
  return currentData;
}

async function loadAllMovies() {
  currentData = [];
  let data;
  data = await getAllMovies("../..");
  console.log(data);

  for (let i = 0; i < data.data.length; i++) {
    currentData.push(data.data[i]);
  }
  // allData = [...currentData]
  return currentData;
}

async function loadAllGenre() {
  let page = 1;
  let genreData = [];
  let data;
  let options = [];
  data = await getAllGenres("../..", page);
  genreData.push(...data.data);
  genreData.forEach((element) => {
    $("#select-genre").append(
      `<option value=${element.id}>${element.name}</option>`
    );
    options.push({ id: element.id, name: element.name });
  });
  $(".modal #genre").selectize({
    valueField: "id",
    labelField: "name",
    options: options,
  });
}

async function loadAllStudio() {
  let data = await getAllStudios("../..");
  data.data.forEach((element) => {
    $("#select-studio").append(
      `<option value=${element.id}>${element.name}</option>`
    );
    $(".modal #studio").append(
      `<option value=${element.id}>${element.name}</option>`
    );
  });
}

async function loadAllLanguage() {
  let data = await getAllLanguages("../..");
  data.data.forEach((element) => {
    $("#select-language").append(
      `<option value=${element.id}>${element.name}</option>`
    );
    $(".modal #language").append(
      `<option value=${element.id}>${element.name}</option>`
    );
  });
}

function fillEditData(id) {
  let editModal = $("#ModalEditUser");
  let data = currentData.find((e) => e.movieId === id);
  editModal.find("#id").val(data.movieId);
  editModal.find("#name").val(data.name);
  editModal.find("#time").val(data.time);
  editModal.find("#language").val(data.language).change();
  editModal.find("#studio").val(data.studio).change();
  editModal.find("#director").val(data.director);
  editModal.find("#age").val(data.age);
  editModal.find("#trailer").val(data.urlTrailer);
  editModal.find("#year").val(data.year);
  editModal.find("#premiere").val(data.premiere);
  editModal.find("#story").val(data.story);
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

FileList.prototype.convertAllToBase64 = function (regexp) {
  // empty regexp if not set
  regexp = regexp || /.*/;
  //making array from FileList
  var filesArray = Array.prototype.slice.call(this);
  var base64PromisesArray = filesArray
    .filter(function (file) {
      return regexp.test(file.name);
    })
    .map(function (file) {
      return file.convertToBase64();
    });
  return Promise.all(base64PromisesArray);
};
