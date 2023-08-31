import {createStore} from 'vuex'

const store = createStore({
    state() {
        return {
            signUpState: {
                isChose: true,
                isCust: false,
                isSeller: false,
            },

            account: {
                id: '',
                name: '',
                email: '',
                tel: '',
                mileage: 0,
                seller_id:'',
                seller_name:'',
                seller_number:'',
                seller_tel:'',
            },

            product: {
                category_id: '',
                product_id: '',
                product_name: '',
                img1: '',
                img2: '',
                img3: '',
                img4: '',
                img5: '',
                option_id: '',
                option_name: '',
                option_content: '',
                option_price: 0,
                status: '',
            },

            isOpenModal: false,

            seller:{
              currentPage : 0,
            },
        }
    },

    mutations: {
        setSignupState(state, payload) {
            this.state.signUpState.isChose = payload.isChose;
            this.state.signUpState.isCust = payload.isCust;
            this.state.signUpState.isSeller = payload.isSeller;
        },

        setAccount(state, payload) {
            this.state.account.id = payload.cust_id;
            this.state.account.name = payload.cust_name;
            this.state.account.email = payload.cust_email;
            this.state.account.tel = payload.cust_tel;
            this.state.account.mileage = payload.mileage;

            this.state.account.seller_id = payload.seller_id;
            this.state.account.seller_name = payload.seller_name;
            this.state.account.seller_number = payload.seller_number;
            this.state.account.seller_tel = payload.seller_tel;
        },

        setProduct(state, payload) {
            this.state.product.category_id = payload.category_id;
            this.state.product.product_id = payload.product_id;
            this.state.product.product_name = payload.product_name;
            this.state.product.img1 = payload.img1;
            this.state.product.img2 = payload.img2;
            this.state.product.img3 = payload.img3;
            this.state.product.img4 = payload.img4;
            this.state.product.img5 = payload.img5;
            this.state.product.option_id = payload.option_id;
            this.state.product.option_name = payload.option_name;
            this.state.product.option_content = payload.option_content;
            this.state.product.option_price = payload.option_price;
            this.state.product.status = payload.status;
        },

        setModal(state, payload) {
            this.state.isOpenModal = payload;
        },

        setSeller(state, payload){
          this.state.seller.currentPage = payload;
        },
    }

})

export default store