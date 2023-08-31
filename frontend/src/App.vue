<template>
  <div class="wrap">
    <router-view/>
    <div style="height: 500px"></div>
  </div>
</template>

<script>
import store from "@/store/modules/store";
import axios from "axios";

import {watch} from "vue";
import {useRoute} from "vue-router/dist/vue-router";

export default {
  name: "App",
  setup() {

    const logOutUser = {
      id: '',
      name: '',
      email: '',
      tel: '',
      mileage: 0,

      seller_id:'',
      seller_name:'',
      seller_number:'',
      seller_tel:'',
    }

    const check = () => {
        axios.get("api/account/check").then(({data}) => {
          console.log(data);
          store.commit("setAccount", data || logOutUser);
        }).catch(() => {
          console.log('서버요청 실패');
        })


    };
    const route = useRoute();

    watch(route, () => {
      check();
    })

  },

};
</script>
<style>
@import "./assets/css/normalize.css";

:root {
  scroll-behavior: auto;
}

* {
  margin: auto 0;
  padding: 0;
  box-sizing: border-box;
}

a {
  text-decoration: none;
  border: none;
  outline: none;
}

li {
  list-style: none;
}

ul {
  padding: 0;
}

.fade-enter-from {
  opacity: 0;
}

.fade-enter-active {
  transition: all 0.5s;
}

.fade-enter-to {
  opacity: 1;
}

.fade-leave-from {
  opacity: 1;
}

.fade-leave-active {
  transition: all 0.5s;
}

.fade-leave-to {
  opacity: 0;
}

/* 테플릿 */
@media screen and (max-width: 1200px) {
  .wrap {
  }
}

/* 모바일 */
@media screen and (max-width: 768px) {
  .wrap {
  }
}
</style>
