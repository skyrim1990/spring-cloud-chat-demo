<template>
  <div id="addFriend">
    <van-nav-bar 
      title="添加好友"
      left-text="返回"
      left-arrow
      @click-left="back()" />

    <van-search v-model="value"
      style="margin:5px 5px 0px 5px;"
      show-action
      placeholder="请输入好友昵称或者手机号"
      @search="onSearch"
      >

      <template #action>
        <div @click="onSearch">搜索</div>
      </template>

    </van-search>

    <div class="container">

    <div class="friend"
      v-for="friend in friends"
      :key="friend.index">

      <div class="avatar">
        <img :src=friend.avatar />
      </div>
      <div class="content">
        {{friend.name}}
      </div>
      <div class="btn">
        <van-button type="primary"
          size="small"
          @click="sendRequest(friend.id)"
          >发送请求</van-button>
      </div>
    </div>

    </div>

    

  </div>
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'


// @ is an alias to /src
export default {
  name: 'addFriend',
  data: function() {
    return {
      value: '',
      friends: [

     ]
 
    }
  },
  methods: {
    onSearch: function(){
      console.log(this.value)
      if(this.value == "") {
          Notify({ type: 'danger', message: "请输入好友名称或手机号" })
        return;
      }
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/core-api/friend/search/" + this.value)
      .then(resp => {
        if (resp.status) {
          this.friends = resp.data.map((o) =>{
            o.name = o.username
            if(o.avatar !== "/img/header.jpeg") {
              o.avatar = "/resource-api/static/" + o.avatar
            }
            return o 
          })
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }

      })

    },
    back: function(){
      window.history.back()
    },
    toIndex: function() {
      location.href = "/cover"
    },
    sendRequest: function(id) {
      let payload = {
        receiverId: id,
        senderName: sessionStorage.getItem("username"),
        groupName: "",
        groupId: "",
        invitationType: 0, // 好友请求
        handled: 0
      } 
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/core-api/friend/inviteFriend/", payload)
      .then(resp => {
        if (resp.status) {
          this.$toast.success("邀请已发送")
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }

      })


    }
  }
}
</script>

<style scoped lang="scss">
#addFriend{
  width: 100vw;
  height: 100vh;
    .container{
    // border: 1px solid black;
    position: fixed;
    top: 106px;
    bottom: 5px;
    overflow: auto;
  }
  .friend{
    background-color: white;
    margin: 5px 5px 0px 5px;
    border-radius: 5px;
    display: grid;
    grid-template-columns: 1fr 5fr 2fr ;
    grid-template-areas: 
      "avatar content btn";
    // grid-gap: 10px;
  }
  .btn{
    grid-area: btn;
    padding:15px 10px 10px 0px;

  }
  .content {
    text-align: left;
    grid-area: content;
    margin-top: 10px;
    padding: 8px 8px 8px 28px;
  }
  .avatar {
    grid-area: avatar;
    img{
      width: 100%;
      margin: 5px;
      border: 1px solid #EFEFEF;
    }
  }
}

</style>
