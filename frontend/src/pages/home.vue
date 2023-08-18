<template>
  <custom_header @openCategory="openControl" />
  <custom_nav />

  <!-- 배너 -->
  <section class="carousel" data-carousel>
    <div
      id="carouselExampleDark"
      class="carousel carousel-dark slide"
      data-bs-ride="carousel"
    >
      <div class="carousel-indicators">
        <button
          type="button"
          data-bs-target="#carouselExampleDark"
          data-bs-slide-to="0"
          class="active"
          aria-current="true"
          aria-label="Slide 1"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselExampleDark"
          data-bs-slide-to="1"
          aria-label="Slide 2"
        ></button>
        <button
          type="button"
          data-bs-target="#carouselExampleDark"
          data-bs-slide-to="2"
          aria-label="Slide 3"
        ></button>
      </div>
      <div class="carousel-inner">
        <div class="carousel-item-wrap" :class="isOpen ? 'dark' : 'white'">
          <div class="carousel-item active" data-bs-interval="10000">
            <img
              src="https://www.f-mans.com/data/images/cover/category/category_cover_bouquet.jpg"
              class="d-block w-100"
              alt="..."
            />
          </div>
          <div class="carousel-item" data-bs-interval="2000">
            <img
              src="https://www.f-mans.com/data/images/cover/category/category_cover_bouquet.jpg"
              class="d-block w-100"
              alt="..."
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://www.f-mans.com/data/images/cover/category/category_cover_bouquet.jpg"
              class="d-block w-100"
              alt="..."
            />
          </div>
        </div>
      </div>
      <button
        class="carousel-control-prev"
        type="button"
        data-bs-target="#carouselExampleDark"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button
        class="carousel-control-next"
        type="button"
        data-bs-target="#carouselExampleDark"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>
  </section>
  <section class="content">
    <div class="card-wrap">
      <div class="best-title">
        <h2>베스트</h2>
      </div>
      <ul>
        <li class="card-panel" v-for="item in 4" :key="item">
          <custom_card />
        </li>
      </ul>
    </div>
  </section>
</template>
<script>

import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";
import custom_card from "../components/Card.vue";

export default {
  name: "Home",
  created() {
    this.isShow = false;
  },
  methods: {
    openControl() {
      if (!this.isOpen) {
        this.isOpen = true;
      } else {
        this.isOpen = false;
      }
    },
  },
  data() {
    return {
      isOpen: false,
    };
  },
  components: { custom_header, custom_nav, custom_card },

};
</script>

<style scoped>
.card-wrap {
  margin: 0 2.5rem;
}
.carousel-item-wrap {
  filter: brightness(100%);
  transition: all 0.5s;
}

.white {
  filter: brightness(100%);
}
.dark {
  filter: brightness(50%);
}

.best-title {
  margin: 2rem 0.5rem;
}
</style>
