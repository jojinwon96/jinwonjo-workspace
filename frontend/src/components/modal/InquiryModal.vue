<template>
  <!-- 리뷰 모달 시작 -->
  <Transition name="fade">
    <div
        class="modify-modal3"
        v-if="isOpenInquiry == true"
        data-modal-id="1"
    >
      <div class="review-content3">
        <div class="review-close" @click="modalControl">&#10005;</div>
        <div class="review-header">
          <span>문의</span>
        </div>
        <div class="review-body">
          <ul>
            <li class="review-write">
              <span class="review-title">공개여부</span>
              <div class="radio-wrap">
                <input id="show" type="radio" value="N" v-model="info.status">
                <label for="show">공개</label>

                <input id="hidden" type="radio" value="Y" v-model="info.status">
                <label for="hidden">비공개</label>
              </div>
            </li>
            <li class="review-write">
              <span class="review-title">제목</span>
              <textarea rows="1" v-model="info.title"></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">문의 작성</span>
              <textarea rows="5" v-model="info.content"></textarea>
            </li>
            <li>
              <button class="review-button" type="button" @click="accessInquiry">
                전송
              </button>
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
  name: "Inquiry",
  data() {
    return {
      info: {
        product_id:'',
        title: '',
        content: '',
        status:'N',
      },
    };
  },


  watch: {

  },


  methods: {
    ...mapMutations(['setFixed']),

    modalControl() {
      if (this.isOpenInquiry) {
        this.$emit("closeInquiryModal");
        let resetInfo = {
          product_id:'',
          title: '',
          content: '',
          status:'Y',
        }
        this.info = resetInfo;
      }
    },

    accessInquiry(){
      this.info.product_id = this.product_id;
      this.axios.post("/api/goods/input-inquiry", this.info).then(({data})=>{
        if (data > 0){
          this.modalControl();
        }
      })
    },

  },

  props: {
    isOpenInquiry: Boolean,
    product_id:String,
  },


};
</script>

<style>
.modify-modal3 {
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

.review-content3 {
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

.radio-wrap{
  display: flex;
}

.radio-wrap input{
  margin: 0;
}

.radio-wrap label{
  margin-right: 12px;
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
