<template>
  <header :class="{'fixed' : $store.state.isHeaderFixed}">
    <Transition name="fade">
      <div class="category-bg-black" v-if="$store.state.isCategoryOpen">
        <div class="category-bg-white"></div>
      </div>
    </Transition>
    <div class="category" :class="$store.state.isCategoryOpen ? 'openCategory' : 'closeCategory'">
      <div class="category-header">
        <ul>
          <li v-if="!$store.state.account.id">
            <a @click="setCategory">
              <router-link to="/signIn" class="default-border"
              >로그인
              </router-link>
            </a>
            <a @click="[loadSignUp(), setCategory()]">
              <router-link to="/signUp" class="default-border"
              >회원가입
              </router-link>
            </a>
          </li>
          <li v-else>
            <router-link to="/myPage" class="default-border"
            >{{
                $store.state.account.name
              }}<span class="user-sub">님 안녕하세요</span></router-link
            >
            <a class="logOut" @click="[logOut(), $emit('openCategory')]">
              로그아웃
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
          <li @click="onLoadMenu('home')"><span class="util-home">홈</span></li>
          <li @click="onLoadMenu('wishList')"><span class="util-wish">찜목록</span></li>
          <li @click="onLoadMenu('cart')"><span class="util-cart">장바구니</span></li>
          <li @click="onLoadMenu('orderList')"><span class="util-order">주문조회</span></li>
          <li @click="onLoadMenu('home')"><span class="util-inquiry">문의</span></li>
        </ul>
      </div>
      <div class="category-content">
        <div class="category-title">
          <h2>카테고리</h2>
        </div>
        <ul>
          <li v-for="item in categories" :key="item">
            <router-link :to="{name: 'category', params: {category_id:`${item.category_id}`, category_name:`${item.category_name}`}}" class="default-border">
              {{ item.category_name }}<span>&#10095;</span>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
    <div></div>
    <div class="header__wrap">
      <div class="header-wrap">
        <div
            class="category-panel"
            @click="[setCategory(), $emit('openCategory')]"
        >
          <img src="../assets/img/hamburger.png"/>
        </div>

        <div class="main-logo" @click="resetSore()">
          <router-link to="/" class="default-border"
          ><img src="../assets/img/logo.png"
          /></router-link>
        </div>

        <div class="search-wrap">
          <select class="serach-category" v-model="this.info.category_id">
            <option selected value="none">전체</option>
            <option v-for="item in this.categories" :key="item" :value="item.category_id">
              {{ item.category_name }}
            </option>
          </select>
          <input type="text" class="search-text" v-model="this.info.content" @keyup.enter="this.searchGoods"/>
          <button class="search-btn" @click="this.searchGoods"></button>
        </div>

        <!-- <div class="user-panel" v-if="$store.state.account.id"> -->
        <router-link to="/myPage/home" class="default-border user-panel">
          <span class="user-myPage"></span>
        </router-link>
        <router-link to="/cart" class="default-border user-panel">
          <span class="user-cart"></span>
        </router-link>
        <router-link to="/myPage/wishList" class="default-border user-panel">
          <span class="user-like"></span>
        </router-link>
        <!-- </div> -->
      </div>
    </div>
  </header>
</template>

<script>
import store from "@/store/modules/store.js";
import router from "@/router";
import axios from "axios";
import {onMounted, ref} from "vue";

