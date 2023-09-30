<template>
  <header>
    <div class="s-header-wrap">
      <div class="s-header-content">
        <div class="s-header-left">
          <img class="s-logo" src="../../assets/img/logo-removebg.png">
          <span class="s-header-title">판매자 센터</span>
        </div>
        <div class="s-header-right">
          <a>
            <router-link to="/seller" class="s-header-tab">홈</router-link>
          </a>
          <a>
            <router-link :to="{path: '/productManage'}" class="s-header-tab">상품관리</router-link>
          </a>
          <a>
            <router-link :to="{path: '/orderManage/order'}" class="s-header-tab">주문관리</router-link>
          </a>
          <a>
            <router-link :to="{path: '/reviewInquiryManage/review'}" class="s-header-tab">후기/문의</router-link>
          </a>
          <div class="dropdown">
            <a class="btn btn-secondary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
               aria-expanded="false">
            </a>
            <ul class="dropdown-menu">
              <li><span class="dropdown-item" @click="logOut()">로그아웃</span></li>
            </ul>
          </div>
        </div>
      </div>
      <div class="dr"></div>
    </div>
  </header>
</template>

<script>
import {mapMutations, mapState} from "vuex";
import axios from "axios";
import router from "@/router";

export default {
  name: "sellerHeader",

  computed: {
    ...mapState(['account']),
  },

  methods: {
    logOut(){
        const logOutUser = {
          seller_id:'',
          seller_name:'',
          seller_number:'',
          seller_tel:'',
        }
        axios.get("/api/account/logout").then(({status}) => {
          if (status == 200) {
            alert("성공적으로 로그아웃 되었습니다.");
            this.setAccount(logOutUser);
            router.replace({name: "home"});
          }
        })
    },

    ...mapMutations(['setAccount']),
  },
}
</script>

<style scoped>
header {
  background: #575757;
  height: 5rem;
  position: fixed;
  top: 0;
  border-bottom: 1px #d0d0d0 solid;
  z-index: 60;
}

.s-header-wrap {
  margin: 0 auto;
  width: 1000px;
}

.s-header-content {
  display: flex;
  justify-content: space-between;
  color: white;
  padding: 1rem;
}

.s-logo {
  width: 8rem;
}

.s-header-title {
  font-size: 24px;
  margin-left: 5px;
  font-weight: 700;
}

.s-header-left {
  display: flex;
}

.s-header-right {
  display: flex;
  align-items: center;
}

.s-header-tab {
  color: white;
  font-size: 18px;
  font-weight: 600;
  margin: 0 1rem;
  cursor: pointer;
}

.s-tab-menu {
}
</style>