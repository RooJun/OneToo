<template>
  <div>
<!--添加页面-->
    <el-dialog title="添加人员" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="formRules">
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="账号" :label-width="formLabelWidth" prop="username">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
            label="性别:"
            :label-width="formLabelWidth"
            prop="sex"
        >
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份证号" :label-width="formLabelWidth" prop="idNumber">
          <el-input v-model="form.idNumber" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMemberHandle">确 定</el-button>
      </div>
    </el-dialog>
<!--修改页面-->
    <el-dialog title="修改信息" :visible.sync="dialogFormVisible1">
      <el-form :model="form" ref="form" :rules="formRules">
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="账号" :label-width="formLabelWidth" prop="username">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
            label="性别:"
            :label-width="formLabelWidth"
            prop="sex"
        >
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份证号" :label-width="formLabelWidth" prop="idNumber">
          <el-input v-model="form.idNumber" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="sureUpdateMember">确 定</el-button>
      </div>
    </el-dialog>


  <div style="height: 10%">人员管理</div>
    <hr>
  <div class="dashboard-container" id="member-app">
    <div class="container">
      <div class="tableBar">
        <el-input
            v-model="input"
            placeholder="请输入员工姓名"
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

        <el-button
            style="float: right;margin-bottom: 15px"
            type="primary"
            @click="dialogFormVisible = true"
        >
          + 添加员工
        </el-button>

      </div>
      <el-table
          :data="tableData"
          stripe
          class="tableBox"
          v-loading="loading"
      >
        <el-table-column
            prop="name"
            label="姓名"
        ></el-table-column>
        <el-table-column
            prop="username"
            label="账号"
        ></el-table-column>
        <el-table-column
            prop="phone"
            label="手机号"
        ></el-table-column>
        <el-table-column label="身份">
          <template slot-scope="scope">
            {{ String(scope.row.identity) === '0' ? '店员' : '顾客' }}
          </template>
        </el-table-column>
        <el-table-column label="账号状态">
          <template slot-scope="scope">
            {{ String(scope.row.status) === '0' ? '已禁用' : '正常' }}
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
                @click="updataMemberHandle(scope.row)"

            >
              编辑
            </el-button>
            <el-button
                type="text"
                size="small"
                class="delBut non"
                style="color: red"
                @click="changeStatus(scope.row)"
            >
              {{ scope.row.status == '1' ? '禁用' : '启用' }}
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
  name: "Member",
  data(){
    return{
      loading:true,
      pageSize:10,
      counts:0,
      page:1,
      input:'',
      tableData:[
        {
          // name:'张三',
          // username:'zhangsan',
          // phone:'11111111111',
          // identity:'0',
          // status:'1',
        }
      ],
      dialogFormVisible: false,
      dialogFormVisible1:false,
      form: {
        name: '',
        username:'',
        phone:'',
        sex:'男',
        idNumber:'',

      }, formLabelWidth: '120px',
      formRules: {
        name: [{
          required: true,
          trigger: "blur",
          min: 2,
        }],
        username: [{
          required: true,
          trigger: "blur",
          min: 3,
        }],
        phone: [{
          required: true,
          trigger: "blur",
          min: 11,
        }],
        sex: [{
          required: true,
          trigger: "blur",
          min: 1,
        }],
       idNumber: [{
          required: true,
          trigger: "blur",
          min: 10,
        }],
      }
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
        url:"/member/page",
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
    addMemberHandle(){

      this.$refs.form.validate(valid => {
        if (valid) {
          var _this = this
          console.log(this.form.status)
          if(this.form.sex=='男')
            this.form.sex='1'
          else this.form.sex='0'
          axios({
            url: "/member/save",
            method: "post",
            data: _this.form
          }).then(function (resp) {
            if (String(resp.data.code) == '1') {
              _this.$message.success("添加成功")
              _this.form.name=''
              _this.form.username=''
              _this.form.sex='男'
              _this.form.phone=''
              _this.form.idNumber=''
              _this.init()
              _this.dialogFormVisible = false
            } else {
              alert(resp.data.msg)
            }
          })
        } else {
          alert("error submit!!");
          return false;
        }
      });

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
    changeStatus(member){
      if(member.status==1){
        member.status=0
      }
      else
        member.status=1
      var _this=this
      axios({
        url:"/member/update member",
        method:'post',
        data:member
      }).then(function (resp){
        //返回结果的data中为Page数据
        if (String(resp.data.code) === '1') {
          alert(resp.data.data)
          _this.init()
        }else {
          alert(resp.data.msg)
        }
      })

    },
    updataMemberHandle(member){
      this.dialogFormVisible1=true
      var _this=this
      axios({
        url:"/member/getbyid",
        method:'post',
        data:member
      }).then(function (resp){
        if (String(resp.data.code) === '1') {
          _this.form=resp.data.data
          if(_this.form.sex=='1')
            _this.form.sex='男'
          else _this.form.sex='女'
          _this.init()
        }else {
          alert(resp.data.msg)
        }
      })
    },
    sureUpdateMember(){
      this.$refs.form.validate(valid => {
        if (valid) {
          var _this = this
          if(this.form.sex=='男')
            this.form.sex='1'
          else this.form.sex='0'
          axios({
            url:"/member/update member",
            method: "post",
            data: _this.form
          }).then(function (resp) {
            if (String(resp.data.code) == '1') {
              _this.$message.success(resp.data.data)
              _this.form.name=''
              _this.form.username=''
              _this.form.sex=''
              _this.form.phone=''
              _this.form.idNumber=''
              _this.init()
              _this.dialogFormVisible1 = false
            } else {
              _this.$message.warning(resp.data.msg)
            }
          })
        } else {
          alert("error submit!!");
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>