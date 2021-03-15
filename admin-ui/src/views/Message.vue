<template>
  <div id="message_view">
    <admin-layout>
      <h2>消息控制</h2>

      <div id="im" class="cus-row">
        <div class="cus-label">推送即时消息</div>
        <div><input 
            v-model="im_message"
          style="margin:0px 10px;"
          class="text-input" type="text" />
        </div>
        <div>
          <van-button plain 
            type="info"
            @click="send_im_message()"
            >推送
          </van-button>
        </div>
      </div>


    </admin-layout>
  </div>
  
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'

export default {
  data: function() {
    return {
      im_message: ""

    }
  },
  methods: {
    send_im_message: function() {
      var payload = {
        from: "system",
        to: "all",
        type: "system_info",
        content: this.im_message
      }
      var that = this
        request
        .addHeader("auth", sessionStorage.getItem("token"))
        .post("/message-api/systemMessage/", payload )
        .then(resp => {
          if(resp.status) {
            Notify({type: 'success', message: resp.msg})
            that.im_message = ""
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
 
    }

  },
  mounted: function() {

  }
}
</script>

<style scoped lang="scss">
#message_view{

  #im{
    display: flex;
    flex-direction: right;
    border: 1px solid #999999;
    padding: 10px;
    vertical-align: middle;
  }

}

</style>