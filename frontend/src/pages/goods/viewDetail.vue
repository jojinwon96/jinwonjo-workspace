<template>
  <div class="goods-detail-wrap">
    <ul class="tab-list" :class="{ tabFixed: isFixed }">
      <a
          class="tab-button"
          v-for="item in list"
          :key="item.id"
          :class="{ tabActive: item.active }"
          @click="moveStage(item.id)"
      >
        <li>{{ item.label }}</li>
      </a>
    </ul>
    <div ref="tab1" class="tab-content">
      <div class="review-top-wrap">
        <ul>
          <li>
            <select v-model="info.rate">
              <option value="" selected>평점전체</option>
              <option value="1">★</option>
              <option value="2">★★</option>
              <option value="3">★★★</option>
              <option value="4">★★★★</option>
              <option value="5">★★★★★</option>
            </select>
          </li>
          <li>
            <input type="text" placeholder="제목,상품명,작성자" v-model="info.content" @keyup.enter="this.postReviewInquiry"/>
            <button type="button" @click="this.postReviewInquiry">검색</button>
          </li>
        </ul>
      </div>
      <table class="table review-table">
        <thead class="table-light">
        <tr class="text-center">
          <th class="col-1">번호</th>
          <th class="col-5">후기</th>
          <th>평가</th>
          <th>작성자</th>
          <th>등록일</th>
        </tr>
        </thead>
        <tbody>
        <tr class="text-center align-middle" v-for="item in this.reviews" :key="item">
          <td class="col-1">{{ item.review_id }}</td>
          <td class="col-5" @click="reviewDetail(item)">
            <div class="review-img-wrap">
              <img v-if="item.img != null" :src="require(`@/assets/product/uploadfile/${item.img}`)" alt=""/>
              <ul>
                <li>상품 : {{ item.product_name }}</li>
                <li class="fw-bold">제목 : {{ item.title }}</li>
                <li>
                  {{ item.content }}
                </li>
              </ul>
            </div>
          </td>
          <td>
              <span v-for="index in item.rate" :key="index">
                ★
              </span>
          </td>
          <td>{{ item.cust_id }}</td>
          <td>{{ item.date }}</td>
        </tr>
        </tbody>
      </table>
      <div v-if="isReviewWrite == ''">
        <button class="review-btn" @click="openModal('input')">
          리뷰작성하기
        </button>
      </div>
      <div v-else>
        <button v-if="isReviewWrite == 'N'" class="review-btn" @click="openModal('modify')">
          리뷰수정하기
        </button>
      </div>
      <!-- 리뷰 상세보기 -->
      <ReviewDetailModal
          :isOpenReviewDetail="isOpenReviewDetail"
          :reviewDetailInfo="this.reviewDetailInfo"
          @closeReviewDetail="this.closeReviewDetail"/>

      <!-- 리뷰 모달 -->
      <ReviewModal
          :isOpenReview="isOpenReview"
          :target="this.target"
          :reviewKey="this.reviewKey"
          @closeModal="closeModal()"
      ></ReviewModal>

      <div style="clear: both"></div>
      <!--    리뷰페이징    -->
      <ReviewPagination :reviewPagination="this.reviewPagination"
                  :onReviewPageChange="this.onReviewPageChange"
                  :reviewPages="this.reviewPages"/>
    </div>
    <div ref="tab2" class="tab-content">
      <div class="review-top-wrap">
        <ul>
          <li>
          </li>
          <li>
            <input type="text" placeholder="제목,상품명,작성자" v-model="info.inquiry_content" @keyup.enter="this.postReviewInquiry"/>
            <button type="button" @click="this.postReviewInquiry">검색</button>
          </li>
        </ul>
      </div>
      <table class="table review-table">
        <thead class="table-light">
        <tr class="text-center">
          <th class="col-1">번호</th>
          <th>답변</th>
          <th class="col-5">문의</th>
          <th>작성자</th>
          <th>등록일</th>
        </tr>
        </thead>
        <tbody>
        <tr class="text-center align-middle" v-for="item in inquiryList" :key="item" @click="inquiryDetail(item)">
          <td class="col-1">{{ item.inquiry_id }}</td>
          <td v-if="item.answer == null">-</td>
          <td v-else>답변완료</td>
          <td class="col-5">
            <div class="review-img-wrap">
              <ul>
                <li>상품 : {{ item.product_name }}</li>
                <li class="fw-bold">제목 : {{ item.title }}</li>
                <li v-if="item.status == 'Y'">비밀글입니다.</li>
              </ul>
            </div>
          </td>
          <td>{{ item.cust_id }}</td>
          <td>{{ item.date }}</td>
        </tr>
        </tbody>
      </table>
      <button class="review-btn" @click="openInquiryModal">문의작성하기</button>

      <!--   문의 상세 모달   -->
      <inquiryDetailModal
          :isOpenInquiryDetail="isOpenInquiryDetail"
          :inquiryDetailInfo="this.inquiryDetailInfo"
          @closeInquiryDetail="this.closeInquiryDetail"/>

      <!--  문의모달    -->
      <InquiryModal :isOpenInquiry="isOpenInquiry" @closeInquiryModal="closeInquiryModal" :product_id="reviewKey.product_id"/>
      <div style="clear: both"></div>
      <!--    문의페이징    -->
      <inquiryPagination :inquiryPagination="this.inquiryPagination"
                        :onInquiryPageChange="this.onInquiryPageChange"
                        :inquiryPages="this.inquiryPages"/>
    </div>
  </div>
