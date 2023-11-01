const getAllGenre = async (url = "../..", page = 1) => {
  const urls = `${url}/Controller/Genre/ajax.php?action=getAll&page=${page}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getAllGenre };
