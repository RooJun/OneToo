import Vue from 'vue'
import Vuex from 'vuex'
import navigate_header from "./navigate_header"
import dish from "@/store/dish";
import shop from "@/store/shop";

Vue.use(Vuex)

export default new Vuex.Store(
    {
        modules:{
           navigate_header:navigate_header,
            dish:dish,
            shop:shop
        }
    }
)