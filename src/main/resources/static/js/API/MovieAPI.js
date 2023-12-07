const getAllMovies = async (url) => {
  const data = await fetch(`${url}/api/v1/movie`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getHotMovieAPI = async (url) => {
  const data = await fetch(`${url}/api/v1/movie/hot`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getHotMovieAPIPaginated = async (url) => {
  const data = await fetch(`${url}/api/v1/movie/hot?page=1`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getPremiereMoviesByGenreID = async (url, id) => {
  const data = await fetch(`${url}/api/v1/movie/playing?genre-id=${id}`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getPremiereMovies = async (url) => {
  const data = await fetch(`${url}/api/v1/movie/playing`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getUpcomingMovies = async (url, page = 1) => {
  const data = await fetch(`${url}/api/v1/movie/up-coming`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getMovieByID = async (url, id) => {
  const data = await fetch(`${url}/api/v1/movie/${id}`, {
    method: "GET",
  });
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
      name: MovieName,
      director: Director,
      year: Year,
      premiere: Premiere,
      urlTrailer: URLTrailer,
      time: Time,
      studio: StudioID,
      language: LanguageID,
      story: story,
      age: age,
      movieGenres: listGenre,
      verticalPoster: listImage[0],
      horizontalPoster: listImage[1],
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
  getPremiereMoviesByGenreID,
  getHotMovieAPIPaginated,
  getPremiereMovies,
  getUpcomingMovies,
  getMovieByID,
  addMovie,
  updateMovie,
  deleteMovie,
};
