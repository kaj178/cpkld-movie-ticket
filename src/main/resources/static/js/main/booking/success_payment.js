import { XORDecrypt } from "../../Util/EncryptXOR.js";
import { getCustomerByEmail } from "../../API/UserAPI.js";

$(document).ready(() => {
  let billid = sessionStorage.getItem("BillID");
  $(".money-info").text(`Thông tin chuyển tiền Momo:0764884258`);
});
