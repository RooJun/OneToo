<template>
<div>
  <van-button type="warning" style="width: 100%" @click="logout">退出登录</van-button>
</div>
</template>

<script>
import {Button} from "vant";
import axios from "axios";

export default {
  name: "Usermy",
  components:{
    [Button.name]:Button,
  },

  methods:{
    logout(){
      axios({
        url: '/member/logout',
        method: 'get',
      }).then(resp => {
        if (String(resp.data.code) === '1') {
          localStorage.removeItem('userInfo')
          this.$router.push('/userlogin')
          this.$dialog.alert({
            message:"退出成功"
          })
        } else {
          this.$dialog.alert({
            message:resp.data.data
          })
        }
    })
  }
}
}
</script>

<style scoped>

</style>