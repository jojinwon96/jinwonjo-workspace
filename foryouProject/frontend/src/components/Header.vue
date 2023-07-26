<template>
  <header>
    <Transition name="fade">
      <div class="category-bg-black" v-if="isShow">
        <div class="category-bg-white"></div>
      </div>
    </Transition>
    <div class="category" :class="isShow ? 'openCategory' : 'closeCategory'">
      <div class="category-header">
        <ul>
          <li>
            <a>
              <router-link to="/signIn" class="default-border"
                >로그인</router-link
              >
            </a>
            <a>
              <router-link to="/signUp" class="default-border"
                >회원가입</router-link
              >
            </a>
          </li>
          <li
            class="category-close"
            @click="[setCategory(), $emit('openCategory')]"
          >
            <span>&#10005;</span>
          </li>
        </ul>
      </div>
      <div class="category-util">
        <ul>
          <li><a href="">홈</a></li>
          <li><a href="">찜목록</a></li>
          <li><a href="">장바구니</a></li>
          <li><a href="">주문조회</a></li>
          <li><a href="">문의</a></li>
          <li><a href="">쿠폰</a></li>
          <li><a href="">마일리지</a></li>
        </ul>
      </div>
      <div class="category-content">
        <div class="category-title">
          <h2>카테고리</h2>
        </div>
        <ul>
          <li><a href="">꽃바구니</a><span>&#10095;</span></li>
          <li><a href="">꽃다발</a><span>&#10095;</span></li>
          <li><a href="">축하화환</a><span>&#10095;</span></li>
          <li><a href="">근조화환</a><span>&#10095;</span></li>
          <li><a href="">동양란</a><span>&#10095;</span></li>
          <li><a href="">서양란</a><span>&#10095;</span></li>
          <li><a href="">분재</a><span>&#10095;</span></li>
          <li><a href="">공기정화</a><span>&#10095;</span></li>
          <li><a href="">비누꽃</a><span>&#10095;</span></li>
        </ul>
      </div>
    </div>
    <div></div>
    <div class="header-wrap">
      <div
        class="category-panel"
        @click="[setCategory(), $emit('openCategory')]"
      >
        <img src="../assets/img/hamburger.png" />
      </div>

      <div class="main-logo">
        <router-link to="/home" class="default-border"
          ><img src="../assets/img/logo.png"
        /></router-link>
      </div>

      <div class="search-wrap">
        <select class="serach-category">
          <option value="">전체</option>
          <option value="">꽃바구니</option>
          <option value="">꽃다발</option>
          <option value="">꽃박스</option>
        </select>
        <input type="text" class="search-text" />
        <button class="search-btn"></button>
      </div>

      <div>
        <div class="user-panel">
          <span class="user-myPage"></span>
          <span class="user-cart"></span>
          <span class="user-like"></span>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      isShow: false,
    };
  },

  methods: {
    setCategory() {
      if (!this.isShow) {
        this.isShow = true;
      } else {
        this.isShow = false;
      }
    },
  },
};
</script>

<style>
.show {
  display: block;
}
.hide {
  display: none;
}

.active-li {
  background: #3385f096;
}

header {
  z-index: 50;
}

.header-wrap {
  margin: 0 auto;
  padding: 2rem;
  display: flex;
  justify-content: center;
}

.category-panel:active {
  box-shadow: 0px 0px 0px 0px;
}

.category-panel > img {
  width: 40px;
  height: 40px;
  cursor: pointer;
}

.main-logo {
  margin: 0px 2rem;
}

.main-logo img {
  background-size: 200px;
  width: 200px;
  background-repeat: no-repeat;
  cursor: pointer;
}

.search-wrap {
  width: 500px;
  margin-left: 1rem;
  margin-right: 3rem;
  border: 3px solid #3385f096;
}

.serach-category {
  width: 20%;
  padding: 5px;
  outline: 0;
  border: none;
}

.search-text {
  width: 65%;
  outline: 0;
  border: 0;
}

.search-btn {
  background-image: url(../assets/img/search.png);
  margin-top: 2px;
  width: 30px;
  height: 30px;
  background-size: 30px;
  background-repeat: no-repeat;
  vertical-align: middle;
  background-color: white;
  cursor: pointer;
  border: none;
  float: right;
}

.user-panel {
  display: flex;
}

.user-search {
  display: none;
}

.user-panel > span {
  margin-left: 20px;
  width: 45px;
  height: 45px;
  background-size: 45px;
  background-repeat: no-repeat;
  cursor: pointer;
}

