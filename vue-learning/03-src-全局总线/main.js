import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

// const Demo=Vue.extend({})
// const d=new Demo()
// Vue.prototype.x=d
new Vue({
  render: h => h(App),
  beforeCreate() {
   Vue.prototype.$bus=this  //  组件通信的媒介,全局数据总线
  }
}).$mount('#app')
