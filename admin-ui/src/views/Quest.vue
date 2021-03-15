<template>
  <div id="quest_view">
    <admin-layout>
    <h2>Quest</h2>
    <!-- list section begin -->
    <table class="table-bordered full">
      <tr>
        <th>Id</th>
        <th>Title</th>
        <th>isDone</th>
        <th>Created At</th>
        <th>Updated At</th>
        <th>Operation</th>
      </tr>

      <tr v-for="quest in quests"
        :key=quest.index>
        <td>{{quest.id}}</td>
        <td>{{quest.title}}</td>
        <td>{{quest.isDone == 0 ? 'False':'True'}}</td>
        <td>{{quest.createdAt}}</td>
        <td>{{quest.updatedAt}}</td>
        <td>
          <van-button type="danger"
            size="small"
            @click="remove(quest.id)">
            remove
          </van-button>  
        </td>
      </tr>



    </table>
    <div class="no-data" v-if="quests.length == 0">No Data</div>

    <div>
      Current Page: {{ index }}
      Total: {{ total }}
      <a href="#"  @click="lastPage()" >Last  </a> 
      <a href="#"  @click="nextPage()" >Next</a> 
      <!--
      <van-button @click="lastPage()">Last</van-button>
      <van-button @click="nextPage()">Next</van-button>
      -->
    </div>


    <!-- list section end -->





    <hr/>
    <!--insert begin -->
    <h2> Inser Quest </h2>
    <van-form style="padding:10px;border:1px solid black"
      @submit="onSubmit">
      <van-field
      v-model="form_data.title"
      placeholder="insert title here ..."
      label="Title"
      :rules="[{ required: true, message: 'Title is required' }]"
      />

      <div>

    <van-radio-group direction="horizontal" v-model="form_data.isDone">
      <van-radio name="0">To Be Done</van-radio>
      <van-radio name="1">Done</van-radio>
    </van-radio-group>


      </div>
    <van-field
      v-model="form_data.description"
      label="Description"
      type="textarea"
      placeholder="insert description here ..."
      rows="3"
      autosize
    />  

    <div>
      <van-button block type="info" native-type="submit">
       Insert 
      </van-button>
    </div>
  </van-form>
  <!--insert end -->



  </admin-layout>
  </div>
</template>

<script>
import AdminLayout from '../components/AdminLayout.vue'
// @ is an alias to /src


export default {
  name: 'Quest',
  data: function() {
    return {
      form_data: { title: "", isDone: "0", description: ""}
    }
  },
  computed: {
    quests: function(){
      return this.$store.state.questStore.quests
    },
    index: function(){
      return this.$store.state.questStore.index
    },
    total: function(){
      return this.$store.state.questStore.total
    }
  },
  components: {
    AdminLayout
  },
  methods: {
    onSubmit: function(){
      this.$store.commit('add', this.form_data)
      this.form_data = { title: "", isDone: "0", description: ""}
    },
    remove: function(id){
      this.$store.commit('remove', id)
    },
    nextPage: function(){
      this.$store.commit('search', this.$store.state.questStore.index)
    },
    lastPage: function(){
      this.$store.commit('search', this.$store.state.questStore.index - 2)
    }
  },
  beforeCreate: function() {
      this.$store.commit('search', 0)
  }
}
</script>

<style scoped lang="scss">

#quest_view{
  
}
</style>