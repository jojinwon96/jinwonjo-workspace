<template>
  <Transition name="fade">
    <div class="modify-modal" v-show="isOpenModal == true">
      <div class="modal-content">
        <div class="modal-close" @click="setModal(!isOpenModal)">&#10005;</div>

        <div class="modal-body-wrap">
          <div class="modal-body-content">
            <div class="modal-body-title">상품정보</div>
            <div class="modal-table-top">
              <span>항목명</span>
              <input class="modal-opt-name" placeholder="사이즈"/>
              <span class="modal-add-opt">추가</span>
            </div>
            <table class="table modal-table">
              <thead>
              <th class="col-1">상품번호</th>
              <th class="col-1">옵션번호</th>
              <th class="col-4">옵션명</th>
              <th class="col-2">정상가(원)</th>
              <th class="col-1">할인율(%)</th>
              <th class="col-2">판매가(원)</th>
              <th class="col-1"></th>
              </thead>
              <tbody v-for="item in 3" :key="item">
              <td>
                <input class="modal-inp-text" value="p-1"/>
              </td>
              <td>
                <input class="modal-inp-text" value="opt-1"/>
              </td>
              <td>
                <input class="modal-inp-text" value="고급형"/>
              </td>
              <td>
                <input class="modal-inp-num" value="50000"/>
              </td>
              <td>
                <input class="modal-inp-num" value="50"/>
              </td>
              <td>
                <input class="modal-inp-num" value="25000"/>
              </td>
              <td>
                <span class="modal-delete-opt">삭제</span>
              </td>
              </tbody>
            </table>
            <span class="modal-table-reset">초기화</span>

            <div class="rp-img-wrap">
              <span class="rp-img-title">이미지</span>
              <div class="rp-img-content-wrap">
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
                    <input type="file" id="main-file">
                  </div>
                  <div class="rp-img-review" >
                    <span  class="closePreivew">삭제</span>
                    <img class="rp-img"/>
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
                    <input  type="file" id="sub-file" multiple="multiple">
                  </div>
                  <div class="rp-img-review" >
                    <div class="rp-img-review" >
                      <span  class="closePreivew">삭제</span>
                      <img class="rp-img"/>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <span class="modify-access">수정완료</span>
          </div>
        </div>

      </div>
    </div>
  </Transition>
</template>

<script>

import {mapMutations, mapState} from "vuex";

export default {
  name: "modifyModal",

  mounted() {

  },

  computed: {
    ...mapState(['isOpenModal']),
  },

  methods: {
    ...mapMutations(['setModal']),
  },


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
  width: 1200px;
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
}

.modal-table-top > span {
  font-weight: bolder;
}

.modal-add-opt {
  float: right;
  margin: 0 5px;
  font-size: 15px;
  color: white;
  border-radius: 3px;
  padding: 5px 0.8rem;
  background: #575757;
  cursor: pointer;
}

.modal-opt-name {
  border: 1px solid #bbbbbb;
  margin: 0 10px;
  padding: 3px;
  outline: none;
  width: 12%;
}

.modal-table {
  font-size: 14px;
}

.modal-table thead {
  text-align: center;
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

.modal-inp-text {
  text-align: center;
}

.modal-inp-num {
  text-align: right;
}

.modal-delete-opt {
  display: block;
  text-align: center;
  background: #aaa9a9;
  border-radius: 3px;
  color: white;
  padding: 5px 10px;
  cursor: pointer;
}

.modal-table-reset {
  float: right;
  text-align: center;
  font-size: 15px;
  color: white;
  border-radius: 3px;
  margin: -10px 3px;
  padding: 5px 0.8rem;
  background: #aaa9a9;
  cursor: pointer;
}

.rp-img-wrap {
  margin-top: 5rem;
  border-top: 1px solid #cfcdcd;
  padding: 0.2rem 0;
}

.rp-img-title{
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

.rp-img-content-wrap> div{
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

.modify-access{
  float: right;
  background: #3385f096;
  font-size: 20px;
  font-weight: 700;
  color: white;
  padding: 0.8rem 2rem;
  cursor: pointer;
}

</style>