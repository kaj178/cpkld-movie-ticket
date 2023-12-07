const getSeatByRoomID = async (url, roomID = "1") => {
  const data = await fetch(`${url}/api/v1/seat/room?room_id=${roomID}`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
const getseatByID = async (url, seatID = "1") => {
  const data = await fetch(`${url}/api/v1/seat?seat_id=${seatID}`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getSeatByRoomID, getseatByID };
