<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  padding: 0px;
  margin: 0px;
}

.cus-input {
  margin: 0px;
  padding: 10px;
  width: 300px;
}
.full {
  width: 100%;
}
.table-bordered{
  border: 1px solid black;
  border-collapse: collapse;
  margin: auto;
  th, td {
    border: 1px solid black;
    text-align: center;
    padding: 8px;
  }

  tr:nth-child(even){background-color: #f2f2f2}

  th {
    background-color: #4CAF50;
    color: white;
  }
  .blank {
    text-align: center;
    padding: 50px;
  }
}
.table-row{
  text-align: left;
  padding-bottom:10px;
  
}

.form-bordered{
  border-collapse: collapse;
  width: 100%;
  th, td {
    text-align: left;
    padding: 8px;
  }

  tr:nth-child(even){background-color: #f2f2f2}

  th {
    background-color: #4CAF50;
    color: white;
  }
}
.cus-section {  
  border: 1px solid #999999;
  text-align: left;
  padding: 10px;
}
.cus-select {
  font-size: 16px;
  padding: 11px;
  vertical-align: bottom;
}
.no-data{
  background-color: #f2f2f2;
  padding: 8px;
  font-weight: bold;
}
  .cus-row{
    text-align: left;
    padding: 10px 0px;
  } 
  .cus-label{
    display: inline-block;
    // background-color: red;
    vertical-align: bottom;
    font-size: 14px;
    padding: 12px 0px;
    font-weight: bold;
  }
  .text-input{
    padding: 0px;
    vertical-align: bottom;
    display: table-cell;
    padding: 11px;
    
  }
</style>
