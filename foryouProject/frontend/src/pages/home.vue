<template>
  <!-- 배너 -->
  <section class="carousel" data-carousel>
    <div class="carousel-button-wrap">
      <button class="carousel-button prev" data-carousel-button="prev"></button>
      <button class="carousel-button next" data-carousel-button="next"></button>
    </div>
    <ul data-slides>
      <li class="slide" data-active>
        <img src="https://www.f-mans.com/data/images/cover/category/category_cover_bouquet.jpg">
      </li>
      <li class="slide">
        <img
            src="https://cdn.011st.com/11dims/resize/1240x400/quality/100/11src/browsing/space/banner/2023/6/9/2306091811503800626_320.jpg">
      </li>
      <li class="slide">
        <img
            src="https://cdn.011st.com/11dims/resize/1240x400/quality/100/11src/browsing/space/banner/2023/6/8/2306081509071000626_211.jpg">
      </li>
    </ul>
  </section>

  <section class="content">
    <div class="best-title">
      <h2>베스트</h2>
    </div>
    <div class="card-wrap">
      <div class="card-panel" v-for="item in 5" :key="item">
        <Card/>
      </div>
    </div>
  </section>
</template>
<script>

import  Card from "@/components/Card"

export default {
  name: "Home",
  components: {Card},
  // setup() {
  //   const state = reactive({
  //     items: []
  //   })
  //
  //   axios.get("/api/items").then((res) => {
  //     state.items = res.data;
  //   });
  //
  //   return {state}
  // }
}
</script>

<style scoped></style>