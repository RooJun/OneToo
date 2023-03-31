<template>
  <div>
    <div style="height: 10%">菜品管理</div>
    <hr>
    <div class="dashboard-container" id="member-app">
      <div class="container">
        <div class="tableBar">
          <el-input
              v-model="input"
              placeholder="请输入菜品名"
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
              type="text"
              size="small"
              class="delBut non"
              style="color: red"
              @click="deletedishs"
          > 批量删除
          </el-button>

          <el-button
              type="text"
              size="small"
              class="delBut non"
              style="color: lightseagreen"
              @click="changestatusdishsqi"
          > 批量起售
          </el-button>

          <el-button
              type="text"
              size="small"
              class="delBut non"
              style="color: orange"
              @click="changestatusdishsting"
          > 批量停售
          </el-button>

          <el-button
              style="float: right;margin-bottom: 15px"
              type="primary"
              @click="openAddDish"
          >
            + 添加菜品
          </el-button>

          <el-dropdown split-button type="primary" style="margin-right: 3px ; float: right" @click="addCategory">
            +菜品类别
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-for="its in categoryList " :key="its.id" v-text="its.name">{{ its.name }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </div>
        <el-table
            :data="tableData"
            stripe
            class="tableBox"
            v-loading="loading"
            @selection-change="handleSelectionChange"
            tooltip-effect="dark"
        >
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column
              prop="name"
              label="菜品名称"
          ></el-table-column>
          <el-table-column
              prop="image"
              label="图片"
          >
            <template slot-scope="{ row }">


              <el-image style="width: auto; height: 40px; border:none;cursor: pointer;"
                        :src="getimage(row.image)"

              >
                <div slot="error" class="image-slot">
                  <!--                通过slot = error可自定义加载失败内容-->
                  <i class="el-icon-picture-outline"></i>
                </div>
                <div slot="placeholder" class="image-slot">
                  <!--                通过slot = placeholder可自定义占位内容-->
                  <i class="el-icon-loading"></i>加载中
                </div>
              </el-image>
            </template>

          </el-table-column>
          <el-table-column
              prop="categoryName"
              label="菜品分类"
          >

          </el-table-column>
          <el-table-column
              label="售价"
              prop="price"
          >
            <template slot-scope="scope">
              <span>￥{{ scope.row.price }}</span>
            </template>
          </el-table-column>
          <el-table-column label="售卖状态" style="width: 4px">
            <template slot-scope="scope">
              {{ String(scope.row.status) === '0' ? '停售' : '起售' }}
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
                  @click="updateDishHandle(scope.row)"
              >
                修改
              </el-button>
              <el-button
                  type="text"
                  size="small"
                  class="delBut non"
                  style="color: orange"
                  @click="changeStatus(scope.row)"
              >
                {{ scope.row.status == '1' ? '停售' : '起售' }}
              </el-button>
              <el-button
                  type="text"
                  size="small"
                  class="delBut non"
                  style="color: red"
                  @click="deleteDish(scope.row)"
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
import qs from 'qs'
import axios from "axios";
import {mapMutations, mapActions, mapState} from "vuex";

export default {
  name: "Dish",
  data() {
    return {
      multipleSelection: [],
      loading: true,
      dialogVisible: false,
      pageSize: 10,
      counts: 0,
      page: 1,
      input: '',
      tableData: [],
      //若上面中括号内有一个{}，它也会被认为一个对象，里面的值类型为undefined
    }
  },

  created() {
    this.init()
    this.displayCategory()
  },
  computed: {
    ...mapState('dish', ['categoryList'])
  },

  methods: {
    ...mapActions('dish', ['displayCategory']),
    openAddDish() {
      this.$router.push('/home/addDish')
    },

    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val)
    },
    addCategory() {
      this.$router.push('/home/addCategory')
    },

    //注意是``不是''!!!!!!!!!!!!!!!!!!!!!!!
    //直接给src属性一个服务器端资源的地址，img控件会自动获取并解析资源。
    getimage(image) {
      return 'http://192.168.58.100:8080/common/download?name=' + image
      // axios({
      //   method:'get',
      //   url:'/common/download',
      //   params:{
      //     name:image
      //   },
      //   responseType:'blob'
      // }).then(function (resp){
      //
      //   console.log(URL.createObjectURL(resp.data))
      //   return  URL.createObjectURL(resp.data)


      // const dataInfo = resp.data
      // // let reader = new window.FileReader()
      // // // 使用readAsArrayBuffer读取文件, result属性中将包含一个 ArrayBuffer 对象以表示所读取文件的数据
      // // reader.readAsArrayBuffer(dataInfo)
      // // reader.onload = function (e) {
      // //   const result = e.target.result
      // //   const contentType = dataInfo.type
      // //   // 生成blob图片,需要参数(字节数组, 文件类型)
      // //   const blob = new Blob([result], { type: contentType })
      // //   // 使用 Blob 创建一个指向类型化数组的URL, URL.createObjectURL是new Blob文件的方法,可以生成一个普通的url,可以直接使用,比如用在img.src上
      // //   const url = window.URL.createObjectURL(blob)
      // //   console.log(url) // 产生一个类似 blob:d3958f5c-0777-0845-9dcf-2cb28783acaf 这样的URL字符串
      // //   return url
      // // }
      // })
    },
    init() {
      const params = {
        page: this.page,
        pageSize: this.pageSize,
        name: this.input ? this.input : undefined
      }
      var _this = this
      axios({
        url: "/dish/page",
        method: 'get',
        params
      }).then(function (resp) {
        //返回结果的data中为Page数据
        if (String(resp.data.code) === '1') {
          _this.tableData = resp.data.data.records || []
          _this.counts = resp.data.data.total
          _this.page = resp.data.data.current
          console.log(_this.tableData)
          _this.loading = false
        } else {
          alert(resp.data.msg)
        }
      })
    },
    handleQuery() {
      this.page = 1;
      this.init();
    },
    //分页
    handleSizeChange(val) {
      this.pageSize = val
      this.init()
    },
    handleCurrentChange(val) {
      this.page = val
      this.init()
    },
    changeStatus(dish) {
      if (dish.status == 1) {
        dish.status = 0
      } else
        dish.status = 1
      var _this = this
      axios({
        url: "/dish/updatedish",
        method: 'post',
        data: dish
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
    updateDishHandle(dish) {
      this.$router.push('/home/updateDish')
      this.$store.dispatch('dish/updateDishHandle', dish)
    },
    deleteDish(dish) {
      var _this = this

      this.$confirm('确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios({
          url: '/dish/delete',
          method: 'delete',
          params: {id: dish.id}
        }).then(function (resp) {
          if (String(resp.data.code) === '1') {
            _this.$message.success("删除成功")
            _this.init()
          } else {
            this.$message.error("删除失败")
          }
        })
      })
          .catch(b => {
            console.log(b)
          });
    },
    deletedishs() {
      var ids = []
      this.multipleSelection.forEach(item => {
        ids.push(item.id)
      })

      var _this = this
      this.$confirm('这些菜品都会被永久删除，确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios({
          url: '/dish/deletes',
          method: 'delete',
          params: {ids},
          paramsSerializer: params => {
            return qs.stringify(params, {indices: false})
            //  将ids序列化，避免url中出现ids[]=?ids[]=?
          }
        }).then(function (resp) {
          if (String(resp.data.code) === '1') {
            _this.$message.success("删除成功")
            _this.init()
          } else {
            this.$message.error("删除失败")
          }
        })
      })
          .catch(b => {
            console.log(b)
          });

    },
    changestatusdishsqi() {

      this.multipleSelection.forEach(dish => {
        if (dish.status == 0)
          dish.status = 1
      })
      var _this = this
      axios({
        url: "/dish/updatedishs",
        method: 'post',
        data: _this.multipleSelection
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
    changestatusdishsting() {

      this.multipleSelection.forEach(dish => {
        if (dish.status == 1)
          dish.status = 0
      })
      var _this = this
      axios({
        url: "/dish/updatedishs",
        method: 'post',
        data: _this.multipleSelection
      }).then(function (resp) {
        //返回结果的data中为Page数据
        if (String(resp.data.code) === '1') {
          _this.$message.success("修改成功")
          _this.init()
        } else {
          alert(resp.data.msg)
        }
      })

    }

  }
}
</script>

<style scoped>

</style>