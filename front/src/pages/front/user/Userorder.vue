<template>
<div>
  <van-nav-bar
      title="我的订单"
  />
  <div style="display: flex;flex-direction: column;border: solid #9d9d9d;" v-for="order in orderList" :key="order.id">
    <div v-if="GetUserId(order.userId)">
      <div style="display: flex;height: 10%;">
        <div style="float: left;margin-left: 10px">{{order.companyName}}店</div>
        <div style="float: right;margin-right: 10px;margin-left: 180px;color: #737373" v-if="order.status===1">商家准备中</div>
        <div style="float: right;margin-right: 10px;margin-left: 180px;color: #737373" v-if="order.status===2">待派送</div>
        <div style="float: right;margin-right: 10px;margin-left: 180px;color: #737373" v-if="order.status===3">派送中</div>
        <div style="float: right;margin-right: 10px;margin-left: 180px;color: #737373" v-if="order.status===4">已派送</div>
        <div style="float: right;margin-right: 10px;margin-left: 180px;color: #737373" v-if="order.status===5">交易完成完成</div>
        <div style="float: right;margin-right: 10px;margin-left: 180px;color: #737373" v-if="order.status===6">交易取消</div>
      </div>
      <div style="display: flex ; margin-top: 10px;width: 100%" >
        <div v-for="orderD in orderDetail" :key="orderD.id" style="display: flex;flex-direction: column">
          <img :src="getimage(orderD.image)"  v-if="orderD.orderId===order.id" style="height: 70px;width:70px">
          <div v-if="orderD.orderId===order.id">{{orderD.name}}</div>
        </div>
      </div>
      <div>
        <span>下单时间：{{order.orderTime}}</span>
        <span style="float: right">￥{{order.amount}}  共{{order.number}}件</span>
      </div>
      <div>
        <van-button color="linear-gradient(to right, #ff9900, #ffcc33)" style="border-radius: 30px;margin-left: 30px;float: right" @click="receipt(order)"
        >确定收货
        </van-button>
        <van-button color="red" style="border-radius: 30px;margin-left: 30px;float: right" @click="cancel(order)"
        >取消订单
        </van-button>
      </div>
    </div>


  </div>

</div>
</template>

<script>
import {NavBar,Button,Dialog} from "vant";
import axios from "axios";
import qs from "qs";

export default {
  name: "Userorder",
  components:{
    [NavBar.name]:NavBar,
    [Button.name]:Button,
    [Dialog.name]:Dialog
  },
  data(){
    return{
      orderDetail:[],
      orderList:[],
      orderIds:[],
      orderDetailOne:{}
    }
  },
  computed:{

  },
  mounted() {
   this.initOrder()

  },
  methods:{
    GetUserId(orderId){
      if((JSON.parse(localStorage.getItem('userInfo'))).id===orderId)
        return true
      else return false

    },
    initOrder(){
      axios({
        method:'get',
        url:'/order/select'
      }).then(resp=>{
        this.orderList=resp.data.data
        console.log(this.orderList)
        this.orderList.forEach((order)=>{
          this.orderIds.push(order.id)
        })
        this.initOrderDetail()
      })
    },
    initOrderDetail(){
      var _this=this
      axios({
        method:'get',
        url:'/orderDetail/select',
        params:{
          ids:this.orderIds,
        },
        paramsSerializer: params => {
          return qs.stringify(params, {indices: false})
          //  将ids序列化，避免url中出现ids[]=?ids[]=?
        }
      }).then(resp=>{
        this.orderDetail=resp.data.data
        console.log(this.orderDetail)
      }).catch(()=>{
        _this.$dialog.alert({
          message:'访问出错了！'
        })
      })
    },
    getimage(image) {
      return 'http://192.168.58.100:8080/common/download?name=' + image
    },
    receipt(order){
      var _this=this
      if(order.status===3){
        order.status=5

        axios({
          method:'post',
          url:'/order/updateOrder',
          data:order
        }).then(resp=>{
          _this.$dialog.alert({
            message:resp.data.data
          })
        })
      }else {
        _this.$dialog.alert({
          message:"操作失败"
        })
      }
    },
    cancel(order){
      var _this=this
      if(order.status===1){
        order.status=6
        Dialog.confirm({
          title: '取消订单',
          message: '确定取消订单吗？'
        }).then(()=>{
          axios({
                  method:'post',
                  url:'/order/updateOrder',
                  data:order
                }).then(()=>{
            _this.$dialog.alert({
              message:"订单取消成功"
            })
          })
        })
      }else {
        _this.$dialog.alert({
          message:"操作失败"
        })
      }
    }


  }


}
</script>

<style scoped>

</style>