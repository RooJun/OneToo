import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

import ElementUI from 'element-ui'

import 'element-ui/lib/theme-chalk/index.css'

Vue.use(ElementUI)

import router1 from "@/router";

import store from "@/store";
import axios from "axios";

axios.defaults.baseURL='/api'

new Vue({
  render: h => h(App),
  router:router1,
  store,
}).$mount('#app')
