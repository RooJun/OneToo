<template>
  <div class="login" id="login-app">
    <div class="login-box">
      <img src="../../assets/pexels-artem-beliaikin-929192.jpg">
      <el-form
          :model="loginForm"
          status-icon
          :rules="loginRules"

          label-width="100px"
          class="login-container"
      >
        <h3  class="login-title">系统登录</h3>
        <el-form-item
        label="用户名"
        label-width="80px"
        prop="username"
        class="username"
        >
          <el-input
              type="input"
              v-model="loginForm.username"
              auto-complete="off"
              placeholder="请输入账号"

          ></el-input>
        </el-form-item>
        <el-form-item
            label="密码"
            label-width="80px"
            prop="password"
                    >
          <el-input
              type="password"
              v-model="loginForm.password"
              auto-complete="off"
              placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item class="login-submit">
          <el-button type="primary" @click="login" class="login-submit">
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
          </el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: "Login",
  data(){
    return{
      loading:false,
      loginForm:{
        username: 'admin',
        password: '123456'

      },

    }
  },
  computed: {
    loginRules() {
      const validateUsername = (rule, value, callback) => {
        if (value.length < 1 ) {
          callback(new Error('请输入用户名'))
        } else {
          callback()
        }
      }
      const validatePassword = (rule, value, callback) => {
        if (value.length < 6) {
          callback(new Error('密码必须在6位以上'))
        } else {
          callback()
        }
      }
      return {
        'username': [{ 'validator': validateUsername, 'trigger': 'blur' }],
        'password': [{ 'validator': validatePassword, 'trigger': 'blur' }]
      }
    }
  },
  methods:{
    login(){
      if(this.loginForm.password.length>=6)
      {
        this.loading=true
        var _this=this
        axios({
          url: '/member/login',
          method:'post',
          data:this.loginForm
        }).then(resp=>{
          if (String(resp.data.code) === '1') {
            localStorage.setItem('userInfo',JSON.stringify(resp.data.data))
            if(resp.data.data.identity==='1'){
              alert("您没有权限，请去用户的登录页面")
              this.$router.push('/userlogin')
            }
            else
            this.$router.push('/home')
          } else {
            alert(resp.data.msg)
            _this.loading = false
          }
            }

        )
      }else {
        alert("密码输入不规范！")
      }
    }
  }
}
</script>

<style scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-color: #333;
}
.login-box {
  width: 1000px;
  height: 650px;
  border-radius: 8px;
  display: flex;
}
.login-box img {
  margin-top: 75px;
  width: 60%;
  height: 70%;
  border-top-left-radius: 13px;
  border-bottom-left-radius: 13px;
}
.login-container{
  padding-top: 100px;
  display:flex;
  flex-direction: column;
  margin-top: 75px;
  background-color: white;
  height:353px;
  border-top-right-radius: 13px;
  border-bottom-right-radius: 13px;
  background-clip: border-box;
  width: 40%;
  border: 1px solid #eaeaea;
  /*box-shadow: 0 0 25px #cac6c6;*/
}
.login-title{
  margin:0px auto 40px auto;
  text-align: center;
  color: #505458;
}
.login-submit{
 margin-left:35px;

}

</style>