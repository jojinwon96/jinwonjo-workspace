import { createWebHistory, createRouter } from "vue-router";
import signUp from "./pages/signUp.vue";

const routes = [
  {
    path: "/signUp",
    component: signUp,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
