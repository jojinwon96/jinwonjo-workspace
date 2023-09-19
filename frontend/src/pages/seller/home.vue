<template>
  <div class="wrap">
    <sellerHeader/>
    <div class="s-home-wrap">
        <span class="s-home-title">관리 현황</span>
        <div class="s-home-items">
          <div class="s-home-item">
            <span class="s-item-title">처리해야 할 주문건</span>
            <span class="s-item-count">{{ orderCount }}건</span>
          </div>

          <div class="s-home-item">
            <span class="s-item-title">처리해야 할 환불건</span>
            <span class="s-item-count">0건</span>
          </div>

          <div class="s-home-item">
            <span class="s-item-title">처리해야 할 교환건</span>
            <span class="s-item-count">0건</span>
          </div>

          <div class="s-home-item">
            <span class="s-item-title">응답해야 할 Q&A</span>
            <span class="s-item-count">0건</span>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import sellerHeader from "@/components/seller/sellerHeader";
import axios from "axios";

export default {
  name: "sellerHome",

  data(){
    return{
       orderCount:0,
    }
  },

  mounted() {
    // 나중에 서버에서 map으로 받아올예정 (주문건, 환불건, 교환건, qna) 수량
    // 현재는 주문건 수량만 받아옴
    axios.get("/api/seller/order-count").then(({data})=>{
      this.orderCount = data;
    })
  },

  components:{
    sellerHeader,
  }
}
</script>

<style scoped>

.s-home-wrap{
  margin: 8rem auto;
  width: 1000px;
  padding: 1.2rem;
}

.s-home-title{
  display: block;
  font-size: 18px;
  font-weight: 600;
  border-bottom: 1px solid #999999;
  padding: 15px 0;
}

.s-home-items{
  display: flex;
  justify-content: space-between;
  padding: 20px 0;
}

.s-home-item{
  text-align: center;
  background: #666666;
  border-radius: 10px;
  padding: 1.5rem;
}

.s-item-title{
  display: block;
  color: white;
  border-bottom: 1px solid white;
  padding-bottom: 5px;
}

.s-item-count{
  display: block;
  padding-top: 5px;
  /*color: cornflowerblue;*/
  color: white;
  font-weight: bolder;
  font-size: 22px;
}

</style>