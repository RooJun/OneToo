<template>
<div>
  <div style="height: 10%">配送管理</div>
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
            label="订购门店地址"
            prop="companyName"
            :formatter="getCompanyAddress"
        >
        </el-table-column>
        <el-table-column
            label="门店电话"
            prop="companyName"
            :formatter="getCompanyPhone"
        >
        </el-table-column>
        <el-table-column
            prop="remark"
            label="备注"
        ></el-table-column>

        <el-table-column
            prop="orderTime"
            label="下单时间"
        ></el-table-column>

        <el-table-column label="订单状态">
          <template slot-scope="scope">
            <div v-if="String(scope.row.status)==='1'">商家准备中</div>
            <div v-if="String(scope.row.status)==='2'">待派送</div>
            <div v-if="String(scope.row.status)==='3'">派送中</div>
            <div v-if="String(scope.row.status)==='4'">已送达</div>
            <div v-if="String(scope.row.status)==='5'">交易完成</div>
            <div v-if="String(scope.row.status)==='6'">交易已取消</div>
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
              <div v-if="String(scope.row.status)==='2'">派送</div>
              <div v-if="String(scope.row.status)==='3'">取消派送</div>
              <div v-if="String(scope.row.status)==='4'">已送达</div>
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
  name: "Delivery",
  data(){
    return{
      tableData:[],
      loading:true,
      pageSize:10,
      counts:0,
      page:1,
      input:'',
      companies:[]
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
      axios({
        method:'get',
        url:'/company/getCompanies'
      }).then(resp=>{
        if (String(resp.data.code) === '1') {
          _this.companies = resp.data.data || []
          console.log(_this.companies)
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
    getCompanyAddress(row){
      for(var company in this.companies){
        if(row.companyName===this.companies[company].companyName)
          return this.companies[company].address
      }
    },
    getCompanyPhone(row){
      for(var company in this.companies){
        if(row.companyName===this.companies[company].companyName)
          return this.companies[company].phone
      }
    },
    changeStatus(order){
      if(order.status===2||order.status===3){
        if(order.status===2)
          order.status=3
        else order.status=2
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
        this.$message.info("该订单已派送")
      }
    },
}
}
</script>

<style scoped>

</style>