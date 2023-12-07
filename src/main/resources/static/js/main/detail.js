import {
  getMovieByID,
  getPremiereMovies,
  getPremiereMoviesByGenreID,
} from "../API/MovieAPI.js";
import { getAllGenres } from "../API/GenreAPI.js";
import { XORDecrypt } from "../Util/EncryptXOR.js";
import { getCustomerByEmail } from "../API/UserAPI.js";

$(document).ready(function () {
  $(".navbar-toggler").click(function () {
    var color = $(".navbar").css("background-color");
    if (color == "rgba(0, 0, 0, 0.01)") {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.85)");
    } else {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.01)");
    }
  });
});

getPremiereMovies("../..").then((datas) => {
  const cuttingGenre = (data) => {
    let storehtml = "";

    data.flat().forEach((element, index) => {
      if (index == 0) {
        storehtml += `${element}`;
      } else {
        storehtml += '<span class="vertical-line">|</span>' + `${element}`;
      }
    });
    return storehtml;
  };
  datas.data.forEach(async (data, index) => {
    function toHHMM(time) {
      var timeParts = time.split(":");
      var hours = parseInt(timeParts[0], 10);
      var mins = parseInt(timeParts[1], 10);
      if (!isNaN(hours) && !isNaN(mins)) {
        return hours + "h" + " " + mins + "m";
      } else {
        return "Invalid time format";
      }
    }
    let timerender = toHHMM(data.time);
    let genrehtml = await cuttingGenre(data.movieGenres);
    let htmls = "";
    htmls +=
      `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="../public/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="../public/date.svg" alt="" />
                  ${data.premiere}
                </div>
                <div class="duration">
                  <img src="../public/timer.svg" alt="" />
                  ${timerender}
                </div>
                <a href="/detail" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <a href="/order" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book">
                  ĐẶT VÉ
                  <img src="../public/Arrow_right_long.svg" alt="" />
                </button>
                </a>
              </div>
              <img class="poster" src="../public/imagesfilms/poster-vertical/${data.verticalPoster}" alt="" />
            </div>

            <div class="movie-genre">
             ` +
      genrehtml +
      `</div>
            <div class="movie-title">${data.name}</div>
          </div>`;
    $(".row.g-3.movie-premier-container").append(htmls);
    $(".btn-outline").bind("click", (e) => {
      sessionStorage.setItem("MovieIDSelected", e.target.id);
    });
  });
});

// Fetch API tổng
(async function renderMovie() {
  const MovieIDTaking = sessionStorage.getItem("MovieIDSelected")
    ? sessionStorage.getItem("MovieIDSelected")
    : 1;
  let data = await getMovieByID("../..", MovieIDTaking);
  addCarouselInner(data);
  addIntoMovieInfo(data);
  addIntoCasourelIndicator(data);
  addPosterBody(data);
  addMovieContent(data);
})();
const takeAllGenre = async () => {
  const genreContainer = $(".row.g-2.genre-container");
  const button = $("<button>")
    .attr("id", "MBTN00001")
    .addClass("btn-main all-select")
    .text("Tất cả Phim");
  button.on("click", () => changingBtnOnClickGetAll("MBTN00001"));
  genreContainer.append(button);

  const getPageNum = async () => {
    const datas = await getAllGenres("../..");
    return datas.data.length;
  };

  const updatePageNum = async () => {
    const newPageNum = await getPageNum();
    pagenum = newPageNum;
    return pagenum;
  };

  getAllGenres("../..").then((datas) => {
    const datastorender = datas;
    datastorender.data.forEach((data) => {
      const button = $("<button>")
        .addClass("genre-btn-click")
        .attr("id", data.id)
        .text(data.name);
      button.on("click", () => changingBtnOnClick(data.id));
      genreContainer.append(button);
    });
  });
};
// Render Genre
(async function renderGenre() {
  takeAllGenre();
})();
// Modal trailer

