<template>
  <custom_header @openCategory="openControl"/>
  <custom_nav />

  <section v-if="goods.length > 0" class="content search-view-wrap">
    <div class="card-wrap">
    <span class="result-search">검색결과 {{ pagination.listCnt }}건</span>
      <ul>
        <li class="card-panel" v-for="(item,index) in this.goods" :key="item">
          <custom_card :product="item" :idx="index" @setLikeId="setLikeId"/>
        </li>
      </ul>
    </div>
    <pagination :pagination="this.pagination"
                :onPageChange="this.onPageChange"
                :pages="pages"/>
  </section>
  <section v-else class="content no-search">
    <span>{{ content }}검색 결과가 없습니다.</span>
  </section>
</template>

<script>
import custom_header from "@/components/Header.vue";
import custom_nav from "@/components/Nav.vue";
import custom_card from "@/components/Card.vue";
import pagination from "@/components/common/Pagination";

export default {
  name: "searchView",

  data() {
    return {
      goods: [],
      pagination: {},
      pages: [],
      pageInfo: {
        page: 1,
        range: 1,
      },
    };
  },

  props: {
    category_id: {
      type: String,
    },
    content: {
      type: String,
    }
  },

  components: { custom_header, custom_nav, custom_card, pagination },

  watch:{
    category_id:function (){
      this.postSearchGoods();
    },

    content:function (){
      this.postSearchGoods();
    },
  },

  mounted() {
    this.postSearchGoods();
  },

  methods: {
    postSearchGoods(){
      this.pageInfo.category_id = this.category_id;
      this.pageInfo.content = this.content;

      this.axios.post("/api/home/find-goods", this.pageInfo).then((result) => {
        console.log(result.data);
        this.goods = result.data.products;
        this.pagination = result.data.pagination;

        this.pages = [];
        for (let i = this.pagination.startPage; i <= this.pagination.endPage; i++) {
          this.pages.push(i);
        }

      })
    },

    openControl() {
      if (!this.isOpen) {
        this.isOpen = true;
      } else {
        this.isOpen = false;
      }
    },

    setLikeId(info){
      console.log(info);
      this.bestList[info.idx].like_id = info.like_id;
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
      this.postSearchGoods();
    },
  },
}
</script>

<style scoped>
  .search-view-wrap{
    margin: 3rem auto;
  }

  .result-search{
    display: block;
    margin: 1rem 0;
    font-weight: bolder;
  }

  .no-search {
    width: 1060px;
    margin: 5rem auto;
    text-align: center;
    font-size: 24px;
  }
</style>