<template>
  <div>

    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu

          :unique-opened="false"
          :collapse-transition="false"
          background-color="#343744"
          text-color="#bfcbd9"
          active-text-color="yellow"
      >


          <div v-for="item in navigateList" :key="item.id">

            <router-link :to="item.url"  exact >

              <el-menu-item :index="item.id"  >


                <i class="iconfont" :class="item.icon" ></i>
                <span slot="title">{{ item.name }}</span>

              </el-menu-item>



            </router-link>



        </div>


      </el-menu>
    </el-scrollbar>


  </div>
</template>

<script>
import {mapState, } from 'vuex'

export default {

  name: "Navigate",
  data() {
    return {}
  },
  computed: {
    ...mapState('navigate_header', ['navigateList'])
  },
  methods: {
change(name){
  console.log(name)
},



  }
}
</script>

<style scoped>
.router-link-active{
  background-color: greenyellow;
  color: yellow;


}
</style>