const getAllMenu = async (url) => {
  const data = await fetch(`${url}/api/v1/menu`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getDetailMenuByID = async (url, id = "1") => {
  const data = await fetch(`${url}/api/v1/menu?menu_id=${id}`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const addMenu = async (url = "../..", Name, file, Price, status) => {
  const urls = `${url}/api/v1/menu`;
  const data = await fetch(urls, {
    method: "POST",
    body: JSON.stringify({
      name: Name,
      imgUrl: file,
      price: Price,
      status: status,
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
  const urls = `${url}/api/v1/menu`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      name: Name,
      imgUrl: ImageURL,
      price: Price,
      status: status,
      itemId: ItemID,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

export { getAllMenu, getDetailMenuByID, addMenu, updateMenu };
