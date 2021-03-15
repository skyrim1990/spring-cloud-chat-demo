<template>
    <div class="Cover">
      <div id="name">微聊</div>
      <van-button id="btn-login"
                  @click="jumpTo('/login')">登录</van-button>
      <van-button id="btn-regist" type="primary"
                  @click="jumpTo('/regist')" 
                  >注册</van-button>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'Cover',
  components: {
  },
  methods: {
    jumpTo: function(location) {
      window.location= location;
    }
  }
}
</script>

<style scoped lang="scss">

.Cover {
    width: 100vw;
    height: 100vh;
    background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
    background-size: 400vw 400vh;
    animation: gradient 8s ease infinite;
    overflow: auto;
    position: fixed;
}

  #name {
    position: relative;
    font-size: 15vw;
    color: white;
    margin-top: 30vh;
  }

  #btn-login {
    margin-top: 40vh;
    width: 30vw;
    font-size: 5vw;
    margin-right: 6vw;
  }
  #btn-regist {
    width: 30vw;
    font-size: 5vw;
    margin-left: 6vw;
  }

@keyframes gradient {
    0% { background-position: 0% 50%; }
    50% { background-position: 100% 50%; }
    100% { background-position: 0% 50%; }
    
}

</style>

