const getAllTicketsByShowTimeId = async (url, showtimeID) => {
  console.log(showtimeID);
  const urls = `${url}/Controller/Ticket/ajax.php?action=getAllTicketsByShowTimeId&ShowTimeId=${showtimeID}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getAllTicketsByShowTimeId };
