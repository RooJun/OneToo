<template>
<div>
  <van-nav-bar
      title="注册"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
  />
  <van-form ref="form" >
    <van-field
        label="姓名"
        v-model="form.name"
        placeholder="请输入用户名"
        :rules="[{ required: true,}]"
        name="name"

    />
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
    <van-field
        v-model="repassword"
        label="确定密码"
        placeholder="请再次输入密码"
        type="password"
        :rules="[{ required: true,}]"
        @blur="check2pwd"
    />
    <van-field
        v-model="form.phone"
        label="电话号码"
        placeholder="请输入电话号码"
        :rules="[{ required: true,}]"
    >
<!--      <template #button>-->
<!--        &lt;!&ndash; 这里有bug，使用<van-button>无法进行局部表单验证 &ndash;&gt;-->
<!--        &lt;!&ndash; <van-button size="small" type="primary" @click="getCode">发送验证码</van-button> &ndash;&gt;-->
<!--        <div class="button" @click="getCode">发送验证码</div>-->
<!--      </template>-->
    </van-field>
<!--    <van-field label="验证码" v-model="form.code" placeholder="请输入验证码" :rules="[{required: true,-->
<!--                    message: '验证码不能为空',-->
<!--                    trigger: 'onBlur'}]">-->
<!--      &lt;!&ndash; 通过 button 插槽可以在输入框尾部插入按钮 &ndash;&gt;-->

<!--    </van-field>-->

    <van-button type="primary" style="width: 100%" @click="reset">重置</van-button>
    <van-button type="info" style="width: 100%" @click="submit">提交</van-button>
  </van-form>
</div>
</template>

<script>
import {Button,NavBar,Field,CellGroup,Form,Dialog} from 'vant'
import 'vant/lib/button/style';
import axios from "axios";
export default {
  name: "Register",
  components:{
    [Button.name]: Button,
    [NavBar.name]:NavBar,
    [Field.name]:Field,
    [CellGroup.name]:CellGroup,
    [Form.name]:Form,
    [Dialog.Component.name]:Dialog.Component
  },
  data(){
    return{
      repassword:'',
      form:{
        username:'',
        password:'',
        phone:'',
        code:'',
        name:''
      },
    }
  },
  methods:{
    onClickLeft(){
      this.$router.push('/userlogin')
    },
    reset(){
      this.form.password=''
      this.form.username=''
      this.form.code=''
      this.form.phone=''
    },
    submit(){
      if(this.form.phone.length==11){
        this.$refs.form.validate().then(()=>{
          var _this=this
          axios({
            url: '/member/register',
            method:'post',
            data:_this.form
          }).then(resp=>{
            if (String(resp.data.code) === '1') {

              _this.$dialog.alert({
                message:'注册成功!请重新登录'
              })
              this.$router.push('/userlogin')
            } else {
              _this.$dialog.alert({
                message:'注册失败!'+resp.data.msg
              })
            }
          }).catch(()=>{
            alert('b')
          })
        })
      }else {
        this.$dialog.alert({
          message:'电话号码必须是11位'
        })
      }

    },
    getCode(){
      if(this.form.phone.length==11){
        var i=Math.random()*(999999-100000)+100000;
        var j=parseInt(i,10);
        this.form.code=j
      }else {
        alert('电话号码不符合规范!')
      }

    },
    check2pwd(){
      if(this.repassword!=this.form.password){
        this.TipDialog()
        this.repassword=''
      }
    },
    TipDialog(){
      this.$dialog.alert({
        // title:'标题呀',
        message:'两次输入的秘密不一致，请重试'
      }).then(()=>{
        // console.log('点击了确认')
      })
    },

  }
}
</script>

<style scoped>

</style>