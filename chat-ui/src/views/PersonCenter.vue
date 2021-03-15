<template>
  <div id="personCenter">
   
    <div class="userInfo">
      <div class="inline-block avatar">
        <img :src=avatar onerror="this.src='/img/header.jpeg'" />
      </div>
      <div class="inline-block info">
          <div>昵称:   {{name}}</div>
          <div>手机号: {{phoneNumber}}</div>
      </div>
    </div>

    <div style="margin:10px 10px 10px 10px;">
      <van-button type="default" @click="editInfo()" block>修改个人信息</van-button>
    </div>


    <div style="margin:10px 10px 10px 10px;">
      <van-button type="danger" @click="logout()" block>退出</van-button>
    </div>

  </div>
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'

export default {
  name: 'personCenter',
  components: {
  },
  data: () => {
    return {
      avatar: '',
      name: '',
      phoneNumber: "",
      userInfo: null
    }
  },
  methods: {
    getUserInfo: function() {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/api/user/getUserInfoByToken/")
      .then(resp => {
        if (resp.status) {
          this.userInfo = resp.data
          this.name = resp.data.username
          this.phoneNumber = resp.data.phonenumber
          if(resp.data.avatar !== "/img/header.jpeg") {
            this.avatar = "/resource-api/static/" + resp.data.avatar
          } else {
            this.avatar = resp.data.avatar
          }
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }

      })
    },
    inspect: (a) => {
      console.log(a)
    },
    editInfo: function () {
      this.$router.push({path: '/module/editInfo'})
    },
    logout: function () {
      this.$dialog.confirm({
        title: "警告",
        message: "确定要退出吗?"
      }).then(() => {
        sessionStorage.removeItem("token");
        location="/cover"
      }).catch(() => {
        console.log("系统错误")
      })
    }
  },
  mounted: function() {
    console.log("Mounted !")
    this.getUserInfo();
  }
}
</script>

<style scoped lang="scss">
  #personCenter{
    width: 100%;
    height: 100%;
    .userInfo {
      background-color: white;
      margin: 10px;
      padding: 10px;
      text-align: left;
      .avatar{
      }
      .info{
        position: relative;
        top: -20px;
        left: 40px;
      }
      img{
        width: 80px;
        border: 1px solid #EFEFEF;
      }
      div{
      }
    }
  }
</style>

