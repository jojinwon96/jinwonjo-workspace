<template>
  <!-- 주소 모달 시작 -->
  <Transition name="fade">
    <div
        class="modify-modal"
        v-show="isOpenModal == true"
    >
      <div class="review-content">
        <div class="review-close" @click="modalControl">&#10005;</div>
        <div class="review-header">
          <span v-if="target=='input'">배송지 등록</span>
          <span v-else>배송지 수정</span>
        </div>
        <div class="review-body">
          <div class="addr-content-wrap">
            <ul>
              <li class="addr-td-title">대표설정</li>
              <li>
                <input v-if="modifyAddress.addr_default == 'Y'" type="checkbox" v-model="addrMain" disabled>
                <input v-else type="checkbox" v-model="addrMain">
              </li>
            </ul>
            <ul>
              <li class="addr-td-title">배송지명</li>
              <li class="addr-td-content">
                <input type="text" v-model="addressName">
                <div class="addr-error">{{ addressNameError }}</div>
              </li>
            </ul>
            <ul>
              <li class="addr-td-title">받는사람</li>
              <li class="addr-td-content">
                <input type="text" v-model="name">
                <div class="addr-error">{{ nameError }}</div>
              </li>
            </ul>
            <ul>
              <li class="addr-td-title">연락처</li>
              <li class="addr-td-phone">
                <input class="addr-phone" type="text" v-model="phone[0]">
                <span class="input-separator">-</span>
                <input class="addr-phone" type="text" v-model="phone[1]">
                <span class="input-separator">-</span>
                <input class="addr-phone" type="text" v-model="phone[2]">
                <div class="addr-error">{{ phoneError }}</div>
              </li>
            </ul>
            <ul>
              <li class="addr-td-title">주소</li>
              <li class="addr-td">
                <button class="find-post" type="button" @click="findAddress">주소찾기</button>
                <input class="addr-post addr-readonly" readonly type="text" v-model="address[0]"/>
              </li>
            </ul>
            <ul>
              <li class="addr-td-title"></li>
              <li class="addr-address">
                <input class="addr-readonly" readonly type="text" v-model="address[1]">
              </li>
            </ul>
            <ul>
              <li class="addr-td-title"></li>
              <li class="addr-address">
                <input type="text" v-model="address[2]">
                <div class="addr-error">{{ addressError }}</div>
              </li>
            </ul>
          </div>
        </div>
        <div class="addr-footer">
          <button v-if="this.target == 'input'" class="address-access" @click="accessAddress">등록하기</button>
          <button v-else class="address-access" @click="accessAddress">수정하기</button>
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
  name: "addressModal",
  data() {
    return {
      addrMain: false,
      addressName: '',
      name: '',
      phone: [],
      address: ['', '', ''],
      addressNameError: '',
      nameError: '',
      phoneError: '',
      addressError: '',
    };
  },

  watch: {
    isOpenModal(value) {
      if (value) {
        document.body.style = `overflow: hidden`;
      } else {
        document.body.style = `overflow: auto`;
      }
    },

    target(value) {
      if (value == 'input') {
        this.addrMain = false;
        this.addressName = '';
        this.name = '';
        this.phone = [];
        this.address = ['', '', ''];
      } else {
        if (this.modifyAddress.addr_default == 'Y') {
          this.addrMain = true;
        } else {
          this.addrMain = false;
        }
        this.addressName = this.modifyAddress.addr_name;
        this.name = this.modifyAddress.name;
        this.phone = this.modifyAddress.phone.split("-");
        this.address = [this.modifyAddress.post, this.modifyAddress.addr, this.modifyAddress.addr_detail];
      }
      console.log(value);
      this.addressNameError = '';
      this.nameError = '';
      this.phoneError = '';
      this.addressError = '';
    },

  },

  computed: {
    ...mapState(['isHeaderFixed'])
  },

  methods: {
    ...mapMutations(['setFixed']),

    resetAddress() {
      this.$emit("closeModal");
      this.addrMain = false;
      this.addressName = '';
      this.name = '';
      this.phone = [];
      this.address = ['', '', ''];
      this.addressNameError = '';
      this.nameError = '';
      this.phoneError = '';
      this.addressError = '';
    },

    modalControl() {
      if (this.isOpenModal) {
        this.resetAddress();
      }
    },

    closeModal() {
      this.$emit("closeModal");
    },

    findAddress() {
      console.log(window.screen.height);
      let width = 800,
          height = 600;
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.address[0] = data.zonecode;
          this.address[1] = data.address;
        },
        width: width,//생성자에 크기 값을 명시적으로 지정해야 합니다.
        height: height,
      }).open({
        left: 2450,
        top: 200
      });
    },

    addressNamePass() {
      if (this.addressName == '') {
        this.addressNameError = '필수 입력 항목입니다.';
        return false;
      }

      this.addressNameError = '';
      return true;
    },

    namePass() {
      if (this.name == '') {
        this.nameError = '필수 입력 항목입니다.';
        return false;
      }

      this.nameError = '';
      return true;
    },

    phonePass() {
      if (this.phone[0] == '' || this.phone[1] == '' || this.phone[2] == '') {
        this.phoneError = '필수 입력 항목입니다.';
        return false;
      }

      this.phoneError = '';
      return true;
    },

    addressPass() {

      if (this.address[0] == '' || this.address[1] == '' || this.address[2] == '') {
        this.addressError = '필수 입력 항목입니다.';
        return false;
      }

      this.addressError = '';
      return true;
    },

    accessAddress() {
      this.addressNamePass();
      this.namePass();
      this.phonePass();
      this.addressPass();

      if (this.addressNamePass() && this.namePass() && this.phonePass() && this.addressPass()) {
        const axiosConfig = {
          headers: {
            "Content-Type": "application/json"
          }
        }
        let info = {
          address_id: '',
          addr_name: this.addressName,
          name: this.name,
          post: this.address[0],
          addr: this.address[1],
          addr_detail: this.address[2],
          phone: this.phone[0] + '-' + this.phone[1] + '-' + this.phone[2],
          addr_default: this.addrMain ? 'Y' : 'N'
        }
        let url = '';

        if (this.target == 'input') {
          url = '/api/account/input-address';
        } else {
          url = '/api/account/modify-address';
          info.address_id = this.modifyAddress.address_id;
        }

        axios.post(url, JSON.stringify(info), axiosConfig)
            .then(({data}) => {
              if (data > 0) {
                if (this.target == 'input') {
                  alert('성공적으로 등록하였습니다.');
                } else {
                  alert('성공적으로 수정하였습니다.');
                }
                this.resetAddress();
              }
            })
      }
    },

  },


  props: {
    isOpenModal: Boolean,
    modifyAddress: Object,
    target: String,
  }
  ,
}
</script>

