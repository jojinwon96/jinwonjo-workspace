<template>
  <custom_header />
  <custom_nav />
  <section class="content">
    <h3 class="cart-title">장바구니</h3>
    <div class="cart-wrap">
      <div class="cart-content">
        <div class="allCheck-wrap">
          <input
            type="checkbox"
            class="allCheck"
            id="allCheck"
            v-model="isAllCheck"
            @input="allCheck"
          />
          <label for="allCheck">모두선택</label>
          <span @click="[deleteSelection(), totalPrice()]">선택삭제</span>
        </div>
        <ul class="cart-main" v-for="(item, idx) in cartList" :key="idx">
          <li>
            <input
              type="checkbox"
              class="check"
              id="check"
              v-model="item.selected"
              @change="[totalPrice(), oneCheck()]"
            />
            <label for="check">{{ item.pName }}</label>
            <span @click="[deleteProduct(idx), totalPrice()]">&#10005;</span>
            <div class="cart-img-wrap">
              <img src="../assets/img/bouquet1.jpg" alt="" />
              <ul>
                <li>
                  <span>상품금액 </span
                  >{{ comma((item.price + item.optPrice) * item.count) }}원
                </li>
                <li>
                  <span>할인금액 </span
                  >{{
                    comma(
                      sale(item.price + item.optPrice, item.pDiscount) *
                        item.count
                    )
                  }}원
                </li>
                <li>
                  <span>할인적용금액</span
                  >{{
                    comma(
                      discount(item.price + item.optPrice, item.pDiscount) *
                        item.count
                    )
                  }}원
                </li>
              </ul>
            </div>
          </li>
          <li>
            <div class="count-wrap">
              <span>옵션 : {{ item.optName }}</span>
              <div class="count-content">
                <div class="count-top">
                  <button @click="[countDecrease(item), totalPrice()]">
                    -
                  </button>
                  <input
                    type="text"
                    oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
                    :value="`${item.count}`"
                    @input="
                      [
                        countControl((item.count = $event.target.value), item),
                        totalPrice(),
                      ]
                    "
                  />
                  <button @click="[countIncrease(item), totalPrice()]">
                    +
                  </button>
                </div>
                <div class="count-bottom">
                  <label class="pay-num">{{
                    comma(
                      discount(
                        (item.price + item.optPrice) * item.count,
                        item.pDiscount
                      )
                    )
                  }}</label>
                  <label class="pay-won">원</label>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <div class="cart-pay">
        <ul>
          <li>
            <span>총 상품금액</span>
            <span>{{ comma(originTotal) }}원</span>
          </li>
          <li>
            <span>총 할인금액</span> <span>{{ comma(saleTotal) }}원</span>
          </li>
          <li class="total-price">
            <span>총 상품금액</span><span>{{ comma(total) }}원</span>
          </li>
        </ul>
        <button class="buy-button">구매하기</button>
      </div>
    </div>

    <br /><br /><br /><br /><br />
  </section>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";
