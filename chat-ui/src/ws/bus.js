const bus = Object.create({})

bus.init = function() {
  this.mapping = []
}

bus.subscribe = function (name, func){
  let index = this.mapping.findIndex(function(o) {return o.name == name})
  if(index < 0 ) {
    console.log("订阅" + name + "失败 !")
  } else {
    this.mapping[index].subscribes.push(func)
  }
}

bus.regist = function (name) {
  this.mapping.push(
    {name: name, subscribes: []}
  )
}

bus.send = function (name, param) {
  let index = this.mapping.findIndex(function(o) {return o.name == name})
  if(index < 0 ) {
    console.log("未注册事件 !")
  } else {
    this.mapping[index].subscribes.forEach(function(func){
      var newParam = Object.assign({}, param)
      func(newParam)
    })
  }
}

export default bus;



