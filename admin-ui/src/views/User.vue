<template>
  <div id="user_view">
   <admin-layout>
 

    <h2>用户管理</h2>

    <div class="cus-row">
      <div class="cus-label">用户名</div>
      <input v-model="searchName" class="text-input" style="margin: 0px 10px;" type="text" />
      <van-button plain
        @click="findUsers()()"
        type="info">搜索</van-button>
    </div>

    <table class="table-bordered full">

      <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>手机号</th>
        <th>年龄</th>

        <th>性别</th>
        <th>状态</th>

        <th>创建时间</th>
        <th>更新时间</th>
        <th>操作</th>
      </tr>

      <tr v-for="user in users" v-bind:key=user.index>
        <td>{{ user.id  }}</td>
        <td>{{ user.username }}</td>
        <td>{{ user.phonenumber }}</td>
        <td>{{ user.age}}</td>

        <td>{{ user.gender == 0 ? "女" : "男" }}</td>
        <td>{{ user.active == 0 ?  "禁用": "启用" }}</td>

        <td>{{ user.createdAt }}</td>
        <td>{{ user.updatedAt }}</td>

        <td>
          <van-button 
            type="info"
            @click=inspect(user.id)
            style="margin-right:10px;"
            size="small">
          详情
          </van-button>
          <van-button v-if="user.active == 0"
            @click=active(user.id)
            size="small"
            type="primary">
            启用
          </van-button>
          <van-button v-else
            @click=deactive(user.id)
            size="small"
            type="danger">
            禁用
          </van-button>
 
        </td>
      </tr>

    </table>

    <div class="pagination">
      <div class="infos">
        共{{ totalCount }}条数据 当前页{{ users.length }}条 
        当前第{{ pageIndex + 1 }}页 共{{ pageCount}}页
      </div>

      <div class="buttons">
        <van-button class="btn" plain 
          @click="lastPage()"
          type="info" size="small">上一页</van-button>

        <van-button
          v-for="i in genMenu" :key=i.index
          class="btn" plain
          v-bind:class="{active: isCurrentPage(i)}"
          @click="goToPage(i)"
          type="info" size="small">{{i}}</van-button>

        <van-button class="btn" plain
          @click="nextPage()"
          type="info" size="small">下一页</van-button>
      </div>


   </div>

    </admin-layout>

  </div>
</template>

<script>
// @ is an alias to /src
import request from '@/utils/request.js'
import { Notify } from 'vant'

export default {
  name: 'user',
  data: function() {
    return {
      searchName: '',
      pageIndex: 0,
      pageSize: 10,
      totalCount: 0,
      pageCount: 0,
      length: 3,
      users: []
    }
  },
  computed: {
    genMenu: function()  {
      let arr = []
      arr.push(this.pageIndex)
      for(let i = 1; i <= this.length; i++) {
        arr.push(this.pageIndex + i)
        arr.push(this.pageIndex - i)
      }
      return arr.filter((i) => {
        return i <= this.pageCount && i >0;
      })

    },
  },
  methods:{
    inspect: function (id) {
      location.href = '/user/' + id
    },
    isCurrentPage: function(s){
      return s == this.pageIndex + 1
    },
    lastPage: function() {
      let i = this.pageIndex - 1;
      if(i < 0) {
        this.findUsers()(0)
      } else {
        this.findUsers()(i)
      }
    },
    nextPage: function() {
      let i = this.pageIndex + 1;
      i = i >= this.pageCount - 1 ? this.pageCount - 1 : i
      this.findUsers()(i)
    },
    goToPage: function(i) {
      this.findUsers()(i-1)
    },
    active: function(id) {
      var that = this
        request
        .addHeader("auth", sessionStorage.getItem("token"))
        .get("/auth/user/active/" + id )
        .then(resp => {

          if(resp.status) {
            Notify({type: 'success', message: resp.msg})
            that.findUsers()();
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    },
    deactive: function(id) {
      var that = this
        request
        .addHeader("auth", sessionStorage.getItem("token"))
        .get("/auth/user/deactive/" + id )
        .then(resp => {

          if(resp.status) {
            Notify({type: 'success', message: resp.msg})
            that.findUsers()();
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
 
    },
    findUsers: function(){
      var that = this
      return function(pageIndex) {
        let i = pageIndex
        if(pageIndex === undefined) {
          i = that.pageIndex
        }

        request
        .addHeader("auth", sessionStorage.getItem("token"))
        .post("/auth/user/findUsers", {
          name: that.searchName,
          pageIndex: i,
          pageSize: that.pageSize
        })
        .then(resp => {
          if(resp.status) {
            that.users = resp.data.data
            that.pageIndex = resp.data.index
            that.totalCount = resp.data.total 
            that.pageCount = Math.ceil(that.totalCount/that.pageSize)
          } else {
            Notify({type: 'danger', message: resp.msg})

          }
        })
      }
 
    },
  },
  mounted: function() {
    this.findUsers()();
  }
}
</script>
<style scoped lang="scss">

#user_view{
  .active {
    background-color: #1989fa;
    color: white;
  }

  .pagination{
    padding: 10px 0px;
    .buttons{
      float: right;
      .btn{
        display: inline-block;
        margin-right: 10px;
        &:last-child{
          margin-right: 0px;
        }
      }

    }
    .infos{
      float: left;
      display: table-cell;
      vertical-align: middle;
      font-size: 12px;
      line-height:12px;
      height: 12px;
      padding: 10px 0px;
    }
  }
  
}
</style>