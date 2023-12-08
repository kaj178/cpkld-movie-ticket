import {
  getAllMovies,
  getHotMovieAPI,
  getPremiereMoviesByGenreID,
  getHotMovieAPIPaginated,
  getPremiereMovies,
  getUpcomingMovies,
} from "../API/MovieAPI.js";
import { getAllGenres } from "../API/GenreAPI.js";
// import { getPromotionsEvent } from "../PromotionAPI.js";
// import { getAllFormatsByMovieId } from "../FormatAPI.js";
// import { XORDecrypt } from "../EncryptXOR.js";
// import { getCustomerByEmail } from "../UserAPI.js";

$(document).ready(function () {
  async function loadAllMovies() {
    let currentData = [];
    let data;
    data = await getAllMovies("../..");
    for (let i = 0; i < data.data.length; i++) {
      currentData.push(data.data[i]);
    }
    // allData = [...currentData]
    console.log(currentData);
    return currentData;
  }

  $(".navbar-toggler").click(function () {
    var color = $(".navbar").css("background-color");
    if (color == "rgba(0, 0, 0, 0.01)") {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.85)");
    } else {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.01)");
    }
  });

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

  // Gọi phim Hot
  // getHotMovieAPI(".").then(async (datas) => {
  getHotMovieAPIPaginated("../..").then(async (datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";
      data["movieGenres"].flat().forEach((element) => {
        storehtml += `<span>${element}</span>`;
      });
      return storehtml;
    };
    // console.log(datas)
    let count = 0;
    for (const data of datas.data) {
      // console.log(typeof data.time)
      if (count < 3) {
        count++;
      } else {
        break;
      }
      let genrehtml = await cuttingGenre(data);
      let htmls = "";
      let time = toHHMM(data.time);
      // if (data.listImage[0]) {
      //   imageurl = data.listImage[0].ImagePath;
      // }
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
              <img src="../public/Star.svg" alt="" />
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
          <div class="movie-info">
            <span class="duration">${time}</span>
            <span class="dot">●</span>
            <span class="premier-date">${data.premiere}</span>
            <span class="dot">●</span>
            <span class="country"></span>
          </div>
          <div class="sypnosis">
           ${data.story}
          </div>
          <div class="button-container">
          
            <button class="btn-main btn-trailer" data-urlTrailer="${data.urlTrailer}" onclick="showTrailer(this)" >
              TRAILER
              <img src="/public/play.svg" alt="" />
            </button>
            <a href="/detail" style="text-decoration:None">
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
            style="background-image: url('../public/imagesfilms/poster-horizontal/${data.horizontalPoster}')"
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
  getPremiereMovies("../..").then((datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";
      data["movieGenres"].flat().forEach((element, index) => {
        if (index == 0) {
          storehtml += `${element}`;
        } else {
          storehtml += '<span class="vertical-line">|</span>' + `${element}`;
        }
      });
      return storehtml;
    };

    datas.data.forEach(async (data, index) => {
      const timetoadd = await toHHMM(data.time);
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
                <div class="rating">${data.rating} / 5</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="/detail" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <a href="/order" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
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

  getUpcomingMovies("./..").then((datas) => {
    const cuttingGenre = (data) => {
      let storehtml = "";

      data["movieGenres"].flat().forEach((element, index) => {
        if (index == 0) {
          storehtml += `${element}`;
        } else {
          storehtml += '<span class="vertical-line">|</span>' + `${element}`;
        }
      });
      return storehtml;
    };

    datas.data.forEach(async (data, index) => {
      let genrehtml = await cuttingGenre(data);
      let htmls = "";
      const timetoadd = await toHHMM(data.time);
      /// let imagebannerObj = data.listImage;
      // let filteredMovies = imagebannerObj.filter(function (movie) {
      //   return movie.type == "1" // && movie.ImagePath;
      // });
      // if (filteredMovies.length == 0) {
      //   return;
      // }

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
                <div class="rating">${data.rating} / 5</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="/detail" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <a href="/order" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
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

    getPremiereMovies("../..").then((datas) => {
      const cuttingGenre = (data) => {
        let storehtml = "";

        data["movieGenres"].flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element}`;
          } else {
            storehtml += '<span class="vertical-line">|</span>' + `${element}`;
          }
        });
        return storehtml;
      };
      datas.data.forEach(async (data, index) => {
        const timetoadd = await toHHMM(data.time);
        let genrehtml = await cuttingGenre(data);
        let htmls = "";
        // let imagebannerObj = data.listImage;
        // let filteredMovies = imagebannerObj.filter(function (movie) {
        //   return movie.type == "1" && movie.ImagePath;
        // });
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
                <div class="rating">${data.rating} / 5</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="/detail" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <a href="/order" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
                </button>
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
  async function changingBtnOnClick(GenreID) {
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

        data["movieGenres"].flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element}`;
          } else {
            storehtml += '<span class="vertical-line">|</span>' + `${element}`;
          }
        });
        return storehtml;
      };
      datas.data.forEach(async (data, index) => {
        const timetoadd = await toHHMM(data.time);
        let genrehtml = await cuttingGenre(data);
        let htmls = "";
        // let imagebannerObj = data.listImage;
        // let filteredMovies = imagebannerObj.filter(function (movie) {
        //   return movie.type == "1" && movie.ImagePath;
        // });
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
                <div class="rating">${data.rating} / 5</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="/public/date.svg" alt="" />
                  ${data.premiere}
                </div>
                <div class="duration">
                  <img src="/public/timer.svg" alt="" />
                  ${timetoadd}
                </div>
                <a href="/detail" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <a href="/order" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book m-auto" style="display:block">
                  ĐẶT VÉ
                </button>
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

  const takeAllGenre = async () => {
    const genreContainer = $(".row.g-2.genre-container");
    const button = $("<button>")
      .attr("id", "MBTN00001")
      .addClass("btn-main all-select")
      .text("Tất cả Phim");
    button.on("click", () => changingBtnOnClickGetAll("MBTN00001"));
    genreContainer.append(button);

    const setPageNum = async () => {
      const datas = await getAllGenres("../..");
      return datas.data.length;
    };

    const updatePageNum = async () => {
      const newPageNum = await setPageNum();
      pagenum = newPageNum;
      return pagenum;
    };

    //var pagenum = await updatePageNum();
    //for (let page = 1; page <= pagenum; page++) {
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
    //}
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

{
  /* <a href="${data.urlTrailer}" style="text-decoration: none;" target="_blank">
            <button class="btn-main btn-trailer">
              TRAILER
              <img src="../public/play.svg" alt="" />
            </button>
          </a> */
}
