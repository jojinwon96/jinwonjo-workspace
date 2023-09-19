<template>
  <custom_header/>
  <custom_nav/>
  <section class="content">
    <h3 class="order-title">주문/결제</h3>
    <div class="order-wrap">
      <div class="order-contents">
        <div class="order-content">
          <h4 class="content-title">주문자</h4>
          <div class="order-input-panel">
            <label for="name">이름</label>
            <input type="text" v-model="info.order_name"/>
          </div>
          <div class="order-input-panel">
            <label for="email">이메일</label>
            <input type="text" v-model="email[0]"/>
            <span class="ordrer-input-separator">@</span>
            <select class="input-email-domain sign-up-input" id="domain" v-model="email[1]">
              <option selected value="0">선택해주세요</option>
              <option value="naver.com">naver.com</option>
              <option value="hanmail.net">hanmail.net</option>
              <option value="daum.net">daum.net</option>
              <option value="gmail.com">gmail.com</option>
              <option value="nate.com">nate.com</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="outlook.com">outlook.com</option>
              <option value="icloud.com">icloud.com</option>
            </select>
          </div>
          <div class="order-input-panel">
            <label for="phone">휴대전화</label>
            <input class="order-input-phone" type="text" v-model="phone[0]"/>
            <span class="ordrer-input-separator">-</span>
            <input class="order-input-phone" type="text" v-model="phone[1]"/>
            <span class="ordrer-input-separator">-</span>
            <input class="order-input-phone" type="text" v-model="phone[2]"/>
          </div>
        </div>

        <div class="order-content">
          <div class="delivery-title">
            <h4 class="">배송지</h4>
            <span @click="this.isOpenModal = true">배송지 불러오기</span>
          </div>
          <div class="order-input-panel">
            <label for="">받는사람</label>
            <input type="text" v-model="info.recipient"/>
          </div>
          <div class="order-input-panel">
            <label for="">연락처</label>
            <input class="order-input-phone" type="text" v-model="recipient_phone[0]"/>
            <span class="ordrer-input-separator">-</span>
            <input class="order-input-phone" type="text" v-model="recipient_phone[1]"/>
            <span class="ordrer-input-separator">-</span>
            <input class="order-input-phone" type="text" v-model="recipient_phone[2]"/>
          </div>
          <div class="order-input-panel">
            <label for="" class="address-title">주소</label>
            <div class="custom-input-wrap">
              <div class="custom-input-top">
                <input
                    type="text"
                    class="sign-up-input input-post read-only"
                    name="post"
                    v-model="info.post"
                    readonly
                />
                <button
                    type="button"
                    class="sign-up-input sign-up-btn find-address-btn"
                >
                  주소찾기
                </button>
              </div>
              <div class="custom-input-bottom">
                <input class="read-only" type="text" readonly v-model="info.addr"/>
                <input type="text" v-model="info.addr_detail"/>
              </div>
            </div>
          </div>
        </div>

        <div class="order-content">
          <h4 class="content-title">주문상품</h4>

          <div class="products-wrap" v-for="item in this.orderList" :key="item">
            <div class="order-product-title">
              <span>{{ item.company_name }} </span>
            </div>
            <div class="order-product-content">
              <img v-if="item.uploadFile == 'Y'" :src="require('@/assets/product/uploadfile/' + item.img)" alt=""/>
              <img v-else :src="item.img">
              <div class="op-content">
                <span>{{ item.product_name }}</span>
                <span v-if="item.option_name2 != ''">{{ `${item.option_content1} : ${item.option_content2}` }}</span>
                <span v-else>옵션명 : {{ item.option_content1 }}</span>
                <span>{{ comma(item.price) }}원 / {{ item.count }}개</span>
                <span class="sub-total">{{ comma(item.price * item.count) }}원</span>
              </div>
            </div>
          </div>
        </div>

        <div class="order-content">
          <h4 class="content-title">쿠폰</h4>
          <select class="order-coupon-wrap" v-model="info.coupon">
            <option :value="info.coupon" selected disabled>쿠폰을 선택해 주세요</option>
            <option v-for="item in this.account.couponList" :key="item" :value="item">
              {{ `${item.coupon_name} (${comma(item.coupon_price)}원 할인)` }}
            </option>
          </select>
          <div v-if="info.coupon.coupon_list_id > 0" class="coupon-item">
            <span>{{ `${info.coupon.coupon_name} (${comma(info.coupon.coupon_price)}원 할인)` }}</span>
            <span class="coupon-item-close" @click="info.coupon = {coupon_list_id:0, coupon_price:0}">&#10005;</span>
          </div>
        </div>

        <div class="order-content">
          <h4 class="content-title">마일리지</h4>
          <div class="order-point-wrap">
            <input type="text"
                   @input="mileageControl($event)"
                   v-model="info.mileage"
                   oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');"
            />
            <button @click="info.mileage = this.account.mileage">전액사용</button>
          </div>
          <span class="test"
          >사용 가능 마일리지 <span class="point">{{ comma(this.account.mileage) }} P</span></span
          >
        </div>
      </div>
      <div class="order-pay">
        <div class="order-pay-wrap">
          <span class="order-pay-title">결제금액</span>
          <div class="order-pay-content">
            <div class="opc-title">
              <span>총 상품 금액</span>
              <span>쿠폰 사용</span>
              <span>마일리지 사용</span>
            </div>
            <div class="opc-content">
              <span>{{ comma(info.optionTotal) }}원</span>
              <span>{{ comma(info.coupon.coupon_price) }}원</span>
              <span>{{ comma(info.mileage) }}원</span>
            </div>
          </div>
          <div class="op-result">
            <span>최종 결제 금액</span>
            <input class="op-num" readonly v-model="totalPrice"/>
          </div>
        </div>
        <button type="button" @click="requestPay">결제하기</button>
      </div>
    </div>
  </section>
  <AddressListModal
      :isOpenModal="isOpenModal"
      @closeModal="modalControl"
      @getAddress="getAddress"/>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";
