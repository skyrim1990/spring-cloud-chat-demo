import request from '@/utils/request.js'
import { Notify } from 'vant'

const roleStore = {
  state: () => {
    return {
      roles: [],
      index: 0,
      total: 0,
      newRole: '',
    }
  },
  mutations: {
    findRoles: function(state) {
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/auth/role/findRoles")
        .then(resp => {
          state.roles = resp.data
        })
    },
    addRole: function(state, payload){
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .post("/auth/role/add/" + payload)
        .then(resp => {
          if(resp.status === true) {
            Notify({type: 'success', message: resp.msg})
            this.commit("findRoles")
          }else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    },
    removeRole: function(state, payload){
      request
      .addHeader("auth", sessionStorage.getItem("token"))
      .delete("/auth/role/remove/" + payload)
        .then(resp => {
          if(resp.status === true) {
            Notify({type: 'success', message: resp.msg})
            this.commit("findRoles")
          } else {
            Notify({type: 'danger', message: resp.msg})
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
    roles (state) {
      return state.roles
    }
  }
}

export default roleStore;