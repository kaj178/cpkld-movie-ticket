import {
  getAllMovies,
  getHotMovieAPI,
  getPremierMovie,
  getUpcomingMovie,
  getByGenreID,
} from "../../API/MovieAPI.js";
import { getAllGenre } from "../../API/GenreAPI.js";
import { getPromotionsEvent } from "../../PromotionAPI.js";
import { getAllFormatsByMovieId } from "../../FormatAPI.js";
import { XORDecrypt } from "../../EncryptXOR.js";
import { getCustomerByEmail } from "../../UserAPI.js";

$(document).ready(function () {

  async function loadAllMovies() {
    currentData = []
    let data;
    data = await getAllMovies("../..")
    for (let i = 0; i < data.data.length; i++) {
      currentData.push(data.data[i])
    }
    // allData = [...currentData]
    console.log(currentData)
    return currentData
  }

  $(".navbar-toggler").click(function () {
    var color = $(".navbar").css("background-color");
    if (color == "rgba(0, 0, 0, 0.01)") {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.85)");
    } else {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.01)");
    }
  });

  function toHHMM(minutes) {
    var hours = Math.floor(minutes / 60);
    var mins = minutes % 60;
    return hours + "h" + " " + mins + "m";
  }

  // Gọi phim Hot
  // getHotMovieAPI(".").then(async (datas) => {
  loadAllMovies().then(async (datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";
      data["movieGenres"].flat().forEach((element) => {
        storehtml += `<span>${element}</span>`;
      });
      return storehtml;
    };

    // const cuttingActor = (data) => {
    //   let storehtml = "";
    //   data["ListActor"].flat().forEach((element) => {
    //     storehtml += `<span>${element.NameActor}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>`;
    //   });
    //   return storehtml;
    // };
    
    let count = 0;
    for (const data of datas) {
      if (count < 3) {
        count++;
      } else {
        break;
      }
      let genrehtml = await cuttingGenre(data);
      // let actorhtml = await cuttingActor(data);
      const timetoadd = await toHHMM(data.Time);
      let htmls = "";
      let imageurl = "";
      if (data.listImage[0]) {
        imageurl = data.listImage[0].ImagePath;
      }
      if (count - 1 == 0) {
        htmls = `<div id=${data.movieId} class="carousel-item active">`;
      } else {
        htmls = `<div id=${data.movieId} class="carousel-item">`;
      }
      htmls +=
        `
        <div class="movie-content">
          <div class="rating">
            <div class="rating-star">
              <img src="/public/Star.svg" alt="" />
              ${data.rating}
            </div>
            <div class="rating-imdb">IMDB 7.2</div>
          </div>
          <div class="genre-list">` +
        genrehtml +
        `</div>
          <div class="movie-title">
            ${data.name}
          </div>
          <div class="actor-list">
            ` +
        `
          </div>
          <div class="movie-info">
            <span class="duration">${timetoadd}</span>
            <span class="dot">●</span>
            <span class="premier-date">${data.premiere}</span>
            <span class="dot">●</span>
            <span class="country">${data.studio}</span>
          </div>
          <div class="sypnosis">
           ${data.story}
          </div>
          <div class="button-container">
            <button class="btn-main btn-trailer" data-trailerurl=${data.urlTrailer} onclick="showTrailer(this)" >
              TRAILER
              <img src="/public/play.svg" alt="" />
            </button>
            <a href="./View/Detail/index.html" style="text-decoration:None">
            <button class="btn-main btn-book">
              Chi tiết
              <img src="/public/Arrow_right_long.svg" alt="" />
            </button>
          </a>
          </div>
        </div>
        <div class="background">
          <div class="shadow-box"></div>
          <div
            class="movie-preview-img"
            style="background-image: url('${imageurl}')"
          ></div>
        </div>
      </div>`;
      await $(".carousel-inner").append(htmls);
      $(".btn-main.btn-book").on("click", () => {
        const dataIdNeededToChoose = $(".carousel-item.active").attr("id");
        sessionStorage.setItem("MovieIDSelected", dataIdNeededToChoose);
      });
    }
  });

  // Gọi phim đang chiếu
  getPremierMovie(".").then((datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";

      data["ListGenre"].flat().forEach((element, index) => {
        if (index == 0) {
          storehtml += `${element.GenreName}`;
        } else {
          storehtml +=
            '<span class="vertical-line">|</span>' + `${element.GenreName}`;
        }
      });
      return storehtml;
    };
    datas.forEach(async (data, index) => {
      let imagebannerObj = data.listImage;
      let filteredMovies = imagebannerObj.filter(function (movie) {
        return movie.type == "1" && movie.ImagePath;
      });
      const timetoadd = await toHHMM(data.Time);
      let genrehtml = await cuttingGenre(data);
      let htmls = "";
      htmls +=
        `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="/public/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.Premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="./View/Detail/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.MovieID}>Chi tiết</button>
                </a>
                <a href="./View/Order/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
                </button>
              </a>

              </div>
              <img class="poster" src="${filteredMovies[0].ImagePath}" alt="" />
            </div>

            <div class="movie-genre">
             ` +
        genrehtml +
        `</div>
            <div class="movie-title">${data.MovieName}</div>
          </div>`;
      $(".row.g-3.movie-premier-container").append(htmls);
      $(".btn-outline").bind("click", (e) => {
        sessionStorage.setItem("MovieIDSelected", e.target.id);
      });
    });
  });
  // getPromotionsEvent(".").then((res) => {
  //   let data = res.list;
  //   console.log((data));
  //   data.forEach(element => {
  //     let html = `           <div class="movie-card col-xl-4 col-lg-4 col-md-4 col-sm-6 col-6">
  //       <div class="card-img">
  //         <img class="poster" src="./${element.url_image}" alt="" />
  //       </div>

  //       <div class="event-title">
  //         ${element.Description}
  //       </div>
  //     </div>`
  //     $('.event').find('.row').append(html);
  //   });
  // })
  // Gọi phim chuẩn bị chiếu
  getUpcomingMovie(".").then((datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";

      data["ListGenre"].flat().forEach((element, index) => {
        if (index == 0) {
          storehtml += `${element.GenreName}`;
        } else {
          storehtml +=
            '<span class="vertical-line">|</span>' + `${element.GenreName}`;
        }
      });
      return storehtml;
    };

    datas.forEach(async (data, index) => {
      let genrehtml = await cuttingGenre(data);
      let htmls = "";
      const timetoadd = await toHHMM(data.Time);
      let imagebannerObj = data.listImage;
      let filteredMovies = imagebannerObj.filter(function (movie) {
        return movie.type == "1" && movie.ImagePath;
      });
      if (filteredMovies.length == 0) {
        return;
      }

      htmls +=
        `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="/public/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.Premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="./View/Detail/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.MovieID}>Chi tiết</button>
                </a>
                <a href="./View/Order/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
                </button>
                </a>
              </div>
              <img class="poster" src="${filteredMovies[0].ImagePath}" alt="" />
            </div>

            <div class="movie-genre">
             ` +
        genrehtml +
        `</div>
            <div class="movie-title">${data.MovieName}</div>
          </div>`;
      $(".row.g-3.movie-upcoming-container").append(htmls);
    });
  });

  // Lấy toàn bộ Genre
  async function changingBtnOnClickGetAll(GenreID) {
    const movieContainer = $(".row.g-3.movie-premier-container");
    const GenreContainer = $(".row.g-2.genre-container");
    movieContainer.html("");
    const removeBtnColor = GenreContainer.find("button.btn-main");
    removeBtnColor.removeClass("btn-main");
    const AddingBtnColor = GenreContainer.find(`#${GenreID}`);
    AddingBtnColor.addClass("btn-main");

    getPremierMovie(".").then((datas) => {
      const cuttingGenre = (data) => {
        let storehtml = "";

        data["ListGenre"].flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element.GenreName}`;
          } else {
            storehtml +=
              '<span class="vertical-line">|</span>' + `${element.GenreName}`;
          }
        });
        return storehtml;
      };
      datas.forEach(async (data, index) => {
        const timetoadd = await toHHMM(data.Time);
        let genrehtml = await cuttingGenre(data);
        let htmls = "";
        let imagebannerObj = data.listImage;
        let filteredMovies = imagebannerObj.filter(function (movie) {
          return movie.type == "1" && movie.ImagePath;
        });
        htmls +=
          `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="/public/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.Premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="./View/Detail/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.MovieID}>Chi tiết</button>
                </a>
                <a href="./View/Order/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
                </button>
              </div>
              <img class="poster" src="${filteredMovies[0].ImagePath}" alt="" />
            </div>

            <div class="movie-genre">
             ` +
          genrehtml +
          `</div>
            <div class="movie-title">${data.MovieName}</div>
          </div>`;
        $(".row.g-3.movie-premier-container").append(htmls);
        $(".btn-outline").bind("click", (e) => {
          sessionStorage.setItem("MovieIDSelected", e.target.id);
        });
      });
    });
  }
  async function changingBtnOnClick(GenreID) {
    const movieContainer = $(".row.g-3.movie-premier-container");
    const GenreContainer = $(".row.g-2.genre-container");
    movieContainer.html("");
    const removeBtnColor = GenreContainer.find("button.btn-main");
    removeBtnColor.removeClass("btn-main");
    const AddingBtnColor = GenreContainer.find(`#${GenreID}`);
    AddingBtnColor.addClass("btn-main");
    getByGenreID(".", GenreID).then((datas) => {
      const cuttingGenre = (data) => {
        let storehtml = "";

        data["ListGenre"].flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element.GenreName}`;
          } else {
            storehtml +=
              '<span class="vertical-line">|</span>' + `${element.GenreName}`;
          }
        });
        return storehtml;
      };
      datas.forEach(async (data, index) => {
        const timetoadd = await toHHMM(data.Time);
        let genrehtml = await cuttingGenre(data);
        let htmls = "";
        let imagebannerObj = data.listImage;
        let filteredMovies = imagebannerObj.filter(function (movie) {
          return movie.type == "1" && movie.ImagePath;
        });
        htmls +=
          `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="/public/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.Premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="./View/Detail/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.MovieID}>Chi tiết</button>
                </a>
                <a href="./View/Order/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
                </button>
              </div>
              <img class="poster" src="${filteredMovies[0].ImagePath}" alt="" />
            </div>

            <div class="movie-genre">
             ` +
          genrehtml +
          `</div>
            <div class="movie-title">${data.MovieName}</div>
          </div>`;
        $(".row.g-3.movie-premier-container").append(htmls);
        $(".btn-outline").bind("click", (e) => {
          sessionStorage.setItem("MovieIDSelected", e.target.id);
        });
      });
    });
  }
  const takeAllGenre = async () => {
    const genreContainer = $(".row.g-2.genre-container");
    const button = $("<button>")
      .attr("id", "MBTN00001")
      .addClass("btn-main all-select")
      .text("Tất cả Phim");
    button.on("click", () => changingBtnOnClickGetAll("MBTN00001"));
    genreContainer.append(button);
    const setPageNum = async () => {
      const datas = await getAllGenre(".", 1);
      return datas["pagination"]["num_pages"];
    };

    const updatePageNum = async () => {
      const newPageNum = await setPageNum();
      pagenum = newPageNum;
      return pagenum;
    };

    var pagenum = await updatePageNum();
    for (let page = 1; page <= pagenum; page++) {
      getAllGenre(".", page).then((datas) => {
        const datastorender = datas["genres"];
        datastorender.forEach((data) => {
          const button = $("<button>")
            .addClass("genre-btn-click")
            .attr("id", data.GenreID)
            .text(data.GenreName);
          button.on("click", () => changingBtnOnClick(data.GenreID));
          genreContainer.append(button);
        });
      });
    }
  };
  takeAllGenre();
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
});