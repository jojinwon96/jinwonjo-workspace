<template>
  <div class="s-pm-rp-wrap">
    <span class="s-pm-rp-title">기본정보</span>
    <div class="basic-wrap">
      <ul class="tr">
        <li class="td td-title">상품명</li>
        <li class="td td-content">
          <div class="td-out-line" :class="{'error-bolder': isProductNameChk == false}">
            <input class="td-input"
                   @input="productNamePass()"
                   v-model="defaultItem.product_name"
                   placeholder="상품명 입력"/>
            <span class="input-limit">0/50</span>
          </div>
          <div class="error-msg" v-if="isProductNameChk == false">상품명을 입력해주세요.</div>
        </li>
      </ul>

      <ul class="tr">
        <li class="td td-title">관리 카테고리</li>
        <li class="td td-content">
          <select @change="[changeCategory($event.target.value), categoryPass()]" v-model="defaultItem.category_id"
                  class="td-select"
                  :class="{'error-bolder': isCategoryChk == false}">
            <option selected disabled value="">선택</option>
            <option v-for="item in this.options" :key="item" :value="item.category_id">{{ item.category_name }}</option>
          </select>
          <div class="error-msg" v-if="isCategoryChk == false">카테고리를 선택해 주세요.</div>
        </li>
      </ul>

      <ul class="tr">
        <li class="td td-title">대표가</li>
        <li class="td td-content">
          <span class="td-notice">대표가 가격 정보는 옵션의 정보를 따릅니다.</span>
          <div class="td-flex">
            <div class="td-column">
              <span class="td-col-title">정상가</span>
              <input class="td-col-content"
                     :value="defaultItem.option_price + '원'" readonly/>
            </div>

            <div class="td-column">
              <span class="td-col-title">할인율</span>
              <input class="td-col-content" :value="defaultItem.discount + '%'" readonly/>
            </div>

            <div class="td-column">
              <span class="td-col-title">판매가</span>
              <input class="td-col-content" :value="defaultItem.price + '원'" readonly/>
            </div>
          </div>
        </li>
      </ul>
    </div>

    <div class="rp-option-wrap" :class="{'error-bolder': isOptionChk == false}">
      <div class="rp-option-title-wrap">
        <span class="rp-option-title">필수옵션</span>
        <div class="rp-option-title-sub">
          <span v-if="optionItems.length < 1">등록된 옵션 없음</span>
          <span v-else>등록된 옵션 <span class="rp-option-active">{{ optionItems.length }}개</span></span>
          <span @click="this.isOpenOptionWrap = !this.isOpenOptionWrap"
                :class="[ this.isOpenOptionWrap == false ? 'rpOptionTitleBtn' : 'rpOptionTitleBtn2' ]">옵션 펼쳐보기<span>&#10095;</span></span>
        </div>
      </div>


      <div v-if="this.isOpenOptionWrap == true">
        <div class="rp-option-content-wrap">
          <div class="rp-option-default-wrap">
            <div class="rp-option-default">
              <span>선택 항목수 </span>
              <select @change="chagneOptionCount($event.target.value)">
                <option disabled selected>선택</option>
                <option value="1">1</option>
                <option value="2">2</option>
              </select>
              <input type="text"
                     @input="this.optionNames[0] = $event.target.value"
                     placeholder="첫번째 항목명" v-if="optionNameCount > 0"/>
              <input type="text"
                     @input="this.optionNames[1] = $event.target.value"
                     placeholder="두번째 항목명" v-if="optionNameCount > 1"/>
            </div>
          </div>

          <div v-if="optionNameCount > 0">
            <span @click="addItem" class="option-add-btn">+ 필수 옵션 추가</span>
            <span class="option-notice">* 최대 2개</span>
          </div>

          <table class="table text-center rp-table" v-if="optionItems.length > 0">
            <thead>
            <tr>
              <td class="col-1">대표</td>
              <td v-if="optionNameCount > 0">{{ optionNames[0] }}</td>
              <td v-if="optionNameCount > 1">{{ optionNames[1] }}</td>
              <td>정상가(원)</td>
              <td class="col-1">할인율(%)</td>
              <td>판매가(원)</td>
              <td class="col-1">재고</td>
              <td class="col-1">최대구매수량</td>
              <td class="col-1"></td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(item, index) in this.optionItems" :key="item">
              <td>
                <input type="radio"
                       @change="[selectMainProduct(item), optionPass()]"
                       v-model="defaultItem.main"
                       :value="index"/>
              </td>
              <td v-if="optionNameCount > 0">
                <input type="text"
                       @input="item.option_content1 = $event.target.value"/>
              </td>
              <td v-if="optionNameCount > 1">
                <input type="text"
                       @input="item.option_content2 = $event.target.value"/>
              </td>
              <td>
                <input class="rp-num" type="text"
                       @keyup="comma($event, item, 'option_price')"
                       :value="item.option_price"
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')"/>
              </td>
              <td>
                <input class="rp-num" type="text"
                       @keyup="comma($event, item, 'discount')"
                       :value="item.discount"
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')"/>
              </td>
              <td>
                <input class="rp-num" type="text"
                       :value="item.price" readonly
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')"/>
              </td>
              <td>
                <input class="rp-num"
                       @input="item.stock = $event.target.value"
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')"/>
              </td>
              <td>
                <input class="rp-num"
                       @input="item.maxCount = $event.target.value"
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '')"/>
              </td>
              <td>
                <span @click="[deleteOption(index), optionPass()]" class="option-delete">삭제</span>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div class="error-msg" v-if="isOptionChk == false">옵션을 하나 이상 선택해주세요.</div>
    <div class="rp-img-wrap">
      <span class="rp-img-title">이미지</span>
      <div class="rp-img-content-wrap" :class="{'error-bolder': isImgChk == false}">
        <div class="rp-img-main">
          <span class="rp-img-top-title">
            대표 이미지
          </span>
          <span class="rp-img-bottom-title">
            첫번째 또는 상품리스트에노출할 이미지를 선택해주세요.
          </span>
          <div class="rp-filebox">
            <input class="upload-name" placeholder="첨부파일">
            <label for="main-file">파일찾기</label>
            <input @change="[productImgInput($event,'main'), mainImgPass()]" type="file" id="main-file"
                   multiple="multiple">
          </div>
          <div class="rp-img-review" v-if="this.mainUrl != ''">
            <span @click="deletePreview('main', null)" class="closePreivew">삭제</span>
            <img class="rp-img" :src="`${this.mainUrl}`"/>
          </div>
        </div>

        <div class="rp-img-sub">
          <span class="rp-img-top-title">
            서브 이미지
          </span>
          <span class="rp-img-bottom-title">
            상품 상세페이지에서 등장하는 추가 이미지로 최대 4장 업로드 가능합니다
          </span>
          <div class="rp-filebox">
            <input class="upload-name" placeholder="첨부파일">
            <label for="sub-file">파일찾기</label>
            <input @change="productImgInput($event,'sub')" type="file" id="sub-file" multiple="multiple">
          </div>
          <div class="rp-img-review" v-for="(item,index) in this.subUrls" :key="item">
            <div class="rp-img-review" v-if="this.subUrls[index] != ''">
              <span @click="deletePreview('sub', index)" class="closePreivew">삭제</span>
              <img class="rp-img" :src="`${this.subUrls[index]}`"/>
            </div>
          </div>
        </div>
      </div>
      <div class="error-msg" v-if="isImgChk == false">대표 이미지를 선택해주세요.</div>
    </div>
    <span @click="insertSubmit" class="registration-access">등록하기</span>
  </div>
