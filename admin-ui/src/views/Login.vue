<template>
<div>
       <h2>登录 
         <!--
         | 
         <a 
          style="font-size:14px;"
          href="/regist">注册</a>
          -->
       </h2>
      <table class="table-bordered">
        <tr>
          <td>账号:</td>
          <td>
            <input class="cus-input" type="text"
              v-model="form_data.account" />
          </td>
        </tr>
        <tr>
          <td>密码:</td>
          <td>
            <input class="cus-input" type="password"
              v-model="form_data.password" />
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <van-button block type="info" @click="login()">
              登录
            </van-button>
          </td>
        </tr>
      </table>

</div>

</template>

<script>

export default {
  name: 'Session',
  data: function() {
    return {
      form_data: { account: "", password: ""}
    }
  },
  computed: {
    username: function(){
      return this.$store.state.sessionStore.username
    },
    token: function(){
      return this.$store.state.sessionStore.token
    }
  },
  components: {
  },
  methods: {
    login: function(){
      this.$store.commit('login', {
        phonenumber: this.form_data.account,
        password: this.form_data.password
      })
      this.form_data = { account: "", password: ""}
    },
    logout: function(){
      sessionStorage.removeItem("token");
      window.location = "/login"
      // this.$store.commit('logout', this.token)
    },
    onSubmit: function(){
      this.$store.commit('add', this.form_data)
      this.form_data = { title: "", isDone: "0", description: ""}
    },
    remove: function(id){
      this.$store.commit('remove', id)
    },
    nextPage: function(){
      this.$store.commit('search', this.$store.state.questStore.index)
    },
    lastPage: function(){
      this.$store.commit('search', this.$store.state.questStore.index - 2)
    }
  },
  beforeCreate: function() {
  }
}
</script>

<style scoped lang="scss">
#session_view{
  
}
</style>