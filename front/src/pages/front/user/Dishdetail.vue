<template>
<div style="display: flex;flex-direction: column">
  <div>
    <img :src="getimage(shopDetail.image)" width="100%">
  </div>
  <div>
  {{shopDetail.description}}
  </div>

</div>
</template>

<script>
import {mapState} from "vuex";
import {Button} from "vant";

export default {
  name: "Dishdetail",
  components:{
    [Button.name]:Button
  },
  computed:{
    ...mapState('shop',['shopDetail',])
  },
  methods:{
    getimage(){
      return 'http://192.168.58.100:8080/common/download?name='+this.shopDetail.image
    },

  }

}
</script>

<style scoped>

</style>