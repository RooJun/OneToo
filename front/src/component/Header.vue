<template>
  <div>
    <div style="color: #737373">
      OneToo外卖管理系统


      <i style=" float: right;margin-top: 20px;color: orangered ;" class="el-icon-back"
         @click="loginOut">退出登录</i>
      <i class="el-icon-user" style="float: right;font-size: 25px;margin-right: 50px;margin-top: 19px;"></i>
      <h1 style="float: right;margin-top: 1px;margin-right: 5px">{{ user.name }}</h1>

    </div>


  </div>

</template>

<script>

import axios from "axios";

export default {
  name: "Header",
 data(){
    return{
      user:''
    }
 },
  mounted() {
    this.user =JSON.parse(localStorage.getItem("userInfo"))

  },
  methods: {
    loginOut() {
      axios({
        url: '/member/logout',
        method: 'get',
      }).then(resp => {
        if (String(resp.data.code) === '1') {
          localStorage.removeItem('userInfo')
          this.$router.push('/login')
          alert("退出成功！")
        } else {
          alert(resp.data.msg)
        }

      })
    }
  }
}
</script>

<style scoped>

</style>