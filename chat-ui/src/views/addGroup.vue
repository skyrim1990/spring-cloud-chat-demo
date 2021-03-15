<template>
  <div id="addGroup">
    <van-nav-bar 
      title="发起群聊"
      left-text="返回"
      left-arrow
      @click-left="back()" />

    <div class="container">

      <van-form style="margin:10px 5px">
        <van-field class="space" name="群名称" label="群名称" placeholder="群名称"
          v-model="groupName"
          :rules="[{required: true, message: '请填写群名称'}]" />
      </van-form>





      <div class="friend"
        v-for="friend in friends"
        :key="friend.index">

        <div class="avatar">
          <img 
          alt="/img/header.jpeg"
          :src=friend.avatar />
        </div>

        <div class="content">
          {{friend.name}}
        </div>

        <div class="btn">
          <van-switch size="20" v-model="friend.selected" />
        </div>

      </div>
    </div>


    <div class="submit">
      <van-button type="primary"
        @click="sendRequest()" block>群发邀请</van-button>
    </div>

    

  </div>
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'

// @ is an alias to /src
export default {
  name: 'addGroup',
  data: function() {
    return {
      obj: {},
      groupName: '',
      friends: [
      ]
 
    }
  },
  methods: {
    onSearch: function(){
      console.log(this.value)
    },
    back: function(){
      window.history.back()
    },
    toIndex: function() {
      location.href = "/cover"
    },
    getFriends: function () {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/core-api/friend/getFriends/")
      .then(resp => {
        if (resp.status) {
          this.friends = resp.data.map((o) =>{
            o.name = o.username
            o.selected = false
            return o 
          })
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }
      })
    },
    sendRequest: function() {
      if(this.groupName == "") {
          Notify({ type: 'danger', message: "群名称不能为空 !" })
      }
      if(this.friends.length == 0) {
        Notify({ type: 'danger', message: "请选择好友"})
        return 
      }

      var result = this.friends.filter((o) => {
        return o.selected == true
      }).map((o) => {
        return o.id;
      }).join(",")

      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/core-api/group/add/", 
        {
          name: this.groupName,
          ids: result
        })
      .then(resp => {
        if (resp.status) {
          Notify({ type: 'success', message: resp.msg })
          setTimeout(() => {
          this.$router.go(-1)
        }, 2000 );
 
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }
      })
    }
  },
  mounted: function() {
    this.getFriends()
  }
}
</script>

<style scoped lang="scss">
#addGroup{
  width: 100vw;
  height: 100vh;
  .container{
    // border: 1px solid black;
    position: absolute;
    top: 46px;
    bottom: 54px;
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
  .submit{
    padding: 5px;
    position: fixed;
    bottom: 0px;
    left:0px;
    right: 0px;
  }
}

</style>
