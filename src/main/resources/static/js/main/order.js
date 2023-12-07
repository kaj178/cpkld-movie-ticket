import { getAllGenres } from "../API/GenreAPI.js";
import {
  getShowTimeByID,
  getAllShowTimesByDate,
  getShowTimeByDateAndGenre,
  getShowTimeByMovieandTheater,
  getShowTimeByDateAndTheater,
} from "../API/ShowTimeAPI.js";
import { getAllTheaters } from "../API/TheaterAPI.js";
import { getMovieByID } from "../API/MovieAPI.js";
import { getRoomById } from "../API/RoomAPI.js";
import { getFormatById } from "../API/FormatAPI.js";
import { getAllTicketsByShowTimeId } from "../API/TicketAPI.js";
import { getCustomerByEmail } from "../API/UserAPI.js";
import { XORDecrypt } from "../Util/EncryptXOR.js";

$(document).ready(function () {
  let previousOption = "";
  let previousOptionName = "";
  const date = new Date();
  $(".navbar-toggler").click(function () {
    var color = $(".navbar").css("background-color");
    if (color == "rgba(0, 0, 0, 0.01)") {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.85)");
    } else {
      $(".navbar").css("background", "rgba(0, 0, 0, 0.01)");
    }
  });
  // Select_Theater_Container
  (async function getAllTheatersFunc() {
    let datas = await getAllTheaters("../..");
    let htmls = "";
    for (const data of datas.data) {
      htmls += `<option value=${data.theaterId}>${data.theaterName}</option>`;
    }
    $(".Select_Theater_Container").append(htmls);
  })();
  // Search by Theater
  $(".search-by-theater").click(() => {
    const datetoFetch = sessionStorage.getItem("DayChoosing");
    const select = document.querySelector(".Select_Theater_Container");
    const selectedOption = select.options[select.selectedIndex].value;
    const selectedOptionName = select.options[select.selectedIndex].text;
    sessionStorage.setItem("TheaterName", selectedOptionName);
    if (selectedOption != "Rạp phim" && previousOption != selectedOption) {
      previousOption = selectedOption;
      previousOptionName = selectedOptionName;
      TakingShowTime(false);
    }
  });

  // Lấy toàn bộ Genre
  function changingBtnOnClickGetAll(GenreID, callingagain = true) {
    const GenreContainer = $(".row.g-2.genre-container");
    const removeBtnColor = GenreContainer.find("button.btn-main");
    removeBtnColor.removeClass("btn-main");
    const AddingBtnColor = GenreContainer.find(`#${GenreID}`);
    AddingBtnColor.addClass("btn-main");
    if (callingagain) {
      TakingShowTime(false);
    }
  }

  function changingBtnOnClick(GenreID) {
    const GenreContainer = $(".row.g-2.genre-container");
    const removeBtnColor = GenreContainer.find("button.btn-main");
    removeBtnColor.removeClass("btn-main");
    const AddingBtnColor = GenreContainer.find(`#${GenreID}`);
    AddingBtnColor.addClass("btn-main");
    const IdMovieContainerCheck = [];
    const IdFormatContainerCheck = [];
    const datetoFetch = sessionStorage.getItem("DayChoosing");
    // Lấy phim Dựa Vào Ngày và Thể Loại
    getShowTimeByDateAndGenre("../..", "20230424", GenreID).then(
      async (datas) => {
        await $(".card-film-item-container").html("");
        const cuttingGenre = (data) => {
          let storehtml = "";
          data.flat().forEach((element) => {
            storehtml += `<span>${element.name}</span>`;
          });
          return storehtml;
        };
        datas.data.forEach(async (data) => {
          let AllTicketContent = await getAllTicketsByShowTimeId(
            "../..",
            data.showTimeId
          );
          let ticketNum = AllTicketContent.data.length;
          dataMovie = data.movie;
          const datatoCut = data.startTime;
          // const takingTime = await datatoCut.split(" ")[1];
          const renderTime = await datatoCut.split(":");
          const hasIdFormatPair = await IdFormatContainerCheck.some(
            (pair) =>
              pair.movieId === data.movieId && pair.formatId === data.formatId
          );
          const ShowTimeContainerCheck = [];
          let dataMovie = await TakingMovieByID(data.movieId);
          dataMovie = dataMovie.movie;
          if (
            !IdMovieContainerCheck.includes(data.movieId) ||
            !hasIdFormatPair ||
            !ShowTimeContainerCheck.includes(data.showTimeId)
          ) {
            IdMovieContainerCheck.push(data.movieId);
            IdFormatContainerCheck.push((data.movieId, data.formatId));
            ShowTimeContainerCheck.push(data.showTimeId);
            let dataMovie = await TakingMovieByID(data.movieId);
            let RoomData = await TakingRoomByID(data.roomId);
            dataMovie = dataMovie.movie;
            RoomData = RoomData["room"];
            const datatoCut = data.startTime;
            const GenreList = await cuttingGenre(dataMovie);
            // const takingTime = await datatoCut.split(" ")[1];
            const renderTime = await datatoCut.split(":");
            const htmls = `

                   <div class="row card-film-cotainer">
                     <div class="col-lg-3 item-img-container col-md-12 col-sm-12">
                       <img
                         src="../../images/poster.jpg"
                         alt=""
                         style="border-radius: 10px"
                       />
                     </div>
                     <div class="col-lg-8 item-info col-md-12 col-sm-12">
                       <div class="info-container-about-film">
                         <div class="heading-info-container">
                           <h2 class="heading-text">${dataMovie.MovieName}</h2>
                           <div class="rating">
                             <div class="rating-star">
                               <img src="../../images/Star.svg" alt="" />
                               ${dataMovie.rating}
                             </div>
                             <div class="rating-imdb">IMDB 7.2</div>
                           </div>
                         </div>
                         <p class="genre-text">${GenreList}</p>
                         <p class="type-text">2D | Phụ đề</p>
                      </div>
                      <div class="time-choosing-container container-fluid">
                        <div class="item-container row" showtimeid="${showtimeid} formatID="${formatID}></div>
                        </div>
                      `;
            await $(".card-film-item-container").append(htmls);
            let timehtmls = "";
            timehtmls += `
                      <div class="item-choosing-time col-lg-2 col-md-5 col-sm-6" showtimeid=${
                        data.ShowtimeID
                      } id=${data.RoomID} movieid=${data.MovieID}>
                        <p class="place-to-choose">Room ${RoomData.RoomName}</p>
                        <p class="time-to-choose">${renderTime[0]}:${
              renderTime[1]
            }</p>
                        <p class="seat-info"><b>${
                          RoomData.NumberOfSeats - ticketNum
                        }</b>/${RoomData.NumberOfSeats} Chỗ Ngồi</p>
                      </div>
                      `;
            const elementsWithMatchingAttrs = document.querySelector(
              `.item-container.row[showtimeid="${showtimeid}"][formatID="${formatID}"]`
            );

            await $(elementsWithMatchingAttrs).append(timehtmls);
          } else {
            let dataMovie = await TakingMovieByID(data.MovieID);
            let RoomData = await TakingRoomByID(data.RoomID);
            dataMovie = dataMovie["movie"];
            RoomData = RoomData["room"];
            const datatoCut = data.StartTime;
            const GenreList = await cuttingGenre(dataMovie);
            const takingTime = await datatoCut.split(" ")[1];
            const renderTime = await takingTime.split(":");
            const showtimeid = data.ShowtimeID;
            const formatID = data.FormatID;

            const elementsWithMatchingAttrs = document.querySelector(
              `.item-container.row[showtimeid="${showtimeid}"][formatID="${formatID}"]`
            );

            let timehtmls = "";
            timehtmls += await `
                      <div class="item-choosing-time col-lg-2 col-md-5 col-sm-6" showtimeid=${
                        data.ShowtimeID
                      } id=${data.RoomID} movieid=${data.MovieID}>
                        <p class="place-to-choose">Room ${RoomData.RoomName}</p>
                        <p class="time-to-choose">${renderTime[0]}:${
              renderTime[1]
            }</p>
                        <p class="seat-info"><b>${
                          RoomData.NumberOfSeats - ticketNum
                        }</b>/${RoomData.NumberOfSeats} Chỗ Ngồi</p>
                      </div>
                      `;
            await $(elementsWithMatchingAttrs).append(timehtmls);
          }
        });
        $(
          `.item-choosing-time[showtimeid="${showtimeid}"][formatID="${formatID}"]`
        ).bind("click", () => {
          let idRoom = $(this).attr("id");
          let idShowtime = $(this).attr("showtimeid");
          let movieID = $(this).attr("movieid");
          console.log(movieID);
          sessionStorage.setItem("RoomIDChoosing", idRoom);
          sessionStorage.setItem("ShowTimeID", idShowtime);
          sessionStorage.setItem("TheaterName", previousOptionName);
          sessionStorage.setItem(
            "TimeChoosing",
            `${renderTime[0]}:${renderTime[1]}`
          );
          sessionStorage.setItem("movieid", movieID);
          window.location.href = "../Booking/ChooseSeat/index.html";
        });
      }
    );
  }

  const takeAllGenre = async () => {
    const genreContainer = $(".row.g-2.genre-container");
    const button = $("<button>")
      .attr("id", "MBTN00001")
      .addClass("btn-main all-select")
      .text("Tất cả Phim");
    button.bind("click", () => changingBtnOnClickGetAll("MBTN00001"));
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

    var pagenum = await updatePageNum();
    for (let page = 1; page <= pagenum; page++) {
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
  };
  // Lấy phim bởi ID
  const TakingMovieByID = async (ID) => {
    let data = await getMovieByID("../..", ID);
    return data;
  };
  // Lấy Room bởi ID
  const TakingRoomByID = async (ID) => {
    let data = await getRoomById("../..", ID);
    return data;
  };
  // Lấy ra ShowTime
  const TakingShowTime = async (takingGenre = true) => {
    if (takingGenre) {
      await takeAllGenre();
    }
    const datetoGet = sessionStorage.getItem("DayChoosing");
    let listDate = datetoGet.split("-");
    if (listDate[2].length == 1) {
      listDate[2] = "0" + listDate[2];
    }
    let newDate = "";
    for (let i = 0; i < listDate.length; i++) {
      newDate += listDate[i];
    }
    await changingBtnOnClickGetAll("MBTN00001", false);
    await $(".card-film-item-container").html("");
    const IdMovieContainerCheck = [];
    const IdFormatContainerCheck = [];
    const ShowTimeContainerCheck = [];
    const datas = await getShowTimeByDateAndTheater(
      "../..",
      newDate,
      previousOption
    );
    const cuttingGenre = (data) => {
      let storehtml = "";
      data.forEach((element) => {
        storehtml += `<span>${element}</span>`;
      });
      return storehtml;
    };
    for (const data of datas.data) {
      console.log(data);
      let AllTicketContent = await getAllTicketsByShowTimeId(
        "../..",
        data.showTimeId
      );
      let ticketNum = AllTicketContent.data.length;
      const hasIdFormatPair = await IdFormatContainerCheck.some(
        (pair) =>
          pair.movieId === data.movie.movieId && pair.formatId === data.formatId
      );
      let dataMovie = await TakingMovieByID(data.movie.movieId);
      dataMovie = dataMovie.data;
      const datatoCut = data.startTime;
      // const takingTime = await datatoCut.split(" ")[1];
      const renderTime = await datatoCut.split(":");
      if (
        !IdMovieContainerCheck.includes(data.movie.movieId) ||
        !hasIdFormatPair ||
        !ShowTimeContainerCheck.includes(data.showTimeId)
      ) {
        IdFormatContainerCheck.push((data.movie.movieId, data.formatId));
        IdMovieContainerCheck.push(data.movie.movieId);
        ShowTimeContainerCheck.push(data.showTimeId);
        let dataMovie = await TakingMovieByID(data.movie.movieId);
        let RoomData = await TakingRoomByID(data.roomId);
        console.log(RoomData);
        let FormatName = await getFormatById("../..", data.formatId);
        let FormatRender = FormatName.data[0].formatName;
        dataMovie = dataMovie.data[0];
        RoomData = RoomData.data;
        // const ImageLink = dataMovie["listImage"][0].ImagePath;
        const ImageLink = dataMovie.verticalPoster;
        const datatoCut = data.startTime;
        const GenreList = await cuttingGenre(dataMovie.movieGenres);
        // const takingTime = await datatoCut.split(" ")[1];
        const renderTime = await datatoCut.split("T");
        const showtimeid = data.showTimeId;
        const formatID = data.formatId;
        const htmls = `
              <div class="row card-film-cotainer">
                <div class="col-lg-3 item-img-container col-md-12 col-sm-12">
                  <img
                    src="../public/imagesfilms/poster-vertical/${ImageLink}"
                    alt=""
                    style="border-radius: 10px"
                  />
                </div>
                <div class="col-lg-8 item-info col-md-12 col-sm-12">
                  <div class="info-container-about-film">
                    <div class="heading-info-container">
                      <h2 class="heading-text">${dataMovie.name}</h2>
                      <div class="rating">
                        <div class="rating-star">
                          <img src="../public/Star.svg" alt="" />
                          ${dataMovie.rating}
                        </div>
                        <div class="rating-imdb">IMDB 7.2</div>
                      </div>
                    </div>
                    <p class="genre-text">${GenreList}</p>
                    <p class="type-text">${FormatRender}</p>
                  </div>
                  <div class="time-choosing-container container-fluid">
                    <div class="item-container row" showtimeid=${data.showTimeId} formatID=${data.formatId}></div>
                  </div>
                </div>
              </div>
            `;
        await $(".card-film-item-container").append(htmls);
        let timehtmls = "";
        timehtmls += `
              <div class="item-choosing-time col-lg-2 col-md-5 col-sm-6" showtimeid=${
                data.showTimeId
              } id=${data.roomId} movieid=${data.movie.movieId}>
                <p class="place-to-choose">${RoomData[0].roomName}</p>
                <p class="time-to-choose">${renderTime[1]}</p>
                <p class="seat-info"><b>${
                  RoomData[0].amountSeats - ticketNum
                }</b>/${RoomData[0].amountSeats} Chỗ Ngồi</p>
              </div>
            `;
        const elementsWithMatchingAttrs = document.querySelector(
          `.item-container.row[showtimeid="${data.showTimeId}"][formatID="${data.formatId}"]`
        );
        await $(elementsWithMatchingAttrs).append(timehtmls);
      } else {
        let dataMovie = await TakingMovieByID(data.movie.movieId);
        let RoomData = await TakingRoomByID(data.roomId);
        dataMovie = dataMovie.data;
        RoomData = RoomData.data;
        const datatoCut = data.startTime;
        const GenreList = await cuttingGenre(dataMovie.movieGenres);
        // const takingTime = await datatoCut.split(" ")[1];
        const renderTime = await datatoCut.split(":");

        const elementsWithMatchingAttrs = document.querySelector(
          `.item-container.row[showtimeid="${data.showTimeId}"][formatID="${data.formatId}"]`
        );

        const placetoadd = $(elementsWithMatchingAttrs);
        let timehtmls = "";
        timehtmls += `
        <div class="item-choosing-time col-lg-2 col-md-5 col-sm-6" showtimeid=${
          data.showTimeId
        } id=${data.roomId} movieid=${data.movieId}>
          <p class="place-to-choose">Room ${RoomData.roomName}</p>
          <p class="time-to-choose">${renderTime[0]}:${renderTime[1]}</p>
          <p class="seat-info"><b>${RoomData.amountSeats - ticketNum}</b>/${
          RoomData.amountSeats
        } Chỗ Ngồi</p>
        </div>
      `;
        await placetoadd.append(timehtmls);
      }
      const showtimeid = data.showTimeId;
      const formatID = data.formatId;
      console.log($(`.item-choosing-time`).html());
      $(`.item-choosing-time`).bind("click", function (event) {
        let idRoom = $(this).attr("id");
        let idShowtime = $(this).attr("showtimeid");
        sessionStorage.setItem("RoomIDChoosing", idRoom);
        sessionStorage.setItem("ShowTimeID", idShowtime);
        sessionStorage.setItem("TheaterName", previousOptionName);
        sessionStorage.setItem(
          "TimeChoosing",
          `${renderTime[0]}:${renderTime[1]}`
        );
        sessionStorage.setItem("movieid", $(this).attr("movieid"));

        window.location.href = "/booking/choose-seat";
      });
    }
  };
  function ChangingDate() {
    const daysOfWeek = [
      "Chủ nhật",
      "Thứ hai",
      "Thứ ba",
      "Thứ tư",
      "Thứ năm",
      "Thứ sáu",
      "Thứ bảy",
    ];
    const months = [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec",
    ];

    // Get the date container and arrow buttons
    const previousDateBtn = $("#prev-day");
    const nextDateBtn = $("#next-day");
    const AddingDay = (date) => {
      const dayOfMonth = date.getDate();
      const month = date.getMonth();
      const year = date.getFullYear();
      sessionStorage.setItem(
        "DayChoosing",
        `${year}-${month + 1}-${dayOfMonth}`
      );
    };
    const dayOfMonth = date.getDate();
    const month = date.getMonth();
    const year = date.getFullYear();
    // Initialize the current date to the first day in the HTML code
    let currentDate = new Date(`${months[month]} ${dayOfMonth}, ${year}`);
    // Initialize the date container with the first five days
    previousDateBtn.on("click", () => {
      currentDate.setDate(currentDate.getDate() - 1);
      updateDateContainer();
    });

    nextDateBtn.on("click", () => {
      currentDate.setDate(currentDate.getDate() + 1);
      updateDateContainer();
    });
    // Update the date container with the current date
    async function updateDateContainer() {
      previousOption = "";

      $(".date-Container").html("");
      AddingDay(currentDate);
      let dateHtml = "";
      for (let i = 0; i < 5; i++) {
        const dayOfWeek = daysOfWeek[currentDate.getDay()];
        const dayOfMonth = currentDate.getDate();
        const month = months[currentDate.getMonth()];

        const isActive = i === 0 ? "active" : "";
        dateHtml += `
                        <div class="item-day-holder ${isActive}">
                          <div class="day-text ${isActive}">${dayOfWeek}</div>
                          <div class="day-number ${isActive}">${dayOfMonth}</div>
                          <div class="month-text ${isActive}">${month}</div>
                        </div>
                      `;
        currentDate.setDate(currentDate.getDate() + 1);
      }
      $(".date-Container").append(dateHtml);
      currentDate.setDate(currentDate.getDate() - 5); // Reset the current date
    }
    updateDateContainer();
  }
  ChangingDate();
});
