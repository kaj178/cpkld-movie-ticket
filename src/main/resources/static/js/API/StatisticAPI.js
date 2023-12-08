const getRevenueForDate = async (url, date = null, page = 1) => {
  const data = await fetch(
    `${url}/Controller/Statistic/ajax.php?action=getRevenueForDate&date=${date}&page=${page}`,
    {
      method: "GET",
    }
  );
  const datatorender = await data.json();
  return datatorender;
};
const getRevenueForMonth = async (url, year = null, month = null, page = 1) => {
  const data = await fetch(`${url}/api/v1/booking/statistic`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
const getRevenueForYear = async (url, year = null, page = 1) => {
  const data = await fetch(`${url}/api/v1/booking/statistic`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
const getRevenueForQuarterOfYear = async (
  url,
  year = null,
  quarter = null,
  page = 1
) => {
  const data = await fetch(`${url}/api/v1/booking/statistic`, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};
export {
  getRevenueForDate,
  getRevenueForMonth,
  getRevenueForQuarterOfYear,
  getRevenueForYear,
};
