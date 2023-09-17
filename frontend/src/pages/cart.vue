<template>
  <custom_header/>
  <custom_nav/>
  <section class="content">
    <h3 class="cart-title">장바구니</h3>
    <div class="cart-wrap" v-if="cartList.length > 0">
      <div class="cart-content">
        <div class="allCheck-wrap">
          <input
              type="checkbox"
              id="allCheck"
              checked
              v-model="allSelected"
          />
          <label for="allCheck">모두선택</label>
          <span @click="deleteSelection">선택삭제</span>
        </div>
        <ul class="cart-main" v-for="(item,index) in cartList" :key="item">
          <li>
            <input
                type="checkbox"
                :value="item"
                v-model="selectList"
            />
            <label for="check">{{ item.product_name }}</label>
            <span @click="deleteProduct(index)">&#10005;</span>
            <div class="cart-img-wrap">
              <img v-if="item.uploadFile != 'Y'" :src="item.img" alt=""/>
              <img v-else :src="require(`@/assets/product/uploadfile/${item.img}`)" alt=""/>
              <ul>
                <li>
                  <span>상품금액 </span
                  >{{ comma(item.option_price * item.count) }}원
                </li>
                <li>
                  <span>할인금액 </span
                  >{{
                    comma(
                        (item.option_price - item.price) *
                        item.count
                    )
                  }}원
                </li>
                <li>
                  <span>할인적용금액</span
                  >{{
                    comma(
                        item.price * item.count
                    )
                  }}원
                </li>
              </ul>
            </div>
          </li>
          <li>
            <div class="count-wrap">
              <span>{{ item.option_content1 }} : {{ item.option_content2 }}</span>
              <div class="count-content">
                <div class="count-top">
                  <button @click="[countDecrease(item), totalPrice()]">
                    -
                  </button>
                  <input
                      type="text"
                      oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');"
                      :value="`${item.count}`"
                      @input="
                      [
                        countControl(item.count = $event.target.value, item),
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
                          item.price * item.count
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
        <button class="buy-button" @click="buyCartList">구매하기</button>
      </div>
    </div>
    <div class="empty-cart" v-else>
      <span>장바구니에 담긴 상품이 없습니다...</span>
    </div>

    <br/><br/><br/><br/><br/>
  </section>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";
import axios from "axios";

export default {
  name: "cart",
  data() {
    return {
      total: 0,
      originTotal: 0,
      saleTotal: 0,
      cartList: [],
      selectList: [],
    };
  },

  mounted() {
    this.getCartList();
  },

  computed: {

    allSelected: {
      //getter
      get: function () {
        this.totalPrice();
        return this.cartList.length == this.selectList.length;
      },
      //setter
      set: function (e) {
        this.selectList = e ? this.cartList : [];
      },
    },

  },

  methods: {
    getCartList() {
      axios.get("/api/account/cart").then(({data}) => {
        this.cartList = data;
        this.selectList = data;

        console.log(this.cartList);
      })
    },

    buyCartList() {
      console.log(this.selectList);
    },

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

    modifyCart(value, target, count){
      let info = {
        cart_id: value.cart_id,
        count : count,
        target : target
      }
      axios.post("/api/modify-cart", info).then(({data})=>{
        if (data > 0){
          value.count = data;
        } else {
          console.log('수정실패');
        }
      })

    },

    postDeleteCart(value){
      axios.post('/api/delete-cart', value,
          { headers: { 'Content-Type': 'application/json' } }).then(({data})=>{
            if (data != null){
              this.cartList = data;
              this.selectList = data;
            } else {
              console.log('삭제 실패');
            }
      })
    },

    deleteProduct(index) {
      let deleteList = [];
      deleteList.push(this.selectList[index]);
      this.postDeleteCart(JSON.stringify(deleteList));
    },

    deleteSelection() {
      let value = JSON.stringify(this.selectList);
      this.postDeleteCart(value);
    },

    countDecrease(value) {
      if (value.count > 1) {
        this.modifyCart(value, 'decrease');
      }
    },

    countControl(value, item) {
      if (value <= 0) {
        this.modifyCart(item, 'one');
      } else {
        this.modifyCart(item, 'custom', value);
      }

    },

    countIncrease(value) {
      this.modifyCart(value, 'increase');
    },

    totalPrice() {
      this.originTotal = 0;
      this.saleTotal = 0;
      this.total = 0;

      this.selectList.forEach((item) => {
        this.total += item.count * item.price;

        this.originTotal += item.option_price * item.count;

        this.saleTotal += (item.option_price - item.price) * item.count;
      });
    },

  },
  components: {
    custom_header,
    custom_nav,
  },
};
</script>

<style>
.empty-cart{
  margin: 0 auto;
  width: 1060px;
  border: 1px solid #aaa9a9;
}

.empty-cart span{
  display: block;
  padding: 10rem;
  text-align: center;
}

.cart-title {
  margin: 3rem 0;
  font-weight: 500;
  text-align: center;
  text-decoration: underline;
  text-underline-offset: 10px;
}

.cart-wrap {
  margin: 0 auto;
  width: 1060px;
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
  font-size: 14px;
}

.count-top {
  border: 1px solid #bbb;
}

.count-wrap input,
.count-wrap button {
  border: none;
  background: white;
  text-align: center;
  height: 20px;
  outline: none;
}

.count-wrap button {
  font-size: 24px;
  font-weight: bolder;
  width: 20px;
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
