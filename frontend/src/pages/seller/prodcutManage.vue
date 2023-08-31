<template>
  <seller-header/>
  <div class="s-pm-wrap">
    {{}}
    <div class="s-pm-items">
      <span @click="setSeller(0)" class="pm-item-btn" :class="{ itemActive : seller.currentPage == 0}">상품조회</span>
      <span class="partition"></span>
      <span @click="setSeller(1)" class="pm-item-btn" :class="{ itemActive : seller.currentPage == 1}">상품등록</span>
      <span class="partition"></span>
      <span @click="setSeller(2)" class="pm-item-btn" :class="{ itemActive : seller.currentPage == 2}">상품수정</span>

    </div>

    <inquiryProduct :id="account.seller_id" v-if="seller.currentPage === 0"/>
    <registrationProduct v-if="seller.currentPage === 1"/>
    <modifyProduct v-if="seller.currentPage === 2"/>

  </div>
</template>

<script>
import SellerHeader from "@/components/seller/sellerHeader";
import InquiryProduct from "@/components/seller/inquiryProduct";
import RegistrationProduct from "@/components/seller/registrationProduct";
import ModifyProduct from "@/components/seller/modifyProduct";
import {mapMutations, mapState} from "vuex";

export default {
  name: "prodcutManage",

  computed:{
    ...mapState(['seller', 'account']),
  },

  methods:{
    ...mapMutations(['setSeller']),
  },

  components: {ModifyProduct, RegistrationProduct, InquiryProduct, SellerHeader}
}
</script>

<style scoped>
.s-pm-wrap {
  margin: 8rem auto;
  width: 1000px;
  padding: 1.2rem;
}

.s-pm-items {
  display: flex;
  justify-content: center;
  border-bottom: 1px solid #bbbbbb;
  padding-bottom: 5rem;
}

.pm-item-btn {
  border-radius: 5px;
  padding: 1rem;
  font-size: 18px;
  color: white;
  background: #999999;
  cursor: pointer;
}

.itemActive {
  background: #575757
}

.pm-item-btn:hover {
  background: #575757
}

.partition {
  width: 1px;
  margin: 0 1rem;
  background: #cfcdcd;
  height: 3.5rem;
}
</style>