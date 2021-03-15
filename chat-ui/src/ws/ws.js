import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { Notify } from 'vant'

const ws = Object.create({})

ws.init = function() {
  console.log("ws init ")
  this.socket = new SockJS('/ws?token=' + sessionStorage.getItem("token"))
  this.stompClient = Stomp.over(this.socket)
  this.stompClient.connect({token: "-------------this is token---------------"}, function(){
    window.bus.regist('groupChat')
    window.bus.regist('singleChat')
    console.log("WS connect successful")
  })
}

ws.connect = function () {
  console.log("ws connect")

  this.stompClient.subscribe('/topic/system', (msg) => {
    let message = JSON.parse(msg.body)
    console.log("Received: " + message)
    Notify({type: 'success', message: "系统信息: " + message.content})
  }, (err) => {
    console.log("Err: " + err)
  });

  this.stompClient.subscribe('/user/topic/single' , (msg) => {
    let message = JSON.parse(msg.body)

    let friends = JSON.parse(sessionStorage.getItem("friends"))
    let friendIndex = friends.findIndex((f) => {
      return f.id == message.from
    })
    if(friendIndex < 0) {
      if(message.from == sessionStorage.getItem("id")) {
        message.senderName = sessionStorage.getItem("username")
        message.senderAvatar = "/resource-api/static/" + sessionStorage.getItem("avatar")
      } else {
        console.log("错误Id")
      }
    } else {
      message.senderName = friends[friendIndex].name
      message.senderAvatar = friends[friendIndex].avatar
    }

    if(message.groupId == null) {
      window.bus.send('singleChat', message)
      if(message.senderName != undefined && message.from != sessionStorage.getItem("id")) {
        Notify({ type: 'success', 
          message: "来自用户"+ message.senderName+"的信息: " + message.content})
      }
    } else {

      let groups = JSON.parse(sessionStorage.getItem("groups"))
      let groupIndex = groups.findIndex((g) => {
        return g.id = message.groupId
      })

      message.groupName = groups[groupIndex].name



      window.bus.send('groupChat', message)
      Notify({ type: 'success', 
        message: "来自群"+ message.groupName + "的信息: " + message.content})
    }

  }, (err) => {
    console.log("Err: " + err)
  });



}

ws.sendMsg = function (url, content) {
  console.log("ws send message")
  this.stompClient.send(url,{} ,JSON.stringify(content))
}

ws.disconnect = function () {
  console.log("ws disconnect")
  this.stompClient.disconnect();
}

export default ws;