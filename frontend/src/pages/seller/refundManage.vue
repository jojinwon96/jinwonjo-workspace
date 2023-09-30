<template>
  <seller-header/>
  <div class="s-om-wrap">
    <div class="s-pm-items">
      <router-link to="/orderManage/order" class="default-border">
        <span class="pm-item-btn">주문</span>
      </router-link>
      <span class="partition"></span>
      <router-link to="/orderManage/refund" class="default-border">
        <span class="pm-item-btn itemActive">취소/환불</span>
      </router-link>
    </div>
    <div class="s-om-head">
      <div class="s-om-head-left">
        <div class="tr">
          <div class="td td-title">상태</div>
          <div class="td td-content">
            <select class="order-status" v-model="pageInfo.refundStatus">
              <option value="A" selected>전체</option>
              <option value="N">취소신청</option>
              <option value="Y">처리완료</option>
            </select>
          </div>
        </div>

        <div class="tr">
          <div class="td td-title">검색</div>
          <div class="td td-content">
            <select class="search-target" v-model="pageInfo.target">
              <option value="all" selected>전체</option>
              <option value="order_id">주문번호</option>
              <option value="order_detail_id">상세주문번호</option>
              <option value="order_name">주문자명</option>
              <option value="product_name">상품명</option>
            </select>
            <input class="search-input" type="text" @keyup.enter="onSearch()" v-model="pageInfo.content">
          </div>
        </div>
      </div>

      <div class="s-om-head-right">
        <button class="s-om-search-btn" type="button"
                @click="onSearch">검색하기</button>
        <button class="s-om-reset-btn" @click="onReset">초기화</button>
      </div>
    </div>
    <div class="s-om-body">
      <table class="table text-center">
        <thead>
        <tr>
          <th class="col-2">
            <span>주문번호</span>
            <span>주문자</span>
            <span>연락처</span>
          </th>
          <th class="col">상세주문번호</th>
          <th>주문일</th>
          <th class="col-3">
            <span>상품명</span>
            <span>선택옵션</span>
          </th>
          <th>수량</th>
          <th class="col-2">환불금액</th>
          <th>상태</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in this.refundList" :key="item">
          <th>
            <span>{{ item.order_id }}</span>
            <span>{{ item.order_name }}</span>
            <span>{{ item.phone }}</span>
          </th>
          <th>{{ item.order_detail_id }}</th>
          <th>{{ item.order_date }}</th>
          <th>
            <span>{{ item.product_name }}</span>
            <span v-if="item.option_name2 == ''">{{ `${item.option_name1} : ${item.option_content1}` }}</span>
            <span v-else>{{ `${item.option_content1} : ${item.option_content2}` }}</span>
          </th>
          <th>{{ item.count }}</th>
          <th>{{ item.price * item.count }}</th>
          <th>
              <span v-if="item.refundStatus == 'N'">
                취소신청
              </span>
            <span v-else>
                처리완료
              </span>
          </th>
          <th>
            <button class="om-success-btn" v-if="item.refundStatus == 'N'" @click="onRefund(item)">완료</button>
            <button class="om-success-btn-disabled" v-else disabled>완료</button>
          </th>
        </tr>
        </tbody>
      </table>
    </div>
    <Pagination :pagination="this.pagination"
                :onPageChange="this.onPageChange"
                :pages="pages"/>
  </div>
</template>

<script>
import SellerHeader from "@/components/seller/sellerHeader";
import Pagination from "@/components/common/Pagination";
import {mapMutations, mapState} from "vuex";
import axios from "axios";

export default {
  name: "refundManage.vue",

  data() {
    return {
      refundList: [],
      pagination: {},
      pages: [],
      pageInfo: {
        page: 1,
        range: 1,
        target:'all',
        refundStatus:'A',
        content:'',
      }
    }
  },

  mounted() {
    this.postRefund();
  },

  computed: {
    ...mapState(['seller', 'account']),
  },

  methods: {
    ...mapMutations(['setSeller']),

    onSearch() {
      this.postRefund();
    },

    onReset(){
      this.pageInfo.page = 1;
      this.pageInfo.range = 1;
      this.pageInfo.target = 'all';
      this.pageInfo.status = 'A'
      this.pageInfo.content = '';
      this.postRefund();
    },

    postRefund() {
      axios.post("/api/seller/refund", this.pageInfo).then(({data}) => {
        console.log(data);
        this.refundList = data.order;
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

      this.postRefund();
    },

    onRefund(value){
      if (confirm('해당 상품의 취소신청을 처리 하시겠습니까?')){
        axios.post("/api/seller/modify-refund",value).then(({data})=>{
          if (data > 0){
            alert('성공적으로 상품의 취소신청을 처리하였습니다.');
            this.postRefund();
          }
        })
      }
    },
  },

  components: {
    SellerHeader,
    Pagination
  }
}
</script>

<style scoped>
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

.s-om-wrap {
  margin: 8rem auto;
  width: 1000px;
  padding: 1.2rem;
}

.s-om-head {
  display: flex;
  width: 100%;
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
  font-size: 14px;
  padding: 3px;
}

.order-status {
  width: 70%;
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

.s-om-reset-btn{
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

.om-success-btn {
  border: none;
  background: #3385f096;
  color: white;
  padding: 5px 12px;
}

.om-success-btn-disabled {
  border: none;
  background: #aaa9a9;
  color: white;
  padding: 5px 12px;
}
</style>