<template>
  <!-- 리뷰 모달 시작 -->
  <Transition name="fade">
    <div
        class="modify-modal2"
        v-if="isOpenInquiryDetail == true"
        data-modal-id="1"
        @click="closeModal($event)"
    >
      <div class="review-content2">
        <div class="review-close" @click="modalControl">&#10005;</div>
        <div class="review-header">
          <span>문의정보</span>
        </div>
        <div class="review-body">
          <ul>
            <li class="review-write">
              <span class="review-title">상품명</span>
              <textarea rows="1" :value="inquiryDetailInfo.product_name" readonly></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">작성자</span>
              <textarea rows="1" :value="inquiryDetailInfo.cust_id" readonly></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">제목</span>
              <textarea rows="1" :value="inquiryDetailInfo.title" readonly></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">내용</span>
              <textarea rows="5" :value="inquiryDetailInfo.content" readonly></textarea>
            </li>
            <li class="review-write" v-if="inquiryDetailInfo.answer != null">
              <span class="review-title">답변</span>
              <textarea rows="5" :value="inquiryDetailInfo.answer" readonly></textarea>
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

    };
  },


  watch: {
    isOpenInquiryDetail(value) {
      if (value) {
        document.body.style = `overflow: hidden`;
      } else {
        document.body.style = `overflow: auto`;
        this.imgFile = '';
      }

    },
  },


  methods: {
    ...mapMutations(['setFixed']),

    modalControl() {
      if (this.isOpenInquiryDetail) {
        this.$emit("closeInquiryDetail");
      }
    },

    closeModal(e) {
      let modalId = e.target.dataset.modalId;
      if (modalId == 1) {
        this.$emit("closeInquiryDetail");
      }
    },
  },

  props: {
    isOpenInquiryDetail: Boolean,
    inquiryDetailInfo: Object,
  },


};
</script>

<style>
.modify-modal2 {
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

.review-content2 {
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

.review-detail-name {
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
  background: #eeeeee;
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

.file-delete2 {
  position: absolute;
  padding: 5px 12px;
  top: 10px;
  right: 10px;
  border: none !important;
  background: #3385f096 !important;
  color: white;
}

.review-button {
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
