const getStudioById = async (url, id = "1") => {
  const data = await fetch(
    `${url}/Controller/Studio/ajax.php?action=getStudioById&id=${id}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
const getAllStudios = async (url) => {
  const data = await fetch(`${url}/api/v1/studio`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
const addStudio = async (url = "../..", StudioName, Address, Phone, Email) => {
  const urls = `${url}/Controller/Studio/ajax.php`;
  const data = await fetch(urls, {
    method: "POST",
    body: JSON.stringify({
      StudioName: StudioName,
      Address: Address,
      Phone: Phone,
      Email: Email,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};
const removeStudio = async (url = "../..", id) => {
  const urls = `${url}/Controller/Studio/ajax?id=${id}.php`;
  const data = await fetch(urls, {
    method: "DELETE",
  });
  const datatorender = await data.json();
  return datatorender;
};
const updateStudio = async (
  url = "../..",
  StudioName,
  Address,
  Phone,
  Email,
  StudioID
) => {
  const urls = `${url}/Controller/Studio/ajax.php`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      StudioName: StudioName,
      Address: Address,
      Phone: Phone,
      Email: Email,
      StudioID: StudioID,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

export { getStudioById, getAllStudios, addStudio, removeStudio, updateStudio };
