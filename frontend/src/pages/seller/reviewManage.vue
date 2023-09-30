<template>
  <seller-header/>
  <div class="s-rm-wrap">
    <div class="s-pm-items">
      <router-link to="/reviewInquiryManage/review" class="default-border">
        <span class="pm-item-btn itemActive">후기</span>
      </router-link>
      <span class="partition"></span>
      <router-link to="/reviewInquiryManage/inquiry" class="default-border">
        <span class="pm-item-btn">문의</span>
      </router-link>
    </div>

    <div class="s-om-head">
      <div class="s-om-head-left">
        <div class="tr">
          <div class="td td-title">상태</div>
          <div class="td td-content">
            <select class="order-status" v-model="pageInfo.status">
              <option value="A" selected>전체</option>
              <option value="N">작성글</option>
              <option value="Y">삭제글</option>
            </select>
          </div>
        </div>

        <div class="tr">
          <div class="td td-title">검색</div>
          <div class="td td-content">
            <select class="search-target" v-model="pageInfo.rate">
              <option value="0" selected>평점전체</option>
              <option value="1">★</option>
              <option value="2">★★</option>
              <option value="3">★★★</option>
              <option value="4">★★★★</option>
              <option value="5">★★★★★</option>
            </select>
            <select class="search-target" v-model="pageInfo.target">
              <option value="all" selected>전체</option>
              <option value="name">작성자</option>
              <option value="title">제목</option>
              <option value="product_name">상품명</option>
            </select>
            <input class="search-input" type="text" v-model="pageInfo.content" @keyup.enter="postReview">
          </div>
        </div>

      </div>

      <div class="s-om-head-right">
        <button class="s-om-search-btn" type="button" @click="postReview">검색하기</button>
        <button class="s-om-reset-btn" @click="reviewReset">초기화</button>
      </div>
    </div>

    <div class="s-om-body" v-if="this.reviewList.length > 0">
      <table class="table text-center">
        <thead>
        <tr>
          <th>리뷰번호</th>
          <th>작성자</th>
          <th>상품명</th>
          <th>제목</th>
          <th>평점</th>
          <th>작성일</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
          <tr v-for="item in reviewList" :key="item">
            <td>{{ item.review_id }}</td>
            <td>{{ item.cust_id }}</td>
            <td>{{ item.product_name }}</td>
            <td>{{ item.title }}</td>
            <td>
              <star-rating :rating="parseInt(item.rate)" :read-only="true" :show-rating="false" :star-size="15" :inline="true"/>
            </td>
            <td>{{ item.date }}</td>
            <td>
              <button class="review-detail-btn" @click="onReviewDetail(item)">상세</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <Pagination :pagination="this.pagination"
                :onPageChange="this.onPageChange"
                :pages="pages"/>
    <ReviewDetailModal :isOpenReview="isOpenReview" :review="review" @closeModal="modalControl"/>
  </div>
</template>

<script>
import {mapMutations, mapState} from "vuex";
import SellerHeader from "@/components/seller/sellerHeader";
import Pagination from "@/components/common/Pagination";
import ReviewDetailModal from "@/components/seller/modal/reviewDetailModal";
import axios from "axios";

export default {
  name: "reviewInquiryManage",

  data() {
    return {
      isOpenReview:false,
      review:{},

      reviewList: [],
      pagination: {},
      pages: [],
      pageInfo: {
        page: 1,
        range: 1,
        status: 'A',
        rate: 0,
        target: 'all',
        content:'',
      },
    }
  },

  mounted() {
    this.postReview();
  },

  computed: {
    ...mapState(['seller', 'account']),
  },

  methods: {
    ...mapMutations(['setSeller']),

    reviewReset(){
      let reset = {
        page: 1,
        range: 1,
        status: 'A',
        rate: 0,
        target: 'all',
        content:'',
      }
      this.pageInfo = reset;
      this.postReview();
    },

    postReview() {
      axios.post("/api/seller/review", this.pageInfo).then(({data}) => {
        console.log(data);
        this.reviewList = data.reviews;
        this.pagination = data.pagination;

        this.pages = [];
        for (let i = this.pagination.startPage; i <= this.pagination.endPage; i++) {
          this.pages.push(i);
        }

      })
    },

    onPageChange(target, num) {
      let range = this.pageInfo.range,
          rangeSize = this.pagination.rangeSize;

      if (target == 'next') {
        this.pageInfo.page = (range * rangeSize) + 1;
        this.pageInfo.range = range + 1;
      } else if (target == 'prev') {
        this.pageInfo.page = ((range - 2) * rangeSize) + 1;
        this.pageInfo.range = range - 1;
      } else {
        if (num > 0) {
          this.pageInfo.page = parseInt(num);
        }
      }

      this.postReview();
    },

    onReviewDetail(item){
      this.review = item;
      this.isOpenReview = true;
    },

    modalControl(){
      this.isOpenReview = false;
      this.postReview();
    },
  },

  components: {
    SellerHeader,
    Pagination,
    ReviewDetailModal,
  }
}
</script>

<style scoped>
.s-rm-wrap {
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
  display: block;
  width: 120px;
  text-align: center;
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

.s-om-head {
  display: flex;
  background: #cccccc;
}

.s-om-head-left {
  display: table;
  border-spacing: 10px;
  width: 90%;
  padding: 12px;
}

.s-om-head-right {
  width: 20%;
  padding: 12px;
}

.tr {
  display: table-row;
}

.td {
  display: table-cell;
  vertical-align: middle;
}

.td-title {
  width: 5%;
  font-size: 14px;
  font-weight: bolder;
}

.td-content {
  width: 50%;
}

.s-om-head select {
  margin: 0 5px;
  font-size: 14px;
  padding: 3px;
}

.order-status {
  width: 87.2%;
  border: none;
  outline: none;
}

.search-target {
  width: 15%;
  border: none;
  outline: none;
}

.search-input {
  box-sizing: border-box;
  width: 54%;
  border: none;
  outline: none;
  font-size: 14px;
  margin: 0 6px;
  padding: 5px 5px;
}

.s-om-search-btn {
  border: 1px solid #cccccc;
  background: #3385f096;
  font-size: 18px;
  font-weight: bolder;
  border-radius: 5px;
  color: white;
  width: 100%;
  padding: 1.5rem 0;
}

.s-om-reset-btn {
  border: 1px solid #cccccc;
  background: #575757;
  font-size: 18px;
  font-weight: bolder;
  border-radius: 5px;
  color: white;
  width: 100%;
  padding: 0.6rem 0;
}

.s-om-body {
  border-top: 1px solid #cccccc;
  padding: 8px 0;
  margin: 2rem 0;
}

thead {
  background: #575757;
  color: white;
  font-size: 12px;
  vertical-align: middle;
}

tbody {
  vertical-align: middle;
}

thead span,
tbody span {
  display: block;
}

.review-detail-btn{
  border: none;
  background: #3385f096;
  padding: 5px 12px;
  color: white;
  font-weight: 600;
}
</style>