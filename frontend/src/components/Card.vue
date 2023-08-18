<template>
  <div class="card-img">
    <span class="best">BEST</span>
    <span class="like-btn-panel fade">
      <img src="../assets/img/like.png" class="like-btn" />
    </span>
  </div>
  <div class="product-title">
    개업 화분 배달 식당 사무실 전국 꽃배달 서비스 개업식 축하화분
  </div>
  <div class="price-panel">
    <span class="discount">10%</span>
    <span class="price">15,000</span>원~
    <span class="origin-price">16,500원</span>
  </div>
  <span class="star">
    ★★★★★
    <span>★★★★★</span>
    <input
      type="range"
      oninput="drawStar(this)"
      value="1"
      step="1"
      min="0"
      max="10"
    />
  </span>
  <span class="review-count">(13)</span>
</template>

<script>
export default {
  name: "Card",
};
</script>

<style scoped>
.card-img {
  height: 200px;
  vertical-align: middle;
  background-image: url("../assets/img/bouquet1.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  transition: all 0.1s linear;
}

.card-img:hover {
  transform: scale(1.02);
}

.product-title {
  font-size: 12px;
  font-weight: bolder !important;
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
  font-size: 20px;
  font-weight: bold;
}

.origin-price {
  color: rgb(169, 168, 168);
  font-size: 15px;
  text-decoration: line-through;
}

.star {
  position: relative;
  font-size: 20px;
  color: #ddd;
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
  position: absolute;
  background-color: rgb(255, 255, 255);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  top: 170px;
  left: 0;
  right: 0;
  margin: auto;
  cursor: pointer;
  opacity: 0;
  transition: 0.2s;
}

.like-btn {
  position: absolute;
  width: 30px;
  height: 30px;
  top: 10px;
  left: 10px;
}

.fade-in {
  opacity: 1;
}

.fade-out {
  opacity: 0;
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
