<template>
  <div class="paging-navigation" v-if="this.reviewPages.length > 0">
    <a @click="onReviewPageChange('prev')" v-if="this.reviewPagination.prev == true">◀</a>
    <a @click="onReviewPageChange('num', item)" v-for="(item,index) in this.reviewPages" :key="index"
       :class="{'page-active' : this.reviewPagination.page == item}">{{ item }}</a>
    <a @click="onReviewPageChange('next')" v-if="this.reviewPagination.next == true">▶</a>
  </div>
</template>

<script>
export default {
  name: "ReviewPagination",
  data(){
    return{
    }
  },
  props: ['reviewPagination', 'onReviewPageChange', 'reviewPages'],
}
</script>

<style scoped>

</style>