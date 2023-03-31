<template>
	<div>
		<h1>当前求和为：{{ sum }}</h1>

    <h3>
      学校：{{school}},
      学习：{{student}}
    </h3>
    <h2>下方组件的总人数是：{{personList.length}}</h2>
		<select v-model.number="n">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
		</select>
		<button @click="increment(n)">+</button>
		<button @click="decrement(n)">-</button>
		<button @click="incrementOdd">当前求和为奇数再加</button>
		<button @click="incrementWait">等一等再加</button>
	</div>
</template>

<script>
import {mapState,mapMutations} from 'vuex'
	export default {
    // eslint-disable-next-line vue/multi-word-component-names
		name:'Count',
		data() {
			return {
				n:1, //用户选择的数字
			}
		},
    computed: {
      // ...mapState({sum:'sum',school:'school',student:'student'})
    ...mapState('countAbout',['sum','school','student']),
      ...mapState('personAbout',['personList'])
    },
    methods: {
			// increment(){
      //   this.$store.commit('JIA',this.n)
      //
			// },
			// decrement(){
      //   this.$store.commit('JIAN',this.n)
      //
			// },
      ...mapMutations( 'countAbout',{increment:'JIA',decrement:'JIAN'}),
			incrementOdd(){
          this.$store.dispatch('countAbout/jiaOdd',this.n)
			},
			incrementWait(){

          this.$store.dispatch('countAbout/jiaWait',this.n)

			},
		},
	}
</script>

<style lang="css">
	button{
		margin-left: 5px;
	}
</style>