</template>

<script>
import axios from "axios";
import {mapMutations, mapState} from "vuex";

export default {
  name: "registrationProduct",
  data() {
    return {
      isProductNameChk: true,
      isCategoryChk: true,
      isOptionChk: true,
      isImgChk: true,

      isOpenOptionWrap: false,

      defaultItem: {
        category_id: '',
        product_name: '',
        option_name1: '',
        option_content1: '',
        option_name2: '',
        option_content2: '',
        option_price: 0,
        discount: 0,
        price: 0,
        stock: 0,
        maxCount: 0,
        main: '',
      },

      optionNameCount: 0,
      optionNames: ['', ''],
      optionItems: [],

      mainFile: '',
      mainUrl: '',

      subUrls: [],
      files: [],

      options:[],
    }
  },

  mounted() {
    axios.get("/api/seller/category").then(({data})=>{
      this.options = [...data];
    })
  },

  computed: {
    ...mapState(['account', 'seller']),
  },

  methods: {
    ...mapMutations(['setSeller']),

    productNamePass() {
      if (this.defaultItem.product_name == '') {
        this.isProductNameChk = false;
      } else {
        this.isProductNameChk = true;
      }

      return this.isProductNameChk;
    },

    categoryPass() {
      if (this.defaultItem.category_id == '') {
        this.isCategoryChk = false;
      } else {
        this.isCategoryChk = true;
      }

      return this.isCategoryChk;
    },

    optionPass() {
      if (this.defaultItem.option_price == '') {
        this.isOptionChk = false;
      } else {
        this.isOptionChk = true;
      }

      return this.isOptionChk;
    },

    mainImgPass() {
      if (this.mainFile == '') {
        this.isImgChk = false;
      } else {
        this.isImgChk = true;
      }

      return this.isImgChk;
    },


    insertSubmit() {
      const formData = new FormData();

      this.productNamePass();
      this.categoryPass();
      this.optionPass();
      this.mainImgPass();

      if (this.productNamePass() && this.categoryPass() && this.optionPass() && this.mainImgPass()) {
        for (const item of this.optionItems) {
          item.category_id = this.defaultItem.category_id;
          item.product_name = this.defaultItem.product_name;
          item.option_price = this.onlyNum(item.option_price);
          item.price = this.onlyNum(item.price);
          item.stock = this.onlyNum(item.stock);
          item.maxCount = this.onlyNum(item.maxCount);
        }

        this.files.unshift(this.mainFile);

        for (let i = 0 ; i < this.files.length; i++) {
          console.log(this.files[i].name);
          formData.append("files", this.files[i]);
        }

        formData.append('products', new Blob([JSON.stringify(this.optionItems)], {type: "application/json"}));


        axios.post("/api/seller/products", formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          }
        }).then(({data}) => {
          console.log(data);
          if (data > 0){
            alert("상품을 성공적으로 등록하였습니다.");
            this.setSeller(0);
          } else {
            alert("서버 로딩 실패");
          }
        })

      }
    },

    discount(price, discount) {
      return price - (price * (discount * 0.01));
    },

    onlyNum(value) {
      let regex = /[^0-9]/g;
      let result = String(value).replace(regex, "");
      return parseInt(result);
    },

    comma(event, item, target) {
      let regex = /\B(?=(\d{3})+(?!\d))/g;

      if (target == 'option_price') {
        item.option_price = (event.target.value).replace(regex, ",");
      } else if (target == 'discount') {
        item.discount = event.target.value;
        if (item.discount > 100) {
          item.discount = 100;
        }
      }

      if (item.option_price == '') {
        item.price = 0;
      } else {
        let result = String(this.discount(this.onlyNum(item.option_price), item.discount));
        item.price = result.replace(regex, ",");
      }

      this.checkMain(item);
    },

    changeCategory(e) {
      this.defaultItem.category_id = e;
      console.log(e);
    },

    chagneOptionCount(e) {
      this.optionNameCount = e;
      if (e < 2) {
        this.optionNames[1] = '';
      }
    },

    addItem() {
      let item = {
        category_id: '',
        seller_id: this.account.seller_id,
        option_name1: this.optionNames[0],
        option_content1: '',
        option_name2: this.optionNames[1],
        option_content2: '',
        option_price: 0,
        discount: 0,
        price: 0,
        stock: 0,
        maxCount: 0,
        main: 'N',
      }

      console.log(this.account.seller_id);

      this.optionItems.push(item);
    },

    deleteOption(index) {
      if (this.optionItems[index].main == 'Y') {
        this.defaultItem.option_price = 0;
        this.defaultItem.discount = 0;
        this.defaultItem.price = 0;
        this.defaultItem.main = 'N';
      }

      this.optionItems.splice(index, 1);
    },

    selectMainProduct(item) {
      this.defaultItem.option_price = item.option_price;
      this.defaultItem.discount = item.discount;
      this.defaultItem.price = item.price;

      this.optionItems.forEach((data) => {
        data.main = '';
      })

      item.main = 'Y';
    },

    checkMain(item) {
      if (item.main == 'Y') {
        this.defaultItem.option_price = item.option_price;
        this.defaultItem.discount = item.discount;
        this.defaultItem.price = item.price;
      }
    },

    deletePreview(target, index) {
      if (index == null) {
        this.mainUrl = '';
        this.mainFile = '';
      } else {
        this.files.splice(index, 1);
        this.subUrls.splice(index, 1);
      }
    },

    productImgInput(e, target) {
      if (target == 'main') {
        this.mainFile = e.target.files[0];
        this.mainUrl = URL.createObjectURL(e.target.files[0]);
      } else {
        this.files.push(e.target.files[0]);
        this.subUrls.push(URL.createObjectURL(e.target.files[0]));
      }
    },
  }
}
</script>

