<template>
  <div class="paging-navigation">
    <a @click="onInquiryPageChange('prev')" v-if="this.inquiryPagination.prev == true">◀</a>
    <a @click="onInquiryPageChange('num', item)" v-for="(item,index) in this.inquiryPages" :key="index"
       :class="{'page-active' : this.inquiryPagination.page == item}">{{ item }}</a>
    <a @click="onInquiryPageChange('next')" v-if="this.inquiryPagination.next == true">▶</a>
  </div>
</template>

<script>
export default {
  name: "inquiryPagination",
  data(){
    return{
    }
  },
  props: ['inquiryPagination', 'onInquiryPageChange', 'inquiryPages'],
}
</script>

<style scoped>

</style>