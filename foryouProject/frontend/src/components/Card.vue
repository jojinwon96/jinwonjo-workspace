<template>
    <div class="card-img">
      <span class="best">BEST</span>
      <span class="like-btn-panel fade">
                            <img src="../assets/img/like.png" class="like-btn">
                        </span>
    </div>
    <div class="product-title">개업 화분 배달 식당 사무실 전국 꽃배달 서비스 개업식 축하화분</div>
    <div class="price-panel">
      <span class="discount">10%</span>
      <span class="price">15,000</span>원~
      <span class="origin-price">16,500원</span>
    </div>
    <span class="star">
                        ★★★★★
                        <span>★★★★★</span>
                        <input type="range" oninput="drawStar(this)" value="1" step="1" min="0" max="10">
                    </span>
    <span class="review-count">(13)</span>
</template>

<script>
export default {
  name: "Card"
}
</script>

<style scoped>

</style>