import {
    getHotMovieAPI,
    getPremiereMovies,
    getUpcomingMovies,
    getPremiereMoviesByGenreID,
  } from "../API/MovieAPI.js";
  import { getAllGenres } from "../API/GenreAPI.js";
  import { XORDecrypt } from "../Util/EncryptXOR.js";
  import { getCustomerByEmail } from "../API/UserAPI.js";
  $(document).ready(function () {

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

    $(".navbar-toggler").click(function () {
      var color = $(".navbar").css("background-color");
      if (color == "rgba(0, 0, 0, 0.01)") {
        $(".navbar").css("background", "rgba(0, 0, 0, 0.85)");
      } else {
        $(".navbar").css("background", "rgba(0, 0, 0, 0.01)");
      }
    });

    // Gọi phim đang chiếu
    getPremiereMovies("../..").then(async (datas) => {
      const cuttingGenre = (data) => {
        let storehtml = "";

        data.flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element}`;
          } else {
            storehtml +=
              '<span class="vertical-line">|</span>' +
              `${element}`;
          }
        });
        return storehtml;
      };

      datas.data.forEach(async (data, index) => {
        let time = toHHMM(data.time);
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
                  ${time}
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
    // Gọi phim chuẩn bị chiếu
    getUpcomingMovies("../..").then(async (datas) => {
      const cuttingGenre = (data) => {
        let storehtml = "";

        data.flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element}`;
          } else {
            storehtml +=
              '<span class="vertical-line">|</span>' +
              `${element}`;
          }
        });
        return storehtml;
      };
      datas.data.forEach(async (data, index) => {
        let genrehtml = await cuttingGenre(data.movieGenres);
        let time = toHHMM(data.time);
        console.log(data);
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
                  ${time}
                </div>
                <button class="btn-outline">Chi tiết</button>
                <button class="btn-main">Đặt vé</button>
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

          data.flat().forEach((element, index) => {
            if (index == 0) {
              storehtml += `${element}`;
            } else {
              storehtml +=
                '<span class="vertical-line">|</span>' +
                `${element}`;
            }
          });
          return storehtml;
        };
        datas.data.forEach(async (data, index) => {
          let genrehtml = await cuttingGenre(data);
          let time = toHHMM(data.time);
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
                  ${time}
                </div>
                <a href="/detail" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.movieId}>Chi tiết</button>
                </a>
                <button class="btn-main">Đặt vé</button>
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
              storehtml +=
                '<span class="vertical-line">|</span>' +
                `${element}`;
            }
          });
          return storehtml;
        };
        datas.data.forEach(async (data, index) => {
          let genrehtml = await cuttingGenre(data.movieGenres);
          let timerender = toHHMM(data.time);
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
                <button class="btn-main">Đặt vé</button>
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
      const getPageNum = async () => {
        const datas = await getAllGenres("../..");
        return datas.data.length;
      };

      const updatePageNum = async () => {
        const newPageNum = await getPageNum();
        pagenum = newPageNum;
        return pagenum;
      };

      var pagenum = await updatePageNum();
    //   for (let page = 1; page <= pagenum; page++) {
        getAllGenres("../..").then((datas) => {
            const datastorender = datas.data;
            datastorender.forEach((data) => {
            const button = $("<button>")
                .addClass("genre-btn-click")
                .attr("id", data.id)
                .text(data.name);
            button.on("click", () => changingBtnOnClick(data.id));
            genreContainer.append(button);
            });
        });
    }

    takeAllGenre();
  });