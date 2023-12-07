const getAllCustomer = async (url = "../..", page) => {
  const urls = `${url}/api/v1/customer?page=${page}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getAllManager = async (url = "../..", page) => {
  const urls = `${url}/api/v1/manager`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const changePassword = async (url = "../..", id, newPass, oldPass) => {
  const urls = `${url}/Controller/User/ajax.php`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      action: "changePassword",
      id: id,
      new_password: newPass,
      old_password: oldPass,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const updateCustomer = async (
  url = "../..",
  id,
  email,
  password,
  fullname,
  address,
  phone
) => {
  const urls = `${url}/Controller/User/ajax.php`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      action: "update_customer",
      id: id,
      email: email,
      password: password,
      fullname: fullname,
      address: address,
      phone: phone,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const getCustomerByEmail = async (url, email) => {
  const urls = `${url}/api/v1/customer?email=${email}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const getUserById = async (url, id) => {
  const urls = `${url}/Controller/User/ajax.php?action=getUserById&id=${id}`;
  const data = await fetch(urls, {
    method: "GET",
  });
  const datatorender = await data.json();
  return datatorender;
};

const updateManager = async (
  url = "../..",
  id,
  email,
  password,
  fullname,
  phone
) => {
  const urls = `${url}/Controller/User/ajax.php`;
  const data = await fetch(urls, {
    method: "PUT",
    body: JSON.stringify({
      action: "update_manager",
      id: id,
      email: email,
      password: password,
      fullname: fullname,
      phone: phone,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const addManager = async (
  url = "../..",
  email,
  password,
  fullname,
  phone,
  address
) => {
  const urls = `${url}/api/v1/manager`;
  const data = await fetch(urls, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify({
      action: "addManger",
      fullname: fullname,
      email: email,
      password: password,
      address: address,
      phone: phone,
    }),
  });
  const datatorender = await data.json();
  return datatorender;
};

const deleteManager = async (url = "../..", email) => {
  const urls = `${url}/Controller/User/ajax?email=${email}.php`;
  const data = await fetch(urls, {
    method: "DELETE",
  });
  const datatorender = await data.json();
  return datatorender;
};

export {
  getAllCustomer,
  getAllManager,
  changePassword,
  updateCustomer,
  updateManager,
  addManager,
  deleteManager,
  getCustomerByEmail,
  getUserById,
};
