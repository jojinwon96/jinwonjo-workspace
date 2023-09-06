<template>
  <Transition name="fade">
    <div class="modify-modal" v-show="isOpenModal == true">
      <div class="modal-content">
        <div class="modal-close" @click="setModal(!isOpenModal)">&#10005;</div>

        <div class="modal-body-wrap" v-if="this.products.length > 0">
          <div class="modal-body-content">
            <div class="modal-body-title">상품정보</div>
            <div class="modal-table-top">
              <div class="tr">
                <span class="modal-top-title td">상품명</span>
                <input class="modal-product-name modal-top-common td"
                       @input="onChangeOption('product_name', $event.target.value)"
                       v-model="this.products[0].product_name"/>
              </div>
              <div class="tr">
                <span class="modal-top-title td">옵션명1</span>
                <input class="modal-opt-name modal-top-common td"
                       @input="onChangeOption('option_name1', $event.target.value)"
                       v-model="this.products[0].option_name1"/>
              </div>
              <div class="tr">
                <span class="modal-top-title td">옵션명2</span>
                <input class="modal-opt-name modal-top-common td"
                       @input="onChangeOption('option_name2', $event.target.value)"
                       v-model="this.products[0].option_name2"/>
              </div>
            </div>
            <span class="modal-add-opt" @click="addProduct">옵션추가</span>
            <table class="table modal-table">
              <thead>
              <th class="col-1">대표</th>
              <th class="col-2">{{ this.products[0].option_name1 }}</th>
              <th class="col-2">{{ this.products[0].option_name2 }}</th>
              <th class="col">정상가(원)</th>
              <th class="col-1">할인율(%)</th>
              <th class="col-1">판매가(원)</th>
              <th class="col-1">재고</th>
              <th class="col-1">
                <div>최대구매수량</div>
              </th>
              <th>상태</th>
              </thead>
              <tbody v-for="(item,index) in this.products" :key="item">
              <td>
                <input type="radio"
                       :checked="item.main == 'Y'"
                       @change="onSelectMain(item)"
                       :value="index"/>
              </td>
              <td>
                <input class="modal-inp-text"
                       @input="item.option_content1 = $event.target.value"
                       v-model="item.option_content1"/>
              </td>
              <td>
                <input class="modal-inp-text"
                       @input="item.option_content2 = $event.target.value"
                       v-model="item.option_content2"/>
              </td>
              <td>
                <input class="modal-inp-num"
                       oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                       @input="priceCalculation('option_price', item, $event)"
                       v-model="item.option_price"/>
              </td>
              <td>
                <input class="modal-inp-num"
                       oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                       @input="priceCalculation('discount', item, $event)"
                       v-model="item.discount"/>
              </td>
              <td>
                <input class="modal-inp-num"
                       oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                       readonly
                       v-model="item.price"/>
              </td>
              <td>
                <input class="modal-inp-num"
                       oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                       @input="item.stock = $event.target.value"
                       v-model="item.stock">
              </td>
              <td>
                <input class="modal-inp-num"
                       oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                       @input="item.maxCount = $event.target.value"
                       v-model="item.maxCount">
              </td>
              <td>
                <span v-if="item.status == 'Y'" class="modal-opt-status modal-active-opt" @click="changeStatus(item)">판매중</span>
                <span v-else class="modal-opt-status modal-delete-opt" @click="changeStatus(item)">판매중지</span>
              </td>
              </tbody>
            </table>


            <div class="rp-img-wrap">
              <span class="rp-img-title">이미지</span>
              <div class="rp-img-content-wrap">
                <div class="rp-img-main">
                <span class="rp-img-top-title">
                  기존 대표 이미지
                </span>
                  <div class="rp-filebox">
                  </div>
                  <div class="rp-img-review" v-if="this.mainUrl != ''">
                    <input type="file" id="main-file"
                           style="display: none"
                           @change="onChangeImg($event)">
                    <label class="changePreview" for="main-file">변경</label>
                    <img class="rp-img" :src="this.mainUrl">
                  </div>
                </div>
                <div class="rp-img-sub">
                  <span class="rp-img-top-title">
                    변경 대표 이미지
                  </span>
                  <div class="rp-filebox">
                    <div class="rp-img-review" v-if="this.changeUrl != ''">
                      <span class="closePreview" @click="deleteChange">취소</span>
                      <img class="rp-img" :src="this.changeUrl">
                    </div>
                  </div>
                </div>
              </div>
              <span class="modify-access" @click="onSubmit">정보수정</span>
              <span class="modal-table-reset" @click="onReset">초기화</span>

            </div>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script>
import {mapMutations, mapState} from "vuex";
import axios from "axios";
import router from "@/router";

