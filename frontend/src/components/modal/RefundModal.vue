<template>
  <Transition>
    <div
        class="refund-modal-wrap"
        v-show="this.isOpenRefund == true">
      <div class="refund-modal-content">
        <div class="refund-modal-close" @click="closeRefundModal">&#10005;</div>
        <div class="refund-modal-header">
          <span>주문취소신청</span>
        </div>
        <div class="refund-modal-body">
          <div class="refund-modal-contents">
            <span class="refund-content-title">상품정보</span>

            <div class="refund-content-head">
              <div>
                <img v-if="refundOrder.uploadFile != 'Y'" :src="refundOrder.img">
                <img v-else :src="require('@/assets/product/uploadfile/' + refundOrder.img)">
              </div>
              <div class="refund-content-right">
                <span>{{ refundOrder.product_name }}</span>
                <span
                    v-if="refundOrder.option_name2 == ''">{{
                    refundOrder.option_name1
                  }} : {{ refundOrder.option_content1 }}</span>
                <span v-else>{{ refundOrder.option_content1 }} : {{ refundOrder.option_content2 }}</span>
                <span>{{ comma(refundOrder.option_price) }}원 / {{ refundOrder.count }}개</span>
                <span class="sub-total">환불 예정금액 : {{ comma(refundOrder.option_price * refundOrder.count) }}원</span>
              </div>
            </div>

            <span class="refund-content-title">취소사유</span>
            <select v-model="this.info.reason">
              <option value="" selected disabled>선택</option>
              <option value="단순변심">단순변심</option>
              <option value="다른 상품 주문">다른 상품 주문</option>
            </select>
            <div class="refund-content-body">
              <div class="refund-text-wrap">
                <textarea class="refund-text-area" v-model="info.content" :maxlength="size"/>
                <p class="content-count">{{ this.info.content.length }} / {{ size }}</p>
              </div>
            </div>

            <div class="refund-content-footer">
              <button class="refund-button" @click="accessRefund">신청하기</button>
            </div>

          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
import {mapMutations} from "vuex";
import axios from "axios";

export default {
  name: "RefundModal",

  data() {
    return {
      size: 300,
      info:{
        order_detail_id:'',
        reason:'',
        content: '',
      },
    }
  },


  watch: {
    isOpenRefund(value) {
      console.log(this.refundOrder)
      if (value) {
        document.body.style = `overflow: hidden`;
      } else {
        document.body.style = `overflow: auto`;
        this.info.order_detail_id ='';
        this.info.reason = '';
        this.info.content = '';
      }
    },

  },


  methods: {
    ...mapMutations(['setFixed']),

    comma(val) {
      if (val == 0) {
        return 0;
      } else {
        return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },

    closeRefundModal() {
      this.$emit("closeRefundModal");
    },

    accessRefund(){
      if (this.info.reason == ''){
        alert('환불 사유를 선택해 주세요.');
        return;
      }

      if (this.info.content == ''){
        alert('환불 사유를 입력해주세요.');
        return;
      }

      this.info.order_detail_id = this.refundOrder.order_detail_id;

      axios.post("/api/account/input-refund", this.info).then(({data})=>{
        console.log(data);
      })

    },
  },

  props: {
    isOpenRefund: Boolean,
    refundOrder: Object,
  },
}
</script>

<style scoped>
.refund-modal-wrap {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
}

.refund-modal-content {
  position: absolute;
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
  top: 10%;
  left: 30%;
  width: 720px;
  padding: 1rem 1.5rem;
}

.refund-modal-close {
  text-align: right;
  font-size: 24px;
  font-weight: 500;
  cursor: pointer;
}

.refund-modal-header {
  text-align: center;
  font-size: 20px;
  font-weight: 700;
  padding-bottom: 5px;
}

.refund-modal-body {
  margin: 2rem 0;
  width: 100%;
}

.refund-modal-contents {
  display: flex;
  flex-direction: column;
  font-size: 14px;
}

.refund-content-title {
  font-size: 16px;
  font-weight: bolder;
  border-bottom: 1px solid #aaa9a9;
  margin-bottom: 5px;
}

.refund-content-head {
  display: flex;
  margin-bottom: 3rem;
}

.refund-modal-contents img {
  width: 100px;
  height: 100px;
}

.refund-modal-contents select {
  outline: none;
  border: 1px solid #aaa9a9;
  padding: 5px;
  margin: 5px 0;
}

.refund-content-right {
  display: flex;
  flex-direction: column;
  padding: 0 5px;
}

.sub-total {
  font-size: 15px;
  font-weight: bolder;
}

.refund-text-wrap {
  border: 1px solid #aaa9a9;
}

.refund-text-area {
  width: 100%;
  height: 100px;
  padding: 10px;
  resize: none;
  outline: none;
  border: none;
}

.content-count {
  text-align: right;
  margin: 5px;
}

.refund-content-footer{
  margin: 5px 0;
}

.refund-button{
  display: block;
  margin: 5px auto;
  width: 25%;
  background: #3385f096;
  color: white;
  font-weight: bolder;
  padding: 15px 5px;
  border: none;
  border-radius: 3px;
}


</style>