</template>

<script>
import ReviewModal from "../../components/modal/ReviewModal.vue";
import InquiryModal from "@/components/modal/InquiryModal";
import ReviewPagination from "@/components/common/reviewPagination";
import inquiryPagination from "@/components/common/inquiryPagination";
import ReviewDetailModal from "@/components/modal/reviewDetailModal";
import inquiryDetailModal from "@/components/modal/inquiryDetailModal";
import {mapState} from "vuex";

export default {
  name: "viewDetail",
  data(){
    return{
      isStaging: false,
      target:'',
      isFixed: false,
      list: [
        {id: 1, label: "상품후기", content: "콘텐츠1", active: true},
        {id: 2, label: "상품문의", content: "콘텐츠2", active: false},
      ],

      isOpenReview: false,
      reviewPagination: {},
      reviewPages: [],
      reviews:[],

      isOpenReviewDetail:false,
      reviewDetailInfo:{},

      info:{
        product_id:'',
        rate:'',
        title:'',
        content:'',
        page: 1,
        range: 1,

        inquiry_content:'',
        inquiry_page: 1,
        inquiry_range: 1,
      },

      isOpenInquiry:false,
      inquiryPagination: {},
      inquiryPages: [],
      inquiryList:[],

      isOpenInquiryDetail:false,
      inquiryDetailInfo:{},
    }
  },

  props:{
    reviewKey:Object,
    isReviewWrite:String,
  },

  computed:{
    ...mapState(['account'])
  },

  components:{
    ReviewPagination,
    inquiryPagination,
    ReviewModal,
    InquiryModal,
    ReviewDetailModal,
    inquiryDetailModal,
  },

  mounted() {
    document.addEventListener("scroll", this.scroll);
    this.postReviewInquiry();
  },

  beforeUnmount() {
    document.addEventListener("scroll", this.scroll);
  },

  watch:{
    reviewKey: function (value){
      this.info.product_id = value.product_id;
      this.postReviewInquiry();
    },
  },

  methods:{
    postReviewInquiry(){
      this.info.product_id = this.reviewKey.product_id;
      this.axios.post("/api/goods/reviewInquiry", this.info).then(({data})=>{

        this.reviews = data.reviews
        this.reviewPagination = data.reviewPagination;
        this.inquiryList = data.inquiryList;
        this.inquiryPagination = data.inquiryPagination;

        this.reviewPages = [];
        for (let i = this.reviewPagination.startPage; i <= this.reviewPagination.endPage; i++) {
          this.reviewPages.push(i);
        }

        this.inquiryPages = [];
        for (let i = this.inquiryPagination.startPage; i <= this.inquiryPagination.endPage; i++) {
          this.inquiryPages.push(i);
        }
      })
    },

    moveStage(id) {
      let location =
          document.querySelectorAll(".tab-content")[id - 1].offsetTop - 200;
      window.scrollTo({top: location, behavior: "smooth"});
    },

    scroll() {
      const currentScroll = window.scrollY,
          location1 = this.$refs.tab1.offsetTop - 200,
          location2 = this.$refs.tab2.offsetTop - 200;

      if (currentScroll > 767) {
        this.isFixed = true;
      } else {
        this.isFixed = false;
      }

      if (!this.isStaging) {
        if (currentScroll > location1 && currentScroll < location2) {
          this.list[0].active = true;
          this.list[1].active = false;
        } else if (currentScroll >= location2) {
          this.list[0].active = false;
          this.list[1].active = true;
        }
      }

    },

    openModal(target){
      if (this.account.id == undefined){
        alert('로그인이 필요한 기능입니다.');
        return;
      }

      this.axios.post("/api/order/check", this.reviewKey).then(({data})=>{
        if (data > 0){
          this.isOpenReview = true;
          this.target = target;
        } else {
          alert('상품 구매 이력이 없습니다.')
          return;
        }
      })
    },

    closeModal(){
      this.postReviewInquiry();
      this.$emit("closeModal");
      this.isOpenReview = !this.isOpenReview;
    },

    onReviewPageChange(target, num) {
      let range = this.pageInfo.range,
          rangeSize = this.pagination.rangeSize;

      if (target == 'next') {
        this.pageInfo.page = (range * rangeSize) + 1;
        this.pageInfo.range = range + 1;
      } else if (target == 'prev') {
        this.pageInfo.page = ((range - 2) * rangeSize) + 1;
        this.pageInfo.range = range - 1;
      } else {
        if (num > 0) {
          this.pageInfo.page = parseInt(num);
        }
      }

      this.postReviewInquiry();
    },

    openInquiryModal(){
      this.isOpenInquiry = true;
    },

    closeInquiryModal(){
      this.postReviewInquiry();
      this.$emit("closeModal");
      this.isOpenInquiry = !this.isOpenInquiry;
    },

    onInquiryPageChange(target, num) {
      let range = this.info.inquiry_range,
          rangeSize = this.inquiryPagination.rangeSize;

      if (target == 'next') {
        this.info.inquiry_page = (range * rangeSize) + 1;
        this.info.inquiry_range = range + 1;
      } else if (target == 'prev') {
        this.info.inquiry_page = ((range - 2) * rangeSize) + 1;
        this.info.inquiry_range = range - 1;
      } else {
        if (num > 0) {
          this.info.inquiry_page = parseInt(num);
        }
      }

      this.postReviewInquiry();
    },

    reviewDetail(item){
      this.reviewDetailInfo = item;
      this.isOpenReviewDetail = true;
    },

    closeReviewDetail(){
      this.isOpenReviewDetail = !this.isOpenReviewDetail;
    },

    inquiryDetail(item){
      if (item.status == 'Y'){
        if (item.cust_id == this.account.id){
          this.inquiryDetailInfo = item;
          this.isOpenInquiryDetail = true;
        } else{
          alert('비밀글입니다.');
        }
      } else {
        this.inquiryDetailInfo = item;
        this.isOpenInquiryDetail = true;
      }
    },

    closeInquiryDetail(){
      this.isOpenInquiryDetail = !this.isOpenInquiryDetail;
    },
  },
}
</script>

