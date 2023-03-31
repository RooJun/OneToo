<template>
  <div>
    <h2>学生姓名：{{name}}</h2>
    <h2>学生年龄：{{age}}</h2>
    <button @click="sendStudentName">点我给School组件传递数据</button>
  </div>
</template>

<script>

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name:'Student',
  data(){
    return{
      name:'张三',
      age:18
    }
  },
  methods:{
    sendStudentName(){
      this.$bus.$emit('hello',this.name)

    }
  }

}
</script>
>