const LoginAPI = async (url, username, password) => {
  const bodyinput = {
    action: "login",
    email: username,
    password: password,
  };
  const bodytoadd = JSON.stringify(bodyinput);
  const data = await fetch(`${url}/Controller/User/ajax.php`, {
    method: "POST",
    body: bodytoadd,
  });
  const datatorender = await data.json();
  return datatorender;
};
// Lưu ý sau khi đăng nhập xong thì nhớ Set SessionStorage là IDUser
const RegisterAPI = async (url, email, password, fullname, address, phone) => {
  const bodyinput = {
    action: "register",
    email: email,
    password: password,
    fullname: fullname,
    address: address,
    phone: phone,
  };
  const bodytoadd = JSON.stringify(bodyinput);
  const data = await fetch(`${url}/api/v1/customer`, {
    method: "POST",
    body: bodytoadd,
  });
  const datatorender = await data.json();
  return datatorender;
};


export {
  RegisterAPI,
  LoginAPI
}
