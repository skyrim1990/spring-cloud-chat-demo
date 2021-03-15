<template>
  <div id="notes">
    
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'About',
  data: () => {
    return {
      notes: [
        {title: "中文"}
      ]
    }
  },
  components: {
  },
  methods: {
    hello: function() {
      console.log("hello")
    }
  }
}
</script>
