<template>
  <custom_header />
  <custom_nav />
  <section class="content">
    <h3 class="order-title">주문/결제</h3>
    <div class="order-wrap">
      <div class="order-contents">
        <div class="order-content">
          <h4 class="content-title">주문자</h4>
          <div class="order-input-panel">
            <label for="name">이름</label>
            <input type="text" />
          </div>
          <div class="order-input-panel">
            <label for="email">이메일</label>
            <input type="text" />
            <span class="ordrer-input-separator">@</span>
            <select class="input-email-domain sign-up-input" id="domain">
              <option selected value="0">선택해주세요</option>
              <option value="naver.com">naver.com</option>
              <option value="hanmail.net">hanmail.net</option>
              <option value="daum.net">daum.net</option>
              <option value="gmail.com">gmail.com</option>
              <option value="nate.com">nate.com</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="outlook.com">outlook.com</option>
              <option value="icloud.com">icloud.com</option>
              <option value="1">직접입력</option>
            </select>
          </div>
          <div class="order-input-panel">
            <label for="phone">휴대전화</label>
            <input class="order-input-phone" type="text" />
            <span class="ordrer-input-separator">-</span>
            <input class="input-phone" type="text" />
            <span class="ordrer-input-separator">-</span>
            <input class="input-phone" type="text" />
          </div>
        </div>

        <div class="order-content">
          <div class="delivery-title">
            <h4 class="">배송지</h4>
            <span>배송지 불러오기</span>
          </div>
          <div class="order-input-panel">
            <label for="">받는사람</label>
            <input type="text" />
          </div>
          <div class="order-input-panel">
            <label for="">연락처</label>
            <input class="input-phone" type="text" />
            <span class="ordrer-input-separator">-</span>
            <input class="input-phone" type="text" />
            <span class="ordrer-input-separator">-</span>
            <input class="input-phone" type="text" />
          </div>
          <div class="order-input-panel">
            <label for="" class="address-title">주소</label>
            <div class="custom-input-wrap">
              <div class="custom-input-top">
                <input
                  type="text"
                  class="sign-up-input input-post"
                  name="post"
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
                <input type="text" />
                <input type="text" />
              </div>
            </div>
          </div>
        </div>

        <div class="order-content">
          <h4 class="content-title">주문상품</h4>
          <div class="products-wrap">
            <div class="order-product-title">
              <span>(주)엔케이</span>
            </div>
            <div class="order-product-content">
              <img src="../assets/img/bouquet1.jpg" alt="" />
              <div class="op-content">
                <span>지금 이 순간</span>
                <span>옵션 : 기본형</span>
                <span>66,500원 / 1개</span>
              </div>
            </div>
          </div>
        </div>

        <div class="order-content">
          <h4 class="content-title">쿠폰</h4>
          <select name="" id="" class="order-coupon-wrap">
            <option value="0" selected>쿠폰을 선택해 주세요</option>
            <option value="">신규 회원 쿠폰</option>
          </select>
        </div>

        <div class="order-content">
          <h4 class="content-title">마일리지</h4>
          <div class="order-point-wrap">
            <input type="text" />
            <button>전액사용</button>
          </div>
          <span class="test"
            >사용 가능 마일리지 <span class="point">1,000 P</span></span
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
              <span>70,000원</span>
              <span>0원</span>
              <span>0원</span>
            </div>
          </div>
          <div class="op-result">
            <span>최종 결제 금액</span>
            <span class="op-num">70,000원</span>
          </div>
        </div>
        <button type="button">결제하기</button>
      </div>
    </div>
  </section>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";

export default {
  data() {
    return {};
  },

  methods: {},

  components: {
    custom_header,
    custom_nav,
  },
};
</script>

<style>
.order-title {
  margin: 3rem 0;
  font-weight: 500;
  text-align: center;
  text-decoration: underline;
  text-underline-offset: 10px;
}

.order-wrap {
  margin-left: 3rem;
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
}

.order-product-title {
  padding: 1rem;
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
  position: sticky;
  bottom: 60%;
  min-width: 38%;
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
