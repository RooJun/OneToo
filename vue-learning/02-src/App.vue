<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <MyHeader @addTodo="addTodo"/>
        <MyList :todos="todos" />
        <MyFooter :todos="todos" :checkAllTodo="checkAllTodo " :clearAllTodo="clearAllTodo"/>
      </div>
    </div>
  </div>
</template>

<script>
import MyFooter from "@/components/MyFooter";
import MyHeader from "@/components/MyHeader";
import MyList from "@/components/MyList";

export default {
  name: 'App',
  // eslint-disable-next-line vue/no-unused-components
  components: {MyFooter, MyHeader, MyList},
  data(){
    return{
      todos:[
        // {id:'001',title:'吃饭',done:true},
        // {id:'002',title:'睡觉',done:false},
        // {id:'003',title:'开车',done:true},
      ]
    }
  },
  methods:{
    addTodo(todoObj){
      this.todos.unshift(todoObj)
    },
  //  勾选或者取消一个todo
    checkTodo(id){
      this.todos.forEach((todo)=>{
        if (todo.id==id) todo.done=!todo.done
      })

    },
  //  删除；过滤之后会得到一个新的数据组
    deleteTodo(id){
      this.todos=this.todos.filter(todo => todo.id != id)
      // this.todos.filter((todo)=>{
      //   return todo.id!=id
      //     }
      // )
    },
  //  全选or取消全选
    checkAllTodo(done){
      this.todos.forEach((todo)=>{
        todo.done=done
      })
    },
    clearAllTodo(){
      this.todos=this.todos.filter((todo)=>{
        return !todo.done
      })
    }
  },
  mounted() {
    this.$bus.$on('checkTodo',this.checkTodo)
    this.$bus.$on('deleteTodo',this.deleteTodo)
  },
  beforeDestroy() {
    this.$bus.$off('checkTodo')
    this.$bus.$off('deleteTodo')
  }
}
</script>

<style>
/*base*/
body {
  background: #fff;
}

.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}

.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}

.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}

.btn:focus {
  outline: none;
}

.todo-container {
  width: 600px;
  margin: 0 auto;
}

.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}


</style>