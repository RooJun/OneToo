<template>
  <li>
    <label>
      <input type="checkbox" :checked="todo.done" @change="handlecheck(todo.id)"/>
      <span>{{todo.title}}</span>
    </label>
    <button class="btn btn-danger" @click="handleDelete(todo.id)">删除</button>
  </li>
</template>

<script>
export default {
  name: "MyItem",
  //声明接收todo对象
  props:['todo'],
  methods:{
    //勾选or取消勾选
    handlecheck(id){
      // this.checkTodo(id)
      this.$bus.$emit('checkTodo',id)
    },
    //删除
    handleDelete(id){
      if(confirm('确定删除吗？')){
        // this.deleteTodo(id)
        this.$bus.$emit('deleteTodo',id)
      }

    }
  }

}
</script>

<style scoped>

/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display: none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}
/*鼠标悬浮效果*/
li:hover{
  background-color: #ddd;
}
/*鼠标悬浮时，删除按钮显示*/
li:hover button{
  display: block;
}
</style>