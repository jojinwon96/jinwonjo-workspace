<template>
  <div class="pm-content-wrap">
    <div class="pm-content-main">
      <div class="filter-panel">
        <span class="filter-title">카테고리</span>
        <select @change="this.pageInfo.category_id = $event.target.value"
                v-model="pageInfo.category_id"
                class="filter-content">
          <option selected disabled :value="pageInfo.category_id">선택</option>
          <option v-for="item in this.categoryList" :key="item" :value="item.category_id">{{ item.category_name }}</option>
        </select>
      </div>

      <div class="filter-panel">
        <span class="filter-title">상품명</span>
        <div class="filter-content ">
          <input @keyup.enter="onPageSearch()"
                 v-model="pageInfo.keyword"
                 @input="pageInfo.keyword = $event.target.value" class="pm-serach-input"/>
        </div>
      </div>

      <div class="filter-panel">
        <span class="filter-title">상품번호</span>
        <input @keyup.enter="onPageSearch()"
               v-model="pageInfo.product_id"
               @input="pageInfo.product_id = $event.target.value" class="filter-content"/>
      </div>
    </div>
    <div class="pm-content-sub">
      <button @click="onPageSearch()"
              class="pm-search" type="button">검색하기</button>
      <button class="pm-reset"
              @click="onReset()"
              type="button">초기화</button>
    </div>
  </div>

  <div class="pm-result-wrap">
    <span class="pm-result-title">검색결과 총 <span>{{ this.pagination.listCnt }}</span>건</span>
    <table class="table text-center">
      <thead>
      <tr class="text-center">
        <th>상품번호</th>
        <th>옵션번호</th>
        <th>이미지</th>
        <th class="col-5">카테고리/상품명/옵션명</th>
        <th>정가(원)</th>
        <th>할인율(%)</th>
        <th>판매가(원)</th>
        <th>재고</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item) in this.products" :key="item">
        <th>{{ item.product_id }}</th>
        <th>{{ item.option_id }}</th>
        <th v-if="item.uploadFile != 'Y'"><img :src="item.img1"></th>
        <th v-else><img :src="require(`@/assets/product/uploadfile/${item.img1}`)"></th>
        <th>
          <div>
            {{ item.category_name }} / {{ item.product_name }}
          </div>
          <div>
            /{{ item.option_name1 }} : {{ item.option_content1 }}
          </div>
          <div v-if="item.option_name2 != ''">
            /{{ item.option_name2 }} : {{ item.option_content2 }}
          </div>
        </th>
        <th>{{ item.option_price }}</th>
        <th>{{ item.discount }}</th>
        <th>{{ item.price }}</th>
        <th>{{ item.stock }}</th>
      </tr>
      </tbody>
    </table>
  </div>
  <Pagination :pagination="this.pagination"
              :onPageChange="this.onPageChange"
              :pages="pages"/>
</template>
<script>

import axios from "axios";
import Pagination from "@/components/common/Pagination";

export default {
  name: "inquiryProduct",
  data() {
    return {
      categoryList: [],
      products: [],
      pagination: {},
      pages: [],
      pageInfo: {
        page: 1,
        range: 1,
        category_id: '0',
        keyword: '',
        product_id: '',
        target: '0',
      }
    }
  },

  created() {
    this.postProduct();
  },

  methods: {
    onReset(){
      this.pageInfo.category_id = '';
      this.pageInfo.keyword = '';
      this.pageInfo.product_id = '';
    },

    onPageSearch(){
      if (this.pageInfo.category_id == '0'){
        this.pageInfo.category_id = '';
      }

      if (this.pageInfo.product_id != '' || this.pageInfo.keyword != '' || this.pageInfo.category_id !=''){
        this.pageInfo.page = 1;
        this.pageInfo.range = 1;
        this.pageInfo.target = '1';
        this.categoryList = [];
      }

      this.postProduct();
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

      this.postProduct();

    },

    postProduct() {
      if (this.pageInfo.category_id == '0'){
        this.pageInfo.category_id = '';
      }

      const axiosConfig = {
        headers: {
          "Content-Type": "application/json"
        }
      }

      axios.post("/api/seller/pasing/product", JSON.stringify(this.pageInfo), axiosConfig).then((result) => {
        this.products = result.data.products;
        this.pagination = result.data.pagination;
        this.categoryList = result.data.options;

        this.pages = [];
        for (let i = this.pagination.startPage; i <= this.pagination.endPage; i++) {
          this.pages.push(i);
        }
      })
    },
  },

  components:{
    Pagination
  },

  props: {
    id: String,
  },
}
</script>

<style scoped>

.pm-content-wrap {
  display: flex;
  justify-content: space-between;
  margin: 2rem 0;
  background: #cccccc;
  padding: 1rem;
}

.pm-content-main {
  width: 90%;
}

.pm-content-sub {
  width: 20%;
}

.filter-panel {
  padding: 5px 0;
  font-size: 14px;
  font-weight: bolder;
}

.filter-title {
  display: inline-block;
  text-align: left;
  width: 100px;
}

.filter-content {
  display: inline-block;
  width: 50%;
  height: 28px;
  border: none;
  outline: none;
}

.pm-serach-input {
  height: 28px;
  width: 100%;
  border: none;
  outline: none;
}

.pm-search {
  border: 1px solid #cccccc;
  background: #3385f096;
  font-size: 18px;
  font-weight: bolder;
  border-radius: 5px;
  color: white;
  width: 100%;
  padding: 1.5rem 0;
}

.pm-reset {
  border: 1px solid #cccccc;
  background: #575757;
  font-size: 18px;
  font-weight: bolder;
  border-radius: 5px;
  color: white;
  width: 100%;
  padding: 0.6rem 0;
}

.pm-result-title {
  display: block;
  border-bottom: 1px solid #bbbbbb;
  font-weight: 600;
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.pm-result-title > span {
  color: #3385f096;
}

table {
  font-size: 13px;
  vertical-align: middle;
}

tbody img {
  width: 4.5rem;
  height: 4rem;
}

thead {
  background: #666666;
  color: white;
}
</style>