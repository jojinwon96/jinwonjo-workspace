<template>
  <div class="myPage-menu-panel">
    <router-link to="/myPage/home">
      <h4 class="sub-title">마이홈</h4>
    </router-link>
    <h5 class="menu-title">쇼핑</h5>
    <ul class="sub-menu">
      <router-link to="/myPage/orderList">
        <li>주문내역</li>
      </router-link>
      <router-link to="/myPage/orderRefund">
        <li>취소/환불</li>
      </router-link>
      <router-link to="/myPage/wishList">
        <li>위시리스트</li>
      </router-link>
      <router-link to="/cart">
        <li>장바구니</li>
      </router-link>
      <router-link to="/myPage/addressList">
        <li>배송주소록</li>
      </router-link>
    </ul>
    <h5 class="menu-title">활동</h5>
    <ul class="sub-menu">
      <router-link to="/myPage/inquiryList">
        <li>상품문의</li>
      </router-link>
      <router-link to="/myPage/reviewList">
        <li>상품후기</li>
      </router-link>
    </ul>
<!--    <h5 class="menu-title">내 정보</h5>-->
<!--    <ul class="sub-menu">-->
<!--      <router-link to="/myPage/modifyCustomer">-->
<!--        <li>회원정보수정</li>-->
<!--      </router-link>-->
<!--      <li>회원탈퇴</li>-->
<!--    </ul>-->
  </div>
</template>

<script>
export default {
  name: "myPageMenu"
}
</script>

<style scoped>
.myPage-menu-panel {
  float: left;
  width: 17%;
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

.sub-menu li {
  font-size: 14px;
  margin-bottom: 0.7rem;
  color: #575757;
  cursor: pointer;
}

.sub-menu li:hover {
  color: #3385f096;
}
</style>