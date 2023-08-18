<template>
  <custom_header />
  <custom_nav />
  <section class="content">
    <div class="myPage-wrap">
      <div class="myPage-menu-panel">
        <h3 class="sub-title"><a href="#">마이홈</a></h3>
        <h3 class="menu-title">쇼핑</h3>
        <ul class="sub-menu">
          <li>주문내역</li>
          <li>취소/환불</li>
          <li>위시리스트</li>
          <li>장바구니</li>
          <li>배송주소록</li>
        </ul>
        <h3 class="menu-title">해택</h3>
        <ul class="sub-menu">
          <li>마일리지</li>
          <li>쿠폰</li>
        </ul>
        <h3 class="menu-title">활동</h3>
        <ul class="sub-menu">
          <li>1:1문의</li>
          <li>상품문의</li>
          <li>상품후기</li>
        </ul>
        <h3 class="menu-title">내 정보</h3>
        <ul class="sub-menu">
          <li>회원정보수정</li>
          <li>회원탈퇴</li>
        </ul>
      </div>
      <div class="myPage-content">
        <div class="myPage-main">
          <div class="profile">
            <img class="my-profile" src="../assets/img/profile1.png" />
            <h2>{{ $store.state.account.id }}</h2>
            <button>설정</button>
          </div>
          <div class="profile-content">
            <ul>
              <li style="border: none">
                <a href="#">
                  <span class="sub-title2">마일리지</span>
                  <span>2,000</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="sub-title2">쿠폰</span>
                  <span>2개</span>
                </a>
              </li>
              <li>
                <a href="#">
                  <span class="sub-title2">위시리스트</span>
                  <span>0개</span>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";

export default {
  name: "myPage",
  components: {
    custom_header,
    custom_nav,
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
  margin-left: 2rem;
  margin-top: 5rem;
}

.myPage-menu-panel {
  float: left;
  width: 17%;
  padding: 0 20px 40px 0;
}

.myPage-content {
  float: left;
  width: 80%;
  padding: 50px 50px;
}

.sub-title {
  font-weight: bold;
  margin-top: 1rem;
  padding: 1rem;
  cursor: pointer;
}

.sub-title > a {
  color: black;
}

.menu-title {
  font-weight: normal;
  margin-top: 1rem;
  padding: 1rem;
  border-bottom: 1px #cfcdcd solid;
  border-radius: 2px;
}

.sub-menu {
  padding: 1rem;
}

.sub-menu > li {
  font-size: 14px;
  margin-bottom: 0.7rem;
  color: #575757;
  cursor: pointer;
}

.sub-menu > li:hover {
  color: #3385f096;
}

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