import AddressListModal from "@/components/modal/AddressListModal";
import {mapMutations, mapState} from "vuex";
import axios from "axios";
// import router from "@/router";

export default {
  data() {
    return {
      isOpenModal: false,
      email: [],
      phone: [],
      recipient_phone: [],
      orderList: [],
      info: {
        orderList: [],
        cust_id:'',
        order_name: '',
        email: '',
        phone: '',
        recipient: '',
        recipient_phone: '',
        post: '',
        addr: '',
        addr_detail: '',
        coupon: {
          coupon_list_id: 0,
          coupon_price: 0,
        },
        mileage: 0,
        optionTotal: 0,
        price: 0,
      },
    };
  },

  computed: {
    ...mapState(['account']),

    totalPrice: {
      get: function () {
        return this.comma(this.info.optionTotal - this.info.coupon.coupon_price - this.info.mileage) + '원';
      },
    },

  },

  mounted() {
    this.orderList = JSON.parse(localStorage.getItem('orderList'));

    this.orderList.forEach((item) => {
      this.info.optionTotal += (item.price * item.count);
    })
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

    modalControl() {
      if (this.isOpenModal) {
        this.isOpenModal = false;
      } else {
        this.isOpenModal = true;
      }
    },

    getAddress(address) {
      this.info.recipient = address.name;
      this.info.post = address.post;
      this.info.addr = address.addr;
      this.info.addr_detail = address.addr_detail;
      this.recipient_phone = address.phone.split("-");
    },

    mileageControl(e) {
      if (this.account.mileage < e.target.value) {
        this.info.mileage = this.account.mileage;
      }

      if (e.target.value == '') {
        e.target.value = 0;
      }
    },

    requestPay() {
      this.info.cust_id = this.account.id;
      this.info.email = this.email[0] + '@' + this.email[1];
      this.info.phone = this.phone[0] + '-' + this.phone[1] + '-' + this.phone[2];
      this.info.recipient_phone = this.recipient_phone[0] + '-' + this.recipient_phone[1] + '-' + this.recipient_phone[2];
      this.info.price = this.info.optionTotal - this.info.coupon.coupon_price - this.info.mileage;
      this.info.orderList = this.orderList;

      //결제 성공 시 로직,
      axios.post('/api/account/input-order', this.info).then(({data}) => {
        if (data > 0){
          console.log(data);
        }
      })

      // const IMP = window.IMP; // 생략 가능
      // IMP.init("imp86778202"); // 예: imp00000000
      // IMP.request_pay({ // param
      //   pg: 'html5_inicis',
      //   pay_method: "card",
      //   merchant_uid: 'merchant_' + new Date().getTime(),
      //   name: "상품",
      //   amount: this.info.price,
      //   buyer_email: this.info.email,
      //   buyer_name: this.info.order_name,
      // }, rsp => { // callback
      //   if (rsp.success) {
      //     console.log(rsp.success)
      //     console.log(rsp)
      //     localStorage.removeItem('orderList');
      //   } else {
      //     // 결제 실패 시 로직,
      //     console.log("실패")
      //   }
      // });
    },
  },

  components: {
    custom_header,
    custom_nav,
    AddressListModal,
  },
};
</script>

