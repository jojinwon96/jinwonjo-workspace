<template>
  <div class="paging-navigation">
    <a @click="onPageChange('prev')" v-if="this.pagination.prev == true">◀</a>
    <a @click="onPageChange('num', item)" v-for="(item,index) in this.pages" :key="index"
       :class="{'page-active' : this.pagination.page == item}">{{ item }}</a>
    <a @click="onPageChange('next')" v-if="this.pagination.next == true">▶</a>
  </div>
</template>

<script>
export default {
  name: "Pagination",
  data(){
    return{
    }
  },
  props: ['pagination', 'onPageChange', 'pages'],
}
</script>

<style scoped>

</style>