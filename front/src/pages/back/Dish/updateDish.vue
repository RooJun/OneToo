<template>
  <div>
    <el-page-header @back="backDish" content="修改菜品">
    </el-page-header>
    <hr>
    <el-form :model="form" ref="form" :rules="formRules">
      <el-form-item label="菜品名称" :label-width="formLabelWidth" prop="name">
        <el-input v-model="form.name" autocomplete="off" placeholder="请填写菜品名称"
                  style="width: auto;float: left"></el-input>
      </el-form-item>
      <el-form-item label="菜品价格" :label-width="formLabelWidth" prop="price">
        <el-input v-model="form.price" autocomplete="off" placeholder="请设置菜品价格"
                  style="width: auto;float: left"></el-input>
      </el-form-item>
      <el-form-item label="菜品编号" :label-width="formLabelWidth" prop="code">
        <el-input v-model="form.code" autocomplete="off" placeholder="请设置菜品编号"
                  style="width: auto;float: left"></el-input>
      </el-form-item>
      <el-form-item label="菜品分类" :label-width="formLabelWidth" prop="categoryName">
        <!--      <el-input v-model="form.price" autocomplete="off" placeholder="请选择菜品分类" ></el-input>-->
        <template>
          <el-select v-model="form.categoryName" placeholder="请选择" style="float: left">
            <el-option
                v-for="item in categoryList"
                :key="item.id"
                :value="item.name">
            </el-option>
          </el-select>
        </template>
      </el-form-item>

      <el-form-item
          label="菜品图片:"
          prop="image"
          :label-width="formLabelWidth"
      >

          <img v-if="form.image" :src="getimage(form.image)" class="avatar" style="width: 200px;height: 180px;float: left">
<!--          <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->


          <el-upload
              style="float: left;"
              class="avatar-uploader"
              action="api/common/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="imageUrl"  class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>


      </el-form-item>

      <el-form-item label="菜品描述" :label-width="formLabelWidth" prop="description">
        <el-input
            type="textarea"
            :rows="2"
            placeholder="菜品描述，最长200字"
            v-model="form.description">
        </el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="clearDish">重 置</el-button>
      <el-button type="primary" @click="updateDishHandle('form')">确定更改</el-button>
    </div>

  </div>
</template>

<script>
import {mapState} from "vuex";
import axios from "axios";
export default {
  name: "updateDish",
  data(){
    return{
      imageUrl: '',
    }
  },
  computed:{
    ...mapState('dish',['form','formRules','formLabelWidth','categoryList'])
  },
  methods:{
    backDish(){
      this.$router.push('/home/dish')
    },
    clearDish(){
      this.form.name = ''
      this.form.price = ''
      this.form.categoryName = ''
      this.form.image = ''
      this.imageUrl=''
      this.form.description = ''
      this.form.code=''
    },
    updateDishHandle(){
      this.$refs.form.validate(valid => {
        if (valid) {
          var _this = this
          axios({
            url: "/dish/updatedish",
            method: "post",
            data: _this.form
          }).then(function (resp) {
            if (String(resp.data.code) == '1') {
              _this.$message.success("添加成功")
              _this.form.name = ''
              _this.form.price = ''
              _this.form.categoryName = ''
              _this.form.image = ''
              _this.imageUrl=''
              _this.form.description = ''
              _this.form.code=''
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
    getimage(image){
      return 'http://192.168.58.100:8080/common/download?name='+image
    },
    handleAvatarSuccess(res, file) {
      this.form.image=res.data
      // console.log(res.data)
      //res.data为返回来的图片名，即服务器保存的名字
      this.imageUrl = URL.createObjectURL(file.raw);
    },
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  float: left;

}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  /*border: 1px dashed #46b8da;*/
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>