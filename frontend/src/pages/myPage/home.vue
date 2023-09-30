<template>
  <div class="myPage-main">
    <div class="profile">
      <img class="my-profile" src="../../assets/img/profile1.png"/>
      <h2>{{ $store.state.account.id }}</h2>
    </div>
    <div class="profile-content">
      <ul>
        <li style="border: none">
          <a href="#">
            <span class="sub-title2">마일리지</span>
            <span>{{this.account.mileage}}</span>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="sub-title2">위시리스트</span>
            <span>{{this.likeCount}}개</span>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="sub-title2">장바구니</span>
            <span>{{this.cartCount}}개</span>
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>

import {mapState} from "vuex";

export default {
  name: "MyPageHome",
  data(){
    return{
      mileage:0,
      likeCount:0,
      cartCount:0,
    }
  },

  computed:{
    ...mapState(['account']),
  },

  mounted() {
    this.axios.get("/api/account/myPage-info").then(({data})=>{
      console.log(data);
      this.likeCount = data.likeCount;
      this.cartCount = data.cartCount;
    })
  },


}
</script>

<style scoped>
.myPage-main {
  height: 50vh;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.16), 0 1px 5px rgba(0, 0, 0, 0.23);
  text-align: center;
}

.profile {
  display: inline-block;
  margin: 2rem;
}

.profile > h2 {
  padding: 10px 0px;
}

.profile > button {
  font-weight: bold;
  padding: 10px 25px;
  background-color: #ffffff;
  border: 1px solid #cbcbcb96;
  font-size: 13px;
  cursor: pointer;
}

.profile > button:hover {
  background-color: #ccc;
}

.my-profile {
  width: 150px;
}

.profile-content {
  font-size: 0px;
  display: block;
}

.profile-content ul {
  padding: 25px;
}

.myPage-main li {
  display: inline-block;
  font-size: 30px;
  border-left: 1px #ccc solid;
}

.myPage-main a {
  display: block;
  color: black;
}

.myPage-main span {
  display: block;
  padding: 5px 20px;
  text-align: center;
}

.sub-title2 {
  font-size: 15px;
}
</style>