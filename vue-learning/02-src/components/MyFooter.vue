<template>
<!--  v-show 内为真，则该div显示，否则不显示-->
  <div class="todo-footer" v-show="total">
    <label>
      <input type="checkbox" :checked="isAll" @click="checkAll"/>
    </label>
    <span>
          <span>已完成{{doneTotal}}</span> / 全部{{total}}
        </span>
    <button class="btn btn-danger" @click="clearAll">清除已完成任务</button>
  </div>
</template>

<script>
export default {
  name: "MyFooter",
  props:['todos','checkAllTodo','clearAllTodo'],
  computed:{
    total(){
      return this.todos.length
    },
    doneTotal(){
      // return this.todos.reduce((pre,current)=>pre +(current.done? 1:0),0)
      // const x=this.todos.reduce((pre,current)=>{
      //   return pre+(current.done?1:0)
      // },0)

    //  第二种方法
      return this.todos.filter(todo=>todo.done==true).length
    },
    isAll(){
      return this.doneTotal==this.total&&this.total>0
    }

  },
  methods:{
    checkAll(e){
      this.checkAllTodo(e.target.checked)
    },
    clearAll(){
      this.clearAllTodo()
    }

  }
}
</script>

<style scoped>

/*footer*/
.todo-footer {
  height: 40px;
  line-height: 40px;
  padding-left: 6px;
  margin-top: 5px;
}

.todo-footer label {
  display: inline-block;
  margin-right: 20px;
  cursor: pointer;
}

.todo-footer label input {
  position: relative;
  top: -1px;
  vertical-align: middle;
  margin-right: 5px;
}

.todo-footer button {
  float: right;
  margin-top: 5px;
}
</style>