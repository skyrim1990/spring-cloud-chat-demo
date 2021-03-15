import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import questStore from '@/store/questStore'
import userStore from '@/store/userStore'
import roleStore from '@/store/roleStore'
import sessionStore from '@/store/sessionStore'

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    questStore, userStore, roleStore, sessionStore
  }
})
