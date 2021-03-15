<template>
  <div id="session_view">
    <h2>Session</h2>
    <hr />

    <!-- list section begin -->
    <div v-if="token">
    <h2>Current User</h2>
    <table class="table-bordered ">
      <tr>
        <th>Username</th>
        <th>Token</th>
        <th>Operation</th>
      </tr>

      <tr>
        <td>{{username}}</td>
        <td>{{token}}</td>
        <td>
          <van-button
            @click="logout()"
            type="danger">Logout</van-button>
        </td>
      </tr>

    </table>
    </div>
    <!-- list section end -->

    <!-- login section begin -->
    <div v-else>
    </div>
    <!-- login section end -->

  </div>
</template>

<script>
// @ is an alias to /src


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
        account: this.form_data.account,
        password: this.form_data.password
      })
      this.form_data = { account: "", password: ""}
    },
    logout: function(){
      this.$store.commit('logout', this.token)
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