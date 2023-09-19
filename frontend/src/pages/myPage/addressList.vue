<template>
  <h4 class="myPage-content-title">배송주소록</h4>
  <div class="myPage-content">
    <div class="myPage-head">
      <button class="add-address" type="button" @click="modalControl('input')">등록</button>
    </div>
    <div class="address-body">
      <table class="table text-center">
        <thead class="table-light text-secondary">
          <tr>
            <th>대표</th>
            <th>배송지명</th>
            <th>받는분</th>
            <th>주소</th>
            <th>연락처</th>
            <th>수정/삭제</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in addressList" :key="item">
            <td>
              <span v-if="item.addr_default == 'Y'">&#10003;</span>
            </td>
            <td>{{ item.addr_name }}</td>
            <td>{{ item.name }}</td>
            <td>{{ `(${item.post}) ${item.addr} ${item.addr_detail}` }}</td>
            <td>{{ item.phone }}</td>
            <td>
              <button class="addr-modify-btn" type="button" @click="modifyAddress(item, 'modify')">수정</button>
              <button class="addr-modify-btn" type="button" @click="deleteAddress(item)">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <addressModal
      :isOpenModal="isOpenModal"
      :modifyAddress="this.address"
      :target="this.target"
      @closeModal="modalControl"
  ></addressModal>
</template>

<script>
import addressModal from "@/components/myPage/addressModal";
import {mapMutations} from "vuex";
import axios from "axios";
export default {
  name: "addressList.vue",

  data(){
    return{
      target:'',
      isOpenModal: false,
      addressList: [],
      address:{},
    }
  },

  components:{
    addressModal,
  },

  mounted() {
    this.getAddress();
  },

  methods:{
    ...mapMutations(['setFixed']),

    getAddress(){
      axios.get('/api/account/address').then(({data})=>{
        this.addressList = data;
      })
    },

    modalControl(target){
      this.target = target;
      if (this.isOpenModal){
        this.isOpenModal = false;
        document.body.style = `overflow: auto`;
        this.getAddress();
      } else {
        this.isOpenModal = true;
        document.body.style = `overflow: hidden`;
      }
    },

    modifyAddress(value, target){
      this.isOpenModal = true;
      this.address = value;
      this.target = target;
    },

    deleteAddress(item){
      if (item.addr_default == 'Y'){
        alert('대표 배송지는 삭제할 수 없습니다.');
        return;
      }
      const axiosConfig = {
        headers: {
          "Content-Type": "application/json"
        }
      }
      axios.post('/api/account/delete-address', JSON.stringify(item), axiosConfig).then(({data})=>{
        if (data > 0){
          alert('성공적으로 삭제하였습니다.');
          this.getAddress();
        }
      })
    },
  },
}
</script>

<style scoped>
.myPage-head {
  padding-bottom: 10px;
  margin: 1rem 0;
}

.add-address{
  border: 1px solid #f4f4f4;
  background: #3385f096;
  padding: 5px 10px;
  color: white;
  font-weight: bolder;
  border-radius: 3px;
}

.add-address:hover{
  border: 1px solid #aaa9a9;
}

thead, tbody{
  font-size: 12px;
}

.addr-modify-btn{
  border: none;
  background: #3385f096;
  margin: 0 3px;
  padding: 5px 10px;
  color: white;
  font-weight: bolder;
  border-radius: 5px;
}

</style>