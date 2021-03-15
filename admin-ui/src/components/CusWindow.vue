<template>
  <div class="cus-window">

    <div class="cus-content">
      <div class="title">{{ title }}
        <!--
        <div class="close-button">X</div>
        -->
      </div>
      <div class="slot">
      <slot></slot>
      <div class="footer">

      </div>
      </div>
    </div>

    <div class="mask"></div>

  </div>
</template>

<script>
export default {
  name: 'cus-window',
  props: ['title'],
  data: function() {
    return {
    }
  },
  methods: {
    
  },
  computed: {

  }
  
}
</script>

<style scoped lang="scss">
.cus-window{
  border: 1px solid black;
  position: fixed;
  top: 0px;
  bottom: 0px;
  right: 0px;
  left: 0px;

  .footer{

  }
  .mask{
    position: relative;
    width: 100vw;
    height: 100vh;
    background-color: rgba(1, 1, 1, 0.3);
  }
}
.cus-content{
  margin-top: 100px;
  position: fixed;
  width: 100%;
  z-index: 100;
}
.title{
  background-color: blue;
  width: 50vw;
  margin: auto;
  color: white;
  font-size: 24px;
  padding: 5px;
}
.close-button{
  font-size: 24px;
  padding: 4px;
  margin-top: -1px;
  margin-right: -1px;
  display: inline-block;
  background-color: red;
  color: white;
  float: right;
}

.slot{
  min-width: 400px;
  width: 50vw;
  min-height: 100px;
  margin: auto;
  background-color: white;
  padding: 20px 5px;
}

</style>