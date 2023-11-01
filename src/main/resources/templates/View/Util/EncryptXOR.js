function XOREncrypt(StringEncrypt) {
  var result = "";
  for (var i = 0; i < StringEncrypt.length; i++) {
    var c = StringEncrypt.charCodeAt(i);
    var k = "abcdefghyz@431".charCodeAt(i % "abcdefghyz@431".length);
    result += String.fromCharCode(c ^ k);
  }
  return result;
}

// Decrypt a string
function XORDecrypt(StringEncrypt) {
  return XOREncrypt(StringEncrypt);
}
export { XOREncrypt, XORDecrypt };