.user-cart {
  background-image: url(../assets/img/cart.png);
}

.user-cart:hover {
  background-image: url(../assets/img/cart-active.png);
}

.user-myPage {
  background-image: url(../assets/img/user.png);
}

.user-myPage:hover {
  background-image: url(../assets/img/user-active.png);
}

.user-like {
  background-image: url(../assets/img/like.png);
}

.user-like:hover {
  background-image: url(../assets/img/like-active.png);
}

.category {
  position: fixed;
  width: 350px;
  height: 100%;
  z-index: 998;
  transform: translateX(-350px);
  transition: all 1s;
  z-index: 990;
}

.openCategory {
  transform: translateX(0);
}
.closeCategory {
  transform: translateX(-350px);
}

.fade-enter-from {
  opacity: 0;
}
.fade-enter-active {
  transition: all 0.5s;
}
.fade-enter-to {
}
.fade-leave-from {
}
.fade-leave-active {
  transition: all 0.5s;
}
.fade-leave-to {
  opacity: 0;
}

.default-border {
  border: none !important;
}

.category ul {
  margin: 0;
  padding: 0;
}

.category-bg-white {
  background-color: none;
  opacity: 0;
  width: 100%;
  height: 100%;
}

.category-bg-black {
  position: fixed;
  background: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
}

.category a {
  color: black;
}

.category-header > ul {
  padding: 1rem;
  display: flex;
  align-items: center;
  background-color: white;
  border-bottom: 5px solid rgb(222, 222, 222);
}

.category-header li:nth-child(1) {
  flex-grow: 1;
}
.category-header li:nth-child(2) {
  flex-grow: 0;
}
.category-header li:nth-child(3) {
  flex-grow: 1;
}

.category-header a {
  font-weight: bold;
  border: 1px solid rgb(231, 230, 230);
  padding: 8px;
}

.category-header a:hover {
  border: 1px solid rgb(151, 151, 151);
}

.category-header span {
  display: block;
  font-size: 30px;
  padding-bottom: 5px;
  cursor: pointer;
}

.category-util {
  border-bottom: 8px solid rgb(222, 222, 222);
}

.category-util > ul {
  background-color: white;
}

.category-util li {
  display: inline-block;
  margin: 1rem;
  width: 3rem;
  text-align: center;
}

.category-util a {
  background-position: 50% 0px;
  background-image: url(../assets/img/cart.png);
  background-repeat: no-repeat;
  background-size: 40px;
  display: block;
  padding-top: 50px;
  font-size: 12px;
}

.category-content {
  background-color: white;
  padding-top: 10px;
  height: 1000px;
}

.category-title {
  margin: 1rem;
}

.category-content li {
  cursor: pointer;
  padding: 1rem;
}

.category-content li:hover {
  color: white;
  background-color: #3385f096;
}

.category-content span {
  float: right;
}

.fade-in {
  opacity: 0.5;
}

.fade-out {
  opacity: 0;
}

.content {
  margin: 0 auto;
  max-width: 1240px;
  padding-left: 40px;
  padding-right: 40px;
}

.card-wrap {
  font-size: 0;
}

.card-panel {
  display: inline-block;
  box-sizing: border-box;
  width: 24%;
  vertical-align: top;
  padding: 10px;
  margin: 1% 0 0 1%;
  font-size: 13px;
  line-height: 1.4;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.16), 0 1px 1px rgba(0, 0, 0, 0.23);
  cursor: default;
}

.card-panel:hover {
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

@media screen and (max-width: 1240px) {
  .card-panel {
    width: 45%;
  }
}

@media screen and (max-width: 1000px) {
  .main-logo > img {
    background-size: 150px;
    width: 150px;
  }

  .user-search {
    display: block;
    width: 40px !important;
    height: 35px !important;
    background-size: 35px !important;
  }

  .main-logo {
    display: none;
  }

  .search-wrap {
    margin-right: 0;
  }

  .user-panel {
    margin-left: 0;
  }
}

/* 모바일 */
@media screen and (max-width: 768px) {
  header {
    padding-left: 5px;
    padding-right: 5px;
  }

  .user-panel > span {
    margin-left: 0;
  }

  .user-like {
    display: none;
  }

  .serach-category {
    width: 30px;
  }

  .search-text {
    width: 100px;
  }

  .card-panel {
    width: 90%;
  }
}
</style>
