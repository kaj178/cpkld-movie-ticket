function postLocalStorage(key, valuetoadd) {
  // Post Lên Local Storage
  Object.defineProperty(window.localStorage, key, {
    value: valuetoadd,
    writable: false,
    configurable: false,
    enumerable: true,
  });
}
function setLocalStorage(key, value) {
  // Lấy từ Local Storage
  let storedValue = JSON.parse(localStorage.getItem(key));
  // Set giá trị từ Local Storage
  storedValue[key] = value;
  // Đưa lên LocalStorage
  localStorage.setItem(key, JSON.stringify(storedValue));
}
export { postLocalStorage, setLocalStorage };
