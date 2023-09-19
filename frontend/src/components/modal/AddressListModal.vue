<template>
  <!-- 주소 모달 시작 -->
  <Transition name="fade">
    <div
        class="addr-modal"
        v-show="isOpenModal == true"
    >
      <div class="addr-content">
        <div class="addr-close" @click="closeModal">&#10005;</div>
        <div class="addr-header">
          <span>배송지 목록</span>
        </div>
        <div class="addr-body">
          <div class="addr-contents">
            <table class="table text-center">
              <thead class="table-light text-secondary">
              <tr>
                <th>선택</th>
                <th>대표</th>
                <th>배송지명</th>
                <th>받는분</th>
                <th>주소</th>
                <th>연락처</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item) in this.addressList" :key="item">
                <td>
                  <input type="radio"
                         :checked="item.addr_default == 'Y'"
                         v-model="address"
                         :value="item"/>
                </td>
                <td>
                  <span v-if="item.addr_default == 'Y'">&#10003;</span>
                </td>
                <td>{{ item.addr_name }}</td>
                <td>{{ item.name }}</td>
                <td>{{ `(${item.post}) ${item.addr} ${item.addr_detail}` }}</td>
                <td>{{ item.phone }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="addr-footer">
          <button class="address-access" @click="accessAddress">가져오기</button>
        </div>
      </div>
    </div>
  </Transition>
  <!-- 리뷰 모달 끝 -->
</template>

<script>
import {mapMutations, mapState} from "vuex";
import axios from "axios";

export default {
  name: "AddressListModal",
  data() {
    return {
      addressList: [],
      address:{},
    };
  },

  watch: {
    isOpenModal(value) {
      if (value) {
        this.getAddress();
        document.body.style = `overflow: hidden`;
      } else {
        document.body.style = `overflow: auto`;
      }
    },

  },

  computed: {
    ...mapState(['isHeaderFixed'])
  },

  methods: {
    ...mapMutations(['setFixed']),

    getAddress() {
      axios.get('/api/account/address').then(({data}) => {
        this.addressList = data;
        this.address = this.addressList.find((item)=>item.addr_default == 'Y');
      })
    },

    closeModal() {
      this.$emit("closeModal");
    },

    accessAddress(){
      this.$emit("getAddress", this.address);
      this.closeModal();
    },
  },

  props: {
    isOpenModal: Boolean,
  }
  ,
}
</script>

<style>
.addr-modal {
  display: block;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999;
}

.addr-content {
  position: absolute;
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
  top: 10%;
  left: 30%;
  width: 720px;
  padding: 1rem 1.5rem;
}

.addr-close {
  text-align: right;
  font-size: 24px;
  font-weight: 500;
  cursor: pointer;
}

.addr-header {
  text-align: center;
  font-size: 20px;
  font-weight: 700;
  border-bottom: 1px solid #cfcdcd;
  padding: 1rem 0;
}

.addr-body {
  margin: 1rem 0;
  width: 100%;
}

.addr-contents {
  width: 100%;
}

table {
  font-size: 13px;
}

.address-access {
  display: block;
  margin: auto;
  width: 25%;
  background: #3385f096;
  color: white;
  font-weight: bolder;
  padding: 15px 5px;
  border: none;
  border-radius: 3px;
}

.addr-footer {
  border-top: 1px solid #cfcdcd;
  padding: 1rem 0;
}


</style>
