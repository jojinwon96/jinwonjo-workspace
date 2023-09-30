<template>
  <!-- 리뷰 모달 시작 -->
  <Transition name="fade">
    <div
        class="modify-modal2"
        v-if="isOpenReview == true"
        data-modal-id="1"
        @click="closeModal($event)"
    >
      <div class="review-content2">
        <div class="review-close" @click="modalControl">&#10005;</div>
        <div class="review-header">
          <span v-if="target=='input'">리뷰작성</span>
          <span v-else>리뷰수정</span>
        </div>
        <div class="review-body">
          <ul>
            <li class="review-write">
              <span class="review-title">상품명</span>
              <textarea rows="1" :value="reviewKey.product_name" readonly></textarea>
            </li>
            <li>
              <span class="review-title">별점</span>
              <div class="inner">
                <star-rating :show-rating="false" v-model:rating="this.info.rate"></star-rating>
              </div>
            </li>
            <li class="review-file">
              <button
                  class="file-delete2"
                  v-if="isUpload == true"
                  @click="clearImage"
              >
                삭제
              </button>
              <div class="review-upload" v-if="isUpload == true">
                <img :src="imgUrl" alt=""/>
              </div>
              <span class="review-title">사진 첨부 (선택)</span>
              <label className="input-file-button" for="input-file">
                사진첨부
              </label>
              <input
                  @change="onInputImage"
                  type="file"
                  id="input-file"
                  style="display: none"
              />
            </li>
            <li class="review-write">
              <span class="review-title">제목</span>
              <textarea rows="1" v-model="info.title"></textarea>
            </li>
            <li class="review-write">
              <span class="review-title">리뷰 작성</span>
              <textarea rows="5" v-model="info.content"></textarea>
            </li>
            <li>
              <button v-if="this.target=='input'" class="review-button" type="button" @click="accessReview('input')">
                전송
              </button>
              <button v-else class="review-button" type="button" @click="accessReview('modify')">수정</button>
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
  name: "reviewModal",
  data() {
    return {
      imgUrl: "",
      isUpload: false,
      info: {
        cust_id: this.reviewKey.cust_id,
        product_id: this.reviewKey.product_id,
        title: '',
        content: '',
        rate: 0,
      },
      imgFile: '',
    };
  },


  watch: {
    isOpenReview(value) {
      console.log(this.reviewKey);
      if (value) {
        document.body.style = `overflow: hidden`;
      } else {
        document.body.style = `overflow: auto`;
        this.info = {};
        this.imgFile = '';
      }

      if (this.target == 'modify') {
        this.info.product_id = this.reviewKey.product_id;
        this.axios.post("/api/goods/find-review", this.info).then(({data}) => {
          this.info.title = data.title;
          this.info.content = data.content;
          this.info.rate = data.rate;
          if (data.img != null){
            this.imgUrl = require(`@/assets/product/uploadfile/${data.img}`);
            this.isUpload = true;
          }
        })
      }
    },
  },


  methods: {
    ...mapMutations(['setFixed']),

    clearImage() {
      this.isUpload = false;
      this.imgFile = '';
      this.imgUrl = '';
    },

    onInputImage(e) {
      this.imgFile = e.target.files[0];

      let file = e.target.files;
      let url = URL.createObjectURL(file[0]);
      this.imgUrl = url;

      if (this.imgUrl != null) {
        this.isUpload = true;
      }
    },

    modalControl() {
      if (this.isOpenReview) {
        this.$emit("closeModal");
        this.clearImage();
      }
    },

    closeModal(e) {
      let modalId = e.target.dataset.modalId;
      if (modalId == 1) {
        this.$emit("closeModal");
        this.clearImage();
      }
    },

    accessReview(target) {
      let url = ''

      if (target == 'input') {
        url = '/api/goods/input-review'
      } else {
        url = '/api/goods/modify-review'
      }

      const formData = new FormData();

      formData.append("file", this.imgFile);
      formData.append("review", JSON.stringify(this.info));

      this.axios.post(url, formData, {
        headers: {'Content-Type': 'multipart/form-data'}
      }).then(({data}) => {
        if (data > 0) {
          if (target == 'input') {
            alert('성공적으로 리뷰를 작성하였습니다.');
          } else {
            alert('성공적으로 리뷰를 수정하였습니다.');
          }

          this.modalControl();
        }
      })

    },
  },

  props: {
    isOpenReview: Boolean,
    reviewKey: Object,
    target: String,
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

.review-img {
  display: flex;
}


.review-img img {
  width: 80px;
  height: 80px;
  border-radius: 4px;
}

.review-img span {
  margin: 0 !important;
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
