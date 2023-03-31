<template>
<div>
  <h1>人员列表</h1>
  <input type="text" v-model="name" placeholder="请输入名字">
  <button @click="add">添加</button>
  <button @click="addWang">添加一个姓王的人</button>
  <button @click="addPersonServe">添加一个人名字随机</button>
  <ul>
    <li v-for="p in personList" :key="p.id">{{p.name}}</li>
  </ul>
  <h2>上方组件的求和为：{{sum}}</h2>
  <h2> 列表中第一个人的名字为：{{firstPersonName}}</h2>
</div>
</template>

<script>
import {mapState} from "vuex";
import {nanoid} from "nanoid";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Person",
  data(){
    return{
      name:''
    }
  },
  computed:{
    // personList(){
    //   return this.$store.state.personList
    // }
    ...mapState('countAbout',['sum']),
    ...mapState('personAbout',['personList']),
    firstPersonName() {
      return this.$store.getters['personAbout/firstPersonName']
    }
  },
  methods:{
    add(){
      const personObj={id:nanoid(),name:this.name}
      //!!!!!!!!!!!!!!!!!!!!!!!!!!11
      this.$store.commit('personAbout/ADD_PERSON',personObj)
      this.name=''
    },
    addWang(){
      const personObj = {id :nanoid(),name:this.name}
        this.$store.dispatch("personAbout/addPersonWang",personObj)
      this.name=''

          },
    addPersonServe() {
      this.$store.dispatch('personAbout/addPersonServe')
    }
  }
}
</script>

<style scoped>

</style>