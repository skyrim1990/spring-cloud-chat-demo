import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

import Login from '../views/Login.vue'
import Regist from '../views/Regist.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  { path: '/login', name: 'Login', component: Login},
  { path: '/regist', name: 'Regist', component: Regist},
  {
    path: '/quest', name: 'quest',
    component: () => import('../views/Quest.vue')
  },
  {
    path: '/role', name: 'role',
    component: () => import('../views/Role.vue')
  },
  {
    path: '/user/:id', name: 'userDetail',
    component: () => import('../views/UserDetail.vue')
  },
  {
    path: '/user', name: 'quest',
    component: () => import('../views/User.vue')
  },
  {
    path: '/session', name: 'session',
    component: () => import('../views/Session.vue')
  },
  {
    path: '/message', name: 'message',
    component: () => import('../views/Message.vue')
  },
  {
    path: '/resource', name: 'resource',
    component: () => import('../views/Resource.vue')
  },
  {
    path: '*', component: () => import('../views/NotFound.vue')

  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  let dest = to.path
  let token = sessionStorage.getItem("token")
 
  // if(to.path == '/regist') next()
  // next({path: '/regist'})

  function isNeedAuthUrl(dest_url) {
    let urls = ['/', '/login', '/regist']
    let result = urls.find((a)=> {
      return a == dest_url
    })
    return result == undefined

  }
  console.log(to.path)

  if(token === null) {
    if (isNeedAuthUrl(dest)){
      next({ path: '/login' })
    } else {
      next()
    }
  } else {
    if (
      dest === '/login' ||
      dest === '/regist') {
      next({ path: from.path })
    } else {
      next()
    }
  }

  /*

    && token === null) {
      if(
    dest === '/login' || 
    dest === '/regist'
    ) {
      next()
    } else {
      next({path: '/login'})
    }
  } else {
    next()
  }
  */

})

export default router
