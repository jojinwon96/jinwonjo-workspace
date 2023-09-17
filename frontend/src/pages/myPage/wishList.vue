<template>
  <h4 class="myPage-content-title">위시리스트</h4>
  <div class="myPage-content" v-if="checkList.length > 0 ">
    <div class="myPage-head">
      <input type="checkbox" value="all" v-model="allSelected">
      <label for="all">전체</label>
      <button class="checked-delete" @click="checkedDelete">선택삭제</button>
    </div>
    <ul class="wish-content">
      <li class="wish-product-wrap" v-for="(item) in checkList" :key="item">
        <img v-if="item.uploadFile == 'Y'" :src="require('@/assets/product/uploadfile/' + item.img1)">
        <img v-else :src="item.img1">
        <div class="wish-product-name">
          <input
              type="checkbox"
              :value="item"
              v-model="selectList"
          />
          <label> {{ item.product_name }}</label>
        </div>
        <span class="wish-product-price">{{ comma(item.price) }}원</span>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "wishList",

  data() {
    return {
      checkList: [],
      selectList: [],
      price : 0,
    };
  },

  mounted() {
    this.postLikeList();
  },

  computed: {
    allSelected: {
      //getter
      get: function () {
        return this.checkList.length == this.selectList.length;
      },
      //setter
      set: function (e) {
        this.selectList = e ? this.checkList : [];
      },
    },

  },

  methods:{
    comma(val) {
      if (val == 0) {
        return 0;
      } else {
        return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },

    postLikeList(){
      axios.get('/api/account/likeList').then(({data})=>{
        this.checkList = data;
      })
    },

    checkedDelete(){
      if (this.selectList == ''){
        alert('선택된 상품이 없습니다.');
      } else {
        axios.post("/api/account/delete-likeList", JSON.stringify(this.selectList),
            { headers: { 'Content-Type': 'application/json' } }
        ).then(({data})=>{
          if (data > 0){
            this.postLikeList();
          }
        })

      }
    },
  },
}
</script>

<style scoped>
.myPage-head {
  padding-bottom: 10px;
  padding-left: 5px;
  margin: 1rem 0;
}

.myPage-content input[type=checkbox] {
  width: 20px;
  accent-color: #3385f096;
}

.myPage-content{
  width: 100%;
}

.wish-content {
  display: flex;
  flex-wrap: wrap;
  margin: -16px 0 0 -16px;
}

.wish-product-wrap {
  width: calc(25% - 16px);
  margin: 16px 0 0 16px;
  padding: 0px 5px;
  font-size: 14px;
}

.wish-product-wrap img {
  width: 200px;
  height: 200px;
}

.wish-product-name{
  margin: 5px 0;
}

.wish-product-price{
  margin: 3px 0;
  color: #3385f096;
  font-weight: bolder;
  font-size: 13px;
}

.checked-delete {
  font-size: 14px;
  margin: 0 1rem;
  border: 1px solid #aaa9a9;
  background: #f4f4f4;
  padding: 5px;
  cursor: pointer;
}

.checked-delete:hover {
  border: 1px solid black;
}



</style>