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
    amountItem: numofticket,
    bookingTime: time,
    promotionName: voucher,
    customerId: CID,
    showTimeId: SID,
    totalPrice: total,
    seats: listtic,
    menus: listme,
  };
  console.log(bodyinput);
  const bodytoadd = JSON.stringify(bodyinput);
  const data = await fetch(`${url}/api/v1/booking`, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: bodytoadd,
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllBookingsByCustomerID = async (url = "../..", id) => {
  const urls = `${url}/api/v1/booking?customer_id=${id}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllBooking = async (url = "../..", page = 1) => {
  const urls = `${url}/api/v1/booking/all`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const changeStatus = async (url = "../..", id, status) => {
  const urls = `${url}/api/v1/booking`;
  const data = await fetch(urls, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
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