<style scoped>

.s-pm-rp-wrap {
  margin: 1rem 0;
}

.s-pm-rp-title {
  font-size: 18px;
  font-weight: bolder;
}

.basic-wrap {
  display: table;
  border: 1px solid #f0f0f0;
  border-bottom: none;
  margin: 0.5rem 0;
  width: 100%;

  font-size: 14px;
}

.tr {
  display: table-row;
}

.td {
  border-bottom: 1px solid #dddddd;
  display: table-cell;
  padding: 1rem
}

.td-title {
  font-weight: 600;
  background: #f0f0f0;
  width: 20%;
}

.td-out-line {
  padding: 5px;
  border: 1px solid #cfcdcd;
}

.td-input {
  width: 90%;
  padding: 3px;
  border: none;
  outline: none;
}

.input-limit {
  float: right;
}

.td-select {
  width: 50%;
  padding: 5px;
  border: 1px solid #cfcdcd;
  outline: none;
}

.td-notice {
  display: block;
  font-size: 11px;
  font-weight: bolder;
  color: red;
  padding-bottom: 10px;

}

.td-flex {
  width: 80%;
  display: flex;
  justify-content: space-between;
}

.td-column {
  display: flex;
  flex-direction: column;
}

.td-col-title {
  font-size: 12px;
  font-weight: 700;
  padding-bottom: 5px;
}

