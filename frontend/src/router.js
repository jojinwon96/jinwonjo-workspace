import {createWebHistory, createRouter} from "vue-router";

import home from "./pages/home.vue";
import signUp from "./pages/signUp.vue";
import signIn from "./pages/signIn.vue";
import cart from "./pages/cart.vue";
import order from "./pages/order.vue";
import category from "./pages/goods/category.vue";
import view from "./pages/goods/view.vue";

//판패자
import sellerHome from "./pages/seller/home.vue";
import productManage from "./pages/seller/prodcutManage.vue"

// 마이페이지
import myPage from "./pages/myPage/myPage.vue";
import myPageHome from "./pages/myPage/home";
import wishList from "@/pages/myPage/wishList";
import addressList from "@/pages/myPage/addressList";

// store
import store from "@/store/modules/store";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            component: home,
            name: 'home'
        },
        {
            path: "/myPage",
            component: myPage,
            beforeEnter: (to, from, next) => {
                if (store.state.account.id != undefined) {
                    return next();
                }
                alert('로그인이 필요한 서비스입니다.');
                next("/signIn");
            },
            children: [
                {
                    path: "home",
                    component: myPageHome,
                    name: 'myPageHome',
                },
                {
                    path: "wishList",
                    component: wishList,
                    name: 'wishList',
                },
                {
                    path: "addressList",
                    component: addressList,
                    name: 'addressList',
                },
            ]
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
            beforeEnter: (to, from, next) => {
                if (store.state.account.id != undefined) {
                    return next();
                }
                alert('로그인이 필요한 서비스입니다.');
                next("/signIn");
            }
        },
        {
            path: "/order",
            component: order,
        },
        {
            path: "/goods",
            children: [
                {
                    path: "category/:category_id/:category_name",
                    component: category,
                    name: 'category',
                    props: true,
                },
                {
                    path: "view/:product_id",
                    component: view,
                    name: 'view',
                    props: true,
                },
            ],
        },
        {
            path: "/",
            children: [
                {
                    path: "seller",
                    component: sellerHome,
                    name: 'sellerHome',
                },
                {
                    path: "productManage",
                    component: productManage,
                    name: 'productManage',
                },
            ]
        },
    ]
});

export default router;
