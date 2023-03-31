<template>
  <div style="">

    <div class="information">
      <van-image
          width="100%"
          height="35vw"
          :src="require('../../../assets/pexels-artem-beliaikin-929192.jpg')"
      />
      <div style="position:absolute; z-index:2;  top:50px;left: 36px;background-color: white;height: 82px;width: 300px">
        <div style="top:1px">ONETOO连锁餐厅</div>
        <hr>
        <div style="font-size: 5px">简介: ONTOO中餐厅是外卖点餐的独立的品牌，定位“大众 化的美食外送餐饮”，旨为顾客打造专业美食。</div>

      </div>

    </div>
    <div style="display: flex;width: 100%">

      <div style="width: 25%; ">
        <van-sidebar v-model="activeKey">
          <van-sidebar-item :title="category.name" v-for="category in categoryList" :key="category.id"
                            @click="onChange(category.id)"/>
        </van-sidebar>
      </div>


      <div style="width: 75%;bottom: 130px">
        <van-card
            v-for="dish in dishDate"
            :key="dish.id"
            :price="dish.price"
            :desc="dish.description"
            :title="dish.name"
            :thumb="getimage(dish.image)"

        >
          <div slot="tags" @click="dishdetail(dish)">
            <van-tag plain type="danger">月销888</van-tag>
          </div>
          <div slot="footer">
            <van-button size="small" @click="add(dish)">+</van-button>
          </div>
        </van-card>


      </div>


    </div>
    <van-popup v-model="show">
      <Dishdetail/>
    </van-popup>

    <van-popup
        v-model="show1"
        position="bottom"
        style="bottom: 110px"
        :style="{ height: '30%' }"
    >

      <div>
        <div>
          <span>购物车</span>

          <van-icon name="close" style="float: right" @click="closeShow1"/>
          <span style="float: right;font-weight: 10;font-size: 10px" @click="clearCart">清空</span>

        </div>

        <div style="display: flex;margin-top: 20px;height: 70px" v-for="cart in carts" :key="cart.id">
          <img :src="getimage(cart.image)" style="width: 30%;">
          <div style="display: flex;flex-direction: column;width: 40%;margin-left: 5px">
            <span>{{ cart.name }}</span>
            <span style="margin-top: 30px;color: orange">￥{{ cart.number * cart.price }}</span>
          </div>
          <div style="margin-top: 40px;margin-left: 40px">
            <van-button size="small" @click="reduce(cart)">-</van-button>
            <span style="color: red">{{ cart.number }}</span>
            <van-button size="small" @click="add(cart)">+</van-button>
          </div>
        </div>


      </div>

    </van-popup>


    <!--  <van-loading type="spinner" color="#1989fa" style="justify-content: center;align-items: center;display: flex;"/>-->
  </div>
</template>

<script>
import {Loading, Image, Sidebar, SidebarItem, Notify, Card, Button, Tag, Popup, Icon,Dialog} from 'vant'
import Shopcart from "@/pages/front/user/Shopcart";
import axios from "axios";
import Dishdetail from "@/pages/front/user/Dishdetail";
import {mapState,mapMutations} from "vuex";

export default {
  name: "Userhome",
  components: {
    [Loading.name]: Loading,
    [Image.name]: Image,
    [Sidebar.name]: Sidebar,
    [SidebarItem.name]: SidebarItem,
    [Notify.name]: Notify,
    [Card.name]: Card,
    [Button.name]: Button,
    [Tag.name]: Tag,
    Shopcart,
    [Popup.name]: Popup,
    Dishdetail,
    [Icon.name]: Icon,
    [Dialog.name]:Dialog

  },
  data() {
    return {
      activeKey: '',
      categoryList: [],
      categoryName: '',
      dishDate: [],
      show: false,
      //要加入到购物车的dish
      dishCart: {},
      //加入购物车后返回的数据
      cartOne: {},
      carts: [],
      //给购物车传数据
      value:{
        shopCount:0,
        sum:0
      }
    }
  },
  created() {


  },
  computed: {
    ...mapState('shop', ['show1','sum','shopCount']),
  },
  mounted() {
    this.showCarta()
    this.init()
  },
  methods: {
    onChange(id) {
      // Notify({ type: 'primary', message: this.categoryName});
      var _this = this
      axios({
        url: '/dish/selectbycategoryid',
        method: 'get',
        params: {
          id
        }
      }).then(function (resp) {
        _this.dishDate = resp.data.data
      }).catch(() => {
        this.$dialog.alert({
          message: '访问出错了'
        })
      })

    },
    getimage(image) {
      return 'http://192.168.58.100:8080/common/download?name=' + image
    },
    init() {
      var _this = this
      axios({
        url: '/category/displaycategory',
        method: 'get'
      }).then(function (resp) {
        _this.categoryList = resp.data.data
        _this.onChange(_this.categoryList[0].id)

      }).catch(() => {
        this.$dialog.alert({
          message: '访问出错了'
        })
      })


    },
    dishdetail(dish) {
      this.$store.commit('shop/DISHDETAIL', dish)
      this.show = true
    },
    add(dish) {
      var _this = this
      axios({
        method: 'post',
        url: '/shoppingCart/add',
        data: dish
      }).then(resp => {
        _this.cartOne = resp.data.data
        console.log(_this.cartOne)
        this.showCarta()
      })



    },

    showCarta() {
      axios({
        method: 'get',
        url: '/shoppingCart/show',
        params: {
          userId: (JSON.parse(localStorage.getItem('userInfo'))).id
        }
      }).then(resp => {
        this.carts = resp.data.data
        this.changeCart()
        this.OrderCarts()
      })

    },
    OrderCarts(){
      this.$store.commit('shop/ORDERCARTS',this.carts)
    },

    reduce(cart){
      axios({
        method: 'post',
        url: '/shoppingCart/reduce',
        data: cart
      }).then(resp => {
       console.log(resp.data.data)
        this.showCarta()
      })
    },
    changeCart(){
      this.value.sum=0
      this.value.shopCount=0
      this.carts.forEach(cart=>{
        this.value.sum+=cart.price*cart.number
        this.value.shopCount+=cart.number
      })
      this.$store.commit('shop/SHOPALL',this.value)
    },
    closeShow1(){
      var value=false

      this.$store.commit('shop/SHOW',value)
    },
    clearCart(){

     var userId=(JSON.parse(localStorage.getItem('userInfo'))).id
      Dialog.confirm({
        title: '清空购物车',
        message: '确定清空吗？'
      }).then(() => {
        axios({
          method:'get',
          url:'shoppingCart/clearCart',
          params:{
            id:userId
          }
        }).then(resp=>{
          this.$dialog.alert({
            message:resp.data.data
          })
          this.showCarta()
          this.closeShow1()
        })
      }).catch(() => {
        // on cancel
      });
    }



  },


}
</script>

<style scoped>
.information {
  position: relative;
}
</style>