import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'

import { Notify } from 'vant';
import { Button } from 'vant';
import { Form } from 'vant';
import { Field } from 'vant';
import { Radio, RadioGroup } from 'vant';
import { NavBar } from 'vant';
import {Tabbar, TabbarItem} from 'vant';
import {Icon} from 'vant';
import {Popover} from 'vant';
import {Toast} from 'vant';
import { List } from 'vant';
import { Dialog } from 'vant';
import { Search } from 'vant';
import { Checkbox, CheckboxGroup } from 'vant';
import { Switch } from 'vant';
import { Tag } from 'vant';
import { Uploader } from 'vant';

import bus from './ws/bus'

Vue.use(NavBar);
Vue.use(Notify);
Vue.use(Button);
Vue.use(Form);
Vue.use(Field);
Vue.use(RadioGroup);
Vue.use(Radio);
Vue.use(Tabbar);
Vue.use(TabbarItem);
Vue.use(Icon);
Vue.use(Popover);
Vue.use(Toast);
Vue.use(List);
Vue.use(Dialog);
Vue.use(Search);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);
Vue.use(Switch)
Vue.use(Tag)
Vue.use(Uploader)


Vue.config.productionTip = false


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

window.onload = function(){
  bus.init()
  window.bus = bus
}