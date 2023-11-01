const getAllTheater = async (url = "../..") => {
  const urls = `${url}/Controller/Theater/ajax.php?action=getAllTheater`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getAllTheater };
