<template>
  <h4 class="myPage-content-title">주문취소</h4>
  <div class="myPage-content">
    <div class="myPage-head">
      <select class="refund-select">
        <option value="0" disabled selected>선택</option>
        <option value="">전체</option>
        <option value="N">처리중</option>
        <option value="Y">처리완료</option>
      </select>
    </div>
    <div class="refund-body">

      <div class="refund-content-wrap" v-for="item in refundList" :key="item">
        <div class="refund-content-head">
          <div>
            <span>신청일 : {{ item.date }}</span>
            <span v-if="item.processing_date != null"> / </span>
            <span>처리일 : {{ item.processing_date  }}</span>
          </div>
          <div>
            <span class="refund-i-btn" v-if="item.status == 'N'">처리중</span>
            <span class="refund-y-btn" v-else>처리완료</span>
          </div>
        </div>
        <div class="refund-content">
          <div class="refund-content-left">
            <ul>
              <li>
                <img v-if="item.uploadFile != 'Y'" :src="item.img">
                <img v-else :src="require('@/assets/product/uploadfile/' + item.img)">
              </li>
              <li class="refund-column">
                <span>{{ item.product_name }}</span>
                <span v-if="item.option_name2 != ''">{{ `${item.option_content1} : ${item.option_content2}` }}</span>
                <span v-else>옵션명 : {{ item.option_content1 }}</span>
                <span>{{ comma(item.price) }}원 / {{ item.count }}개</span>
                <span class="sub-total">{{ comma(item.price * item.count) }}원</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "orderRefund",

  data() {
    return {
      refundList: [],
    }
  },

  mounted() {
    axios.get("/api/account/refund").then(({data}) => {
      console.log(data);
      this.refundList = data;
    })
  },

  methods:{
    comma(val) {
      if (val == 0) {
        return 0;
      } else {
        return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },
  },
}
</script>

<style scoped>
.myPage-head {
  padding-bottom: 10px;
  margin: 1rem 0;
  display: flex;
}

.refund-select {
  border: 1px solid #cccccc;
  outline: none;
  font-size: 14px;
  padding: 5px 0;
}

.refund-content-wrap {
  width: 100%;
  margin: 14px 0;
  padding: 12px;
  font-size: 14px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.16), 0 1px 1px rgba(0, 0, 0, 0.23);
}

.refund-content-wrap:hover {
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23);
}

.refund-content {
  display: flex;
}

.refund-content-head {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #cccccc;
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.refund-content-head span {
  font-size: 14px;
  font-weight: bolder;
}

.refund-content-left ul {
  display: flex;
}

.refund-content-left img {
  width: 100px;
  height: 100px;
}

.refund-column {
  padding: 0 5px;
  display: flex;
  flex-direction: column;
}

.sub-total {
  font-size: 14px;
  font-weight: bolder;
}

.refund-i-btn {
  border: none;
  padding: 5px 10px;
  margin: 0 5px;
  background: #aaa9a9;
  color: white;
  font-size: 14px !important;
}

.refund-x-btn {
  border: none;
  padding: 5px 10px;
  background: #3385f096;
  color: white;
  font-size: 14px !important;
  cursor: pointer;
}

.refund-y-btn {
  display: block;
  border: none;
  padding: 5px 10px;
  background: #3385f096;
  color: white;
  font-size: 14px !important;
}

</style>