.td-col-content {
  border: 1px solid #cfcdcd;
  background: #eaebef;
  padding: 5px;
  outline: none;
  text-align: right;
  font-size: 14px;
}

.rp-option-wrap {
  margin-top: 5rem;
  padding: 1rem 0;
  border-top: 1px solid #cfcdcd;
}

.rp-option-title-wrap {
  display: flex;
  justify-content: space-between;
}

.rp-option-title,
.rp-img-title {
  font-size: 18px;
  font-weight: bolder;
  margin-bottom: 0.5rem;
}

.rp-option-active {
  color: #3385f096 !important;
  font-weight: bolder;
}

.rp-option-title-sub > span {
  margin: 0 10px;
}

.rp-option-title-sub :first-child {
  font-weight: 700;
  color: #aaa9a9;
}

.rpOptionTitleBtn {
  background: #3385f096;
  border-radius: 5px;
  color: white;
  font-weight: 600;
  padding: 0.5rem 1rem;
  cursor: pointer;
}

.rpOptionTitleBtn > span {
  vertical-align: middle;
  margin-left: 9px;
  display: inline-block;
  font-size: 20px;
  font-weight: 600;
  color: white !important;
  transform: rotate(90deg) !important;
}

.rpOptionTitleBtn2 {
  border: 1px solid #3385f096;
  background: white;
  border-radius: 5px;
  color: #3385f096;
  font-weight: 600;
  padding: 0.5rem 1rem;
  cursor: pointer;
}

.rpOptionTitleBtn2 > span {
  vertical-align: middle;
  margin-left: 9px;
  display: inline-block;
  font-size: 20px;
  font-weight: 600;
  color: #3385f096 !important;
  transform: rotate(-90deg) !important;
}


.rp-option-content-wrap {
  margin-top: 1rem;
  margin-bottom: 2rem;
}

.rp-option-default-wrap {
  display: flex;
  justify-content: space-between;
  width: 100%;
  background: #f0f0f0;
  margin: 1rem 0;
  font-size: 14px;
  padding: 1rem;
  border-top: 1px solid #666666;

}

.rp-option-default {
  display: flex;
  width: 80%;
  background: #f0f0f0;
  font-size: 13px;
}

.option-delete {
  background: #575757;
  padding: 5px 12px;
  color: white;
  border-radius: 3px;
  font-weight: 600;
  cursor: pointer;
}

.rp-option-default :not(:first-child) {
  padding: 5px;
  outline: none;
  border: 1px solid #cfcdcd;
  margin: 0 5px;
}

.rp-option-default select {
  width: 10%;
}

.rp-option-default span {
  width: 12%;
}

.option-add-btn {
  padding: 0.3rem 1rem;
  border-radius: 3px;
  background: #575757;
  color: white;
  font-size: 14px;
  cursor: pointer;
}

.option-notice {
  margin: 0 10px;
  font-size: 14px;
  color: red;
}

.rp-table {
  width: 100%;
  font-size: 14px;
  margin: 1rem 0;
}

.rp-table thead {
  vertical-align: middle;
  text-align: center;
  background: #f0f0f0;
}

.rp-table tbody {
  text-align: right;
}

.rp-table input {
  width: 100%;
  padding: 3px;
  border: 1px solid #aaa9a9;
  outline: none;
}

.rp-num {
  text-align: right;
}

.rp-img-wrap {
  margin-top: 5rem;
  border-top: 1px solid #cfcdcd;
  padding: 1rem 0;
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

.closePreivew {
  float: right;
  background: #575757;
  padding: 3px 10px;
  font-size: 12px;
  font-weight: 800;
  color: white;
  cursor: pointer;
  margin-bottom: 5px;
}

.registration-access {
  float: right;
  background: #3385f096;
  font-size: 20px;
  font-weight: 700;
  color: white;
  padding: 0.8rem 2rem;
  cursor: pointer;
}

.error-msg {
  color: red;
}

.error-bolder {
  border: 1px solid red;
}

</style>