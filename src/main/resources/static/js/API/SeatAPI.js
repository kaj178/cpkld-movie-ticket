const getSeatByRoomID = async (url, roomID = "1") => {
  const data = await fetch(
    `${url}/Controller/Seat/ajax.php?action=getSeatByRoomID&room_id=${roomID}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
const getseatByID = async (url, seatID = "1") => {
  const data = await fetch(
    `${url}/Controller/Seat/ajax.php?action=getSeatById&id=${seatID}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
export { getSeatByRoomID, getseatByID };
