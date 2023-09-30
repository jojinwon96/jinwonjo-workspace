<template>
  <h4 class="myPage-content-title">상품문의</h4>
  <div class="myPage-content">
    <div class="address-body">
      <table class="table text-center">
        <thead class="table-light text-secondary">
        <tr>
          <th>번호</th>
          <th>답변</th>
          <th>상품명</th>
          <th>등록일</th>
          <th>답변일</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
          <tr v-for="item in inquiryList" :key="item">
            <td>{{item.inquiry_id}}</td>
            <td>
              <span v-if="item.answer != null">답변완료</span>
              <span v-else>미답변</span>
            </td>
            <td>{{item.product_name}}</td>
            <td>{{item.date}}</td>
            <td>{{item.answer_date}}</td>
            <td>
              <button class="inquiry-detail-button" @click="onInquiryDetail(item)">상세</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <inquiryDetailModal
      :isOpenInquiryDetail="isOpenInquiryDetail"
      :inquiryDetailInfo="this.inquiryDetailInfo"
      @closeInquiryDetail="this.closeInquiryDetail"/>
</template>

<script>
import axios from "axios";
import inquiryDetailModal from "@/components/modal/inquiryDetailModal";

export default {
  name: "inquiryList",
  data(){
    return{
      inquiryList:[],
      isOpenInquiryDetail:false,
      inquiryDetailInfo:{},
    }
  },

  components:{
    inquiryDetailModal
  },

  mounted() {
    this.postInquiry();
  },

  methods:{
    postInquiry(){
      axios.get("/api/account/inquiry").then(({data}) => {
        console.log(data);
        this.inquiryList = data;
      })
    },

    closeInquiryDetail(){
      this.isOpenInquiryDetail = !this.isOpenInquiryDetail;
    },

    onInquiryDetail(item){
      this.isOpenInquiryDetail = !this.isOpenInquiryDetail;
      this.inquiryDetailInfo = item;
    },
  },
}
</script>

<style scoped>
thead, tbody {
  font-size: 12px;
  vertical-align: middle;
}

.review-detail-button {
  font-size: 14px !important;
  border: none;
  background: #3385f096;
  padding: 3px 3px;
  color: white;
  font-weight: bolder;
  border-radius: 5px;
}

.review-img-wrap {
  text-align: left;
  display: flex;
}

.review-img-wrap img {
  width: 4.5rem;
  height: 4rem;
}

.review-img span {
  font-size: 16px;
  font-weight: 600;
}

.review-img-wrap ul {
  margin-left: 5px;
  font-size: 13px;
  margin-bottom: 0;
}

.review-img-wrap li {
  max-width: 350px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.inquiry-detail-button {
  font-size: 14px !important;
  border: none;
  background: #3385f096;
  padding: 5px 12px;
  color: white;
  font-weight: bolder;
  border-radius: 5px;
}
</style>