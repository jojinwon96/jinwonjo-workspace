<template>
  <h4 class="myPage-content-title">주문내역</h4>
  <div class="myPage-content">
    <div class="myPage-head">

    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "orderList.vue",

  data(){
    return{
      orderList:[],
    }
  },

  mounted() {
    axios.get("/api/account/order").then(({data})=>{
      this.orderList = data;
      console.log(this.orderList);
    })
  },
}
</script>

<style scoped>
.myPage-head {
  padding-bottom: 10px;
  padding-left: 5px;
  margin: 1rem 0;
}
</style>