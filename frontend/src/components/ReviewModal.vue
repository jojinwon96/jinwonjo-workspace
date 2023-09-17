<template>
  <!-- 리뷰 모달 시작 -->
  <Transition name="fade">
    <div
      class="modify-modal"
      v-show="isOpenReview == true"
      data-modal-id="1"
      @click="closeModal($event)"
    >
      <div class="review-content">
        <div class="review-close" @click="modalControl">&#10005;</div>
        <div class="review-header">
          <span>리뷰쓰기</span>
        </div>
        <div class="review-body">
          <ul>
            <li class="review-img">
              <img src="../assets/img/bouquet1.jpg" alt="" />
              <span>지금 이 순간</span>
            </li>
            <li>
              <span class="review-title">별점</span>
              <div class="inner">
                <div class="star-rating">
                  <div
                    class="star"
                    v-for="index in 5"
                    :key="index"
                    @click="check(index)"
                  >
                    <span class="star" v-if="index >= score">★</span>
                    <span class="star fill-star" v-if="index < score">★</span>
                  </div>
                </div>
              </div>
            </li>
            <li class="review-file">
              <button
                class="file-delete"
                v-if="isUpload == true"
                @click="clearImage"
              >
                삭제
              </button>
              <div class="review-upload" v-if="isUpload == true">
                <img :src="`${imgUrl}`" alt="" />
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
              <span class="review-title">리뷰 작성</span>
              <textarea name="" id=""></textarea>
            </li>
            <li>
              <button class="review-button" type="button">전송</button>
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
    return { score: 0, imgUrl: "", isUpload: false };
  },


  watch: {
    isOpenReview(value) {
      if (value) {
        document.body.classList.add("oh");
      } else {
        this.$emit("closeModal");
        document.body.classList.remove("oh");
        this.isUpload = false;
        this.setFixed(true);
      }
    },
  },

  computed: {
  },

  mounted() {
    document.addEventListener("keyup", this.keyControl);
  },

  beforeUnmount() {
    document.removeEventListener("keyup", this.keyControl);
  },
  methods: {
    ...mapMutations(['setFixed']),

    clearImage() {
      this.isUpload = false;
      // this.$nextTick(() => {
      //   this.isUpload = true;
      // });
    },

    onInputImage(e) {
      let file = e.target.files;
      let url = URL.createObjectURL(file[0]);
      this.imgUrl = url;

      if (this.imgUrl != null) {
        this.isUpload = true;
      }
    },

    check(index) {
      this.score = index + 1;
    },

    keyControl(e) {
      if (e.keyCode === 27) {
        this.$emit("closeModal");
        document.body.classList.remove("oh");
        this.clearImage();
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
  },

  props: {
    isOpenReview: Boolean,
  },
};
</script>

<style>
.modify-modal {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  z-index: 70;
}

.review-content {
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);

  margin: 2rem auto;
  width: 720px;
  padding: 1rem 1.5rem;
}

.oh {
  overflow: hidden;
  padding-right: 17px;
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
  width: 100px;
  height: 100px;
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
  height: 110px;
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
  border-radius: 4px;
  margin-bottom: 1rem;
}

.review-upload img {
  display: block;
  width: 200px;
  margin: 0 auto;
  height: 100%;
}

.file-delete {
  position: absolute;
  top: 10px;
  right: 10px;
  border: none !important;
  background: #3385f096 !important;
  color: white;
}

.review-button {
  width: 100%;
  padding-top: 1rem;
  padding-bottom: 2.5rem;
  background: #3385f096 !important;
  font-size: 18px !important;
  border: 1px solid #ccc;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  vertical-align: middle;
}



</style>
