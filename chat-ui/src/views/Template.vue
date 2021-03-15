<template>
  <div id="module">
    <div id="header">
      <van-nav-bar :title=title>
        <template #right>
        <van-popover v-model="showPopOverMenu"
          theme="dark"
          :offset=offset
          placement="bottom-end"
          trigger="click"
          :actions="actions"
          @select="onSelect">
          <template #reference>
            <van-icon name="add-o" size="18" style="margin-top:8px;" />
          </template>
        </van-popover>
        </template>
      </van-nav-bar> 
    </div>

    <div id="content">
      <router-view></router-view>
    </div>

    <div id="footer">
      <van-tabbar v-model="active">
        <van-tabbar-item @click="go('/module/chats', 0)" icon="comment-o">信息</van-tabbar-item>
        <van-tabbar-item @click="go('/module/friends', 1)" icon="friends-o">通讯录</van-tabbar-item>
        <!--
        <van-tabbar-item @click="go('/module/notes', 2)" icon="records">手账</van-tabbar-item>
        -->
        <van-tabbar-item @click="go('/module/personCenter', 2)" icon="contact">个人中心</van-tabbar-item>
      </van-tabbar>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'chats',
  components: {
  },
  data: function() {
    return {
      offset: [15, 20],
      active: 0,
      actions: [
        {text: '添加好友', action: 'addFriend', icon: 'add-o'},
        {text: '发起群聊', action: 'addGroup', icon: 'cluster-o'}
      ],
      showPopOverMenu: false
    }
  },
  computed: {
    title: () => {
      let name = location.pathname.slice(8)
      if(name === 'chats') {
        return '信息'
      }
      if(name === 'friends') {
        return '通讯录'
      }
      if(name === 'notes') {
        return '手账'
      }
      if(name === 'personCenter') {
        return '个人中心'
      }
      if(name === 'editInfo') {
        return '修改个人信息'
      }
 
      return "";
    }
  },
  methods: {
    go: function (url)  {
      this.$router.push({ path: url})
    },
    onSelect(action) {
      // this.$toast(action.text);
      if(action.action == 'addFriend') {
        this.$router.push({ path: "/addFriend"})
      }
      if(action.action == 'addGroup') {
        this.$router.push({ path: "/addGroup"})
      }
    },
    phoneValidator: (a) => {
      return /^1[3-9]\d{9}$/.test(a);
    },
  },
  mounted: function() {
      let name = location.pathname.slice(8)
      if(name === 'chats') {
        this.active = 0 
      }
      if(name === 'friends') {
        this.active = 1 
      }
      /*
      if(name === 'notes') {
        this.active = 2 
      }
      */
      if(name === 'personCenter') {
        this.active = 2
      }
 
  }
}
</script>

<style scoped lang="scss">
  #module{
    .van-icon {
      color: black !important;
    }
    background-color: #EFEFEF;
    height: 100vh;

    #header {
      height: 46px;
    }

    #content {
      position: fixed;
      background-color: #cecece;
      bottom: 50px;
      top: 46px;
      left: 0px;
      right: 0px;
      overflow: auto;
    }

    #footer {
      height: 50px;
    }
}

</style>
