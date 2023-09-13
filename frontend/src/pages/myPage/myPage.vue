<template>
  <custom_header />
  <custom_nav />
  <section class="content">
    <div class="myPage-wrap">
      <MyPageMenu/>
      <div class="myPage-content-wrap">
        <router-view />
      </div>
    </div>
  </section>
</template>

<script>
import custom_header from "../../components/Header.vue";
import custom_nav from "../../components/Nav.vue";
import MyPageMenu from "@/components/myPage/myPageMenu";


export default {
  name: "myPage",

  data(){
    return{
      currentPage:'',
    }
  },

  components: {
    custom_header,
    custom_nav,
    MyPageMenu,
  },
};
</script>

<style>
a {
  text-decoration: none;
  border: none;
  outline: none;
}

.myPage-wrap {
  margin: 5rem auto;
  width: 1080px;
}

.myPage-content-wrap {
  float: left;
  width: 82%;
  padding-left: 1rem;
}

.myPage-content-title{
  text-align: center;
  text-decoration: underline;
  text-underline-position : under;
}

.myPage-content{
  margin: 2rem 0;
  border-top: 1px solid #bbbbbb;
}
</style>
