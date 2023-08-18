import { createStore } from 'vuex'

const store = createStore({
    state(){
        return{
            account:{
                id:'',
                name:'',
                email:'',
                tel:'',
                mileage:0
            }
        }
    },

    mutations: {
        setAccount(state, payload){
            this.state.account.id = payload.cust_id;
            this.state.account.name = payload.cust_name;
            this.state.account.email = payload.cust_email;
            this.state.account.tel = payload.cust_tel;
            this.state.account.mileage = payload.mileage;
        }
    }
})

export default store;