<style>
.read-only {
  background: #cfcdcd;
}

.order-title {
  margin: 3rem 0;
  font-weight: 500;
  text-align: center;
  text-decoration: underline;
  text-underline-offset: 10px;
}

.order-wrap {
  margin: 0 auto;
  width: 1060px;
  display: flex;
  justify-content: space-between;
}

.order-contents {
  border-radius: 5px;
  width: 60%;
}

.order-content {
  margin-bottom: 5rem;
}

.content-title {
  padding: 1rem 0;
  border-bottom: 1px #ccc solid;
}

.order-input-panel {
  display: flex;
  padding: 0.8rem 0;
}

.order-input-panel label {
  width: 12%;
}

.order-input-panel input {
  width: 35%;
  padding: 8px;
  border: 1px solid #ccc;
  outline: none;
}

.input-email-domain {
  width: 35%;
  padding: 8px;
  border: 1px solid #ccc;
  outline: none;
}

.order-input-phone {
  width: 22.5% !important;
}

.ordrer-input-separator {
  font-size: 18px;
  padding: 0 10px;
}

.delivery-title {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  margin-bottom: 0.5rem;
  border-bottom: 1px #ccc solid;
}

.delivery-title span {
  color: #3385f096;
  font-weight: 600;
  cursor: pointer;
}

.find-address-btn {
  padding: 9px;
  margin: 0 5px;
  border: none;
  background: #3385f096;
  color: white;
}

.address-title {
  width: 13.7% !important;
  height: 125px;
}

.custom-input-wrap {
  width: 100%;
}

.custom-input-top > input {
  width: 25.8%;
}

.custom-input-bottom {
  display: flex;
  flex-direction: column;
}

.custom-input-bottom > input {
  margin-top: 1rem;
  width: 87%;
}

.products-wrap {
  margin-top: 1rem;
  border: 1px solid #ddd;
  font-size: 14px;
}

.order-product-title {
  padding: 10px 14px;
  background: #ddd;
}

.order-product-title span {
  font-weight: 700;
}

.order-product-content {
  padding: 1rem;
  display: flex;
}

.order-product-content img {
  border-radius: 3px;
  width: 6rem;
  height: 5rem;
}

.op-content {
  display: flex;
  flex-direction: column;
  margin: 0 10px;
}

.sub-total {
  display: block;
  margin-top: 3px;
  padding-top: 3px;
  border-top: 1px solid #eaebef;
}

.op-content span:last-child {
  font-weight: 700;
}

.order-coupon-wrap {
  width: 100%;
  margin-top: 0.5rem;
  border: 1px solid #ccc;
  padding: 8px;
  outline: none;
}

.coupon-item {
  display: flex;
  justify-content: space-between;
  margin: 1rem 0;
  padding: 7px 14px;
  border: 1px solid #bbbbbb;
}

.coupon-item-close {
  cursor: pointer;
}

.order-point-wrap * {
  margin-top: 0.5rem;
  padding: 8px;
}

.order-point-wrap input {
  border: 1px solid #ccc;
  width: 40%;
  outline: none;
}

.order-point-wrap button {
  margin: 0 5px;
  padding: 9px;
  border: none;
  color: white;
  font-weight: bolder;
  background: #3385f096;
}

.order-point-wrap + span {
  margin-top: 3px;
  display: block;
}

.point {
  color: #3385f096;
  font-weight: 700;
}

.order-pay {
  position: fixed;
  width: 20%;
  right: 22%;
  height: 250px;
  border: 1px solid #ccc;
}

.order-pay-wrap {
  padding: 1.5rem 1.5rem 0.5rem 1.5rem;
}

.order-pay-title {
  display: block;
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 1rem;
}

.order-pay-content {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.order-pay-content * {
  margin-bottom: 0.3rem;
  display: flex;
  flex-direction: column;
}

.opc-content {
  text-align: right;
}

.op-num {
  border: none;
  outline: none;
  text-align: right;
}

.op-result {
  display: flex;
  justify-content: space-between;
  padding: 1.5rem 0;
  border-top: 1px solid #eee;
  font-size: 18px;
  font-weight: 700;
}

.order-pay button {
  border: none;
  background: #3385f096;
  color: white;
  font-weight: 700;
  font-size: 20px;
  width: 100%;
  padding: 15px;
}
</style>
