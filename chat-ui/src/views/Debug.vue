<template>
  <div class="about">

    <div>调试页面</div>

    <div>
      <van-button @click="incr()" type="primary">+</van-button>

      Count: {{ count }} 
      
      <van-button @click="decr()" type="primary">-</van-button>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'About',
  data: () => {
      return {
      }
  },
  computed:{
    count: {
      get() {
        return this.$store.state.count;
      },
      set() {}
    } 
  },
  components: {
  },
  methods: {
      incr:  function() {
        this.$store.commit('increment', 30)
      },
      decr: function() {
        this.$store.commit('decrement', 10)
      },
      say: () => {
          // debugger
          console.log("Say")
      },
    hello: function() {
      console.log("hello")
    }
  },
  mounted: function() {
    this.count = this.$store.state.count
  }
}
</script>
