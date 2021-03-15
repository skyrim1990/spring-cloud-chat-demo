<template>
  <div id="friends">

    <div v-if="requests.length != 0">

    <div class="chat"
      v-for="request in requests"
      :key="request.index"
      @click="confirm(request)">

      <div class="avatar">
        <img :src=request.avatar />
      </div>
      <div class="content">
        {{request.name}} <span style="color:#999999;">请求添加你为好友</span>
      </div>
    </div>



    </div>

    <div class="chat"
      v-for="friend in friends"
      :key="friend.index"
      @click="inspect(friend)">

      <div class="avatar">
        <img :src=friend.avatar />
      </div>
      <div class="content">
        {{friend.name}} 
        {{friend.phonenumber}}
      </div>
      <div class="time">
        <van-button style="margin-top:15px;"
          @click="removeFriend(friend.id)"
          type="danger" size="small">删除好友</van-button>
      </div>
    </div>

  </div>
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'


export default {
  name: 'friends',
  components: {
  },
  data: () => {
    return {
      friends: [
        // {avatar: "/img/mLogo1.gif",   name: '小猪' }
        // ,{avatar: "/img/mLogo2.gif",  name: '小狗' }
      ],

      requests: [
        // {avatar: "/img/mLogo1.gif",   name: '小鸭' }
        // ,{avatar: "/img/mLogo1.gif",   name: '小明' }
      ]
    }
  },
  methods: {
    getFriends: function () {
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
 
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }
      })
    },
    getInvitation: function() {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/core-api/friend/getInvitations/")
      .then(resp => {
        if (resp.status) {
          this.requests = resp.data.map((obj) => {
            obj.name = obj.senderName
            obj.avatar = "/img/mLogo2.gif"
            return obj;
          }).filter((obj) => {
            return obj.handled == 0;
          })
          
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }
      })
 

    },
    inspect: (a) => {
      console.log(a)
    },
    removeFriend: function(id) {

      var that = this
      this.$dialog.confirm({
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        title: "警告", 
        message: "确定要删除好友吗 ?"})
        .then(() => {
          request
            .addHeader("auth", sessionStorage.getItem("token"))
            .delete("/core-api/friend/remove/" + id)
            .then(resp => {
              if (resp.status) {
                Notify({ type: 'success', message: resp.msg })
                that.getFriends()
                that.getInvitation()
              } else {
                Notify({ type: 'danger', message: resp.msg })
              }
            })
        })
        .catch(() => {
          console.log("删除好友失败!")
        })
 
    },
    confirm: function(a) {
      var that = this
      this.$dialog.confirm({
        cancelButtonText: "拒绝",
        confirmButtonText: "接受",
        title: "好友请求", 
        message: "接受" + a.name + "的好友添加请求?"})
        .then(() => {
          request
            .addHeader("auth", sessionStorage.getItem("token"))
            .get("/core-api/friend/acceptFriend/"
              + a.senderId + "/" + a.id
            )
            .then(resp => {
              if (resp.status) {
                Notify({ type: 'success', message: resp.msg })
                that.getInvitation()
                that.getFriends()
              } else {
                Notify({ type: 'danger', message: resp.msg })
              }
            })
 
          console.log("添加好友成功!")
        })
        .catch(() => {
          console.log("添加好友失败!")
        })
    }
  },
  mounted: function() {
    this.getFriends();
    this.getInvitation();
  }
}
</script>

<style scoped lang="scss">
  #friends{
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
      margin-top: 10px;
      padding: 8px 8px 8px 28px;
      overflow: auto;
    }
    .chat{
      background-color: white;
      margin: 5px 5px 0px 5px;
      border-radius: 5px;
      display: grid;
      grid-template-columns: 1fr 5fr 2fr ;
      grid-template-areas: 
        "avatar content time state";
      // grid-gap: 10px;
    }
}

</style>

