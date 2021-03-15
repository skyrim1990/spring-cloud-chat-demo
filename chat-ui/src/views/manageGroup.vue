<template>
  <div id="manageGroup">
    <van-nav-bar 
      title="群聊管理"
      left-text="返回"
      left-arrow
      right-text="邀请好友"
      @click-right="invite()"
      @click-left="back()" />

    <div class="container">
      <div class="friend"
        v-for="friend in friends"
        :key="friend.index">

        <div class="avatar">
          <img :src=friend.avatar />
        </div>

        <div class="content">
          {{friend.name}}
        </div>

        <div class="btn">
          <van-switch size="20" v-model="friend.selected" />
        </div>

      </div>
    </div>


    <div class="submit">
      <van-button type="danger"
        @click="removeFriend()" block>移除好友</van-button>
    </div>

    

  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'manageGroup',
  data: function() {
    return {
      obj: {},
      value: '',
      groupId: '1',
      friends: [
        {avatar: "/img/mLogo1.gif",   name: '小猪', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
        ,{avatar: "/img/mLogo2.gif",  name: '小狗', selected: false }
      ]
 
    }
  },
  methods: {
    onSearch: function(){
      console.log(this.value)
    },
    back: function(){
      window.history.back()
    },
    invite() {
      location.href = "/groupAddFriend/" + this.groupId
    },
    removeFriend: function() {
      var result = this.friends.filter((o) => {
        return o.selected == true
      })
      if(result.length != 0) {
        this.$toast.success("好友已移除")
        /*
        setTimeout(() => {
          history.back()
        }, 2000 );
        */
      } else {
        this.$toast.fail("请选择成员")
      }

    }
  },
  mounted: function() {
    this.groupId = this.$route.params.id | 0 
  }

}
</script>

<style scoped lang="scss">
#manageGroup{
  width: 100vw;
  height: 100vh;
  .container{
    // border: 1px solid black;
    position: absolute;
    top: 46px;
    bottom: 54px;
    overflow: auto;
  }
  .friend{
    background-color: white;
    margin: 5px 5px 0px 5px;
    border-radius: 5px;
    display: grid;
    grid-template-columns: 1fr 5fr 2fr ;
    grid-template-areas: 
      "avatar content btn";
    // grid-gap: 10px;
  }
  .btn{
    grid-area: btn;
    padding:15px 10px 10px 0px;

  }
  .content {
    text-align: left;
    grid-area: content;
    margin-top: 10px;
    padding: 8px 8px 8px 28px;
  }
  .avatar {
    grid-area: avatar;
    img{
      width: 100%;
      margin: 5px;
      border: 1px solid #EFEFEF;
    }
  }
  .submit{
    padding: 5px;
    position: fixed;
    bottom: 0px;
    left:0px;
    right: 0px;
  }
}

</style>
