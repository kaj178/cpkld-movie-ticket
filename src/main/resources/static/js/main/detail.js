import {
    getMovieByID,
    getPremierMovie,
    getByGenreID,
  } from "../API/MovieAPI.js";
  import { getAllGenre } from "../API/GenreAPI.js";
  import { XORDecrypt } from "../Util/EncryptXOR.js";
  import { getCustomerByEmail } from "../API/UserAPI.js";
  $(document).ready(function () {
    // Navbar for logged in
    if (sessionStorage.getItem("Email")) {
      let email = XORDecrypt(window.sessionStorage.getItem("Email"));
      getCustomerByEmail("../..", email).then((res) => {
        let data = JSON.parse(res.user);
        let name = data.customer.FullName;
        $("#create-accout").addClass("visually-hidden");
        $("#login-accout").addClass("visually-hidden");
        $("#user-account")
          .removeClass("visually-hidden")
          .find("a")
          .text(name);
        $("#signout").removeClass("visually-hidden");
      });
    }

    $("#signout a").click(() => {
      sessionStorage.removeItem("Email");
      window.location.href = ".";
    });

    $(".navbar-toggler").click(function () {
      var color = $(".navbar").css("background-color");
      if (color == "rgba(0, 0, 0, 0.01)") {
        $(".navbar").css("background", "rgba(0, 0, 0, 0.85)");
      } else {
        $(".navbar").css("background", "rgba(0, 0, 0, 0.01)");
      }
    });
  });
  getPremierMovie("../../.").then((datas) => {
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
      function toHHMM(minutes) {
        var hours = Math.floor(minutes / 60);
        var mins = minutes % 60;
        return hours + "h" + " " + mins + "m";
      }
      let timerender = await toHHMM(data.Time);
      let genrehtml = await cuttingGenre(data);
      let htmls = "";
      htmls +=
        `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="../../images/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="../../images/date.svg" alt="" />
                  ${data.Premiere}
                </div>
                <div class="duration">
                  <img src="../../images/timer.svg" alt="" />
                  ${timerender}
                </div>
                <a href="./index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.MovieID}>Chi tiết</button>
                </a>
                <a href="../Order/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book">
                  ĐẶT VÉ
                  <img src="../../images/Arrow_right_long.svg" alt="" />
                </button>
                </a>
              </div>
              <img class="poster" src="../../${imagebannerObj[0].ImagePath}" alt="" />
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
  // Fetch API tổng
  (async function renderMovie() {
    const MovieIDTaking = sessionStorage.getItem("MovieIDSelected")
      ? sessionStorage.getItem("MovieIDSelected")
      : "M0001";
    let data = await getMovieByID("../../.", MovieIDTaking);
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
    const setPageNum = async () => {
      const datas = await getAllGenre("../../.", 1);
      return datas["pagination"]["num_pages"];
    };

    const updatePageNum = async () => {
      const newPageNum = await setPageNum();
      pagenum = newPageNum;
      return pagenum;
    };

    var pagenum = await updatePageNum();
    for (let page = 1; page <= pagenum; page++) {
      getAllGenre("../../.", page).then((datas) => {
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
  // Render Genre
  (async function renderGenre() {
    takeAllGenre();
  })();
  // Modal trailer

  // Thêm dữ liệu vào Carousel
  async function addCarouselInner(data) {
    const datatoadd = data["movie"];
    let flag = 0;
    let htmls = ``;
    let indicatorHTML;
    let i = 0;
    for (const element of datatoadd["listImage"]) {
      if (flag == 0) {
        flag = 1;
        htmls = `
        <div class="carousel-item active">
        <div class="background">
        <div
          class="movie-preview-img"
          style="
            background-image: url('../../${element.ImagePath}');
          "
        >
          <button
            class="play-btn"
            data-toggle="modal"
            data-target="#modal-trailer"
            data-trailerurl=${data.movie.URLTrailer}
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
        src="../../${element.ImagePath}"
        alt=""
      />
    </button>`;
      } else {
        htmls = `
      <div class="carousel-item">
        <div class="background">
          <div
            class="movie-preview-img"
            style="background-image: url('../../${element.ImagePath}')"
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
      <img class="thumbnail-img" src="../../${element.ImagePath}" alt="" />
    </button>`;
      }

      $(".carousel-inner").append(htmls);
      $(".carousel-indicators").append(indicatorHTML);
      i++;
    }
  }
  // Thêm dữ liệu vào Movie
  async function addIntoMovieInfo(data) {
    function toHHMM(minutes) {
      var hours = Math.floor(minutes / 60);
      var mins = minutes % 60;
      return hours + "h" + " " + mins + "m";
    }
    const datatoadd = data["movie"];
    let timerender = await toHHMM(datatoadd.Time);
    const htmls = `<div class="rating">
      <div class="rating-star">
        <img src="../../images/Star.svg" alt="" />
       ${datatoadd.rating}
      </div>
      <div class="rating-imdb">IMDB 7.2</div>
    </div>
    <div class="movie-title">
      ${datatoadd.MovieName}
    </div>
    <div class="movie-info">
      <span class="duration">${timerender}</span>
      <span class="dot">●</span>
      <span class="premier-date">${datatoadd.Premiere}</span>
    </div>

    <div class="button-container">
      <a href="../Order/index.html" style="text-decoration:None;display:block;width:100%">
        <button class="btn-main btn-book">
        ĐẶT VÉ
        <img src="../../images/Arrow_right_long.svg" alt="" />
      </button></a>
    </div>`;
    $(".movie-content").append(htmls);
  }
  // Thêm dữ liệu vào Casourel-Indicator
  async function addIntoCasourelIndicator(data) {
    const datatoadd = data["movie"];
    let flag = 0;
    let htmls = ``;
    for (const element of datatoadd["listImage"]) {
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
        src="../../${element.ImagePath}"
        alt=""
      />
    </button>`;
      } else {
        htmls += `    <button
      type="button"
      data-bs-target="#movie-slide"
      data-bs-slide-to="1"
      aria-label="Slide 2"
    >
      <img class="thumbnail-img" src="../../${element.ImagePath}" alt="" />
    </button>`;
      }
    }
    $(".Casourel-Indicator").append(htmls);
  }
  //  Thêm dữ liệu Poster Body(about-poster)
  async function addPosterBody(data) {
    console.log(data);
    const datatoadd = data["movie"];
    const posterContainer = datatoadd.listImage[1];
    console.log(posterContainer);
    const htmls = `<img
               id="about-poster-img"
               src="../../${posterContainer.ImagePath}"
             />`;
    $("#about-poster").append(htmls);
  }
  //  Thêm dữ liệu vào about-describe
  async function addMovieContent(data) {
    const datatoadd = data["movie"];
    const cuttingGenre = (data) => {
      let storehtml = "";
      data["ListGenre"].flat().forEach((element) => {
        storehtml += `<li>${element.GenreName}</li>`;
      });
      return storehtml;
    };
    const cuttingActor = (data) => {
      let storehtml = "";
      data["ListActor"].flat().forEach((element) => {
        storehtml += `${element.NameActor}&nbsp&nbsp&nbsp&nbsp`;
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
                ${datatoadd["story"]}
              </p>
            </div>
            <div class="about-line">
              <p>Ngày khởi chiếu</p>
              <p>${datatoadd.Premiere}</p>
            </div>
            <div class="about-line">
              <p>Diễn viên</p>
              <p class="about-short">
                ` +
      cuttingActor(datatoadd) +
      `
              </p>
            </div>
            <div class="about-line">
              <p>Đạo diễn</p>
              <p>${datatoadd.Director}</p>
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

    getPremierMovie("../../.").then((datas) => {
      const cuttingGenre = (data) => {
        let storehtml = "";

        data["ListGenre"].flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element.GenreName}`;
          } else {
            storehtml +=
              '<span class="vertical-line">|</span>' +
              `${element.GenreName}`;
          }
        });
        return storehtml;
      };
      datas.forEach(async (data, index) => {
        let imagebannerObj = data.listImage;
        let filteredMovies = imagebannerObj.filter(function (movie) {
          return movie.type == "1" && movie.ImagePath;
        });
        function toHHMM(minutes) {
          var hours = Math.floor(minutes / 60);
          var mins = minutes % 60;
          return hours + "h" + " " + mins + "m";
        }
        let timerender = await toHHMM(data.Time);
        let genrehtml = await cuttingGenre(data);
        let htmls = "";
        htmls +=
          `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="../../images/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="../../images/date.svg" alt="" />
                  ${data.Premiere}
                </div>
                <div class="duration">
                  <img src="../../images/timer.svg" alt="" />
                  ${timerender}
                </div>
                <a href="../Detail/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.MovieID}>Chi tiết</button>
                </a>
                <a href="../Order/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book">
                  ĐẶT VÉ
                  <img src="../../images/Arrow_right_long.svg" alt="" />
                </button>
                </a>
              </div>
              <img class="poster" src="../../${imagebannerObj[0].ImagePath}" alt="" />
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
  function changingBtnOnClick(GenreID) {
    const movieContainer = $(".row.g-3.movie-premier-container");
    const GenreContainer = $(".row.g-2.genre-container");
    movieContainer.html("");
    const removeBtnColor = GenreContainer.find("button.btn-main");
    removeBtnColor.removeClass("btn-main");
    const AddingBtnColor = GenreContainer.find(`#${GenreID}`);
    AddingBtnColor.addClass("btn-main");
    getByGenreID("../../.", GenreID).then((datas) => {
      const cuttingGenre = (data) => {
        let storehtml = "";

        data["ListGenre"].flat().forEach((element, index) => {
          if (index == 0) {
            storehtml += `${element.GenreName}`;
          } else {
            storehtml +=
              '<span class="vertical-line">|</span>' +
              `${element.GenreName}`;
          }
        });
        return storehtml;
      };
      datas.forEach(async (data, index) => {
        let imagebannerObj = data.listImage;
        let filteredMovies = imagebannerObj.filter(function (movie) {
          return movie.type == "1" && movie.ImagePath;
        });
        function toHHMM(minutes) {
          var hours = Math.floor(minutes / 60);
          var mins = minutes % 60;
          return hours + "h" + " " + mins + "m";
        }
        let timerender = await toHHMM(data.Time);
        let genrehtml = await cuttingGenre(data);
        let htmls = "";
        htmls +=
          `<div class="movie-card col-xl-2 col-lg-3 col-md-4 col-sm-4 col-6">
            <div class="card-img">
              <div class="card-info">
                <img
                  src="../../images/Star.svg"
                  alt=""
                  width="22px"
                  height="22px"
                />
                <div class="rating">${data.rating} / 10</div>
                <div class="age">${data.age}</div>
                <div class="date">
                  <img src="../../images/date.svg" alt="" />
                  ${data.Premiere}
                </div>
                <div class="duration">
                  <img src="../../images/timer.svg" alt="" />
                  ${timerender}
                </div>
                <a href="./index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                <button class="btn-outline" id=${data.MovieID}>Chi tiết</button>
                </a>
                <a href="../Order/index.html" style="text-decoration:None;display:block;width:100%;text-align:center">
                  <button class="btn-main btn-book">
                  ĐẶT VÉ
                  <img src="../../images/Arrow_right_long.svg" alt="" />
                </button>
                </a>
              </div>
              <img class="poster" src="../../${imagebannerObj[0].ImagePath}" alt="" />
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