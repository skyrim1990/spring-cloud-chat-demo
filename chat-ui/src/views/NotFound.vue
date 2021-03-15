<template>
  <div id="about">
    <h1>你所找的页面并不存在</h1>
    <van-button @click="toIndex()" >返回</van-button>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'About',
  components: {
  },
  methods: {
    toIndex: function() {
      location.href = "/cover"
    }
  }
}
</script>

<style scoped lang="scss">
#about {
  width: 100vw;
  height: 100vh;
  h1{
    margin: 0px;
    padding: 20vw 7vw;
  }
}

</style>
