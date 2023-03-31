<template>
  <div class="demo">
    <h2>学校名称：{{name}}</h2>
    <h2>学校地址：{{address}}</h2>
    <button value="点我显示学校名称" @click="showName"></button>
  </div>
</template>

<script>

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name:'School',
  data(){
    return{
      name:'hnust',
      address:'湖南'
    }
  },
  methods:{
    showName(){
      alert(this.name)
    }
  },
  mounted() {
    this.$bus.$on(
        'hello',(data)=>{
          console.log('我是School组件我收到了数据',data)
        }
    )
  },
  beforeDestroy() {
    this.$bus.$off('hello')
  }
}
</script>

<style>
.demo{
  background-color: pink;
}
</style>