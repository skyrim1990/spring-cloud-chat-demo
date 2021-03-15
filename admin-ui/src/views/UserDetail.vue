<template>
  <div id="userDetail">
    <admin-layout>
    <h2>用户详情</h2>

    <table class="table-bordered full">
      <tr>
        <th>角色名称</th>
        <th>操作</th>
      </tr>


      <tr v-for="r in userRoles" :key=r.index>
        <td>{{ r.roleName }}</td>
        <td>
          <van-button
            @click="removeRole(r.id)"
            type="danger">
            删除 
          </van-button>
        </td>
      </tr>

    </table>

    <h2>添加角色</h2>
    <div class="cus-section">
      <select class="cus-select"
        style="margin-right:10px;"
        v-model=selectedRole>
        <option v-for="r in roles"
          :value=r.id
          :key=r.index>
          {{r.name}}
        </option>
      </select>

      <van-button
        @click="addRole()"
        type="primary">添加</van-button>

    </div>
    </admin-layout>
  </div>
</template>

<script>
import request from '@/utils/request.js'
import { Notify } from 'vant'
import AdminLayout from '../components/AdminLayout.vue'

export default {
  components: { AdminLayout },
  name: 'UserDetail',
  data: function(){
    return {
      selectedRole: 1,
      userId: '',
      userRoles: [],
      roles: []
    }
  },
  methods: {
    getUserRoles: function () {
      var that = this
        request
        .addHeader("auth", sessionStorage.getItem("token"))
        .get("/auth/user/getRoles/" + that.userId)
        .then(resp => {
          if(resp.status) {
            that.userRoles = resp.data
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    },
    getRoles: function() {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/auth/role/findRoles")
        .then(resp => {
          this.roles = resp.data
        })
 
    },
    removeRole: function(id) {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .delete("/auth/user/removeUserRole/" + id)
        .then(resp => {
          if(resp.status) {
            Notify({type: 'success', message: resp.msg})
            this.getUserRoles()
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
 
 
    },
    addRole: function() {
        request
        .addHeader("auth", sessionStorage.getItem("token"))
        .get("/auth/user/addRole/" + this.userId + "/" + this.selectedRole)
        .then(resp => {
          if(resp.status) {
            Notify({type: 'success', message: resp.msg})
            this.getUserRoles()
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    }
  },
  mounted: function () {
    this.userId = this.$route.params.id
    this.getRoles()
    this.getUserRoles()
  }
}

</script>

<style scoped lang="scss">
#userDetail{

}
</style>