<template>
  <div>

<!--表单检验el-form-item要加prop！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！-->
    <!--添加页面-->
    <el-dialog title="添加门店" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="formRules" >
        <el-form-item label="门店编号" :label-width="formLabelWidth" prop="companyId">
          <el-input v-model="form.companyId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="门店地址" :label-width="formLabelWidth" prop="address">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
            label="营业状态:"
            :label-width="formLabelWidth"
            prop="status"
        >
          <el-radio-group v-model="form.status">
            <el-radio label="营业"></el-radio>
            <el-radio label="歇业"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="门店名" :label-width="formLabelWidth" prop="companyName">
          <el-input v-model="form.companyName" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMemberHandle('form')">确 定</el-button>
      </div>
    </el-dialog>
    <!--修改页面-->
    <el-dialog title="修改门店信息" :visible.sync="dialogFormVisible1">
      <el-form :model="form" ref="form" :rules="formRules">
        <el-form-item label="门店编号" :label-width="formLabelWidth" prop="companyId">
          <el-input v-model="form.companyId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="门店地址" :label-width="formLabelWidth" prop="address">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
            label="门店营业状态"
            :label-width="formLabelWidth"
            prop="status"
        >
          <el-radio-group v-model="form.status">
            <el-radio label="营业"></el-radio>
            <el-radio label="歇业"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="门店名" :label-width="formLabelWidth" prop="companyName">
          <el-input v-model="form.companyName" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="sureUpdateMember()">确 定</el-button>
      </div>
    </el-dialog>





    <div style="height: 10%">门店管理</div>
    <hr>
    <div class="dashboard-container" id="member-app">
      <div class="container">
        <div class="tableBar">
          <el-input
              v-model="input"
              placeholder="请输入门店名"
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
            + 添加门店
          </el-button>

        </div>
        <el-table
            :data="tableData"
            stripe
            class="tableBox"
            v-loading="loading"
        >
          <el-table-column
              prop="companyId"
              label="门店编号"
          ></el-table-column>
          <el-table-column
              prop="address"
              label="门店地址"
          ></el-table-column>
          <el-table-column
              prop="phone"
              label="门店电话"
          ></el-table-column>
          <el-table-column
              label="门店名"
              prop="companyName"
          >
          </el-table-column>
          <el-table-column label="营业状态">
            <template slot-scope="scope">
              {{ String(scope.row.status) === '0' ? '歇业' : '正常营业' }}
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
                  style="color: orange"
                  @click="changeStatus(scope.row)"
              >
                {{ scope.row.status == '1' ? '歇业' : '营业' }}
              </el-button>
              <el-button
                  type="text"
                  size="small"
                  class="delBut non"
                  style="color: red"
                  @click="deleteCompany(scope.row)"

              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            class="pageList"
            :page-sizes="[5, 10, 20, 30]"
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
  name: "Company",
  data() {
    return {
      loading:true,
      dialogVisible: false,
      pageSize: 10,
      counts: 0,
      page: 1,
      input: '',
      tableData: [
        {
          // name:'张三',
          // username:'zhangsan',
          // phone:'11111111111',
          // identity:'0',
          // status:'1',
        }
      ],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      form: {
        companyName: '',
        companyId: '',
        address: '',
        status: '',
        phone: '',
      }, formLabelWidth: '120px',
      formRules: {
        companyName: [{
          required: true,
          trigger: "blur",
          min: 3,
        }],
        companyId: [{
          required: true,
          trigger: "blur",
          min: 3,
        }],
        address: [{
          required: true,
          trigger: "blur",
          min: 3,
        }],
        status: [{
          required: true,
          trigger: "blur",
          min: 1,
        }],
        phone: [{
          required: true,
          trigger: "blur",
          min: 3,
        }],
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      const params = {
        page: this.page,
        pageSize: this.pageSize,
       name: this.input ? this.input : undefined
      }
      var _this = this
      axios({
        url: "/company/page",
        method: 'get',
        params
      }).then(function (resp) {
        //返回结果的data中为Page数据
        if (String(resp.data.code) === '1') {
          _this.tableData = resp.data.data.records || []
          _this.counts = resp.data.data.total
          _this.page = resp.data.data.current
          console.log(_this.tableData)
          _this.loading=false
        } else {
          alert(resp.data.msg)
        }
      })
    },
    handleQuery() {
      this.page = 1;
      this.init();
    },
    addMemberHandle(form) {
      // var _this = this
      // console.log(this.form.status)
      // if (this.form.status == '营业')
      //   this.form.status = '1'
      // else this.form.status = '0'

      this.$refs[form].validate(valid => {
        if (valid) {
          var _this = this
          console.log(this.form.status)
          if (this.form.status == '营业')
            this.form.status = '1'
          else this.form.status = '0'
          axios({
            url: "/company/save",
            method: "post",
            data: _this.form
          }).then(function (resp) {
            if (String(resp.data.code) == '1') {
              _this.$message.success("添加成功")
              _this.form.companyName = ''
              _this.form.companyId = ''
              _this.form.address = ''
              _this.form.status = ''
              _this.form.phone = ''
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
    handleSizeChange(val) {
      this.pageSize = val
      this.init()
    },
    //
    handleCurrentChange(val) {
      this.page = val
      this.init()
    },
    changeStatus(company) {
      if (company.status == 1) {
        company.status = 0
      } else
        company.status = 1
      var _this = this
      axios({
        url: "/company/update member",
        method: 'post',
        data: company
      }).then(function (resp) {
        //返回结果的data中为Page数据
        if (String(resp.data.code) === '1') {
          _this.$message.success("修改成功")
          _this.init()
        } else {
          alert(resp.data.msg)
        }
      })

    },
    updataMemberHandle(member) {
      this.dialogFormVisible1 = true
      var _this = this
      axios({
        url: "/company/getbyid",
        method: 'post',
        data: member
      }).then(function (resp) {
        if (String(resp.data.code) === '1') {
          _this.form = resp.data.data
          if (_this.form.status == '1')
            _this.form.status = '营业'
          else _this.form.status = '歇业'
          _this.init()
        } else {
          _this.$message.info(resp.data.msg)
        }
      })
    },
    sureUpdateMember() {
      this.$refs.form.validate(valid => {
        if (valid) {
          var _this = this
          console.log(this.form.status)
          if (this.form.status == '营业')
            this.form.status = '1'
          else this.form.status = '0'
          axios({
            url: "/company/updatecompany",
            method: "post",
            data: _this.form
          }).then(function (resp) {
            if (String(resp.data.code) == '1') {
              _this.$message.success(resp.data.data)
              _this.form.companyName = ''
              _this.form.companyId = ''
              _this.form.address = ''
              _this.form.status = ''
              _this.form.phone = ''
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

    },
    deleteCompany(company) {
     var _this=this
      this.$confirm('确认删除？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(value=> {
        console.log(value)
        console.log(company.id)
        axios({
          url:'/company/delete',
          method:'delete',
          params:{id:company.id}

        }).then(function (resp){
          if (String(resp.data.code) === '1') {
            _this.$message.success("删除成功")
            _this.init()
          }
          else {
            this.$message.error("删除失败")
          }
        })
      })
          .catch(b => {
            console.log(b)
          });



    },

  }
}
</script>

<style scoped>

</style>