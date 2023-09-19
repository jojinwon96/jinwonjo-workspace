<template>
  <seller-header/>
  <div class="s-om-wrap">
    <h1>주문관리페이지</h1>
  </div>
</template>

<script>
import SellerHeader from "@/components/seller/sellerHeader";
import {mapMutations, mapState} from "vuex";
import axios from "axios";

export default {
  name: "orderManage.vue",

  mounted() {
    axios.get("/api/seller/order").then(({data})=>{
      console.log(data);
    })
  },

  computed: {
    ...mapState(['seller', 'account']),
  },

  methods: {
    ...mapMutations(['setSeller']),
  },

  components: {
    SellerHeader,
  }
}
</script>

<style scoped>
.s-om-wrap {
  margin: 8rem auto;
  width: 1000px;
  padding: 1.2rem;
}
</style>