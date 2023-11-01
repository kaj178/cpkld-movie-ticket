const getPromotionsVoucher = async (url, page = "1") => {
  const data = await fetch(
    `${url}/Controller/Promotion/ajax.php?action=getPromotionsVorcher&page=${page}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
const getPromotionsEvent = async (url, page = "1") => {
  const data = await fetch(
    `${url}/Controller/Promotion/ajax.php?action=getPromotionsEvent&page=${page}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
const calculateTotalPrice = async (url, code, totalPrice) => {
  const urls = `${url}/Controller/Booking/ajax.php`;
  const data = await fetch(urls, {
    method: "POST",
    body: JSON.stringify({
      action: "caluateTotalPriceUsingCode",
      code: code,
      TotalPrice: totalPrice,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};
export { getPromotionsVoucher, getPromotionsEvent, calculateTotalPrice };
