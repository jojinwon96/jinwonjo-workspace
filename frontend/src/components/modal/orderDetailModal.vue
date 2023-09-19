<template>
  <Transition>
    <div
        class="order-modal-wrap"
        v-show="this.isOpenModal == true">
      <div class="order-modal-content" v-if="order.length > 0">
        <div class="order-modal-close" @click="closeModal">&#10005;</div>
        <div class="order-modal-header">
          <span>주문정보</span>
        </div>
        <div class="order-modal-body">
          <div class="order-modal-contents">
            <div class="address-item">
              <div class="omc-head">
                <span>배송정보</span>
              </div>
              <div class="omc-item-address">
                <div class="omc-item-address-form">
                  <span>받는사람 : {{ order[0].recipient }}</span>
                </div>
                <div class="omc-item-address-form">
                  <span>{{ `(${order[0].post}) ${order[0].addr} ${order[0].addr_detail}` }}</span>
                </div>
              </div>
            </div>

            <div class="omc-head">
              <span>결제정보</span>
            </div>
            <div class="omc-body">
              <div class="omc-item" v-for="item in order" :key="item">
                <span>{{ item.product_name }}</span>
                <div class="omc-item-price">
                  <span>&#10005;{{ item.count }}</span>
                  <span>{{ comma(item.count * item.option_price) }}원</span>
                </div>
              </div>
              <div class="order-total-wrap">
                <span>총 결제 금액</span>
                <span>{{ comma(order[0].price) }}원</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: "OrderDetailModal",

  watch: {
    isOpenModal(value) {
      console.log(this.order);
      if (value) {
        document.body.style = `overflow: hidden`;
      } else {
        document.body.style = `overflow: auto`;
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

    closeModal() {
      this.$emit("closeModal");
    },
  },

  props: {
    isOpenModal: Boolean,
    order: Array,
  },
}
</script>

<style scoped>

.order-modal-wrap {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
}

.order-modal-content {
  position: absolute;
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
  top: 10%;
  left: 30%;
  width: 720px;
  padding: 1rem 1.5rem;
}

.order-modal-close {
  text-align: right;
  font-size: 24px;
  font-weight: 500;
  cursor: pointer;
}

.order-modal-header {
  text-align: center;
  font-size: 20px;
  font-weight: 700;
  border-bottom: 1px solid #cfcdcd;
  padding-bottom: 5px;
}

.order-modal-body {
  margin: 2rem 0;
  width: 100%;
}

.order-modal-contents {
  width: 100%;
}

.omc-head {
  font-size: 16px;
  font-weight: bolder;
}

.omc-body {
  font-size: 14px;
}

.omc-item {
  width: 100%;
  margin: 5px 0;
  display: flex;
  justify-content: space-between;
}

.omc-item-price span {
  margin: 0 5px;
}

.order-total-wrap {
  font-size: 15px;
  font-weight: bolder;
  border-top: 1px dashed #bbbbbb;
  padding: 5px 0;
  margin-right: 3px;
  display: flex;
  justify-content: space-between;
}

.address-item {
  margin: 2rem 0;
}

.omc-item-address {
  width: 100%;
  margin: 5px 0;
}

.omc-item-address-form {
  display: flex;
}

.order-modal-footer {
  border-top: 1px solid #cfcdcd;
  padding: 1rem 0;
}
</style>