export default {
  name: "Header",
  setup() {
    let categories = ref([]);
    let info = ref({category_id:'none', content:''});

    onMounted(() => {
      axios.get('/api/seller/category').then(({data}) => {
        categories.value = data;
      });
    });

    const loadSignUp = () => {
      const state = {
        isChose: true,
        isCust: false,
        isSeller: false,
      };

      store.commit("setSignupState", state);
    };

    const logOut = () => {
      const logOutUser = {
        id: "",
        name: "",
        email: "",
        tel: "",
        mileage: 0,
      };
      axios.get("/api/account/logout").then(({status}) => {
        if (status == 200) {
          alert('성공적으로 로그아웃 되었습니다.');
          store.commit("setAccount", logOutUser);
          sessionStorage.removeItem("loginUser");
          store.commit("setCategory", false);
          router.replace({name: "home"});
        }
      });
    };

    const searchGoods = () =>{
      if (info.value.content == ''){
        alert('검색어를 입력해주세요.');
        return;
      }
      router.replace({
        name:'search',
        params:{category_id: `${info.value.category_id}`, content: `${info.value.content}`}
      })
    }

    const setCategory = () => {

      if (!store.state.isCategoryOpen) {
        store.commit("setCategory", true);
      } else {
        store.commit("setCategory", false);
      }
    }

    const resetSore = () => {
      localStorage.removeItem('pageInfo');
    }

    const onLoadMenu = (target) =>{
      router.replace({name: target});
      store.commit("setCategory", false);
    }

    return {info, categories, loadSignUp, logOut, setCategory, resetSore, onLoadMenu, searchGoods};
  },

};
</script>

<style>
.fixed{
  position: fixed;
}

.show {
  display: block;
}

.hide {
  display: none;
}

a {
  color: black;
  cursor: pointer;
}

.active-li {
  background: #3385f096;
}

header {
  top: 0;
  left: 0;
  right: 0;
  border-bottom: 1px #d0d0d0 solid;
  background: white;
  z-index: 998;
}

.header__wrap {
  margin: 0 auto;
  width: 1060px;
}

.header-wrap {
  padding: 1rem 0;
  display: flex;
  justify-content: space-between;
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
}

.main-logo img {
  background-size: 200px;
  width: 200px;
  background-repeat: no-repeat;
  cursor: pointer;
}

.search-wrap {
  width: 500px;
  margin-right: 6%;
  font-size: 14px;
  border: 2px solid #3385f096;
  border-radius: 2px;
}

.serach-category {
  width: 20%;
  padding: 5px;
  outline: 0;
  border: none;
}

.search-text {
  outline: 0;
  border: 0;
  padding: 0px 5px;
}

.search-btn {
  background-image: url(../assets/img/search.png);
  margin: 5px 5px;
  width: 23px;
  height: 23px;
  background-size: 23px;
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

.user-panel span,
.user-like {
  /* margin-left: 20px; */
  width: 40px;
  height: 40px;
  background-size: 40px;
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
  transition: all 0.3s;
  z-index: 990;
}

.openCategory {
  transform: translateX(0);
}

.closeCategory {
  transform: translateX(-350px);
}

.default-border {
  border: none !important;
}

.category ul {
  margin: 0;
  padding: 0;
}

.category-bg-white {
  background: none;
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
  padding: 0.5rem;
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
  margin: 0 2px;
}

.user-sub {
  display: inline !important;
  font-size: 12px !important;
  font-weight: normal;
}

.logOut {
  font-size: 14px !important;
  padding: 7px !important;
  margin-left: 4rem !important;
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
  cursor: pointer;
}

.category-util span {
  background-position: 50% 0px;
  background-repeat: no-repeat;
  background-size: 40px;
  display: block;
  padding-top: 50px;
  font-size: 12px;
}

.util-home{background-image: url(https://img.icons8.com/?size=48&id=83326&format=png);}
.util-wish{background-image: url(https://img.icons8.com/?size=50&id=87&format=png);}
.util-cart{background-image: url(https://img.icons8.com/?size=48&id=0DBkCUANmgoQ&format=png);}
.util-order{background-image: url(https://img.icons8.com/?size=32&id=16408&format=png);}
.util-inquiry{background-image: url(https://img.icons8.com/?size=24&id=EunEmG8g2Dt8&format=png);}


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
}

.card-panel {
  width: calc(25% - 16px);
  margin: 16px 0 0 16px;
  padding: 10px;
  font-size: 13px;
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