// Thêm dữ liệu vào Carousel
async function addCarouselInner(data) {
  const datatoadd = data.data[0];
  let flag = 0;
  let htmls = ``;
  let indicatorHTML;
  let i = 0;
  // for (const element of datatoadd) {
  if (flag == 0) {
    flag = 1;
    htmls = `
        <div class="carousel-item active">
        <div class="background">
        <div
          class="movie-preview-img"
          style="
            background-image: url('../public/imagesfilms/poster-horizontal/${datatoadd.horizontalPoster}');
          "
        >
          <button
            class="play-btn"
            data-toggle="modal"
            data-target="#modal-trailer"
            data-trailerurl=${datatoadd.urlTrailer}
            type="button"
            onclick="showTrailer(this)"
          ></button>
        </div>
      </div></div>`;
    indicatorHTML = `
      <button
      type="button"
      data-bs-target="#movie-slide"
      data-bs-slide-to="${i}"
      class="active"
      aria-current="true"
      aria-label="Slide ${i + 1}"
    >
      <img
        class="thumbnail-img trailer-thumbnail"
        src="../public/imagesfilms/poster-horizontal/${
          datatoadd.horizontalPoster
        }"
        alt=""
      />
    </button>`;
  } else {
    htmls = `
      <div class="carousel-item">
        <div class="background">
          <div
            class="movie-preview-img"
            style="background-image: url('../public/imagesfilms/poster-horizontal/${datatoadd.horizontalPoster}')"
          ></div>
        </div>
      </div>`;
    indicatorHTML = `
      <button
      type="button"
      data-bs-target="#movie-slide"
      data-bs-slide-to="${i}"
      aria-label="Slide ${i + 11}"
    >
      <img class="thumbnail-img" src="../public/imagesfilms/poster-vertical/${
        datatoadd.verticalPoster
      }" alt="" />
    </button>`;
  }

  $(".carousel-inner").append(htmls);
  $(".carousel-indicators").append(indicatorHTML);
  i++;
}
// Thêm dữ liệu vào Movie
async function addIntoMovieInfo(data) {
  function toHHMM(time) {
    var timeParts = time.split(":");
    var hours = parseInt(timeParts[0], 10);
    var mins = parseInt(timeParts[1], 10);
    if (!isNaN(hours) && !isNaN(mins)) {
      return hours + "h" + " " + mins + "m";
    } else {
      return "Invalid time format";
    }
  }
  const datatoadd = data.data[0];
  let timerender = toHHMM(datatoadd.time);
  const htmls = `<div class="rating">
      <div class="rating-star">
        <img src="../public/Star.svg" alt="" />
       ${datatoadd.rating}
      </div>
      <div class="rating-imdb">IMDB 7.2</div>
    </div>
    <div class="movie-title">
      ${datatoadd.name}
    </div>
    <div class="movie-info">
      <span class="duration">${timerender}</span>
      <span class="dot">●</span>
      <span class="premier-date">${datatoadd.premiere}</span>
    </div>

    <div class="button-container">
      <a href="/order" style="text-decoration:None;display:block;width:100%">
        <button class="btn-main btn-book">
        ĐẶT VÉ
        <img src="../public/Arrow_right_long.svg" alt="" />
      </button></a>
    </div>`;
  $(".movie-content").append(htmls);
}
// Thêm dữ liệu vào Casourel-Indicator
async function addIntoCasourelIndicator(data) {
  const datatoadd = data.data[0];
  let flag = 0;
  let htmls = ``;
  // for (const element of datatoadd) {
  if (flag == 0) {
    flag = 1;
    htmls += `<button
      type="button"
      data-bs-target="#movie-slide"
      data-bs-slide-to="0"
      class="active"
      aria-current="true"
      aria-label="Slide 1"
    >
      <img
        class="thumbnail-img trailer-thumbnail"
        src="../public/imagesfilms/poster-vertical/${datatoadd.verticalPoster}"
        alt=""
      />
    </button>`;
  } else {
    htmls += `<button
      type="button"
      data-bs-target="#movie-slide"
      data-bs-slide-to="1"
      aria-label="Slide 2"
    >
      <img class="thumbnail-img" src="../public/imagesfilms/poster-vertical/${datatoadd.verticalPoster}" alt="" />
    </button>`;
  }
  $(".Casourel-Indicator").append(htmls);
}

//  Thêm dữ liệu Poster Body(about-poster)
async function addPosterBody(data) {
  console.log(data);
  const datatoadd = data.data[0];
  const posterContainer = datatoadd.verticalPoster;
  console.log(posterContainer);
  const htmls = `<img
               id="about-poster-img"
               src="../public/imagesfilms/poster-vertical/${posterContainer}"
             />`;
  $("#about-poster").append(htmls);
}

