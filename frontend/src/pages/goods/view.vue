<template>
  <custom_header/>
  <custom_nav/>
  <section class="content" v-if="this.goods.length > 0">
    <div class="goods-wrap">
      <div class="search-nav">
        <span><a href="/" class="home">Home</a></span>
        <span>&#10095;</span>
        <span>{{ this.goods[0].category_name }}</span>
      </div>
      <h3 class="goods-title">{{ this.goods[0].product_name }}</h3>
      <div class="goods-thumbs">
        <img v-if="this.goods[0].uploadFile != 'Y'" :src="this.goods[0].img1" alt=""/>
        <img v-else :src="require(`@/assets/product/uploadfile/${this.goods[0].img1}`)" alt=""/>
      </div>
      <div class="goods-spec">
        <div class="goods-price">
          <span class="sale">{{ this.goods[0].discount }}%</span>
          <span
              class="num"
              :innerHTML="comma(this.goods[0].price)"
          ></span>
          <span class="won">원</span>
          <span class="origin-price">{{ comma(this.goods[0].option_price) }}원</span>
        </div>
        <table class="spec">
          <tbody>
          <tr>
            <td class="spec-title">적립해택</td>
            <td class="spec-content">4,000원 적립</td>
          </tr>
          <tr>
            <td class="spec-title">상품후기</td>
            <td class="spec-content">
              <span class="ratings-num">{{ this.goods[0].count }}명</span>
              <div class="star-ratings">
                <star-rating :rating="(this.goods[0].rate)"
                             :read-only="true"
                             :increment="0.01"
                             :show-rating="false"
                             :inline="true"
                             v-bind:star-size="20"/>
              </div>
              <span class="ratings-avg">({{ this.goods[0].rate }})</span>
            </td>
          </tr>
          <tr>
            <td class="spec-title">상품코드</td>
            <td class="spec-content">FA00091</td>
          </tr>
          </tbody>
        </table>
        <div class="option-wrap">
          <span>상품옵션</span>

            <select name="" id="" @change="selectOption(1,$event)">
              <option value="0" selected disabled>{{ this.goods[0].option_name1 }}</option>
              <option
                  v-for="item in this.options"
                  :key="item"
                  :value="item.option_content1">
                <span v-if="item.maxPrice == item.minPrice">{{
                    `${item.option_content1} (${comma(discount(item.minPrice, this.goods[0].discount))}원)`
                  }}</span>
                <span v-else>{{ `${item.option_content1} (${comma(discount(item.minPrice, this.goods[0].discount))}~${comma(discount(item.maxPrice, this.goods[0].discount))}원)` }}</span>
              </option>
            </select>

            <select v-if="this.goods[0].option_name2 != ''" name="" id="" @change="selectOption(2, $event)">
              <option value="0" selected disabled>{{ this.goods[0].option_name2 }}</option>
              <option v-show="optionList.length > 0"
                      class="cart-option"
                      v-for="item in this.optionList"
                      :key="item"
                      :value="JSON.stringify(item)">
                <div class="cart-option">
                  <div>{{ `` }}</div>
                  <div>{{  }}</div>
                  {{`${item.option_content2} +(${comma(item.price)}원)&#160;/&#160;남은수량(${item.stock}개)`}}
                </div>
              </option>
            </select>

        </div>
        <div class="pay-wrap">
          <div class="pay-content" v-for="(item, idx) in cartList" :key="idx">
            <table>
              <tr>
                <td v-if="this.goods[0].option_name2 != ''">{{ `${item.option_content1} : ${item.option_content2}` }}</td>
                <td v-else>{{ item.option_content1 }}</td>
              </tr>
              <tr>
                <td>
                  <div class="select-pay hide">
                    <div class="select-pay-content">
                      <button @click="[countDecrease(item), totalPrice()]">
                        -
                      </button>
                      <input
                          type="text"
                          oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
                          :value="`${item.count}`"
                          @input="
                          [
                            countControl(
                              (item.count = $event.target.value),
                              item
                            ),
                            totalPrice,
                          ]
                        "
                      />
                      <button @click="[countIncrease(item), totalPrice()]">
                        +
                      </button>
                      <span class="goods-maxCount">최대구매수량:{{item.maxCount}}</span>
                    </div>
                    <div class="select-pay-side">
                      <span class="side-num"
                      >{{
                          comma(
                              discount(
                                  item.price * item.count,
                                  item.discount
                              )
                          )
                        }}원</span
                      >
                      <button
                          type="button"
                          @click="[deleteProduct(idx), totalPrice()]"
                      >
                        &#10005;
                      </button>
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </div>
          <div class="pay-top">
            <span class="pay-title"> 총 상품금액 </span>
            <span class="pay-num" :innerHTML="comma(total)"></span>
            <span class="pay-won">원</span>
          </div>
          <div class="pay-bottom">
            <button @click="addMyCart">장바구니</button>
            <button @click="orderGoods">구매하기</button>
          </div>
        </div>
      </div>
    </div>
    <div style="clear: both"></div>
    <view-detail @closeModal="detailControl" :isReviewWrite="this.isReviewWrite" :reviewKey="this.reviewKey"/>
    <div style="height: 1000px"></div>
  </section>
