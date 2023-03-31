<template>
  <div>

    <van-nav-bar
        title="订单支付"
        left-text="返回"
        left-arrow
        @click-left="onClickLeft"
    />
    <van-form ref="form">
      <van-field
          label="姓名"
          v-model="form.username"
          placeholder="请输入收货人姓名"
          :rules="[{ required: true,}]"
          name="name"
      />
      <van-field
          label="收货地址"
          v-model="form.address"
          placeholder="请输入收货地址"
          :rules="[{ required: true,}]"
          name="name"
      />
      <van-field
          label="联系电话"
          v-model="form.phone"
          placeholder="请输入收货人联系电话"
          :rules="[{ required: true,}]"
      />
      <van-radio-group v-model="form.payMethod" direction="horizontal">
        支付方式：
        <van-radio name="1">微信</van-radio>
        <van-radio name="2">支付宝</van-radio>
      </van-radio-group>
      <van-radio-group v-model="form.companyName" direction="horizontal">
        选择门店：
        <van-radio :name="name" v-for="name in companyNames" :key="name">{{ name }}</van-radio>
      </van-radio-group>
    </van-form>
    <div>
      <div>
        订单明细：
      </div>
      <div style="display: flex;margin-top: 20px;height: 70px" v-for="cart in orderCarts" :key="cart.id">
        <img :src="getimage(cart.image)" style="width: 30%;">
        <div style="display: flex;flex-direction: column;width: 40%;margin-left: 5px">
          <span>{{ cart.name }}</span>
          <span style="margin-top: 30px;color: orange">￥{{ cart.price }}</span>
        </div>
        <div style="margin-top: 40px;margin-left: 40px">
          <span style="color: red">x {{ cart.number }}</span>
        </div>
      </div>
      <div style="display: flex">
        <div style="color: #9d9d9d">总数量：{{ shopCount }}</div>
        <div style="margin-left: 200px;color: #9d9d9d">总价：￥ {{ sum }}</div>
      </div>
    </div>
    <van-field
        label="备注"
        v-model="form.remark"
        placeholder="备注信息"
    />
    <van-button color="linear-gradient(to right, #ff9900, #ffcc33)" style="border-radius: 30px;margin-left: 30px"
                @click="Paymemnt">支付
    </van-button>

  </div>

</template>

<script>
import {NavBar, Field, Radio, RadioGroup, DropdownMenu, DropdownItem, Button, Form} from "vant";
import {mapState} from "vuex";
import axios from "axios";

export default {
  name: "Pay",
  components: {
    [NavBar.name]: NavBar,
    [Field.name]: Field,
    [Radio.name]: Radio,
    [RadioGroup.name]: RadioGroup,
    [DropdownMenu.name]: DropdownMenu,
    [DropdownItem.name]: DropdownItem,
    [Button.name]: Button,
    [Form.name]: Form
  },
  data() {
    return {
      form: {
        remark: '',
        payMethod: '1',
        username: '',
        phone: '',
        address: '',
        number: '',
        amount: '',
        companyName: ''
      },
      company: [],
      companyNames: []

    }
  },
  computed: {
    ...mapState('shop', ['orderCarts', 'sum', 'shopCount'])
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      axios({
        method: 'get',
        url: '/company/getNames'
      }).then(resp => {
        this.companyNames = resp.data.data
        console.log(this.companyNames)
      }).catch(() => {
        this.$dialog.alert({
          message: "出错了!"
        })
      })
    },
    onClickLeft() {
      this.$router.push('/userhome')
    },
    getimage(image) {
      return 'http://192.168.58.100:8080/common/download?name=' + image
    },
    Paymemnt() {

      if (this.form.companyName) {
        this.$refs.form.validate().then(() => {
          this.form.amount = this.sum
          this.form.number = this.shopCount
          axios({
            method: 'post',
            url: '/order/add',
            data: this.form,
          }).then(reps => {
            this.$dialog.alert({
              message: reps.data.data + "去订单查看"
            })
            this.$router.push('/userhome')
          })
        })
      } else {
        this.$dialog.alert({
          message: "请选择门店！"
        })
      }


    }

  }
}
</script>

<style scoped>

</style>