<template>
  <div id="editInfo">
   
    <div class="userInfo">
      <div class="inline-block avatar">
        <img :src=avatar onerror="this.src='/img/header.jpeg'" />
      </div>
      <div class="inline-block info">
          <div>昵称:   {{name}}</div>
          <div>手机号: {{phoneNumber}}</div>
      </div>
    </div>

      <div id="new-avatar">
        <div>新头像</div>
        <van-uploader 
          v-model="images"
          :after-read="afterRead" multiple :max-count="1" />
      </div>


    <van-form style="margin-top: 0px;padding:10px;" @submit="submit">

      <van-field class="space" name="新昵称" label="新昵称" placeholder="昵称"
        v-model="username"
        :rules="[{required: true, message: '请填写新昵称'}]" />


    </van-form>



    <div style="margin:10px 10px 10px 10px;">
      <van-button type="primary" @click="submit()" block>提交</van-button>
    </div>


    <div style="margin:10px 10px 10px 10px;">
      <van-button type="info" @click="back()" block>返回</van-button>
    </div>

  </div>
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'

export default {
  name: 'editInfo',
  components: {
  },
  data: () => {
    return {
      images: [],
      avatar: '/img/mLogo2.gif',
      name: '小强',
      phoneNumber: "17875462790",
      userInfo: null,
      username: "",
      newAvatar: ""
    }
  },
  methods: {
    afterRead: function (file) {
      console.log(file)

      var formData = new FormData();
      formData.append("file", file.file)
      formData.append("name", this.phoneNumber + "-头像")
      formData.append("note", "upload from chat ui")
      fetch("/resource-api/resources/insert",{
        method: 'POST',
        body: formData
      }).then((res) => {
        return res.json()
      }).catch(error=> console.log("Error: ",error))
      .then((resp) => {
        this.newAvatar = resp.data
        console.log(resp)
      })
    },
    back: function () {
      this.$router.go(-1)
    },
    submit: function() {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/api/user/updateProfile/" + this.username + "/" + this.newAvatar)
      .then(resp => {
        if (resp.status) {
          Notify({ type: 'success', message: resp.msg })
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }
      })
      .then(() => {
        this.$router.go(-1)
      })
 
    },
    getUserInfo: function() {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/api/user/getUserInfoByToken/")
      .then(resp => {
        if (resp.status) {
          this.userInfo = resp.data
          this.name = resp.data.username
          this.phoneNumber = resp.data.phonenumber
          this.avatar = "/resource-api/static/" + resp.data.avatar
        } else {
          Notify({ type: 'danger', message: resp.msg })
        }

      })
    },
    inspect: (a) => {
      console.log(a)
    },
    editInfo: () => {
      
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
  #editInfo{
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
    #new-avatar {
      background-color: white;
      display: flex;
      margin: 10px;
      padding: 15px;
      font-size: 15px;
      justify-content:  space-between;
      align-items:  center;
    }
  }
</style>

