import request from '@/utils/request.js'
import { Notify } from 'vant'

const userStore = {
  state: () => {
    return {
      users: [],
      newUser: '',
    }
  },
  mutations: {
    searchUser: function(state) {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/auth/users", {})
        .then(resp => {
          state.users = resp.data
        })
    },
    registUser: function(state, payload){
      // state.quests.push(payload)
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/session/regist", payload)
        .then(resp => {
          if(resp.status === true) {
            Notify({type: 'success', message: 'Insert Success'})
            this.commit("searchUser")
          }else {
            Notify({type: 'danger', message: 'Error'})
          }
        })
    },
    activeUser: function(state, payload){
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/api/user/active/" + payload)
        .then(resp => {
          if(resp.status === true) {
            Notify({type: 'success', message: 'Active Success'})
            this.commit("searchUser")
          } else {
            Notify({type: 'danger', message: 'Error'})
          }

        })
    },
    deactiveUser: function(state, payload){
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .get("/api/user/deactive/" + payload)
        .then(resp => {
          if(resp.status === true) {
            Notify({type: 'success', message: 'Deactive Success'})
            this.commit("searchUser")
          } else {
            Notify({type: 'danger', message: 'Error'})
          }

        })
    }

  },
  actions: {},
  getters: {
    users (state) {
      return state.users
    }
  }
}

export default userStore;