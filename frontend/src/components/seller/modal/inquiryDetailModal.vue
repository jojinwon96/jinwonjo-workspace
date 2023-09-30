<template>
  <!-- 리뷰 모달 시작 -->
  <Transition name="fade">
    <div
        class="modify-modal"
        v-if="isOpenInquiry == true"
    >
      <div class="review-content">
        <div class="review-close" @click="modalControl">&#10005;</div>
        <div class="review-header">
          <span>문의정보</span>
        </div>
        <div class="review-body">
          <ul>
            <li class="review-write">
              <span class="review-title">상품명</span>
              <textarea rows="1" :value="inquiry.product_name" readonly></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">작성자</span>
              <textarea rows="1" :value="inquiry.cust_id" readonly></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">제목</span>
              <textarea rows="1" :value="inquiry.title" readonly></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">내용</span>
              <textarea rows="5" :value="inquiry.content" readonly></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">답변</span>
              <textarea rows="5" v-model="info.answer"></textarea>
            </li>
            <li>
              <button class="review-detail-button" type="button" :disabled="inquiry.answer != null" @click="accessAnswer">전송</button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </Transition>
  <!-- 리뷰 모달 끝 -->
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: "inquiryDetailModal",
  data() {
    return {
      info:{
        inquiry_id:'',
        answer:'',
      }
    };
  },

  watch: {
    isOpenInquiry(value) {
      if (value) {
        document.body.style = `overflow: hidden`;
      } else {
        document.body.style = `overflow: auto`;
      }
    },
  },


  methods: {
    ...mapMutations(['setFixed']),

    modalControl() {
      this.$emit('closeModal');
    },

    accessAnswer(){
      this.info.inquiry_id = this.inquiry.inquiry_id;
      console.log(this.info)
      this.axios.post("/api/seller/modify-inquiry", this.info).then(({data})=>{
        if (data > 0){
          alert('성공적으로 답변을 작성하였습니다.');
          this.modalControl();
        }
      })
    },

  },

  props: {
    isOpenInquiry: Boolean,
    inquiry: Object,
  },


};
</script>

<style>
.modify-modal {
  display: block;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  z-index: 999;
}

.review-content {
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);

  margin: 2rem auto;
  width: 720px;
  padding: 3rem 1.5rem;
}


.review-close {
  text-align: right;
  font-size: 24px;
  font-weight: 500;
  cursor: pointer;
}

.review-header {
  text-align: center;
  font-size: 20px;
  font-weight: 700;
}

.review-body li {
  margin: 2rem 0rem;
}

.review-img {
  display: flex;
}

.review-img img {
  width: 80px;
  height: 80px;
  border-radius: 4px;
}

.review-img span {
  margin-left: 1rem;
  font-size: 16px;
  font-weight: 600;
}

.review-title {
  align-items: center;
  font-size: 15px;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.review-write {
  display: flex;
  flex-direction: column;
}

.review-write textarea {
  padding: 0.5rem;
  font-size: 15px;
  resize: none;
  outline: none;
  border: 1px solid #ccc;
}

.review-file {
  display: flex;
  flex-direction: column;
  position: relative;
}

.input-file-button {
  padding: 6px 25px;
  border: 1px solid #3385f096;
  border-radius: 4px;
  color: #3385f096;
  cursor: pointer;
  text-align: center;
}

.star-rating {
  display: flex;
}

.star {
  font-size: 42px;
  color: #ccc;
  font-weight: bolder;
  -webkit-text-stroke-width: 1.5px;
  -webkit-text-stroke-color: #2b2a29;
}

.fill-star {
  color: gold;
}

.review-upload {
  background-color: #eaebef;
  padding: 1rem;
  border-radius: 4px;
  margin-bottom: 1rem;
}

.review-upload img {
  display: block;
  width: 300px;
  height: 300px;
  margin: 0 auto;
}


.review-detail-button {
  width: 100%;
  padding: 1rem 0;
  background: #3385f096 !important;
  font-size: 18px !important;
  border: 1px solid #ccc;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  vertical-align: middle;
}

</style>

