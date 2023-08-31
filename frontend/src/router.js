import {createWebHistory, createRouter} from "vue-router";

import home from "./pages/home.vue";
import signUp from "./pages/signUp.vue";
import signIn from "./pages/signIn.vue";
import myPage from "./pages/myPage.vue";
import cart from "./pages/cart.vue";
import order from "./pages/order.vue";
import category from "./pages/goods/category.vue";
import view from "./pages/goods/view.vue";

import sellerHome from "./pages/seller/home.vue";
import productManage from "./pages/seller/prodcutManage.vue"

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            component: home,
            name: home
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
        {
            path:"/",
            children:[
                {
                    path:"seller",
                    component: sellerHome,
                    name: sellerHome,
                },
                {
                    path:"productManage",
                    component: productManage,
                    name: productManage,
                },
            ]
        },
    ]
});


export default router;
