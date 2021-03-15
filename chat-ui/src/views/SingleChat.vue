<template>
  <div class="singleChat">

    <van-nav-bar :title=friendName
      left-text="返回"
      left-arrow
      @click-left="back()" />

    <div id="content">

      <div v-for="m in messages"
        :key="m.index">
        <div  v-bind:class="{msg: !m.sendByMe, me: m.sendByMe}">
          <div class="avatar">
            <img :src=m.avatar onerror="this.src='/img/header.jpeg'"  />
          </div>
          <div class="name">{{m.name}}</div>
          <div class="content">{{m.content}}</div>
        </div>
      </div>

    </div>

    <div id="footer">
      <div id="input">
        <van-field class="input" v-model="input"/>
      </div>
      <div id="btn">
        <van-button class="button"
          type="primary"
          @click="send()">
          发送
        </van-button>
      </div>
   </div>
  </div>
</template>

<script>
// import request from '@/utils/request.js'
// import { Notify } from 'vant'

// @ is an alias to /src
export default {
  name: 'singleChat',
  data: function() {
    return {
      friendName: '',
      input: '',
      messages: [
      ]
    }
  }, 
  components: {
  },
  methods: {
    back: function() {
      this.$router.go(-1)
    },
    send: function() {
      var msg = {
        from: sessionStorage.getItem("token"),
        to:  this.friendId,
        content: this.input
      };
      window.ws.sendMsg("/app/send", msg)
      this.input = ''
    }, 
    hello: function() {
      console.log("hello")
    }
  },
  mounted: function() {
    var that = this
    window.bus.subscribe('singleChat', function(param){
      console.log(param)
      var m = {
        avatar: param.senderAvatar,
        name: param.senderName,
        content: param.content,
        sendByMe: param.from == sessionStorage.getItem("id") 
      }
      that.messages.push(m)
      setTimeout(function(){
        that.$el.childNodes[1].scrollTo(0, 1000)
      },100)
    })
  
    this.friendName = this.$route.params.data.name
    this.friendId = this.$route.params.data.id
    console.log(this.$route.params.data)
  }
}
</script>

<style scoped lang="scss">
.singleChat{
  #content{
    border-top: 1px solid #efefef;
    border-bottom: 1px solid #efefef;
    padding-bottom: 50px;
    position: fixed;
    overflow: auto;
    top: 46px;
    left: 0px;
    right: 0px;
    bottom: 54px;
  }

  .me{
    float: right;
    grid-template-areas: "name avatar" "content avatar";
    margin: 20px 20px 0px 20px;
    width: 280px;
    border: 1px solid #EFEFEF;

    display: grid;
    grid-template-columns: 5fr 1fr;
    grid-template-rows:1fr 1fr;
    grid-row-gap: 5px;
    .avatar{
      grid-area: avatar;
      img{
        width: 50px;
        height: 50px;
        margin: 5px;
      }
    }
    .name{
      grid-area: name;
      color: #999999;
      font-size: 12px;
      padding-top: 10px;
      text-align: right;
    }
    .content{
      grid-row: content;
      text-align: right;
      font-weight: bold;
      font-size: 16px;
      line-height: 16px;
      overflow: auto;
    }


  }
  .msg{
    margin: 20px 20px 0px 20px;
    width: 280px;
    border: 1px solid #EFEFEF;

    display: grid;
    grid-template-columns: 1fr 5fr;
    grid-template-rows:1fr 1fr;
    grid-row-gap: 5px;
    grid-template-areas: "avatar name " "avatar content";
    .avatar{
      grid-area: avatar;
      img{
        width: 50px;
        height: 50px;
        margin: 5px;
      }
    }
    .name{
      grid-area: name;
      color: #999999;
      font-size: 12px;
      padding-top: 10px;
      text-align: left;
    }
    .content{
      grid-row: content;
      text-align: left;
      font-weight: bold;
      font-size: 16px;
      line-height: 16px;
      overflow: auto;
    }

  }

  #footer{
    position: fixed;
    left: 0px;
    right: 0px;
    bottom: 0px;
    padding: 5px;

    display: grid;
    grid-template-columns: 5fr 1fr;
    grid-row-gap: 5px;
    grid-column-gap: 5px;
    background-color: #EFEFEF;
    
    .input{
      display: inline-block;
      width: 100%;
      vertical-align: bottom;
    }
    .button{
      width: 100%;
    }
  }
}
</style>