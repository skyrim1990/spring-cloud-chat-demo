import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
import Cover from '../views/Cover.vue'
import Template from "../views/Template.vue"

import Chats from '../views/Chats.vue'
import Friends from '../views/Friends.vue'
import Notes from '../views/Notes.vue'
//import UserCenter from '../views/UserCenter.vue'
import PersonCenter from '../views/PersonCenter.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'root',
    component: Cover 
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
  ,{path: '/debug', name: 'debug',  component: () => import('../views/Debug.vue')}
  ,{path: '/error', name: 'error',  component: () => import('../views/Error.vue')}

  ,{path: '/cover', name: 'cover', component: () => import('../views/Cover.vue')}
  ,{path: '/login', name: 'login', component: () => import('../views/Login.vue')}
  ,{path: '/regist', name: 'regist', component: () => import('../views/Regist.vue')}

  ,{path: '/module',  component: Template,
    children: [
      {path: 'chats', name: 'Chats', component: Chats}
      ,{path: 'friends', name: 'friends', component: Friends}
      ,{path: 'personCenter', name: 'personCenter', component: PersonCenter}
      ,{path: 'editInfo', name: 'editInfo', component: () =>  import('../views/EditInfo.vue')}
      ,{path: 'notes', name: 'note', component: Notes }
    ]
  }
  ,{path: '/groupChat/:id', name: 'groupChat',  component: () => import('../views/GroupChat.vue')}
  ,{path: '/singleChat/:id', name: 'singleChat',  component: () => import('../views/SingleChat.vue')}
  ,{path: '/addFriend', name: 'addFriend',  component: () => import('../views/addFriend.vue')}
  ,{path: '/addGroup', name: 'addGroup',  component: () => import('../views/addGroup.vue')}
  ,{path: '/manageGroup/:id', name: 'manageGroup',  component: () => import('../views/manageGroup.vue')}

  ,{path: '/groupAddFriend/:id', name: 'groupAddFriend', component: () => import('../views/groupAddFriend.vue')}

  ,{path: '/*', name: 'notFound',  component: () => import('../views/NotFound.vue')}
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
    let urls = ['/', '/cover', '/login', '/regist']
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

})

export default router
