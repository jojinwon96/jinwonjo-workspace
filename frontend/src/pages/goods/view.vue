<template>
  <custom_header />
  <custom_nav />
  <section class="content">
    <div class="goods-wrap">
      <div class="search-nav">
        <span><a href="/" class="home">Home</a></span>
        <span>&#10095;</span>
        <span>꽃다발</span>
      </div>
      <h3 class="goods-title">지금 이 순간</h3>
      <div class="goods-thumbs">
        <img src="../../assets/img/bouquet1.jpg" alt="" />
      </div>
      <div class="goods-spec">
        <div class="goods-price">
          <span class="sale">{{ product.discount }}%</span>
          <span
            class="num"
            :innerHTML="comma(discount(product.price, product.discount))"
          ></span>
          <span class="won">원</span>
          <span class="origin-price">{{ comma(product.price) }}원</span>
        </div>
        <table class="spec">
          <tbody>
            <tr>
              <td class="spec-title">적립해택</td>
              <td class="spec-content">회원가입시 4,000원 적립</td>
            </tr>
            <tr>
              <td class="spec-title">상품후기</td>
              <td class="spec-content">
                <span class="ratings-num">XX명</span>
                <div class="star-ratings">
                  <div
                    class="star-ratings-fill"
                    :style="{ width: 별점평균 + '%' }"
                  >
                    <span>★</span><span>★</span><span>★</span><span>★</span
                    ><span>★</span>
                  </div>
                  <div class="star-ratings-base">
                    <span>★</span><span>★</span><span>★</span><span>★</span
                    ><span>★</span>
                  </div>
                </div>
                <span class="ratings-avg">({{ 별점평균 }}/5)</span>
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
          <select name="" id="" @change="selectOption">
            <option value="0" selected>사이즈 선택</option>
            <option
              v-for="item in optionList"
              :key="item"
              :value="JSON.stringify(item)"
              :innerHTML="item.optName"
            ></option>
          </select>
        </div>
        <div class="pay-wrap">
          <div class="pay-content" v-for="(item, idx) in cartList" :key="idx">
            <table>
              <tr>
                <td>상품 : {{ item.optName }}</td>
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
                    </div>
                    <div class="select-pay-side">
                      <span class="side-num"
                        >{{
                          comma(
                            discount(
                              (product.price + item.optPrice) * item.count,
                              product.discount
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
            <button>장바구니</button>
            <button>구매하기</button>
          </div>
        </div>
      </div>
    </div>
    <div style="clear: both"></div>
    <div class="goods-detail-wrap">
      <ul class="tab-list" :class="{ tabFixed: isFixed }">
        <a
          class="tab-button"
          v-for="item in list"
          :key="item.id"
          :class="{ tabActive: item.active }"
          @click="moveStage(item.id)"
        >
          <li>{{ item.label }}</li>
        </a>
      </ul>
      <div class="tab-content">
        <p>1번</p>
      </div>
      <div class="tab-content">
        <div class="review-top-wrap">
          <ul>
            <li>
              <select name="" id="">
                <option value="0" selected>평점전체</option>
                <option value="1">★</option>
                <option value="2">★★</option>
                <option value="3">★★★</option>
                <option value="4">★★★★</option>
                <option value="5">★★★★★</option>
              </select>
            </li>
            <li>
              <input type="text" placeholder="제목,상품명,작성자" />
              <button type="button">검색</button>
            </li>
          </ul>
        </div>
        <table class="table review-table">
          <thead class="table-light">
            <tr class="text-center">
              <th class="col-1">번호</th>
              <th class="col-5">후기</th>
              <th>평가</th>
              <th>작성자</th>
              <th>등록일</th>
              <th class="col-1">조회</th>
            </tr>
          </thead>
          <tbody>
            <tr class="text-center align-middle">
              <td class="col-1">1</td>
              <td class="col-5">
                <div class="review-img-wrap">
                  <img src="../../assets/img/bouquet1.jpg" alt="" />
                  <ul>
                    <li>상품 : 지금 이 순간</li>
                    <li class="fw-bold">제목 : 사진과 많이 달라요..</li>
                    <li>
                      Lorem ipsum dolor sit amet consectetur adipisicing elit.
                      Blanditiis numquam maxime praesentium sint neque error
                      voluptatem totam quis cum nihil ab aliquid facilis
                      laudantium perspiciatis vero ut officiis, nulla quod.
                    </li>
                  </ul>
                </div>
              </td>
              <td>★★★★★</td>
              <td>wls****</td>
              <td>2023-08-01 09:10</td>
              <td class="col-1">7</td>
            </tr>
          </tbody>
        </table>
        <button class="review-btn" @click="isOpenReview = true">
          리뷰작성하기
        </button>
        <!-- 리뷰 모달 -->
        <ReviewModal
          :isOpenReview="isOpenReview"
          @closeModal="isOpenReview = false"
        ></ReviewModal>

        <div style="clear: both"></div>
        <div class="paging-navigation">
          <a href="">◀</a>
          <a class="page-active" href="">1</a>
          <a href="">2</a>
          <a href="">3</a>
          <a href="">▶</a>
        </div>
      </div>
      <div class="tab-content">
        <div class="review-top-wrap">
          <ul>
            <li>
              <select name="" id="">
                <option value="0" selected>전체</option>
                <option value="1">상품 문의</option>
                <option value="2">색상 문의</option>
                <option value="3">기타 문의</option>
              </select>
            </li>
            <li>
              <input type="text" placeholder="제목,상품명,작성자" />
              <button type="button">검색</button>
            </li>
          </ul>
        </div>
        <table class="table review-table">
          <thead class="table-light">
            <tr class="text-center">
              <th class="col-1">번호</th>
              <th>상태</th>
              <th>분류</th>
              <th class="col-5">문의</th>
              <th>작성자</th>
              <th>등록일</th>
              <th class="col-1">조회</th>
            </tr>
          </thead>
          <tbody>
            <tr class="text-center align-middle">
              <td class="col-1">1</td>
              <td>답변완료</td>
              <td>[색상 문의]</td>
              <td class="col-5">
                <div class="review-img-wrap">
                  <img src="../../assets/img/bouquet1.jpg" alt="" />
                  <ul>
                    <li>상품 : 지금 이 순간</li>
                    <li class="fw-bold">제목 : 색상문의</li>
                    <li>비밀글입니다.</li>
                  </ul>
                </div>
              </td>
              <td>wls****</td>
              <td>2023-08-01 09:10</td>
              <td class="col-1">2</td>
            </tr>
          </tbody>
        </table>
        <button class="review-btn">문의작성하기</button>
        <div style="clear: both"></div>
        <div class="paging-navigation">
          <a href="">◀</a>
          <a class="page-active" href="">1</a>
          <a href="">2</a>
          <a href="">3</a>
          <a href="">▶</a>
        </div>
      </div>
    </div>
    <div style="height: 1000px"></div>
  </section>
</template>

<script>
import custom_header from "../../components/Header.vue";
import custom_nav from "../../components/Nav.vue";
import ReviewModal from "../../components/ReviewModal.vue";

export default {
  name: "goodsView",
  data() {
    return {
      별점평균: 49.7,
      total: 0,
      product: {
        pId: "1",
        pName: "지금 이 순간",
        price: 70000,
        discount: 5,
      },
      optionList: [
        {
          optId: "opt1",
          optName: "기본형",
          optPrice: 0,
        },
        {
          optId: "opt2",
          optName: "고급형(+20,000원)",
          optPrice: 20000,
        },
        {
          optId: "opt3",
          optName: "특별형(+40,000원)",
          optPrice: 40000,
        },
      ],
      cartList: [],
      isFixed: false,
      isStaging: false,
      isOpenReview: false,
      list: [
        { id: 1, label: "상세설명", content: "콘텐츠1", active: true },
        { id: 2, label: "상품후기", content: "콘텐츠2", active: false },
        { id: 3, label: "상품문의", content: "콘텐츠3", active: false },
      ],
    };
  },

  watch: {},

  mounted() {
    document.addEventListener("scroll", this.scroll);
  },

  beforeUnmount() {
    document.removeEventListener("scroll", this.scroll);
  },

  methods: {
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
      value.count++;
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

    selectOption(e) {
      let obj = JSON.parse(e.target.value),
        cartItem = {
          pId: this.product.pId,
          pName: this.product.pName,
          price: this.product.price,
          pDiscount: this.product.discount,
          optId: obj.optId,
          optName: obj.optName,
          optPrice: obj.optPrice,
          count: 1,
        },
        hasItem = false;

      if (this.cartList.length == 0) {
        this.cartList.push(cartItem);
      } else {
        this.cartList.forEach((item) => {
          if (item.optId == obj.optId) {
            alert("이미 선택한 상품입니다.");
            hasItem = true;
            return;
          }
        });

        if (!hasItem) {
          this.cartList.push(cartItem);
        }
      }

      e.target.value = 0;

      this.totalPrice();
    },

    totalPrice() {
      this.total = 0;
      this.cartList.forEach((item) => {
        this.total += this.discount(
          (item.price + item.optPrice) * item.count,
          item.pDiscount
        );
      });
    },

    moveStage(id) {
      let location =
        document.querySelectorAll(".tab-content")[id - 1].offsetTop - 200;
      window.scrollTo({ top: location, behavior: "smooth" });
    },
    scroll() {
      let currentScroll = window.scrollY,
        location1 =
          document.querySelectorAll(".tab-content")[0].offsetTop - 200,
        location2 =
          document.querySelectorAll(".tab-content")[1].offsetTop - 200,
        location3 =
          document.querySelectorAll(".tab-content")[2].offsetTop - 200;

      console.log(currentScroll);
      if (currentScroll > 767) {
        this.isFixed = true;
      } else {
        this.isFixed = false;
      }

      if (!this.isStaging) {
        if (currentScroll > location1 && currentScroll < location2) {
          this.list[0].active = true;
          this.list[1].active = false;
          this.list[2].active = false;
        } else if (currentScroll > location2 && currentScroll < location3) {
          this.list[0].active = false;
          this.list[1].active = true;
          this.list[2].active = false;
        } else if (currentScroll > location3) {
          this.list[0].active = false;
          this.list[1].active = false;
          this.list[2].active = true;
        }
      }
    },

    ratingToPercent() {
      const score = +this.별점평균 * 20;
      return score + 1.5;
    },
  },
  components: {
    custom_header,
    custom_nav,
    ReviewModal,
  },
};
</script>

<style>
.goods-wrap {
  margin-top: 2rem;
  margin-left: 3rem;
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
  max-width: 100%;
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

.option-wrap > select {
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
  padding: 1rem 5.1rem;
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

.goods-detail-wrap {
  margin-top: 5rem;
  margin-left: 3rem;
  margin-right: 1.5rem;
}

.tabFixed {
  position: sticky;
  top: 95px;
}

.tab-list {
  display: flex;
  justify-content: center;
  list-style-type: none;
  border-bottom: 1px solid #ccc;
  background-color: white;
}

.tab-list::after {
  content: "";
  display: block;
  clear: both;
}

.tab-button {
  padding: 10px 30px;
  margin-right: -1px;
  margin-bottom: -1px;
  color: grey;
  text-decoration: none;
  cursor: pointer;
}

.tabActive {
  border-top: 2px solid #3385f096;
  border-right: 1px solid #ccc;
  border-bottom: 1px solid white;
  border-left: 1px solid #ccc;
  color: black;
}

.tab-content {
  margin-bottom: 20rem;
}

.tab-content select {
  border: 1px solid #ccc;
  font-size: 14px;
  height: 32px;
  padding-left: 5px;
  border-radius: 2px;
}

.tab-content input {
  margin-right: 2px;
  padding: 0 9px;
  width: 180px;
  height: 32px;
  font-size: 13px;
  line-height: 32px;
  border: 1px #ccc solid;
  border-radius: 2px;
  outline: 0;
}

.tab-content button {
  height: 32px;
  font-size: 13px;
  min-width: 60px;
  border: 1px #ccc solid;
  background: #fff;
  line-height: 26px;
  border-radius: 2px;
}

.review-top-wrap > ul {
  margin-top: 10rem;
  display: flex;
  justify-content: space-between;
}

.review-table > thead {
  font-size: 14px;
}

.review-table tbody {
  font-size: 12px;
}

.review-img-wrap {
  text-align: left;
  display: flex;
}

.review-img-wrap img {
  width: 4.5rem;
  height: 4rem;
}

.review-img-wrap ul {
  margin-left: 5px;
  font-size: 13px;
  margin-bottom: 0;
}

.review-img-wrap li {
  max-width: 350px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.review-btn {
  float: right;
  background: #3385f096 !important;
  border: none !important;
  color: white;
  height: 2.3rem !important;
  padding: 0.3rem;
  outline: none;
}
</style>
