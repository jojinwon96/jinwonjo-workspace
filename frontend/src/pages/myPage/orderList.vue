<template>
  <h4 class="myPage-content-title">주문내역</h4>
  <div class="myPage-content">
    <div class="myPage-head">
      <select class="select-date" @change="onChangeDate($event.target.value)">
        <option value="all">전체</option>
        <option value="one-week">1주</option>
        <option value="two-week">2주</option>
        <option value="three-week">3주</option>
        <option value="one-month">1개월</option>
        <option value="two-month">2개월</option>
        <option value="three-month">3개월</option>
        <option value="direct-search">직접검색</option>
      </select>
      <div v-show="isSearch == true">
        <input class="start-date"
               type="date"
               v-model="startDate">
        <span>~</span>
        <input class="end-date"
               type="date"
               v-model="endDate">
        <button class="search-date" type="button" @click="searchDate">검색</button>
      </div>
    </div>
    <div class="orderList-content-wrap" v-for="date in this.groupDate" :key="date">
      <div class="orderList-content-head">
        <span>{{ date }}</span>
        <span class="receipt" @click="modalControl(date)">주문정보&#10095;</span>
      </div>
      <div class="orderList-content-body" v-for="order in this.orderList" :key="order">
        <div class="order-item-wrap" v-if="date == order.order_date">
          <ul class="order-item">
            <li>
              <img v-if="order.uploadFile != 'Y'" :src="order.img">
              <img v-else :src="require('@/assets/product/uploadfile/' + order.img)">
            </li>
            <li>
              <span>{{ order.product_name }}</span>
              <span v-if="order.option_name2 != ''">{{ `${order.option_content1} : ${order.option_content2}` }}</span>
              <span v-else>옵션명 : {{ order.option_content1 }}</span>
              <span>{{ comma(order.option_price) }}원 / {{ order.count }}개</span>
              <span class="sub-total">{{ comma(order.option_price * order.count) }}원</span>
            </li>
          </ul>
          <div class="order-button-wrap">
            <div v-if="order.status == 'Y'">
              <button v-if="order.refundStatus == null" type="button" @click="refundModalControl(order)">주문취소</button>
              <button v-if="order.refundStatus == null" type="button" @click="onReview(order)">후기작성</button>
              <div v-else-if="order.refundStatus == 'N'">
                <button>환불처리중</button>
              </div>
            </div>
            <div v-else>
              <span>주문준비중</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <orderDetailModal
      :isOpenModal="isOpenModal"
      :order="order"
      @closeModal="modalControl"/>

  <RefundModal
      :isOpenRefund="isOpenRefund"
      :refundOrder="refundOrder"
      @closeRefundModal="refundModalControl"/>

</template>

<script>
import axios from "axios";
import orderDetailModal from "@/components/modal/orderDetailModal";
import RefundModal from "@/components/modal/RefundModal";
import router from "@/router";

export default {
  name: "orderList.vue",

  data() {
    return {
      order:[],
      isOpenModal: false,
      refundOrder:{},
      isOpenRefund:false,
      orderList: [],
      groupDate: [],
      isSearch: false,
      startDate: '',
      endDate: '',
    }
  },

  components: {
    orderDetailModal,
    RefundModal,
  },

  mounted() {
    let tmp = [];

    axios.get("/api/account/order").then(({data}) => {
      console.log(data);

      this.orderList = data;

      this.orderList.forEach((item) => {
        tmp.push(item.order_date);
      })

      this.groupDate = tmp.filter((element, index) => {
        return tmp.indexOf(element) === index;
      });
    })
  },

  methods: {
    comma(val) {
      if (val == 0) {
        return 0;
      } else {
        return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },

    onChangeDate(e) {
      if (e == 'direct-search') {
        this.isSearch = true;
      } else {
        this.isSearch = false;
        this.startDate = '';
        this.endDate = '';
      }
    },

    searchDate() {
      if (this.startDate > this.endDate) {
        this.startDate = this.endDate;
      }
    },

    modalControl(date) {
      this.order = [];

      this.orderList.forEach((item)=>{
        if (date == item.order_date){
          this.order.push(item);
        }
      })

      if (this.isOpenModal) {
        this.isOpenModal = false;
      } else {
        this.isOpenModal = true;
      }
    },

    refundModalControl(order){
      if (this.isOpenRefund) {
        this.isOpenRefund = false;
        this.refundOrder = {};
      } else {
        this.isOpenRefund = true;
        this.refundOrder = order;
      }
    },

    onReview(order){
      router.push({
        name: "view",
        params: { product_id: `${order.product_id}`},
      })
    },

  },
}
</script>

<style scoped>
.myPage-head {
  padding-bottom: 10px;
  margin: 1rem 0;
  display: flex;
}

.select-date {
  border: 1px solid #cfcdcd;
  font-size: 14px;
  outline: none;
  padding: 5px 0;
}

.start-date, .end-date {
  margin: 0 5px;
}

input[type="date"] {
  appearance: none;
  -webkit-appearance: none;
  color: #95a5a6;
  font-family: "Helvetica", arial, sans-serif;
  font-size: 18px;
  border: 1px solid #ecf0f1;
  background: #ecf0f1;
  padding: 5px;
  visibility: visible !important;
}


input[type="date"], focus {
  color: #95a5a6;
  box-shadow: none;
  -webkit-box-shadow: none;
  -moz-box-shadow: none;
}

.search-date {
  border: #bbbbbb;
  padding: 8px 12px;
  color: white;
  font-weight: bolder;
  background: #3385f096;
}

.orderList-content-wrap {
  margin: 14px 0;
  padding: 12px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.16), 0 1px 1px rgba(0, 0, 0, 0.23);
}

.orderList-content-wrap:hover {
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

.orderList-content-head {
  display: flex;
  justify-content: space-between;
  font-weight: bolder;
  border-bottom: 1px solid #cfcdcd;
  padding-bottom: 5px;
}

.order-item-wrap {
  padding: 5px 0;
  display: flex;
  justify-content: space-between;
}

.order-item {
  display: flex;
}

.order-item li {
  display: flex;
  flex-direction: column;
  font-size: 13px;
  margin: 0 5px;
}

.order-item img {
  width: 100px;
  height: 100px;
}

.order-button-wrap{
  margin: 0 5px;
}

.order-button-wrap button{
  font-size: 12px;
  border: 1px solid #eaebef;
  margin-left: 5px;
  padding: 5px 10px;
  color: white;
  font-weight: bolder;
  background: #3385f096;
}
.order-button-wrap button:hover{
  border: 1px solid #bbbbbb;
}

.order-button-wrap span{
  font-size: 12px;
  border: 1px solid #eaebef;
  margin-left: 5px;
  padding: 5px 10px;
  color: white;
  font-weight: bolder;
  background: #bbbbbb;
}


.receipt {
  color: #3385f096;
  cursor: pointer;
}

.sub-total {
  margin-top: 3px;
  padding-top: 3px;
  border-top: 1px solid #eaebef;
  font-size: 14px;
  font-weight: bolder;
}
</style>