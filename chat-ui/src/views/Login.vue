<template>
  <div class="login">
    <van-nav-bar title="登录"
      @click-left="onClickLeft"
      left-arrow />
    
    <van-form style="margin-top: 0px;padding:10px;" @submit="onSubmit">

      <van-field name="手机号" label="手机号" placeholder="手机号"
        v-model="phoneNumber"
        :rules="[{ validator: phoneValidator, message: '请输入正确的手机号'}]" />

      <van-field class="space" type="password" name="密码" label="密码"
        v-model="password"
        placeholder="密码"
        :rules="[{required: true, message: '请填写密码'}]" />
     
      <div  style="margin-top: 10vh;">
        <van-button block type="info" native-type="submit">提交</van-button>
      </div>

    </van-form>

    <!--
    {{ phoneNumber }} | {{ password}} 
    -->
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'login',
  components: {
  },
  data: () => {
    return {
      phoneNumber: '',
      password: '',
    }
  },
  methods: {
    phoneValidator: (a) => {
      return /^1[3-9]\d{9}$/.test(a);
    },
    onSubmit() {
      this.$store.commit('login', {
        phonenumber: this.phoneNumber,
        password: this.password
      })
      /*
      this.phoneNumber = ''
      this.password = ''
     this.$notify({
        type: 'success',
        message: '欢迎' + this.phoneNumber + " !"
      });
      */

      /*
      setTimeout(() => {
        window.location = '/module/chats'
      }, 2000)
      */
    },
    onClickLeft: function() {
      this.$router.push({path: '/cover'})
    }
  }
}
</script>

<style scoped lang="scss">
.login{
  height: 100vh;
  .space{
    margin-top: 10px;
  }
}

</style>
