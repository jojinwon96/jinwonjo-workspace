<template>
  <router-link
      :to="{name: 'view', params: {product_id:`${product.product_id}`}}">
    <div v-show="product != null">
      <div @mouseover="isHover=true"
           @mouseleave="isHover=false"
           v-if="product.uploadFile != 'Y'" class="card-img-wrap"
           :style="{ backgroundImage: `url(${product.img1})`}">
      <span @click="onChangeLike" class="like-btn-panel" :class="{'like-show' : isHover == true}">
        <img v-if="product.like_id == null || product.like_id == 0" src="@/assets/img/like.png" class="like-btn"/>
        <img v-else src="@/assets/img/like-red.png" class="like-btn"/>
      </span>
      </div>
      <div @mouseover="isHover=true"
           @mouseleave="isHover=false"
           v-else class="card-img-wrap"
           :style="{'background-image': 'url(' + require('@/assets/product/uploadfile/' + product.img1) + ')'}">
      <span @click="onChangeLike" class="like-btn-panel" :class="{'like-show' : isHover == true}">
        <img v-if="product.like_id == null || product.like_id == 0" src="@/assets/img/like.png" class="like-btn"/>
        <img v-else src="@/assets/img/like-red.png" class="like-btn"/>
      </span>
      </div>
      <div class="product-title">
        {{ product.product_name }}
      </div>
      <div class="price-panel">
        <div v-if="product.discount == 0">
          <span class="price">{{ comma(product.price) }}</span><span class="card-won">원</span>
        </div>
        <div v-else>
          <span class="discount" v-if="product.discount != 0">{{ product.discount }}%</span>
          <span class="price">{{ comma(product.price) }}</span><span class="card-won">원</span>
          <span class="origin-price">{{ comma(product.option_price) }}원</span>
        </div>
      </div>
      <star-rating :rating="(3.5)"
                   :read-only="true"
                   :increment="0.01"
                   :show-rating="false"
                   :inline="true"
                   v-bind:star-size="20"/>
      <span>(3.5)</span>
    </div>
  </router-link>
</template>

<script>
import StarRating from 'vue-star-rating'
import {mapState} from "vuex";
import axios from "axios";

export default {
  name: "Card",

  data() {
    return {
      isHover: false,
    }
  },

  computed: {
    ...mapState(['account'])
  },

  methods: {
    comma(val) {
      if (val == 0) {
        return 0;
      } else {
        return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      }
    },

    onChangeLike() {
      let info = {
        cust_id: this.account.id,
        product_id: this.product.product_id,
        like_id: 0,
        idx: this.idx
      }

      let url = '',
          msg = '';

      if (this.product.like_id == 0 || this.product.like_id == null) {
        url = '/api/account/like';
        msg = '[' + this.product.product_name + '] 상품을 찜';
      } else {
        url = '/api/account/delete-like';
        msg = '[' + this.product.product_name + '] 상품 찜 취소';
        info.like_id = this.product.like_id;
      }


      axios.post(url, info).then(({data}) => {
        console.log(msg);
        console.log(data);
        // alert(msg);
        info.like_id = data;
        this.$emit('setLikeId', info);
      })
    },
  },

  components: {
    StarRating
  },

  props: {
    product: Object,
    idx: Number,
  }
};
</script>

<style scoped>
.card-img-wrap {
  width: 100%;
  height: 200px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  transition: all 0.1s linear;
}

.card-img-wrap:hover {
  transform: scale(1.02);
}

.product-title {
  color: #333333;
  font-size: 14px;
  line-height: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.discount {
  font-size: 20px;
  color: red;
  margin-right: 5px;
}

.price {
  color: #3385f096;
  font-size: 18px;
  font-weight: bold;
}

.card-won {
  font-weight: bolder;
}

.origin-price {
  color: rgb(169, 168, 168);
  font-size: 15px;
  text-decoration: line-through;
}

.star {
  font-size: 14px;
  color: white;
}

.star input {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  opacity: 0;
  cursor: pointer;
}

.star span {
  width: 0;
  position: absolute;
  left: 0;
  color: rgb(148, 148, 0);
  overflow: hidden;
  pointer-events: none;
}

.review-count {
  font-size: 12px;
}

.best {
  display: block;
  background: red;
  color: white;
  text-align: center;
  font-size: 15px;
  font-weight: bold;
  width: 50px;
  height: 30px;
  line-height: 30px;
}

.like-btn-panel {
  display: block;
  position: relative;
  background-color: rgb(255, 255, 255);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  top: 120px;
  margin: auto;
  cursor: pointer;
  opacity: 0;
  transition: 0.5s;
  z-index: 999;
}

.like-btn {
  display: block;
  position: relative;
  top: 10px;
  left: 10px;
  width: 30px;
  height: 30px;
}

.like-show {
  opacity: 1;
}

@media screen and (max-width: 1000px) {
  .card-img {
    /* width: 80%; */
  }
}

@media screen and (max-width: 768px) {
  .card-img {
  }
}
</style>