<style scoped>
.goods-detail-wrap {
  margin: 0 auto;
  width: 1080px;
}

.tabFixed {
  position: sticky;
  top: 95px;
}

.tab-list {
  margin: 5rem 0;
  display: flex;
  justify-content: center;
  list-style-type: none;
  border-bottom: 1px solid #ccc;
  background-color: white;
}

.tab-list::after {
  content: "";
  display: block;
  clear: both;
}

.tab-button {
  padding: 10px 30px;
  margin-right: -1px;
  margin-bottom: -1px;
  color: grey;
  text-decoration: none;
  cursor: pointer;
}

.tabActive {
  border-top: 2px solid #3385f096;
  border-right: 1px solid #ccc;
  border-bottom: 1px solid white;
  border-left: 1px solid #ccc;
  color: black;
}

.tab-content {
  margin-bottom: 20rem;
}

.tab-content select {
  border: 1px solid #ccc;
  font-size: 14px;
  height: 32px;
  padding-left: 5px;
  border-radius: 2px;
}

.tab-content input {
  margin-right: 2px;
  padding: 0 9px;
  width: 180px;
  height: 32px;
  font-size: 13px;
  line-height: 32px;
  border: 1px #ccc solid;
  border-radius: 2px;
  outline: 0;
}

.tab-content button {
  height: 32px;
  font-size: 13px;
  min-width: 60px;
  border: 1px #ccc solid;
  background: #fff;
  line-height: 26px;
  border-radius: 2px;
}

.review-top-wrap > ul {
  margin-top: 10rem;
  display: flex;
  justify-content: space-between;
}

.review-table > thead {
  font-size: 14px;
}

.review-table tbody {
  font-size: 12px;
}

.review-table tr{
  cursor: pointer;
}

.review-img-wrap {
  text-align: left;
  display: flex;
}

.review-img-wrap img {
  width: 4.5rem;
  height: 4rem;
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

.review-btn {
  float: right;
  background: #3385f096 !important;
  border: none !important;
  color: white;
  height: 2.3rem !important;
  padding: 0.3rem;
  outline: none;
}
</style>