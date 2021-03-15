import request from '@/utils/request.js'
import { Notify } from 'vant'

const sessionStore = {
  state: () => {
    return {
      username: '',
      token: ''
    }
  },
  mutations: {
    login: function(state, payload) {
      request.post("/auth/session/login", payload)
        .then(resp => {
          if(resp.status === true) {
            state.token = resp.data.token
            window.sessionStorage.setItem("token", state.token)
            Notify({type: 'success', message: "登录成功!"})
            setTimeout(() => {
              window.location = '/'
            }, 1000)
          } else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    }
  },
  actions: {},
  getters: {
    username (state) {
      return state.username
    },
    token (state) {
      return state.token
    }
  }
}

export default sessionStore;