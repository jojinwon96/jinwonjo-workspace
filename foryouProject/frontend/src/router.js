import { createWebHistory, createRouter } from "vue-router";
import signUp from "./pages/signUp.vue";
import signIn from "./pages/signIn.vue";
import home from "./pages/home.vue";
import goods from "./pages/goods.vue";

const routes = [
  {
    path: "/home",
    component: home,
  },
  {
    path: "/signUp",
    component: signUp,
  },
  {
    path: "/signIn",
    component: signIn,
  },
  {
    path: "/goods",
    component: goods,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
