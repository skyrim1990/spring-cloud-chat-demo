
const request = Object.create({});

let baseUrl = ""
if(process.env.NODE_ENV === 'development') {
  baseUrl = 'http://localhost:3001'
}
function wraped_fetch(url, method, param ) {

  let p = {
    method: method,
    body: JSON.stringify(param),
    headers: new Headers(request.headers)
  } 

  if(method === 'GET' || param === undefined) {
    delete p['body']
  }

  return fetch(baseUrl + url, p)
  .then(res => res.json())
  .catch(err => console.log(err))
}

request.headers = {'content-type': 'application/json'}

request.addHeader = function (key, value) {
  this.headers[key] = value
  return this;
}

request.removeHeader = function(key) {
  delete this.headers[key]
  return this;
}

request.get = function (url) {
  return wraped_fetch(url, "GET", {})
}

request.post = function (url, param) {
  return wraped_fetch(url, "POST", param)
}

request.put = function (url, param) {
  return wraped_fetch(url, "PUT", param)
}

request.delete = function (url, param) {
  return wraped_fetch(url, "DELETE", param)
}


request.test = function () {

  request.addHeader("cus_header", "xxxxxxxxx")
  console.log("Hello")
  wraped_fetch("/api/test", "GET", {})
    .then(resp => {
      console.log(resp)
    })
}


export default request; 