import request from '@/utils/request.js'
import { Notify } from 'vant'

const sessionStore = {
  state: () => {
    return {
      phonenumber: '',
      password: '',
      token: '',
      userInfo: {}
    }
  },
  mutations: {
    login: function(state, payload) {
      request.post("/api/session/login", payload)
        .then(resp => {
          if(resp.status === true) {
            state.token = resp.data.token
            window.sessionStorage.setItem("token", state.token)
            return state
          } else {
            Notify({type: 'danger', message: resp.msg})
            throw new Error("Error")
          }
        })
        .then(() => {
            Notify({type: 'success', message: "登录成功!"})
            setTimeout(() => {
              window.location = '/module/chats'
            }, 500)
        })
        .catch(error => {
          console.log(error) 
        })
    },
    regist: function(state, payload) {
      request
      .post("/api/session/regist/",payload)
        .then(resp => {
          if(resp.status === true) {
            state.token = resp.data.token
            window.sessionStorage.setItem("token", state.token)
          // location.href="/"
            Notify({type: 'success', message: "注册成功 !" })
            setTimeout(() => {
              window.location = '/module/chats'
            }, 1000)
 
            // this.commit("searchRoles")
          }else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    },
    test: function(state){
      request
      .addHeader('auth', sessionStorage.getItem("token"))
      // .get("/api/users")
      .get("/core")
        .then(resp => {
          if(resp.status === true) {
            state.token = ''
            Notify({type: 'success', message: resp.msg})
            // this.commit("searchRoles")
          }else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    }
 
    /*
    logout: function(state, payload){
      request
      .addHeader('auth', payload)
      .get("/session/logout/")
        .then(resp => {
          if(resp.status === true) {
            state.token = ''
            Notify({type: 'success', message: resp.msg})
            // this.commit("searchRoles")
          }else {
            Notify({type: 'danger', message: resp.msg})
          }
        })
    }
    */
  },
  actions: {},
  getters: {
    phoneNumber(state) {
      return state.username
    },
    token (state) {
      return state.token
    }
  },
}

export default sessionStore;