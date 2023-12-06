const getAllTicketsByShowTimeId = async (url, showtimeID) => {
  console.log(showtimeID);
  const urls = `${url}/api/v1/ticket/showtime?showtime_id=${showtimeID}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getAllTicketsByShowTimeId };