export default {
  name: "cart",
  data() {
    return {
      total: 0,
      originTotal: 0,
      saleTotal: 0,
      checkedList: [],
      isAllCheck: true,
      cartList: [
        {
          pId: "pId1",
          pName: "지금 이 순간",
          price: 70000,
          pDiscount: 5,
          optId: "opt1",
          optName: "기본형",
          optPrice: 0,
          count: 2,
          selected: true,
        },
        {
          pId: "pId2",
          pName: "지금 이 순간",
          price: 70000,
          pDiscount: 5,
          optId: "opt2",
          optName: "고급형",
          optPrice: 20000,
          count: 2,
          selected: true,
        },
        {
          pId: "pId3",
          pName: "지금 이 순간",
          price: 70000,
          pDiscount: 5,
          optId: "opt3",
          optName: "특별형",
          optPrice: 40000,
          count: 1,
          selected: true,
        },
      ],
    };
  },

  mounted() {
    this.totalPrice();
  },

  methods: {
    comma(val) {
      if (val == 0) {
        return 0;
      } else {
        return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },

    sale(value, percent) {
      return value * (0.01 * percent);
    },

    discount(value, percent) {
      return value - value * (0.01 * percent);
    },

    deleteProduct(index) {
      this.cartList.splice(index, 1);
      if (this.cartList.length == 1) {
        this.cartList[0].selected = true;
        this.isAllCheck = true;
      }
    },

    deleteSelection() {
      let search = this.cartList.filter((item) => {
        return item.selected != true;
      });

      this.cartList = [...search];

      if (this.cartList.length == 1) {
        this.cartList[0].selected = true;
        this.isAllCheck = true;
      }
    },

    countDecrease(value) {
      if (value.count > 1) {
        value.count--;
      }
    },

    countControl(value, item) {
      if (value <= 0) {
        item.count = 1;
      }
    },

    countIncrease(value) {
      value.count++;
    },

    totalPrice() {
      this.originTotal = 0;
      this.saleTotal = 0;
      this.total = 0;

      this.cartList.forEach((item) => {
        if (item.selected) {
          this.total +=
            this.discount(item.price + item.optPrice, item.pDiscount) *
            item.count;

          this.originTotal += (item.price + item.optPrice) * item.count;

          this.saleTotal +=
            this.sale(item.price + item.optPrice, item.pDiscount) * item.count;
        }
      });
    },

    oneCheck() {
      let result = 0;
      this.cartList.forEach((value) => {
        if (value.selected) {
          result++;
        }
      });

      if (result == this.cartList.length) {
        this.isAllCheck = false;
        this.allCheck();
      } else if (result == 0) {
        this.isAllCheck = true;
        this.allCheck();
      }
    },

    allCheck() {
      if (this.isAllCheck) {
        this.isAllCheck = false;
      } else {
        this.isAllCheck = true;
      }

      this.cartList.forEach((item) => {
        item.selected = this.isAllCheck;
      });

      this.totalPrice();
    },
  },
  components: {
    custom_header,
    custom_nav,
  },
};
</script>

<style>
.cart-title {
  margin: 3rem 0;
  font-weight: 500;
  text-align: center;
  text-decoration: underline;
  text-underline-offset: 10px;
}

.cart-wrap {
  margin-left: 3rem;
  display: flex;
  justify-content: space-between;
}

.cart-content {
  border-radius: 5px;
  background: #f5f5f5;
  padding: 1rem;
  width: 65%;
}

input[type="checkbox"] {
  float: left;
  width: 1.5rem;
  height: 1.5rem;
}

label {
  margin-left: 5px;
}

.allCheck-wrap > * {
  cursor: pointer;
  font-weight: 600;
}

.allCheck-wrap span {
  float: right;
}

.cart-main {
  margin-top: 2.5rem;
  padding-top: 1rem;
  border-top: 1px solid #ccc;
}

.cart-main label {
  font-weight: bolder;
}

.cart-main span {
  float: right;
  cursor: pointer;
  font-weight: bolder;
  font-size: 18px;
}

.cart-img-wrap {
  display: flex;
  padding-top: 1rem;
}

.cart-img-wrap li {
  display: flex;
  justify-content: space-between;
  padding: 2px;
}

.cart-img-wrap span {
  background: white;
}

.cart-img-wrap img {
  border-radius: 3px;
  width: 6rem;
  height: 5rem;
}

.cart-img-wrap span {
  border: 1px solid #bbb;
  border-radius: 5px;
  padding: 0 5px;
  margin: 0 10px;
  font-size: 14px;
  font-weight: 600;
}

.count-wrap {
  display: flex;
  flex-direction: column;
  margin-top: 1rem;
  border-radius: 5px;
  background: #fff;
  padding: 0.5rem;
}

.count-content {
  margin-top: 0.5rem;
  display: flex;
  justify-content: space-between;
}

.count-wrap span {
  font-weight: normal;
  font-size: 16px;
}

.count-top {
  border: 1px solid #bbb;
}

.count-wrap input,
.count-wrap button {
  border: none;
  background: white;
  text-align: center;
  font-size: 16px;
  height: 30px;
  outline: none;
}

.count-wrap button {
  font-size: 24px;
  font-weight: bolder;
  width: 30px;
  padding-bottom: 0.5rem;
}

.count-wrap input {
  margin-top: 1px;
  width: 40px;
  border-left: none;
  border-right: none;
}

.count-bottom {
  display: flex;
}

.pay-num {
  font-size: 24px !important;
  font-weight: 600 !important;
}

.pay-won {
  font-size: 17px;
  margin-left: 3px;
  padding-top: 0.5rem;
}

.cart-pay {
  position: sticky;
  bottom: 65%;
  width: 31%;
}

.cart-pay ul {
  border-radius: 5px;
  background: #f5f5f5;
  padding: 1rem;
}

.cart-pay li {
  display: flex;
  justify-content: space-between;
}

.total-price {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #ccc;
  font-weight: bolder;
  font-size: 20px;
}

.buy-button {
  border: none;
  background: #3385f096;
  color: #fff;
  font-weight: 600;
  width: 100%;
  padding: 1rem;
  border-radius: 2px;
}
</style>
