import axios from "axios";

export default {
    namespaced: true,
    actions: {
        updateDishHandle(context,value) {

            context.commit('UPDATEFORM',value)
        },
        displayCategory(context){
            var _this=this
            axios({
                url:'/category/displaycategory',
                method:'get'
            }).then(function (resp){
                context.commit('DISPLAYCATEGORY',resp.data.data)
                console.log(_this.categoryList)
            })
        }
    },
    mutations: {
        DISPLAYCATEGORY(state,value){
            state.categoryList=value
        },
    UPDATEFORM(state,value){
        state.form=value
    }
    },
    state: {
        categoryList: [],
        form: {},
        formRules: {
            code:[{
                required: true,
                trigger: "blur",
                min: 3,
            }],
            name: [{
                required: true,
                message: '请填写菜品名称',
                trigger: "blur",
                min: 2,
            }],
            price: [{
                required: true,
                trigger: "blur",
            }],
            image: [{
                required: true,
            }],
            categoryName: [{
                required: true,
                trigger: "blur",
                min: 1,
            }],
            description: [
                {
                    max: 200
                }
            ],
        },
        formLabelWidth: '120px',
    }
}
