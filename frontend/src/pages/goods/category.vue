<template>
  <custom_header/>
  <custom_nav/>
  <section class="content">

    <div class="cate-wrap">
      <span class="cate-parent">카테고리</span>
      <span>❯</span>
      <span class="cate-child">{{ this.$route.params.category_name }}</span>
    </div>

    <form action="#">
      <div class="sort-wrap">
        <div class="sort-item-wrap1">
          <ul class="sort-items">
            <li>신규등록순</li>
            <li @click="this.changeSort('asc')">낮은가격순</li>
            <li @click="this.changeSort('desc')">높은가격순</li>
            <li>높은상품평순</li>
            <li>낮은상품평순</li>
            <li>후기많은순</li>
            <li>판매량순</li>
          </ul>
        </div>

        <div class="sort-item-wrap2">
          <ul>
            <li>
              <label for="">가격</label>
              <input type="text"/>
              <label>~</label>
              <input type="text"/>
              <label>원</label>
              <button class="price-btn">검색</button>
            </li>
          </ul>
        </div>
        <div style="clear: both"></div>
      </div>
    </form>
    <div class="card-wrap">
      <ul>
        <li
            class="card-panel"
            v-for="(item, index) in products"
            :key="item"
        >
          <custom_card :product="item" :idx="index" @setLikeId="setLikeId"/>
        </li>
      </ul>
    </div>

    <Pagination :pagination="this.pagination"
                :onPageChange="this.onPageChange"
                :pages="pages"/>
  </section>
</template>

<script>
import custom_header from "../../components/Header.vue";
import custom_nav from "../../components/Nav.vue";
import custom_card from "../../components/Card.vue";
import Pagination from "@/components/common/Pagination";
import axios from "axios";
import {mapMutations, mapState} from "vuex";

export default {
  components: {custom_header, custom_nav, custom_card, Pagination},
  name: 'category',

  data() {
    return {
      products: [],
      pagination: {},
      pages: [],
      pageInfo: {
        page: 1,
        range: 1,
        category_id: this.category_id,
        sortTarget: 'none',
      },
    }
  },

  computed: {
    ...mapState(['isCategoryOpen']),
  },

  watch: {
    category_id: function (value) {

      localStorage.removeItem('pageInfo');
      this.pageInfo.page = 1;
      this.pageInfo.range = 1;
      this.pageInfo.category_id = value;
      this.pageInfo.sort = '';

      this.setCategory(false);

      // 상품 다시 받기
      this.postGoods();
    },

  },

  mounted() {
    this.setCategory(false);

    if (localStorage.getItem('pageInfo') != null) {
      this.getPageInfo();
    }

    this.postGoods();
  },

  methods: {
    ...mapMutations(['setCategory']),

    setLikeId(info){
      this.products[info.idx].like_id = info.like_id;
    },

    changeSort(target){
      this.pageInfo.sortTarget = target;
      this.setPageInfo();
      this.postGoods();
    },

    postGoods() {
      const axiosConfig = {
        headers: {
          "Content-Type": "application/json"
        }
      }
      axios.post("/api/goods", JSON.stringify(this.pageInfo), axiosConfig).then((result) => {
        this.products = result.data.products;
        this.pagination = result.data.pagination;

        this.pages = [];
        for (let i = this.pagination.startPage; i <= this.pagination.endPage; i++) {
          this.pages.push(i);
        }

      })
    },

    onPageChange(target, num) {
      this.getPageInfo();

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

      this.setPageInfo();

      this.postGoods();
    },

    getPageInfo() {
      let pageInfo = JSON.parse(localStorage.getItem('pageInfo'));

      if (pageInfo != null) {
        this.pageInfo.range = pageInfo.range;
        this.pageInfo.page = pageInfo.page;
        this.pageInfo.category_id = pageInfo.category_id;
        this.pageInfo.sortTarget = pageInfo.sortTarget;
      }
    },

    setPageInfo() {
      localStorage.setItem('pageInfo', JSON.stringify(this.pageInfo));
    },
  },

  props: {
    category_id: {
      type: String,
    },
    category_name: {
      type: String,
    }
  }
};
</script>

<style>
.sortActive {
  color: #3385f096;
}

.cate-wrap {
  display: flex;
  margin: 2rem auto;
  width: 1060px;
}

.card-wrap{
  z-index: 888;
}

.cate-wrap span {
  font-size: 16px;
  margin: 0 3px;
}

.sort-wrap {
  margin: 2rem auto;
  width: 1060px;
  font-size: 13px;
  border: 1px solid #ddd;
}

.sort-wrap ul {
  padding: 0;
  margin: 0;
}

.sort-item-wrap1 {
  float: left;
}

.sort-item-wrap2 {
  float: right;
}

.sort-item-wrap2 li {
  border-left: 1px #ddd solid;
}

.sort-items {
  display: flex;
}

.sort-wrap li {
  padding: 0.5rem;
  line-height: 30px;
}

.sort-item-wrap1 li {
  border-right: 1px #ddd solid;
}

.sort-item-wrap1 li:hover {
  color: #3385f096;
  cursor: pointer;
}

.sort-item-wrap2 * {
  margin: 0px 2px;
}

.sort-item-wrap2 input {
  outline: none;
  border: 1px solid #ddd;
  padding: 3px;
  width: 5rem;
}

.card-wrap {
  margin: 0 auto;
  width: 1060px;
}

.card-wrap ul {
  display: flex;
  flex-wrap: wrap;
  margin: -16px 0 0 -16px;
}

.price-btn {
  background: #3385f096;
  color: white;
  font-weight: bolder;
  margin-left: 1.5rem;
  width: 3rem;
  padding: 0.3rem;
  border-radius: 3px;
  border: none;
}

.paging-navigation {
  text-align: center;
  margin-top: 2rem;
  font-size: 0;
}

.paging-navigation a {
  display: inline-block;
  box-sizing: border-box;
  vertical-align: middle;
  min-width: 35px;
  height: 35px;
  line-height: 35px;
  font-size: 12px;
  color: #666;
  font-weight: bolder;
  border: 1px #ddd solid;
}

.page-active {
  color: #3385f096 !important;
  border-color: #3385f096 !important;
}
</style>
