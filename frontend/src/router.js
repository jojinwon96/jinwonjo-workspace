import { createWebHistory, createRouter } from "vue-router";
import home from "./pages/home.vue";
import signUp from "./pages/signUp.vue";
import signIn from "./pages/signIn.vue";
import myPage from "./pages/myPage.vue";
import cart from "./pages/cart.vue";
import order from "./pages/order.vue";
import category from "./pages/goods/category.vue";
import view from "./pages/goods/view.vue";

const routes = [
  {
    path: "/",
    component: home,
  },
  {
    path: "/myPage",
    component: myPage,
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
    path: "/cart",
    component: cart,
  },
  {
    path: "/order",
    component: order,
  },
  {
    path: "/goods",
    children: [
      {
        path: "category",
        component: category,
        name: category,
      },
      {
        path: "view",
        component: view,
        name: view,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