export default {
  name: "modifyModal",

  data() {
    return {
      form: {
        product_id: '',
      },
      orignProducts: [],
      products: [],
      addProducts: [],
      isAdd: '',

      mainFile: '',
      mainUrl: '',
      changeUrl: '',
    }
  },

  created() {
  },

  watch: {
    id: function (value) {
      this.form.product_id = value;
      this.postModifyProduct();
    },

    mainUrl: function (value) {
      this.mainUrl = value;
    }
  },

  computed: {
    ...mapState(['isOpenModal']),
  },

  methods: {
    ...mapMutations(['setModal', 'setSeller']),

    onSubmit() {
      let isChanged = false;

      // 옵션 추가 비교
      if (this.orignProducts.length !== this.products.length) {

        // 상품명, 옵션1, 옵션2 새로운 항목에 추가
        for (let i = 0; i < this.products.length; i++) {
          if (i > 0) {
            this.products[i].option_name1 = this.products[0].option_name1;
            this.products[i].option_name2 = this.products[0].option_name2;
          }
        }
        isChanged = true;
      }

      // 변경된 상품 있는지 비교
      for (let i = 0; i < this.orignProducts.length; i++) {
        let origin = JSON.stringify(this.orignProducts[i]),
            modify = JSON.stringify(this.products[i]);

        if (origin !== modify || this.changeUrl != '') {
          isChanged = true;
        }

      }


      if (!isChanged) {
        alert('수정 항목이 없습니다.');
        return false;
      }

      const formData = new FormData();

      formData.append('file', this.mainFile);
      formData.append('isAdd', this.isAdd);
      formData.append('products', new Blob([JSON.stringify(this.products)], {type: "application/json"}));

      axios.post("/api/seller/modify-products", formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      }).then(({data}) => {
        console.log(data);
        if (data > 0) {
          alert("상품을 성공적으로 수정하였습니다.");
          router.go(0);
        } else {
          alert("데이터 저장 실패");
        }
      })

    },

    changeStatus(item) {
      if (item.main == 'Y') {
        alert('대표 옵션 상태에서는 변경이 불가능 합니다');
        return false;
      } else {
        item.status == 'Y' ? item.status = 'N' : item.status = 'Y';
      }
    },

    priceCalculation(target, item, e) {
      if (target == 'discount') {
        item.discount = e.target.value;
      } else {
        item.option_price = e.target.value;
      }
      item.price = this.discount(item.option_price, item.discount);
    },

    discount(price, discount) {
      return price - (price * (discount * 0.01));
    },

    deleteChange() {
      this.changeUrl = '';
      this.mainFile = '';
    },

    onChangeImg(e) {

      this.changeUrl = URL.createObjectURL(e.target.files[0]);
      this.mainFile = e.target.files[0];
      console.log(this.mainFile);

    },

    postModifyProduct() {
      this.products = [];
      this.mainFile = '';
      this.mainUrl = '';
      this.changeUrl = '';

      axios.post("/api/seller/modify-product", this.form).then(({data}) => {
        for (let i = 0; i < data.length; i++) {
          this.orignProducts.push({...data[i]});
        }
        this.products = data;
        this.mainUrl = require(`@/assets/product/uploadfile/${this.products[0].img1}`);

        console.log(this.products);
      })
    },

    onSelectMain(item) {
      this.products.forEach((data) => {
        data.main = '';
      })

      item.main = 'Y';
    },

    addProduct() {
      this.isAdd = 'add';

      let product = {
        product_id: this.id,
        product_name: this.products[0].product_name,
        option_name1: '',
        option_content1: '',
        option_name2: '',
        option_content2: '',
        option_price: 0,
        discount: 0,
        price: 0,
        stock: 0,
        maxCount: 0,
        main: 'N',
        status: 'Y',
        addStatus: 'Y',
        img1: this.products[0].img1,
        img2: this.products[0].img2,
        img3: this.products[0].img3,
        img4: this.products[0].img4,
        img5: this.products[0].img5,
      }
      this.products.push(product);
    },


    deleteProduct(index) {
      if (this.products.length == 1) {
        alert('옵션은 최대 1개 이상 존재해야 합니다.');
        return;
      }
      this.products.splice(index, 1);

    },

    onReset() {
      this.postModifyProduct();
    },

    onChangeOption(target, value) {
      if (target == 'product_name') {
        this.products[0].product_name = value;
      } else if (target == 'option_name1') {
        this.products[0].option_name1 = value;
      } else if (target == 'option_name2') {
        this.products[0].option_name2 = value;
      }
    },


  },

  props: {
    id: String,
    toggle: Boolean,
  }
}
</script>

<style scoped>

.modify-modal {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  z-index: 70;
}

.modal-content {
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);

  margin: 2rem auto;
  width: 1240px;
  padding: 1rem 1.5rem;
}

.modal-close {
  text-align: right;
  font-size: 24px;
  font-weight: 500;
  cursor: pointer;
}

.modal-body-wrap {
  margin: 0 auto;
  width: 80%;
}

