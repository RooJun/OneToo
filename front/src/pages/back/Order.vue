<template>
<div>
  <div style="height: 10%">订单管理</div>
  <hr>
  <div class="dashboard-container" id="member-app">
    <div class="container">
      <div class="tableBar">
        <el-input
            v-model="input"
            placeholder="请输入订单人姓名"
            style="width: 250px ; float: left"
            clearable
            @keyup.enter.native="handleQuery"
        >
          <i
              slot="prefix"
              class="el-input__icon el-icon-search"
              style="cursor: pointer"
              @click="handleQuery"
          ></i>
        </el-input>

      </div>
      <el-table
          :data="tableData"
          stripe
          class="tableBox"
          v-loading="loading"
      >
        <el-table-column
            prop="username"
            label="用户名"
        ></el-table-column>
        <el-table-column
            prop="address"
            label="用户地址"
        ></el-table-column>
        <el-table-column
            prop="phone"
            label="用户电话"
        ></el-table-column>
        <el-table-column
            label="订购门店"
            prop="companyName"
        >
        </el-table-column>
        <el-table-column
            prop="remark"
            label="备注"
        ></el-table-column>
        <el-table-column
            prop="number"
            label="订购数量"
        ></el-table-column>
        <el-table-column
            prop="amount"
            label="订购总价"
        ></el-table-column>
        <el-table-column
            prop="orderTime"
            label="下单时间"
        ></el-table-column>
        <el-table-column
            label="支付方式"
        >
          <template slot-scope="scope">
            {{ String(scope.row.payMethod) === '1' ? '微信' : '支付宝' }}
          </template>
        </el-table-column>
        <el-table-column label="订单状态">
          <template slot-scope="scope">
            <div v-if="String(scope.row.status)==='1'">商家准备中</div>
            <div v-if="String(scope.row.status)==='2'">待派送</div>
            <div v-if="String(scope.row.status)==='3'">派送中</div>
            <div v-if="String(scope.row.status)==='4'">已送达</div>
            <div v-if="String(scope.row.status)==='5'">订单完成</div>
            <div v-if="String(scope.row.status)==='6'">订单已取消</div>
          </template>
        </el-table-column>
        <el-table-column
            label="操作"
            width="160"
            align="center"
        >
          <template slot-scope="scope">
            <el-button
                type="text"
                size="small"
                class="blueBug"
                @click="changeStatus(scope.row)"
            >
              <div v-if="String(scope.row.status)==='1'">准备完成</div>
              <div v-if="String(scope.row.status)==='2'">取消准备</div>
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          class="pageList"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="counts"
          :current-page.sync="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      ></el-pagination>
    </div>
  </div>
</div>
</template>

<script>
import axios from "axios";

export default {
  name: "Order",
  data(){
    return{
      tableData:[],
      loading:true,
      pageSize:10,
      counts:0,
      page:1,
      input:'',
    }
  },
  created() {
    this.init()
  },
  methods:{
    init(){
      const params = {
        page: this.page,
        pageSize: this.pageSize,
        name: this.input ? this.input : undefined
      }
      var _this=this
      axios({
        url:"/order/page",
        method:'get',
        params
      }).then(function (resp){
        //返回结果的data中为Page数据
        if (String(resp.data.code) === '1') {
          _this.tableData = resp.data.data.records || []
          _this.counts = resp.data.data.total
          _this.page=resp.data.data.current
          _this.loading=false
        }else {
          alert(resp.data.msg)
        }
      })
    },
    handleQuery(){
      this.page = 1;
      this.init();
    },
    //分页
    handleSizeChange(val){
      this.pageSize=val
      this.init()
    },
    //
    handleCurrentChange (val) {
      this.page = val
      this.init()
    },
    changeStatus(order){
      if(order.status===1||order.status===2){
        if(order.status===1)
           order.status=2
        else order.status=1
      var _this=this
      axios({
        url:"/order/updateOrder",
        method:'post',
        data:order
      }).then(function (resp){
        //返回结果的data中为Page数据
        if (String(resp.data.code) === '1') {
          _this.$message.success(resp.data.data)
          _this.init()
        }else {
         _this.$message.warning(resp.data.msg)
        }
      })
      }else {
        this.$message.info("该订单已经准备完成")
      }
    },
  }
}
</script>

<style scoped>

</style>