//  Thêm dữ liệu vào about-describe
async function addMovieContent(data) {
  const datatoadd = data.data[0];
  const cuttingGenre = (data) => {
    let storehtml = "";
    data.movieGenres.flat().forEach((element) => {
      storehtml += `<li>${element}</li>`;
    });
    return storehtml;
  };

  const htmls =
    `<div class="about-kind">
              <ul>
                ` +
    cuttingGenre(datatoadd) +
    `
              </ul>
            </div>
            <div class="about-line">
              <p>Mô tả</p>
              <p class="about-short">
                ${datatoadd.story}
              </p>
            </div>
            <div class="about-line">
              <p>Ngày khởi chiếu</p>
              <p>${datatoadd.premiere}</p>
            </div>
            <div class="about-line">
              <p>Đạo diễn</p>
              <p>${datatoadd.director}</p>
            </div>
            <div class="about-line">
              <p>Giới hạn độ tuổi</p>
              <p> ${datatoadd.age}</p>
            </div>`;
  $("#about-describe").append(htmls);
}
function changingBtnOnClickGetAll(GenreID) {
  const movieContainer = $(".row.g-3.movie-premier-container");
  const GenreContainer = $(".row.g-2.genre-container");
  movieContainer.html("");
  const removeBtnColor = GenreContainer.find("button.btn-main");
  removeBtnColor.removeClass("btn-main");
  const AddingBtnColor = GenreContainer.find(`#${GenreID}`);
  AddingBtnColor.addClass("btn-main");

  getPremiereMovies("../..").then((datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";

      data.movieGenres.flat().forEach((element, index) => {
        if (index == 0) {
          storehtml += `${element}`;
        } else {
          storehtml += '<span class="vertical-line">|</span>' + `${element}`;
        }
      });
      return storehtml;
    };
    datas.data.forEach(async (data, index) => {
      function toHHMM(time) {
        var timeParts = time.split(":");
        var hours = parseInt(timeParts[0], 10);
        var mins = parseInt(timeParts[1], 10);
        if (!isNaN(hours) && !isNaN(mins)) {
          return hours + "h" + " " + mins + "m";
        } else {
          return "Invalid time format";
        }
      }
      let timerender = toHHMM(data.time);
      let genrehtml = await cuttingGenre(data);
      let htmls = "";
      htmls +=
        `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="../public/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="../public/date.svg" alt="" />
                  ${data.premiere}
                </div>
                <div class="duration">
                  <img src="../public/timer.svg" alt="" />
                  ${timerender}
                </div>
                <a href="/detail style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <a href="/order" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book">
                  ĐẶT VÉ
                  <img src="../public/Arrow_right_long.svg" alt="" />
                </button>
                </a>
              </div>
              <img class="poster" src="../public/imagesfilms/poster-vertical/${data.verticalPoster}" alt="" />
            </div>

            <div class="movie-genre">
             ` +
        genrehtml +
        `</div>
            <div class="movie-title">${data.name}</div>
          </div>`;
      $(".row.g-3.movie-premier-container").append(htmls);
      $(".btn-outline").bind("click", (e) => {
        sessionStorage.setItem("MovieIDSelected", e.target.id);
      });
    });
  });
}
function changingBtnOnClick(GenreID) {
  const movieContainer = $(".row.g-3.movie-premier-container");
  const GenreContainer = $(".row.g-2.genre-container");
  movieContainer.html("");
  const removeBtnColor = GenreContainer.find("button.btn-main");
  removeBtnColor.removeClass("btn-main");
  const AddingBtnColor = GenreContainer.find(`#${GenreID}`);
  AddingBtnColor.addClass("btn-main");

  getPremiereMoviesByGenreID("../..", GenreID).then((datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";

      data.flat().forEach((element, index) => {
        if (index == 0) {
          storehtml += `${element}`;
        } else {
          storehtml += '<span class="vertical-line">|</span>' + `${element}`;
        }
      });
      return storehtml;
    };
    datas.data.forEach(async (data, index) => {
      function toHHMM(time) {
        var timeParts = time.split(":");
        var hours = parseInt(timeParts[0], 10);
        var mins = parseInt(timeParts[1], 10);
        if (!isNaN(hours) && !isNaN(mins)) {
          return hours + "h" + " " + mins + "m";
        } else {
          return "Invalid time format";
        }
      }
      let timerender = toHHMM(data.time);
      let genrehtml = await cuttingGenre(data.movieGenres);
      let htmls = "";
      htmls +=
        `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="../public/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="../public/date.svg" alt="" />
                  ${data.premiere}
                </div>
                <div class="duration">
                  <img src="../public/timer.svg" alt="" />
                  ${timerender}
                </div>
                <a href="/details" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <a href="/order" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book">
                  ĐẶT VÉ
                  <img src="../public/Arrow_right_long.svg" alt="" />
                </button>
                </a>
              </div>
              <img class="poster" src="../public/imagesfilms/poster-vertical/${data.verticalPoster}" alt="" />
            </div>

            <div class="movie-genre">
             ` +
        genrehtml +
        `</div>
            <div class="movie-title">${data.name}</div>
          </div>`;
      $(".row.g-3.movie-premier-container").append(htmls);
      $(".btn-outline").bind("click", (e) => {
        sessionStorage.setItem("MovieIDSelected", e.target.id);
      });
    });
  });
}
let modalTrailer = $("#modal-trailer");

$(".play-btn").click(function () {
  modalTrailer.modal("show");
});

$("#modal-trailer .close").click(function () {
  modalTrailer.modal("hide");
});

let iframe = $("#modal-trailer iframe");

modalTrailer.on("hide.bs.modal", function (e) {
  iframe.attr("src", "");
});
