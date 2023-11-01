const getAllMenu = async (url, page = "1") => {
  const data = await fetch(
    `${url}/Controller/Menu/ajax.php?action=getAllMenu&page=${page}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
const getDetailMenuByID = async (url, id = "1") => {
  const data = await fetch(
    `${url}/Controller/Menu/ajax.php?action=getMenuById&id=${id}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
const addMenu = async (
  url = "../..",
  Name, file , Price ,status 
) => {
  const urls = `${url}/Controller/Menu/ajax.php`;
  const data = await fetch(urls, {
    method: "POST",
    body: JSON.stringify({
      Name:Name,
      file:file,
      Price:Price,
      status:status,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};
const updateMenu = async (
  url = "../..",
  Name, 
  ImageURL, 
  Price, 
  status, 
  ItemID
) => {
  const urls = `${url}/Controller/Menu/ajax.php`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      Name:Name,
      ImageURL:ImageURL,
      Price:Price,
      status:status,
      ItemID:ItemID
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getAllMenu, getDetailMenuByID ,addMenu, updateMenu};
