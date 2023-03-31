<template>
  <div >
    <div >
      <router-view style="padding-bottom: 110px" />

      <Shopcart style="position: fixed;bottom: 50px" v-if="scart" />
      <van-tabbar route v-model="active" @change="onChange" class="userbottom">
        <van-tabbar-item replace to="/userhome" icon="wap-home">首页</van-tabbar-item>
        <van-tabbar-item replace to="/userorder" icon="column">订单</van-tabbar-item>
        <van-tabbar-item replace to="/usermy" icon="manager">退出</van-tabbar-item>
      </van-tabbar>
    </div>
  </div>

</template>

<script>
import {Tabbar,TabbarItem} from 'vant'
import Shopcart from "@/pages/front/user/Shopcart";
export default {
  name: "User",
  components:{
    [Tabbar.name]:Tabbar,
    [TabbarItem.name]:TabbarItem,
    Shopcart,
  },
  data(){
    return{
      active:'',
      scart:'true'
    }
  },
  methods:{
    onChange(index){
   this.active=index
      if (index===0){
        this.scart=true
      }else {
        this.scart=false
      }
    }
  }

}
</script>

<style scoped>
.userbottom{
  position: fixed;
  bottom: 0px;
}
</style>