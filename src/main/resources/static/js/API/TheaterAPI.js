const getAllTheaters = async (url) => {
  const urls = `${url}/api/v1/theater`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

export { getAllTheaters };
