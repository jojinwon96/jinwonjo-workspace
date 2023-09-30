import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import router from "./router.js";
import store from "./store/modules/store";
import StarRating from 'vue-star-rating'

let app = createApp(App);

app.config.globalProperties.axios = axios;

app.component("StarRating", StarRating);

app.use(router).use(store).mount("#app");
