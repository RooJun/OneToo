//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'



import store from './store/index'
//关闭Vue的生产提示
Vue.config.productionTip = false
//使用插件


//创建vm
new Vue({
	el:'#app',
	render: h => h(App),

	store:store,
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})