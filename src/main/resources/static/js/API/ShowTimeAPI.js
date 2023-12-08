const getShowTimeByID = async (url, id) => {
  const data = await fetch(`${url}/api/v1/showtime/${id}`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllShowTime = async (url) => {
  const data = await fetch(`${url}/api/v1/showtime`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllShowTimesByDate = async (url, date = "2022-10-10") => {
  const data = await fetch(
    `${url}/Controller/Showtime/ajax.php?action=getShowTimeByDate&date=${date}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const getShowTimeByDateAndGenre = async (
  url,
  date = "20230424",
  Genre = ""
) => {
  const data = await fetch(
    `${url}/api/v1/showtime/date&date=${date}&genre=${Genre}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const getShowTimeByMovieandTheater = async (
  url,
  movieid = 1,
  Theaterid = 1,
  date = "2022-10-10"
) => {
  const data = await fetch(
    `${url}/Controller/Showtime/ajax.php?action=getShowTimeByMovieandTheater&movieid=${movieid}&Theaterid=${Theaterid}&date=${date}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const getAllShowtimeByMovieID = async (
  url,
  movieid = 1,
  date = "2022-10-10"
) => {
  const data = await fetch(
    `${url}/Controller/Showtime/ajax.php?action=getAllShowtimeByMovieID&movieid=${movieid}&date=${date}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const updateShowTime = async (
  url = "../..",
  Price,
  StartTime,
  MovieID,
  EndTime,
  RoomID,
  FormatID,
  ShowtimeID
) => {
  const urls = `${url}/api/v1/showtime/${ShowtimeID}`;
  const data = await fetch(urls, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "PUT",
    body: JSON.stringify({
      price: Price,
      startTime: StartTime,
      movieID: MovieID,
      endTime: EndTime,
      roomId: RoomID,
      formatId: FormatID,
      showTimeId: ShowtimeID,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const removeShowTime = async (url = "../..", movieid) => {
  const urls = `${url}/Controller/Showtime/ajax?movieid=${movieid}.php`;
  const data = await fetch(urls, {
    method: "DELETE",
  });
  const datatorender = await data.json();
  return datatorender;
};

const addShowTime = async (
  url = "../..",
  Price,
  StartTime,
  MovieID,
  EndTime,
  RoomID,
  FormatID
) => {
  const urls = `${url}/api/v1/showtime`;
  console.log({
    price: Price,
    startTime: StartTime,
    movieID: MovieID,
    endTime: EndTime,
    roomId: RoomID,
    formatId: FormatID,
  });
  const data = await fetch(urls, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify({
      price: Price,
      startTime: StartTime,
      movieID: MovieID,
      endTime: EndTime,
      roomId: RoomID,
      formatId: FormatID,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const getShowTimeByDateAndTheater = async (url, date, theaterid) => {
  const data = await fetch(
    `${url}/api/v1/showtime/theater?date=${date}&theater=${theaterid}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

export {
  getShowTimeByID,
  getAllShowTimesByDate,
  getShowTimeByDateAndGenre,
  getShowTimeByMovieandTheater,
  getAllShowtimeByMovieID,
  updateShowTime,
  addShowTime,
  removeShowTime,
  getShowTimeByDateAndTheater,
  getAllShowTime,
};
