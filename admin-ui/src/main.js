import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import Vant from 'vant'
import 'vant/lib/index.css'

import AdminLayout from '@/components/AdminLayout'
import CusWindow from '@/components/CusWindow'
import CusPagination from '@/components/CusPagination'

Vue.component('admin-layout', AdminLayout)
Vue.component('cus-window', CusWindow)
Vue.component('cus-pagination', CusPagination)

Vue.use(Vant);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
