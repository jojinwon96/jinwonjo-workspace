import {createWebHistory, createRouter} from "vue-router";

import home from "./pages/home.vue";
import signUp from "./pages/signUp.vue";
import signIn from "./pages/signIn.vue";
import cart from "./pages/cart.vue";
import order from "./pages/order.vue";
import category from "./pages/goods/category.vue";
import view from "./pages/goods/view.vue";
import searchView from "@/pages/goods/searchView";

//판패자
import sellerHome from "./pages/seller/home.vue";
import productManage from "./pages/seller/prodcutManage.vue"
import orderManage from "@/pages/seller/orderManage";
import refundManage from "@/pages/seller/refundManage";
import reviewManage from "@/pages/seller/reviewManage";
import inquiryManage from "@/pages/seller/inquiryManage";

// 마이페이지
import myPage from "./pages/myPage/myPage.vue";
import myPageHome from "./pages/myPage/home";
import wishList from "@/pages/myPage/wishList";
import addressList from "@/pages/myPage/addressList";
import orderList from "@/pages/myPage/orderList";
import orderRefund from "@/pages/myPage/orderRefund";
import reviewList from "@/pages/myPage/reviewList";
import inquiryList from "@/pages/myPage/inquiryList";
import modifyCustomer from "@/pages/myPage/modifyCustomer";

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
            path: "/search/:category_id/:content",
            component: searchView,
            name: 'search',
            props: true,
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
                {
                    path: "orderList",
                    component: orderList,
                    name: 'orderList',
                },
                {
                    path: "orderRefund",
                    component: orderRefund,
                    name: 'orderRefund',
                },
                {
                    path: "reviewList",
                    component: reviewList,
                    name: 'reviewList',
                },
                {
                    path: "inquiryList",
                    component: inquiryList,
                    name: 'inquiryList',
                },
                {
                    path: "modifyCustomer",
                    component: modifyCustomer,
                    name: 'modifyCustomer',
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
                console.log(store.state.account);
                if (store.state.account.id != undefined) {
                    return next();
                }
                alert('로그인이 필요한 서비스입니다.');
                next("/signIn");
            },
            name: 'cart',
        },
        {
            path: "/order",
            component: order,
            beforeEnter: (to, from, next) => {
                let orderList = JSON.parse(localStorage.getItem('orderList'));
                if (orderList.length > 0) {
                    return next();
                }
                alert('잘못된 접근 입니다.');
                next("/");
            }
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
                {
                    path: "/orderManage",
                    children:[
                        {
                            path: "order",
                            component: orderManage,
                            name: 'orderManage',
                        },
                        {
                            path: "refund",
                            component: refundManage,
                            name: 'refund',
                        }
                    ]
                },
                {
                    path: "/reviewInquiryManage",
                    children:[
                        {
                            path: "review",
                            component: reviewManage,
                            name: 'reviewManage',
                        },
                        {
                            path: "inquiry",
                            component: inquiryManage,
                            name: 'inquiryMange',
                        }
                    ],
                }
            ]
        },
    ]
});

export default router;
