<template>
<div>
  <el-container>
    <el-header>
      <Header/>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <Navigate/>
      </el-aside>
      <el-main>
        <router-view>

        </router-view>
      </el-main>
    </el-container>
  </el-container>
</div>
</template>

<script>
import Navigate from "@/component/Navigate";
import Header from "@/component/Header";
export default {
  name: "Home",
  components: {Header, Navigate}
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  /*text-align: center;*/
  line-height: 60px;
}

.el-aside {
  background-color: rgb(52, 55, 68);
  color: #333;
  text-align: center;
  line-height: 200px;

}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  /*line-height: 160px;*/
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>