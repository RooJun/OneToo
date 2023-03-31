<template>
  <div >
    <van-nav-bar
        title="欢迎登录"
    />
    <van-form ref="form" >
      <van-field
          label="用户名"
          v-model="form.username"
          placeholder="请输入用户名"
          :rules="[{ required: true,}]"
          name="username"


      />
      <van-field

          v-model="form.password"
          label="密码"
          placeholder="请输入密码"
          type="password"
          :rules="[{ required: true,}]"
      />
      <van-button  style="float: right;color: red" @click="forget">忘记密码</van-button>
      <van-button type="primary" style="width: 100%" @click="login">登录</van-button>
      <van-button type="info" style="width: 100%" @click="registermember">注册</van-button>
    </van-form>
  </div>
</template>

<script>
import {Button,NavBar,Field,CellGroup,Form,Notify} from 'vant'
import 'vant/lib/button/style';
import axios from "axios";
export default {
  name: "Userlogin",
  components:{
    [Button.name]: Button,
    [NavBar.name]:NavBar,
    [Field.name]:Field,
    [CellGroup.name]:CellGroup,
    [Form.name]:Form,
    [Notify.name]:Notify,
  },
  data(){
    return{
      form:{
      username:'',
        password:''
      },
    }
    },

  methods:{
    login(){
      this.$refs.form.validate().then(()=>{

        var _this=this
        axios({
          url: '/member/login',
          method:'post',
          data:_this.form
        }).then(resp=>{
          if (String(resp.data.code) === '1') {
            localStorage.setItem('userInfo',JSON.stringify(resp.data.data))
            _this.$notify.success({
              message:'登录成功',
              duration:1000,
              type:'success'
            })
            this.$router.push('/user')
          } else {
            alert(resp.data.msg)
            _this.loading = false
          }

      }).catch(()=>{
        alert('b')
      })
    })

  },
    registermember(){
      this.$router.push('/register')
    },
    forget(){
      this.$router.push('/forgetpassword')
    }
}
}
</script>

<style scoped>

</style>