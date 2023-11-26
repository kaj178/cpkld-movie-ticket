const getAllMovies = async (url) => {
  const data = await fetch(
    `${url}/api/v1/movie`,
    {
      method: "GET"
    }
  )
  const datatorender = await data.json()
  return datatorender
}

const getHotMovieAPI = async (url) => {
  const data = await fetch(
    `${url}/api/v1/movie/hot`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const getPremiereMovies = async (url) => {
  const data = await fetch(
    `${url}/api/v1/movie/playing`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const getUpcomingMovies = async (url, page = 1) => {
  const data = await fetch(
    `${url}/api/v1/movie/up-coming`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const getByGenreID = async (url, genreid = 1, page = 1) => {
  const data = await fetch(
    `${url}/Controller/Movie/ajax.php?action=getMoiveByGenres&page=${page}&genreid=${genreid}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const getMovieByID = async (url, IDMovie = 1) => {
  const data = await fetch(
    `${url}/Controller/Movie/ajax.php?action=getMovieByID&movieid=${IDMovie}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};

const addMovie = async (
  url = "../..",
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
) => {
  const urls = `${url}/Controller/Movie/ajax.php`;
  const data = await fetch(urls, {
    method: "POST",
    body: JSON.stringify({
      action: "addMovie",
      MovieName: MovieName,
      Director: Director,
      Year: Year,
      Premiere: Premiere,
      URLTrailer: URLTrailer,
      Time: Time,
      StudioID: StudioID,
      LanguageID: LanguageID,
      story: story,
      age: age,
      ListActor: listActor,
      ListGenre: listGenre,
      ListImage: listImage,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const updateMovie = async (
  url = "../..",
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
) => {
  const urls = `${url}/Controller/Movie/ajax.php`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      action: "updateMovie",
      MovieID: MovieID,
      MovieName: MovieName,
      Director: Director,
      Year: Year,
      Premiere: Premiere,
      URLTrailer: URLTrailer,
      Time: Time,
      StudioID: StudioID,
      LanguageID: LanguageID,
      story: story,
      age: age,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const deleteMovie = async (url, id) => {
  const data = await fetch(`${url}/Controller/Movie/ajax.php?id=${id}`, {
    method: "DELETE",
  });
  const datatorender = await data.json();
  return datatorender;
};

export {
  getAllMovies,
  getHotMovieAPI,
  getPremiereMovies,
  getUpcomingMovies,
  getByGenreID,
  getMovieByID,
  addMovie,
  updateMovie,
  deleteMovie,
};