</template>

<script>
import custom_header from "../../components/Header.vue";
import custom_nav from "../../components/Nav.vue";

import viewDetail from "@/pages/goods/viewDetail";
import {mapMutations, mapState} from "vuex";
import router from "@/router";

export default {
  name: "goodsView",
  data() {
    return {
      별점평균: 49.7,
      reset: 0,
      total: 0,
      goods: [],

      options: [],
      optionList: [],

      reviewKey:{
        cust_id: '',
        product_id : this.product_id,
        product_name : '',
        img : '',
        uploadFile : '',
      },
      target:'',
      isReviewWrite:'',

      cartList: [],



    };
  },

  computed:{
    ...mapState(['account', 'isHeaderFixed'])
  },

  mounted() {

    this.postGoods();
  },

  methods: {
    ...mapMutations(['setFixed']),

    postGoods() {
      this.axios.post("/api/goods/view", this.product_id).then(({data}) => {
        this.goods = data.goods;
        this.options = data.options;

        this.reviewKey.product_name = this.goods[0].product_name;
        this.reviewKey.uploadFile = this.goods[0].uploadFile;
        this.reviewKey.img = this.goods[0].img1;
      })

      this.getReviewCheck();
    },

    getReviewCheck(){
      this.axios.post("/api/goods/check-review", this.reviewKey).then(({data})=>{
          this.isReviewWrite = data;
      })
    },

    detailControl(){
      this.postGoods();
    },

    comma(val) {
      if (val == 0) {
        return 0;
      } else {
        return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },

    discount(value, percent) {
      return value - value * (0.01 * percent);
    },

    deleteProduct(index) {
      this.cartList.splice(index, 1);
    },

    countIncrease(value) {
      if (value.maxCount > value.count){
        value.count++;
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

    selectOption(target, e) {

      if (target == 1) {
        this.optionList = [];
        if (this.goods[0].option_name2 == '') { // 옵션 1개일때
          this.goods.forEach((item) => {
            if (item.option_content1 == e.target.value) {
              this.addCartList(JSON.stringify(item));
            }
          })
          e.target.value = 0;
        } else {
          this.goods.forEach((item) => {
            if (item.option_content1 == e.target.value) {
              this.optionList.push(item);
            }

          })
        }

      } else { // 옵션 2개일때
        this.addCartList(e.target.value);
        e.target.value = 0;
      }

    },

    addCartList(value) {
      let tmp = JSON.parse(value),
          obj = {},
          hasItem = false;

      obj = {
        cust_id : this.account.id,
        product_id : tmp.product_id,
        option_id : tmp.option_id,
        company_name : tmp.company_name,
        product_name : tmp.product_name,
        option_name1 : tmp.option_name1,
        option_content1 : tmp.option_content1,
        option_name2 : tmp.option_name2,
        option_content2 : tmp.option_content2,
        option_price : tmp.option_price,
        discount : tmp.discount,
        maxCount : tmp.maxCount,
        price : tmp.price,
        count : 1,
        img : tmp.img1,
      }

      obj.count = 1;

      if (this.cartList.length == 0) {
        this.cartList.push(obj);
      } else {
        this.cartList.forEach((item) => {
          if (item.option_id == obj.option_id) {
            alert("이미 선택한 상품입니다.");
            hasItem = true;
            return;
          }
        });

        if (!hasItem) {
          this.cartList.push(obj);
        }
      }

      value = 0;

      this.totalPrice();
    },

    addMyCart() {
      console.log(this.account.id);
      if(this.account.id == undefined){
        alert('로그인이 필요한 기능입니다.');
        return;
      }

      if (this.cartList.length == 0) {
        alert('상품을 선택해주세요.');
        return;
      }

      this.cartList.forEach((item)=>{
        delete item.maxCount;
      })

      this.axios.post("/api/account/input-cart", JSON.stringify(this.cartList),
          { headers: { 'Content-Type': 'application/json' } }).then(({data})=>{
        if (data > 0){
          if(confirm('장바구니로 이동 하시겠습니까?')){
            router.replace({path: "/cart"});
          } else {
            this.cartList = [];
          }
        }

      })

    },

    orderGoods() {
      if (this.cartList.length == 0) {
        alert('상품을 선택해주세요.');
        return;
      }

      localStorage.removeItem('orderList');
      localStorage.setItem('orderList', JSON.stringify(this.cartList));
      router.replace({path: "/order"});
    },

    totalPrice() {
      this.total = 0;
      this.cartList.forEach((item) => {
        this.total += this.discount(
            item.price * item.count,
            item.discount
        );
      });
    },

  },
  components: {
    custom_header,
    custom_nav,
    viewDetail,
  },
  props: {
    product_id: {
      type: String,
    },
  }
};
</script>

<style>

.cart-option{
  display: flex;
}

.cart-option>p{
  display: block;
}

.goods-wrap {
  margin: 3rem auto;
  width: 1060px;
}

.search-nav a {
  color: black;
}

.search-nav > span:not(:first-child) {
  margin-left: 10px;
}

.goods-title {
  margin-top: 2rem;
  margin-bottom: 4rem;
  font-weight: bolder;
  text-align: center;
}

.goods-thumbs {
  width: 50%;
  float: left;
  padding: 0rem 0.2rem;
}

.goods-thumbs > img {
  width: 100%;
  height: 450px;
}

.goods-spec {
  width: 50%;
  padding: 0rem 2rem;
  float: left;
}

.goods-price {
  border-bottom: 1px #d0d0d0 solid;
  padding: 0.5rem 0rem;
}

.sale {
  color: red;
  font-size: 32px;
  font-weight: 500;
  margin-right: 8px;
}

.num {
  font-size: 30px;
  font-weight: 700;
  letter-spacing: -2px;
}

.won {
  margin-left: 4px;
  font-size: 25px;
  line-height: 1em;
  font-weight: 400;
}

.origin-price {
  margin-left: 2px;
  color: #999;
  text-decoration: line-through;
}

.spec {
  width: 100%;
  font-size: 14px;
}

.spec td {
  padding: 0.5rem 0;
}

.spec-title {
  width: 15%;
}

.spec-content {
  display: flex;
}

.ratings-num,
.ratings-avg {
  line-height: 0.2rem;
  margin-right: 0.5rem;
}

.ratings-avg {
  margin-left: 0.2rem;
}

.star-ratings {
  font-size: 16px;
  height: 1.8rem;
  color: #aaa9a9;
  position: relative;
  width: max-content;
  -webkit-text-fill-color: transparent; /* Will override color (regardless of order) */
  -webkit-text-stroke-width: 1.3px;
  -webkit-text-stroke-color: #2b2a29;
}

.star-ratings-fill {
  position: absolute;
  overflow: hidden;
  -webkit-text-fill-color: gold;
}

.star-ratings-base {
  z-index: 0;
  padding: 0;
}

.option-wrap {
  padding: 1rem 0rem;
  border-top: 1px #d0d0d0 solid;
  font-size: 14px;
  display: flex;
  flex-direction: column;
}

.option-wrap > span {
  font-weight: bolder;
}

.option-wrap select {
  width: 100%;
  margin-top: 0.5rem;
  padding: 0.3rem;
}

.pay-top {
  text-align: right;
}

.pay-title {
  font-size: 17px;
}

.pay-num {
  font-weight: 600;
  font-size: 32px;
  letter-spacing: -2px;
  position: relative;
  bottom: -2px;
}

.pay-won {
  font-size: 17px;
  margin-left: 2px;
}

.pay-bottom {
  margin-top: 0.5rem;
  display: flex;
  justify-content: space-between;
}

.pay-bottom button {
  border: 1px #ccc solid;
  background: #fff;
  font-size: 19px;
  padding: 1rem 4.5rem;
}

.pay-bottom > :last-child {
  background: #3385f096;
  color: #fff;
}

.pay-content {
  border: 1px solid #ccc;
  background: #f8f8f8;
  margin-top: 1rem;
  padding: 0.5rem;
}

.pay-content > table {
  width: 100%;
  font-size: 14px;
}

.select-pay {
  margin-top: 0.5rem;
  display: flex;
  justify-content: space-between;
}

.select-pay-wrap {
  margin-top: 3rem;
}

.select-pay-content * {
  border: 1px solid #bbb;
  font-size: 18px;
  text-align: center;
  height: 30px;
}

.select-pay-content button {
  font-weight: bolder;
  width: 30px;
}

.select-pay-content input {
  width: 40px;
  border-left: none;
  border-right: none;
}

.goods-maxCount{
  border:none;
  margin: 0 5px;
  font-size: 14px;
  font-weight: bolder;
  color: cornflowerblue;
}

.side-num {
  font-size: 19px;
  margin: 0 0.3rem;
}

.select-pay-side > button {
  border: 1px solid #bbb;
  font-size: 15px;
  padding: 0.3rem;
  vertical-align: bottom;
}

</style>
