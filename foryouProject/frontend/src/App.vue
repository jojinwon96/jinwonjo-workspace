<template>
  <div class="wrap">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
  components: {},
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
