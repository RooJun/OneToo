<template>

  <div style="display: flex;background-color: #262626;border-radius: 100px;justify-content: center;align-items: center;width: 100%">
<!--    <img src="../../../assets/cart_active.png" style="height: 50px;padding-left: 20px" >-->
<!--    <div class="num" v-show="totalCount>0" style="color: white;height: 16px;width: 16px;background-color: red;border-radius: 20px;align-items: center;padding-left: 3px;font-weight: 700">{{totalCount}}</div>-->
    <van-icon :name="require('../../../assets/cart_active.png')" :badge="shopCount" style="height: 50px;font-size: 50px;" v-if="Changeimg" @click="displayCart" size="50px"/>
    <van-icon :name="require('../../../assets/img.png')" :badge="shopCount" style="height: 60px;font-size: 50px;" v-else @click="displayCart" size="50px"/>
    <span>￥:{{sum}}</span>

    <span style="font-size: 10px">|  还差{{Freesum}}起送</span>

    <van-button color="linear-gradient(to right, #ff9900, #ffcc33)" style="border-radius: 30px;margin-left: 30px" v-if="Changeimg" @click="Pay">去支付</van-button>
    <van-button color="linear-gradient(to right, #a8a8a8, #a8a8a8)" style="border-radius: 30px;margin-left: 30px" v-else >去支付</van-button>

  </div>

</template>

<script>
import {Icon,Button} from 'vant'
import {mapState} from "vuex";

export default {
  name: "Shopcart",
  components:{
    [Icon.name]:Icon,
    [Button.name]:Button,

  },
  data(){
    return{

    }
  },
  computed:{
    Changeimg(){
      if(this.shopCount===0)
      return false;
      else return true
    },
    ...mapState('shop',['shopCount','changeimg','sum','freesum','show1']),
    Freesum(){
      if(this.freesum-this.sum>=0){
        return this.freesum-this.sum
      }
      else return 0
    },
    // show1:{
    //   // set(){
    //   //
    //   // },
    //   // get(){
    //   //
    //   // }
    // }
  },
  methods:{
    displayCart(){
      if(this.show1==false){
        var value=true
        this.$store.commit('shop/SHOW',value)
      }else {
        value=false
        this.$store.commit('shop/SHOW',value)
      }

    },
    Pay(){
      this.$router.push('/pay')
    }
  }
}
</script>

<style scoped>
span{
  color: white;
  margin-left: 20px;
}


</style>