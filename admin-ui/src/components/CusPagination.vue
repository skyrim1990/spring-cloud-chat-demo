<template>
  <div class="CusPagination">

    <van-button class="button" plain type="info" @click="headPage()">
      首页
    </van-button>

    <van-button class="button" plain type="info" @click="lastPage()">
      上一页
    </van-button>

    <van-button class="button" plain type="info" @click="nextPage()">
      下一页
    </van-button>
 
    <van-button class="button" plain type="info" @click="lastPage()">
      尾页
    </van-button>



    <div class="text">
      共{{ totalCount }}条数据  每页10条
      当前第{{currentPage + 1 }}页 共{{ Math.ceil(totalCount / 10) }}页</div>

    <!--
    <div class="text">前往第</div>
    <input class="search" type="text"
    />
    <div class="text">页</div>
    <van-button class="button" plain type="info">跳转</van-button>
    -->

  </div>
  
</template>

<script>
export default {
  name: 'cus-pagination',
  props: ['searc-func', 'totalCount', 'currentPage'],
  data: function() {
    return {
      // currentPage: 0,
      // totalCount: 30,
      // pageSize: 5,
      // searchPage: 0
    }
  },
  methods: {
    goTo: function(page){
      console.log(page)
    },
    headPage: function(){},
    tailPage: function(){},
    lastPage: function(){},
    nextPage: function(){},
    searchPage: function(param){
      console.log(param)
    }

  },
  computed: {

  }
  
}
</script>

<style scoped lang="scss">
.CusPagination{
  border: 1px solid #999999;
  padding: 10px;
  .search{
    padding: 12px;
    position: relative;
    top: 5px;
    background-color: white;
    margin-right: 10px;
    border: 1px solid #999999;
    width: 10px;
  }
  .text{
    display: inline-block;
    margin-right: 10px;
    position: relative;
    top: 5px;
  }
  .button{
    margin-right: 10px;
  }
}

</style>