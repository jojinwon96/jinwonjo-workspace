<template>
  <div class="wrap">
    <custom_header />
    <custom_nav />
    <router-view />
  </div>
</template>

<script>
import custom_header from "./components/Header";
import custom_nav from "./components/Nav";

export default {
  name: "App",
  components: {
    custom_header,
    custom_nav,
  },
  create: function () {
    console.log("ok");
  },
};
</script>
<style>
@import "./assets/css/normalize.css";

:root {
  scroll-behavior: auto;
}

* {
  margin: auto 0;
  padding: 0;
  box-sizing: border-box;
}

a {
  text-decoration: none;
  border: none;
  outline: none;
}

li {
  list-style: none;
}

/* 테플릿 */
@media screen and (max-width: 1200px) {
  .wrap {
  }
}

/* 모바일 */
@media screen and (max-width: 768px) {
  .wrap {
  }
}
</style>
