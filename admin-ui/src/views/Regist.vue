<template>
  <div id="regist_view">
    <!-- Inser Section begin -->
    <div>
    <h2>注册 | 
      <a 
        style="font-size:14px;"
        href="/login">登录</a></h2>
    <table class="table-bordered">
      <tr>
        <td>账号: </td>
        <td>
          <input class="cus-input" type="text" v-model="account" />
        </td>
      </tr>
      <tr>
        <td>密码: </td>
        <td>
          <input class="cus-input" type="password" v-model="password" />
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <van-button block type="info" @click="regist()">
            注册
          </van-button>
        </td>
      </tr>
    </table>
    </div>
    <!-- Inser section end -->
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'regist',
  data: function() {
    return {
      password: "",
      account: ""
    }
  },
  methods:{
    regist: function(){
      this.$store.commit('registUser', { account: this.account, password: this.password})
      this.password = ""
      this.account = ""
    },
  },
  mounted: function() {
  }
}
</script>
<style scoped lang="scss">

#regist_view{
  
}
</style>