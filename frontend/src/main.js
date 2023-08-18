import { createApp } from "vue";
import store from "@/scripts/store"
import App from "./App.vue";
import axios from "axios";
import router from "./router.js";

let app = createApp(App);

app.config.globalProperties.axios = axios;

app.use(router).use(store).mount("#app");
