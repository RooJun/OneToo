import Vue from 'vue'
//此处可发ajax请求

import countOptions from './count'
import personOptions from './person'

//key和value重名可简写
import Vuex from 'vuex'

Vue.use(Vuex)
export default new Vuex.Store({
    modules:{
        countAbout:countOptions,
        personAbout:personOptions
    }
})