<template>
  <div id="resource_view">
    <admin-layout>
      <h2>资源管理</h2>

      <div>
        <table class="table-bordered full">
          <tr>
            <th>Id</th>
            <th>展示</th>
            <th>名称</th>
            <th>注释</th>
            <th>创建时间</th>
            <th>更新时间</th>

          </tr>
          <tr v-for="r in resources"
            :key=r.index>
            <td>{{ r.id }}</td>
            <td>
              <img class="cus-image" :src=trans(r.serialname)>
            </td>
            <td>{{ r.name }}</td>

            <td>{{ r.note }}</td>
            <td>{{ r.createdAt }}</td>
            <td>{{ r.updatedAt }}</td>
            
          </tr>


        </table>
      </div>

      <div>
        <h2>上传资源</h2>

        <form id="form" method="POST" 
          enctype="multipart/form-data">

        <div class="cus-row"> 
          
          <div class="cus-label" style="margin-right:10px;">名称</div>
          <input class="text-input" 
            style="margin-right:10px;"
            type="text" name="name" />

          <input type="file" name="file" />
        </div>

        <div class="cus-row">
          <div class="cus-label" style="margin-right:10px;">注释</div>
          <textarea 
            style="width:100%;min-height:100px;"
            name="note"></textarea>
        </div>
        </form>

        <div>
          <van-button 
          @click="uploadFile()"
          plain type="primary">上传</van-button>
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
      resources: []

    }
  },
  methods: {
    findAllResources: function(){
      var that = this
        request
        .get("/resource-api/resources/findAll")
        .then(resp => {
          if(resp.status) {
            // Notify({type: 'success', message: resp.msg})
            that.resources = resp.data
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
 
    },
    uploadFile: function() {
      var form = document.getElementById("form")
      var formData = new FormData(form);

      fetch("/resource-api/resources/insert",{
        method: 'POST',
        body: formData
      }).then((res) => {
        return res.json()
      }).catch(error=> console.log("Error: ",error))
      .then((resp) => {
        console.log(resp)
        this.findAllResources()
      })

    },
    trans: function(url) {
      if(process.env.NODE_ENV === 'development') {
        return "/resource-api/static/" + url
      } else {
        return "/resource-api/static/" + url
      }
    }

  },
  mounted: function() {
    this.findAllResources()
  }
}
</script>

<style scoped lang="scss">
#resource_view{

  #im{
    display: flex;
    flex-direction: right;
    border: 1px solid #999999;
    padding: 10px;
    vertical-align: middle;
  }

  .cus-image {
    width: 200px;
  }

}

</style>