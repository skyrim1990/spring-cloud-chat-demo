<template>
  <div id="chats">
    <!--
    <div>{{ friends }}</div>
    <hr>
    <div>{{ chats }}</div>
    -->
    <div class="chat"
      v-for="f in newFriends"
      :key="f.index"
      @click="inspect(f)">

      <div class="avatar">
        <img :src=f.avatar />
        <!--
        <div v-if="chat.unRead != 0" class="unRead">{{ chat.unRead }}</div>
        -->
      </div>
      <div class="content">
        <div class="title">{{ f.name }}</div>
        <div class="lastIncome">
          {{f.chat.lastMessage}}
        </div>
      </div>

      <div class="time">
        {{ f.chat.updatedAt}}
      </div>
    </div>
 



    <div class="chat"
      v-for="g in groups"
      :key="g.index"
      @click="inspect_group(g)">

      <div class="avatar">
        <img src="/img/sLogo1.jpg" />
        <!--
        <div v-if="chat.unRead != 0" class="unRead">{{ chat.unRead }}</div>
        -->
      </div>
      <div class="content">
        <div class="title">{{ g.groupName }}</div>
        <div class="lastIncome">{{ g.lastMessage}}</div>
      </div>

      <div class="time">
        {{g.updatedAt}}
      </div>
    </div>
 
    <!--
    <div class="chat"
      v-for="chat in chats"
      :key="chat.index"
      @click="inspect(chat)">

      <div class="avatar">
        <img :src=chat.avatar />
        <div v-if="chat.unRead != 0" class="unRead">{{ chat.unRead }}</div>
      </div>
      <div class="content">
        <div class="title">{{ chat.title }}</div>
        <div class="lastIncome">{{ chat.lastIncome }}</div>
      </div>

      <div class="time">
        {{chat.lastUpdateTime}}
      </div>
    </div>
    -->

  </div>
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'

import ws from '@/ws/ws.js'

export default {
  name: 'chats',
  components: {
  },
  data: () => {
    return {
      id: "",
      groups: [],
      friends: [],
      newFriends: [],
      chats: [
      ]
    }
  },
  methods: {
    inspect: function(a) {
      this.$router.push({name: "singleChat" , params: {data: a}})
    },
    inspect_group: function(a) {
      this.$router.push({name: "groupChat" , params: {data: a}})
    },
    getGroups: function() {
      console.log("getGroups")
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/core-api/group/search/")
      .then(resp => {
        this.groups = resp.data
        let groups = this.groups.map((g) =>{
          let ng = Object.create({})
          ng.id = g.id
          ng.name = g.groupName
          return ng
        })
        sessionStorage.setItem("groups", JSON.stringify(groups))
      })
    },
    getChats: function() {
      console.log("getChats")
      var that = this
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/core-api/friend/getChats")
      .then(resp => {
        if(resp.data == null) return Promise.reject("Null Chats")
        this.chats = resp.data.map((o) => {
          o.members = o.members.split(",").filter((o1) => {
            return o1 != that.id
          })[0]
          return o
        })

        that.newFriends = that.friends.map((f) => {
          if(resp.data == null) return Promise.reject("Null Freinds")
          let result = that.chats.findIndex((o) => {
            return o.members == f.id
          })
          if(result < 0) {
            console.log("error")
          } else {
            f.chat = that.chats[result]
          }
          return f
        })
        
      })
    },
    getFriends: function() {
      var that = this
      console.log("getFriends")
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/core-api/friend/getFriends/")
      .then(resp => {
        if (resp.status) {
          this.friends = resp.data.map((o) =>{
            o.name = o.username
            if(o.avatar !== "/img/header.jpeg") {
              o.avatar = "/resource-api/static/" + o.avatar
            }
            return o 
          })
          let friends = this.friends.map((f) => {
            let nf = Object.create({})
            nf.avatar = f.avatar
            nf.name = f.name
            nf.id = f.id
            return nf
          })
          sessionStorage.setItem("friends", JSON.stringify(friends))
          console.log(this.friends)
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }
      })
      .then(function(){return that.getChats()})
    },
    getUserInfo: function() {
      var that = this
      console.log("getUserInfo")
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/api/user/getUserInfoByToken/")
      .then(resp => {
        if (resp.status) {
          this.id = resp.data.id
          this.userInfo = resp.data
          this.name = resp.data.username
          this.phoneNumber = resp.data.phonenumber
          sessionStorage.setItem("id", resp.data.id)
          sessionStorage.setItem("avatar", resp.data.avatar)
          sessionStorage.setItem("username", this.name)
          sessionStorage.setItem("phonenumber", this.phoneNumber)
        }
      })
      .then(function(){return that.getFriends()})
 
    }
  },
  mounted: function() {
    this.getUserInfo()
    this.getGroups()
    // this.$store.commit('test');
    if(window.ws === undefined) {
      window.ws = ws
      window.ws.init();
      setTimeout(() => {
        window.ws.connect()
      }, 2000)
    }
  }
}
</script>

<style scoped lang="scss">
  #chats{
    height: 100vh;

    .avatar {
      grid-area: avatar;
      img{
        width: 100%;
        margin: 5px;
        border: 1px solid #EFEFEF;
      }
    }
    .content {
      text-align: left;
      grid-area: content;
      padding: 8px 8px 8px 18px;
    }

    .time {
      grid-area: time;
      font-size: 14px;
      padding-top: 20px;
      color: #999999;
    }
    .title{
      font-size: 16px;
    }
    .lastIncome{
      color: #999999;
      margin-top: 6px;
      font-size: 12px;
    }

    .chat{
      background-color: white;
      margin: 5px 5px 0px 5px;
      border-radius: 5px;
      display: grid;
      grid-template-columns: 1fr 5fr 2fr ;
      grid-template-areas: 
        "avatar content time";
      // grid-gap: 10px;
    }

    .unRead{
      position: absolute;
      left: 40px;
      margin-top: -56px;
      color: white;
      background-color: red;
      display: inline-block;
      font-size: 10px;
      line-height: 20px;
      border-radius: 10px;
      width: 20px;
      height: 20px;
      font-weight: bold;
    }

    #header {
      height: 46px;
    }

    #content {
      background-color: #cecece;
      position: fixed;
      bottom: 50px;
      top: 46px;
      left: 0px;
      right: 0px;

    }

    #footer {
      height: 50px;
      background-color: green;
    }
}

</style>
