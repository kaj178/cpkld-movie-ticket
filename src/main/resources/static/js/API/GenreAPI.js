const getAllGenres = async (url) => {
  const urls = `${url}/api/v1/genre`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};


export { getAllGenres };
