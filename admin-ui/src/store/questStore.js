import request from '@/utils/request.js'
import { Notify } from 'vant'

const questStore = {
  state: () => {
    return {
      quests: [],
      title: '',
      index: 0,
      total: 0
    }
  },
  mutations: {
    search: function(state, index) {
      if(index < 0) {
        index = 0
      }
      let param = {
        title: state.title,
        index: index,
        size: 10
      }
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/api/quests", param)
        .then(resp => {
          if(resp.data !== null){
            state.index = resp.data.index + 1
            state.total = resp.data.total
            state.quests = resp.data.data
          }
        })
    },
    add: function(state, payload){
      // state.quests.push(payload)
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/api/quest", payload)
        .then(resp => {
          if(resp.status === true || resp.data !== null) {
            Notify({type: 'success', message: 'Insert Success'})
            this.commit("search", state.index - 1)
          }else {
            Notify({type: 'danger', message: 'Error'})
          }
        })
    },
    remove: function(state, payload){
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .delete("/api/quest/" + payload)
        .then(resp => {
          if(resp.status === true || resp.data !== null) {
            Notify({type: 'success', message: 'Remove Success'})
            this.commit("search", state.index - 1)
          } else {
            Notify({type: 'danger', message: 'Error'})
          }

        })
      /*
      this.state.quests.splice(
        this.state.quests.indexOf(payload), 1 
      )
      */
    }

  },
  actions: {},
  getters: {
    quests (state) {
      return state.quests
    }
  }
}

export default questStore;