import {
  getRevenueForDate,
  getRevenueForMonth,
  getRevenueForQuarterOfYear,
  getRevenueForYear,
} from "../../API/StatisticAPI.js";
import { getAllBooking } from "../../API/BookingAPI.js";
import { XORDecrypt } from "../../Util/EncryptXOR.js";
let allData = [];
let currentData = [];
let table = $("#table-content").DataTable({
  select: {
    style: "single",
    info: false,
  },
  searching: false,
  language: {
    lengthMenu: "Số kết quả / Trang _MENU_",
    zeroRecords: "Không tìm thấy dữ liệu",
    info: "Hiển thị trang _PAGE_ trên _PAGES_",
    infoEmpty: "Đang tìm kiếm dữ liệu",
    infoFiltered: "(filtered from _MAX_ total records)",
    paginate: {
      first: "Trang đầu",
      last: "Trang cuối",
      next: "Trang sau",
      previous: "Trang trước",
    },
  },
});
$("#table-content_filter").hide();
$(document).ready(() => {
  // let authFlag = true;
  // if (sessionStorage.getItem('Email')) {
  //   let email = XORDecrypt(sessionStorage.getItem('Email'));
  //   getUserByEmail("../../..", email).then(res => {
  //     if (res.role !== '2') authFlag = false;
  //   })
  // }
  // else authFlag = false;

  // if (!authFlag) {
  //   window.location.href = "../../Login_Modal/LoginModal.html";
  // }

  // $('.logout-container').click(() => {
  //   sessionStorage.removeItem('Email');
  //   window.location.href = "../../../";
  // })

  $(".item-choosing-block").click(function () {
    $(".item-choosing-block .divider-mini").remove();
    $(this).append("<div class=divider-mini></div>");
  });

  $(".Statistic-month").click(() =>
    LoadRevenueForMonth().then(() => showDataMonth())
  );
  $(".Statistic-month").click(function () {
    $("#table-title").text("Tháng");
  });
  $(".Statistic-year").click(() =>
    LoadRevenueForYear().then(() => showDataYear())
  );
  $(".Statistic-year").click(function () {
    $("#table-title").text("Năm");
  });
  $(".Statistic-quarter").click(() =>
    LoadRevenueForQuarter().then(() => showDataQuarter())
  );
  $(".Statistic-quarter").click(function () {
    $("#table-title").text("Qúy");
  });
  $(".item-choosing-block .divider-mini").remove();
  $(".Statistic-month").parent().append("<div class=divider-mini></div>");
  LoadRevenueForMonth().then(() => showDataMonth());
});
function toVndCurrencyFormat(number) {
  const currencyFormat = new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
    minimumFractionDigits: 0,
  });

  return currencyFormat.format(number);
}
async function LoadRevenueForMonth() {
  currentData = [];
  let yearList = [2022, 2023, 2024];
  let monthList = [1, 2, 3, 4];
  let page = 1;
  let data = await getRevenueForQuarterOfYear("../..", page);
  if (data.data) currentData.push(data.data);
  console.log(currentData);
}
async function LoadRevenueForQuarter() {
  currentData = [];
  let yearList = [2022, 2023, 2024];
  let monthList = [1, 2, 3, 4];
  let page = 1;
  let data = await getRevenueForQuarterOfYear("../..", page);
  if (data.data) currentData.push(data.data);
  console.log(currentData);
}
async function LoadRevenueForYear() {
  currentData = [];
  let yearList = [2022, 2023, 2024];
  let monthList = [1, 2, 3, 4];
  let page = 1;
  let data = await getRevenueForQuarterOfYear("../..", page);
  console.log(data.data);
  if (data.data[0]) currentData.push(data.data);
  console.log(currentData);
}
function showDataYear() {
  table.clear().draw();
  let data = currentData[0];
  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    table.row
      .add([data[i].year, toVndCurrencyFormat(data[i].totalRevenue)])
      .draw();
  }
}
function showDataMonth() {
  table.clear().draw();
  let data = currentData[0];
  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    const datatable = data[i].quarterlyRevenueDTOS;
    datatable.forEach((element) => {
      const data2 = element.monthlyRevenueDTOS;
      data2.forEach((element2) => {
        table.row
          .add([
            `${element2.month}/${element2.year}`,
            toVndCurrencyFormat(element2.totalRevenue),
          ])
          .draw();
      });
    });
  }
}
function showDataQuarter() {
  table.clear().draw();
  let data = currentData[0];
  let numRow = data.length;
  for (let i = 0; i < numRow; i++) {
    const datatable = data[i].quarterlyRevenueDTOS;
    datatable.forEach((element) => {
      table.row
        .add([
          `${element.quarter}/${element.year}`,
          toVndCurrencyFormat(element.totalRevenue),
        ])
        .draw();
    });
  }
}
