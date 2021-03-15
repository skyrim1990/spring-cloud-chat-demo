<template>
  <div id="role_view">
    <admin-layout>
    <h2>角色管理</h2>

    <div class="table-row">
      <van-button type="primary" @click="trigger('show', true)()">新增角色</van-button>
    </div>

    <table class="table-bordered full" style="margin-bottom:10px;">

      <tr>
        <th>ID</th>
        <th>名称</th>
        <th>人数</th>
        <th>操作</th>
      </tr>

      <tr v-if="roles.length === 0">
        <td class="blank" colspan="4">暂无数据</td>
      </tr>

      <tr v-for="role in roles" v-bind:key=role.index>
        <td>{{ role.id }}</td>
        <td>{{ role.name }}</td>
        <td>{{ role.count }}</td>
        <td>
          <van-button
            @click="remove(role.id)"
            type="danger">
            删除 
          </van-button>
        </td>
      </tr>

    </table>

    <!-- Inser Section begin -->
    <cus-window title="新增角色" v-if="show">
    <div>
    <table class="table-bordered">
      <tr>
        <td>角色名称: </td>
        <td>
          <input class="cus-input" type="text" v-model="newRoleName" />
        </td>
        <td>
          <van-button type="primary" @click="submit()">
            新增
          </van-button>
        </td>
        <td>
          <van-button type="danger" @click="trigger('show', false)()">
            取消
          </van-button>
        </td>
      </tr>
    </table>
    </div>
    </cus-window>

    <!-- Inser section end -->

    </admin-layout>
  </div>
</template>

<script>
// @ is an alias to /src
import { Notify } from 'vant'

export default {
  name: 'Role',
  data: function(){
    return {
      newRoleName: "",
      show: false 
    }
  },
  computed: {
    roles: function() {
      return this.$store.state.roleStore.roles
    },
    pageIndex: function() {
      return this.$store.state.roleStore.index
    },
    pageTotal: function() {
      return this.$store.state.roleStore.total
    }
  },
  methods: {
    submit: function(){
      if(this.newRoleName === '') {

        Notify({type: 'danger', message: '角色名称不能为空 !'})
        return
      }
      this.$store.commit('addRole', 
        this.newRoleName
      );
      this.show= false
      this.newRoleName = ""
    },
    remove: function(id){
      this.$store.commit('removeRole', id);
    },
    trigger: function(name, value) {
      var that = this
      return function() {
        that[name] = value;
      }
    },
    inspect: function(i) {
      location.href= "/user/" + i;
    }
  },
    
  mounted: function() {
    this.$store.commit('findRoles')
  }
}
</script>
<style scoped lang="scss">

#role_view{
}
</style>