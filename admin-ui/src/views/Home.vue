<template>
  <div class="home">
    <admin-layout>
      <h1>个人中心</h1>
      <img alt="Vue logo" src="../assets/logo.png">

    </admin-layout>

  </div>
</template>

<script>
// import { hello } from '@/api/user.js'
import AdminLayout from '../components/AdminLayout.vue';

export default {
  name: 'Home',
  data: function(){
    return {
      show: false
    }
  },
  methods: {
    trigger: function(name, value) {
      var that = this
      return function() {
        that[name] = value;
      }
    }
  },
  components: {
    AdminLayout
  },
  mounted: function
    CusPagination() {
    // hello();
  }
}
</script>