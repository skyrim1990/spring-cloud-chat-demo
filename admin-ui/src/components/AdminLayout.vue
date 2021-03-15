<template>
  <div id="AdminLayout">
    <div id="avatar">
      <span id="logo">Logo</span>
    </div>
    <div id="header">
      <span id="logout" @click="logout()">退出</span>
    </div>
    <div id="menu">
    <div id="nav">
      <router-link to="/">个人中心</router-link>
      <router-link to="/user">用户管理</router-link>
      <router-link to="/role">角色管理</router-link>
      <router-link to="/message">消息控制</router-link>
      <router-link to="/resource">资源管理</router-link>
      <!--
        <router-link to="/quest">代办事项</router-link>
      -->
    </div>
    </div>
    <div id="content">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminLayout',
  methods: {
    logout: function(){
      sessionStorage.removeItem("token")
      location.href="/login"
    }
  },
  props: {
  }
}
</script>

<style scoped lang="scss">
#nav {

  a {
    display: block;
    font-weight: bold;
    color: #3498D8;
    line-height: 40px;

    &.router-link-exact-active {
      background-color: #34495E;
      color: #F1C40F;
    }
  }
}
#logo{
  line-height: 60px;
  float:left;
  margin-left: 60px;
  color: white;
  font-weight: bold;
  font-size: 28px;
  letter-spacing: 5px;
}
#logout{
  line-height: 60px;
  float:right;
  margin-right: 20px;
  color: black;
  cursor: pointer;
  &:hover{
    color: red;
  }
}
#AdminLayout{
  display: grid;
  grid-template-columns: 200px auto;
  grid-template-rows:60px auto;
  grid-template-areas:
    "avatar header"
    "menu content";
  position: relative;
  width: 100vw;
  height: 100vh;
}
#avatar{
  grid-area: avatar;
  background-color: #1f2c3a;
}
#header{
  grid-area: header;
  background-color: #ECF0F1;
}
#menu{
  background-color: #2C3E50;
}
#content{
  overflow: auto;
  grid-area: content;
  padding: 10px;
}
</style>
