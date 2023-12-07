const AddBooking = async (
  url,
  numofticket = "1",
  time,
  voucher = "",
  CID,
  SID,
  total,
  listtic,
  listme
) => {
  const bodyinput = {
    NumberOfTickets: numofticket,
    BookingTime: time,
    Voucher: voucher,
    customer_id: CID,
    ShowTimeID: SID,
    TotalPrice: total,
    ListTicket: listtic,
    ListMenu: listme,
  };
  console.log(bodyinput);
  const bodytoadd = JSON.stringify(bodyinput);
  const data = await fetch(`${url}/api/v1/booking`, {
    method: "POST",
    body: bodytoadd,
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllBookingsByCustomerID = async (url = "../..", page = 1, id) => {
  const urls = `${url}&customer_id=${id}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllBooking = async (url = "../..", page = 1) => {
  const urls = `${url}/api/v1/booking`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const changeStatus = async (url = "../..", id, status) => {
  const urls = `${url}/Controller/Booking/ajax.php?action=changeStatus`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      action: "changeStatus",
      booking_id: id,
      status: status,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

export { AddBooking, getAllBookingsByCustomerID, getAllBooking, changeStatus };