.modal-body-title {
  border-top: 1px solid #666666;
  padding: 5px 0;
  font-size: 23px;
  font-weight: 700;
  margin: 1rem 0;
}

.modal-table-top {
  background: #eeeeee;
  margin-bottom: 1rem;
  padding: 0.8rem 0.5rem;
  display: table;
  width: 100%;
}

.modal-table-top > span {
  font-weight: bolder;
}

.modal-top-title {
  width: 10%;
}

.tr {
  display: table-row;
}

.td {
  display: table-cell;
}


.modal-add-opt {
  float: right;
  margin: 5px 5px;
  font-size: 15px;
  color: white;
  border-radius: 3px;
  padding: 5px 0.8rem;
  background: #575757;
  cursor: pointer;
}

.modal-top-common {
  border: 1px solid #bbbbbb;
  margin: 3px 10px;
  padding: 5px;
  outline: none;
}

.modal-product-name {
  width: 70%;
}

.modal-opt-name {
  width: 30%;
}

.modal-table {
  font-size: 14px;
}

.modal-table thead {
  text-align: center;
  vertical-align: middle;
  color: white;
  background: #575757;
}

.modal-table thead > th {
  padding: 0.5rem 0;
}

.modal-table td {
  border-bottom: 1px solid #cfcdcd;
  padding: 10px;
}

tbody input {
  outline: none;
  border: 1px solid #cfcdcd;
  width: 100%;
  padding: 5px;
}

.rp-img-review {
  width: 100%;
  height: 300px;
  margin-bottom: 4rem;
}

.modal-inp-text {
  text-align: center;
}

.read-only {
  background: #cfcdcd;
}

.modal-inp-num {
  text-align: right;
}

.modal-opt-status {
  width: 5rem;
  display: block;
  text-align: center;
  background: #aaa9a9;
  border-radius: 3px;
  color: white;
  padding: 5px 10px;
  cursor: pointer;
}

.modal-delete-opt {
  background: #aaa9a9;
}

.modal-active-opt {
  background: #3385f096;
}

.rp-img-wrap {
  margin-top: 10rem;
  border-top: 1px solid #cfcdcd;
  padding: 0.2rem 0;
}

.rp-img-title {
  display: block;
  font-size: 23px;
  font-weight: 700;
  margin-bottom: 1rem;
}

.rp-img-content-wrap {
  display: flex;
  justify-content: space-around;
  margin: 0.5rem 0;
  border: 1px solid #cfcdcd;
  padding: 1rem;
}

.rp-img-content-wrap > div {
  margin: 0 auto;
}

.rp-img-main {
  width: 47%;
}

.rp-img-sub {
  width: 48%;
}

.rp-img-top-title {
  display: block;
  font-weight: bolder;
  font-size: 14px;
}

.rp-img-bottom-title {
  display: block;
  color: #aaa9a9;
  font-size: 12px;
  font-weight: bolder;
}

.rp-filebox {
  margin: 1.5rem 0;
  display: flex;
}

.rp-filebox .upload-name {
  font-size: 14px;
  height: 30px;
  padding: 0 10px;
  vertical-align: middle;
  border: 1px solid #dddddd;
  color: #999999;
}

.rp-filebox label {
  font-size: 14px;
  padding: 5px 20px;
  color: #fff;
  vertical-align: middle;
  background-color: #999999;
  cursor: pointer;
  height: 30px;
  margin-left: 5px;
}

.rp-filebox input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

.rp-img-review {
  width: 100%;
  height: 300px;
  margin-bottom: 4rem;
}

.rp-img {
  width: 100%;
  height: 100%;
}

.closePreview {
  float: right;
  background: #575757;
  padding: 3px 10px;
  font-size: 12px;
  font-weight: 800;
  color: white;
  cursor: pointer;
  margin-top: 1.6rem;
  margin-bottom: 5px;
}

.changePreview {
  float: right;
  background: #575757;
  padding: 3px 10px;
  font-size: 12px;
  font-weight: 800;
  color: white;
  cursor: pointer;
  margin-bottom: 5px;
}

.modal-table-reset {
  float: right;
  font-size: 20px;
  font-weight: 700;
  color: white;
  border-radius: 3px;
  padding: 0.8rem 2rem;
  margin: 0 5px;
  background: #aaa9a9;
  cursor: pointer;
}

.modify-access {
  float: right;
  background: #3385f096;
  font-size: 20px;
  font-weight: 700;
  color: white;
  padding: 0.8rem 2rem;
  cursor: pointer;
}

.none-img {
  display: inline-block;
  border: 1px #666666 dashed;
  width: 100%;
  height: 300px;
  cursor: pointer;
}

.none-img label {
  display: block;
  position: relative;
  top: 30%;
  text-align: center;
  font-size: 60px;
  cursor: pointer;
}

</style>