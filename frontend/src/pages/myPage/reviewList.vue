<template>
  <h4 class="myPage-content-title">상품후기</h4>
  <div class="myPage-content">
    <div class="address-body">
      <table class="table text-center">
        <thead class="table-light text-secondary">
        <tr>
          <th>번호</th>
          <th>상품명</th>
          <th>평가</th>
          <th>등록일</th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in reviewList" :key="item">
          <td>{{ item.review_id }}</td>
          <td>
            <div class="review-img-wrap">
              <img v-if="item.img != null" :src="require(`@/assets/product/uploadfile/${item.img}`)" alt=""/>
              <ul>
                <li>상품 : {{ item.product_name }}</li>
                <li class="fw-bold">제목 : {{ item.title }}</li>
              </ul>
            </div>
          </td>
          <td>
            <star-rating :rating="(item.rate)"
                         :read-only="true"
                         :show-rating="false"
                         :inline="true"
                         v-bind:star-size="15"/>
          </td>
          <td>{{ item.date }}</td>
          <td>
            <span class="review-detail-button" type="button" @click="onDetail(item)">상세</span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <!-- 리뷰 상세보기 -->
    <ReviewModal
        :isOpenReview="isOpenReview"
        :target="this.target"
        :reviewKey="this.reviewKey"
        @closeModal="closeModal()"
    ></ReviewModal>
  </div>
</template>

<script>
import ReviewModal from "../../components/modal/ReviewModal.vue";
import axios from "axios";

export default {
  name: "reviewList",

  components: {
    ReviewModal,
  },

  data() {
    return {
      reviewList: [],
      reviewKey: {},
      target: '',
      isOpenReview: false,
    }
  },

  mounted() {
    this.postReview();
  },

  methods: {
    postReview() {
      axios.get("/api/account/review").then(({data}) => {
        console.log(data);
        this.reviewList = data;
      })
    },

    onDetail(item) {
      this.target = 'modify';
      this.reviewKey = item;
      this.isOpenReview = true;
    },

    closeModal() {
      this.postReview();
      this.isOpenReview = !this.isOpenReview;
    },
  },
}
</script>

<style scoped>

.add-address:hover {
  border: 1px solid #aaa9a9;
}

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
</style>