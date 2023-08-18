<template>
  <custom_header />
  <custom_nav />
  <section class="content">
    <form action="#">
      <div class="sort-wrap">
        <div class="sort-item-wrap1">
          <ul class="sort-items">
            <li>신규등록순</li>
            <li>낮은가격순</li>
            <li>높은가격순</li>
            <li>높은상품평순</li>
            <li>낮은상품평순</li>
            <li>판매량순</li>
          </ul>
        </div>
        <div class="sort-item-wrap2">
          <ul>
            <li>
              <label for="">가격</label>
              <input type="text" />
              <label>~</label>
              <input type="text" />
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
        <li class="card-panel" v-for="item in 5" :key="item">
          <custom_card />
        </li>
      </ul>
    </div>

    <div class="paging-navigation">
      <a href="">◀</a>
      <a class="page-active" href="">1</a>
      <a href="">2</a>
      <a href="">3</a>
      <a href="">▶</a>
    </div>
  </section>
</template>

<script>
import custom_header from "../../components/Header.vue";
import custom_nav from "../../components/Nav.vue";
import custom_card from "../../components/Card.vue";

export default {
  components: { custom_header, custom_nav, custom_card },
};
</script>

<style>
.sort-wrap {
  margin: 2rem 3rem;
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
  width: 5rem;
}

.card-wrap {
  margin: 0 2.3rem;
}

.price-btn {
  background: #3385f096;
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