<style>
.modify-modal {
  display: block;
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 999;
}

.review-content {
  position: relative;
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
  top: 10%;
  width: 720px;
  padding: 1rem 1.5rem;
}

.review-close {
  text-align: right;
  font-size: 24px;
  font-weight: 500;
  cursor: pointer;
}

.review-header {
  text-align: center;
  font-size: 20px;
  font-weight: 700;
  border-bottom: 1px solid #cfcdcd;
  padding: 1rem 0;
}

.review-body {
  margin: 1rem 0;
  width: 100%;
}

.addr-content-wrap {
  display: table;
  border-spacing: 10px;
}

.addr-content-wrap ul {
  display: table-row;
  font-size: 14px;
  width: 100%;
}

.addr-content-wrap li {
  display: table-cell;
}

.addr-main input {
  padding: 10px 0;
}

.addr-td-title {
  vertical-align: middle;
  width: 10%;
}

.addr-td-content input {
  width: 50%;
  border: 1px solid #bbbbbb;
  padding: 8px 5px;
  border-radius: 3px;
  outline: none;
}

.addr-td-phone {
  display: flex;
}

.addr-phone {
  width: 15%;
  border: none;
  outline: none;
  border: 1px solid #bbbbbb;
  padding: 8px 5px;
  border-radius: 3px;
}

.input-separator {
  display: inline-block;
  width: 2.5%;
  line-height: 38px;
  text-align: center;
}

.find-post {
  width: 15%;
  color: white;
  border: 1px solid #f4f4f4;
  background: #3385f096;
  padding: 10px 0;
}

.addr-post {
  width: 33%;
  background: #cfcdcd;
  margin-left: 2.5%;
  border: none;
  outline: none;
  padding: 10px 8px;
  border-radius: 3px;
}

.addr-readonly {
  background: #cfcdcd;
}

.addr-address input {
  width: 100%;
  outline: none;
  border: none;
  border: 1px solid #bbbbbb;
  padding: 10px 5px;
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

.addr-error {
  color: red;
}

</style>
