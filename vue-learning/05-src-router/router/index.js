import VueRouter from "vue-router";

import About from "@/pages/About";
import Home from "@/pages/back/Home";

export default new VueRouter({
    routes:[
        {
            path:'/About',
            component:About
        },
        {
            path:'/Home',
            component:Home
        }
    ]

})