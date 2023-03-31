export default {
    namespaced:true,
    actions:{

    },
    mutations:{


    },
    state:{
        navigateList: [
            {
                id: '1',
                name: '门店管理',
                url: '/home/company',
                icon: 'el-icon-office-building'
            },
            {
                id: '2',
                name: '人员管理',
                url: '/home/member',
                icon: 'el-icon-user'
            },
            {
                id: '3',
                name: '菜品管理',
                url: '/home/dish',
                icon: 'el-icon-dish'
            },
            {
                id: '4',
                name: '订单管理',
                url: '/home/order',
                icon: 'el-icon-s-order'
            },
            {
                id: '5',
                name: '配送管理',
                url: '/home/delivery',
                icon: 'el-icon-truck'
            },
        ],
        nowNavigate:'人员管理',
    }
}
