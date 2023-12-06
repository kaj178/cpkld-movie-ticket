const getRoomById = async (url, id = "1") => {
  const data = await fetch(`${url}/api/v1/room/${id}`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllRooms = async (url) => {
  const data = await fetch(`${url}/api/v1/room`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const updateRoom = async (
  url = "../..",
  RoomName,
  NumberOfSeats,
  TheaterID,
  RoomID
) => {
  const urls = `${url}/Controller/Room/ajax.php`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      RoomID: RoomID,
      NumberOfSeats: NumberOfSeats,
      TheaterID: TheaterID,
      RoomName: RoomName,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};
const deleteRoom = async (url = "../..", id) => {
  const urls = `${url}/Controller/Room/ajax?id=${id}.php`;
  const data = await fetch(urls, {
    method: "DELETE",
  });
  const datatorender = await data.json();
  return datatorender;
};
const addRoom = async (url = "../..", RoomName, NumberOfSeats, TheaterID) => {
  const urls = `${url}/Controller/Room/ajax.php`;
  const data = await fetch(urls, {
    method: "POST",
    body: JSON.stringify({
      RoomName: RoomName,
      NumberOfSeats: NumberOfSeats,
      TheaterID: TheaterID,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getRoomById, getAllRooms, updateRoom, addRoom, deleteRoom };
