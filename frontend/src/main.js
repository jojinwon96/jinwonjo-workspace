import { createApp } from "vue";
import App from "./App.vue";
import axios from "axios";
import router from "./router.js";
import store from "./store/modules/store";

let app = createApp(App);

app.config.globalProperties.axios = axios;

app.use(router).use(store).mount("#app");
