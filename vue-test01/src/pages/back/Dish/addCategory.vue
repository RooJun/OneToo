<template>
  <div>
    <el-page-header @back="backDish" content="添加菜品分类">
    </el-page-header>
    <hr>
    <div style="display: flex">
      <div class="displayCategory">
        <el-table
            :data="categoryList"
            stripe
            class="tableBox"
            tooltip-effect="dark"
        >
          <el-table-column
              prop="name"
              label="当前菜品分类"
          ></el-table-column>
          <el-table-column
              label="操作"
              width="160"
              align="center"
          >
            <template slot-scope="scope">

              <el-button
                  type="text"
                  size="small"
                  class="delBut non"
                  style="color: red"
                  @click="deleteCategory(scope.row)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="width: 65%;">
        <el-form :model="form" ref="form" :rules="formRules">
          <el-form-item label="菜品类名" :label-width="formLabelWidth" prop="name">
            <el-input v-model="form.name" autocomplete="off" placeholder="请填写菜品名称"
                      style="width: auto;float: left"></el-input>
          </el-form-item>
          <el-form-item label="菜品类别" :label-width="formLabelWidth" prop="category">
            <!--      <el-input v-model="form.price" autocomplete="off" placeholder="请选择菜品分类" ></el-input>-->
            <template>
              <el-select v-model="form.category" placeholder="请选择" style="float: left">
                <el-option
                    :value="caixi">
                </el-option>
                <el-option
                    :value="taocan">
                </el-option>
              </el-select>
            </template>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" style="margin-right: 300px;margin-top: 100px">
          <el-button @click="clearDish">重 置</el-button>
          <el-button type="primary" @click="addDishHandle('form')">保 存</el-button>
        </div>
      </div>

    </div>


  </div>
</template>

<script>
import {mapState} from "vuex";
import axios from "axios";

export default {
  name: "addCategory",
  data() {
    return {
      caixi: "菜系",
      taocan: '套餐',
      form: {
        name: '',
        category: ''

      },
      formRules: {
        name:[
          {
            required: true,
            trigger: "blur",
            min: 1,
          }
        ],
        category:[
          {
            required: true,
            trigger: "blur",
            min: 1,
          }
        ]
      }
    }
  },
  computed: {
    ...mapState('dish', ['categoryList', 'formLabelWidth'])
  },
  methods: {
    backDish() {
      this.$router.push('/home/dish')
    },
    clearDish() {
      this.form.name = ''
      this.form.category = ''
    },
    addDishHandle(){
      this.$refs.form.validate(valid => {

        if (valid) {
          var _this = this
          axios({
            url: "/category/save",
            method: "post",
            data: _this.form
          }).then(function (resp) {
            if (String(resp.data.code) == '1') {
              _this.$message.success("添加成功")
              _this.form.name = ''
              _this.form.category=''
              _this.$router.push('/home/dish')
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
    deleteCategory(category){
      var _this=this

      this.$confirm('确认删除？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=> {
        axios({
          url:'/category/delete',
          method:'delete',
          params:{id:category.id}
        }).then(function (resp){
          if (String(resp.data.code) === '1') {
            _this.$message.success("删除成功")
            _this.$router.push('/home/dish')
          }
          else {
            this.$message.error("删除失败")
          }
        })
      })
          .catch(b => {
            console.log(b)
          });
    }
  }
}
</script>

<style scoped>
.displayCategory {
  width: 35%;
  background-color: #faf2cc;
}
</style>