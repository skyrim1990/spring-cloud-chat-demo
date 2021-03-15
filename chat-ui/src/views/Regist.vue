<template>
  <div class="regist">
    <van-nav-bar title="注册新用户"
      @click-left="onClickLeft"
      left-arrow />
    
    <van-form style="margin-top: 0px;padding:10px;" @submit="onSubmit">

      <van-field name="手机号" label="手机号" placeholder="手机号"
        v-model="phonenumber"
        :rules="[{ validator: phoneValidator, message: '请输入正确的手机号'}]" />

      <van-field class="space" name="昵称" label="昵称" placeholder="昵称"
        v-model="username"
        :rules="[{required: true, message: '请填写昵称'}]" />

      <van-field class="space" type="password" name="密码" label="密码"
        v-model="password"
        placeholder="密码"
        :rules="[{required: true, message: '请填写密码'}]" />

      <van-field class="space" name="radio" label="性别">
        <template #input>
          <van-radio-group v-model="gender" direction="horizontal">
            <van-radio name="1">男</van-radio>
            <van-radio name="2">女</van-radio>
          </van-radio-group>
        </template>
      </van-field>

      <van-field class="space" v-model="age" name="年龄" label="年龄"
        :rules="[{validator: ageValidator, message: '请输入正确的内容'}]"
        placeholder="年龄" />
      
      <div  style="margin-top: 10vh;">
        <van-button block type="info" native-type="submit">提交</van-button>
      </div>

    </van-form>

    <!--
    {{ username }} | {{ password}} | {{ gender }} | {{ age }} | {{ location }}
    -->
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'regist',
  components: {
  },
  data: () => {
    return {
      phonenumber: '',
      username: '',
      password: '',
      gender: '1',
      age: '',
      location: []
    }
  },
  methods: {
    phoneValidator: (a) => {
      return /^1[3-9]\d{9}$/.test(a);
    },
    ageValidator: (a) => {
      if(!isNaN(parseInt(a)) && a > 0) {
        return true
      }
      return false

    },
    onSubmit() {
      this.$store.commit('regist', {
        phonenumber: this.phonenumber,
        username: this.username,
        password: this.password,
        gender: this.gender,
        age: this.age
      })
      /*
     this.$notify({
        type: 'success',
        message: '注册成功 !'
      });

      setTimeout(() => {
        window.location = '/module/chats'
      }, 2000)
      */
    },
    onClickLeft: function() {
      this.$router.push({ path: '/cover'})
    }
  }
}
</script>

<style scoped lang="scss">
.regist{
  height: 100vh;
  .space{
    margin-top: 10px;
  }
}

</style>
