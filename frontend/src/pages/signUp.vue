<template>
  <custom_header/>
  <custom_nav/>
  <div class="content">
    <div class="chose-wrap" v-if="$store.state.signUpState.isChose">
      <div class="chose-content">
        <div class="customer-btn" @click="changeSignUp(0)">
          <span>일반회원 가입 &#10095;</span>
        </div>
        <div class="seller-btn" @click="changeSignUp(1)">
          <span>사업자회원 가입 &#10095;</span>
        </div>
      </div>
    </div>

    <div class="sign-up-wrap" v-if="!$store.state.signUpState.isChose && $store.state.signUpState.isCust">
      <div class="sign-up-panel">
        <div class="sign-up-title">
          <h2>회원가입</h2>
        </div>
        <form @submit.prevent="onCustSubmit">
          <div class="input-panel">
            <div class="id-wrap">
              <label class="input-title" for="id">아이디</label>
              <button @click="duplicateCheck(0)" type="button" class="input-id-check" v-bind:disabled="isIdChk == false"
                      :class="{idCheckActive : isIdChk}">중복확인
              </button>
            </div>
            <input
                type="text"
                class="input-id sign-up-input"
                id="userId"
                name="cust_id"
                placeholder="띄어쓰기 없이 영문/숫자 6~10"
                @blur="idPass"
                @input="idPass"
                v-model="userId"
                :class="{ blurBorder: !idChk }"
            />
            <div class="id-error sign-up-error" :class="{errorCheck : idDuplicateChk}">
              {{ idErrorMsg }}
            </div>
          </div>

          <div class="input-panel">
            <label class="input-title" for="pwd">비밀번호</label>
            <input
                type="password"
                class="input-pwd sign-up-input"
                name="cust_pwd"
                id="pwd"
                placeholder="8~15자의 영문자, 숫자, 특수문자 조합"
                @blur="pwdPass"
                @input="pwd = $event.target.value"
                :class="{ blurBorder: !pwdChk }"
            />
            <div class="pwd-error sign-up-error">
              {{ pwdErrorMsg }}
            </div>
          </div>

          <div class="input-panel">
            <label class="input-title" for="pwdChk">비밀번호 확인</label>
            <input
                type="password"
                class="input-pwd-chk sign-up-input"
                id="pwdChk"
                placeholder="위의 비밀번호를 다시 입력해주세요."
                @blur="reconfirmPwdPass($event.target.value)"
                @input="reconfirmPwdPass($event.target.value)"
                :class="{ blurBorder: !reconfirmPwdChk }"
            />
            <div class="pwdChk-error sign-up-error">
              {{ reconfirmPwdMsg }}
            </div>
          </div>

          <div class="input-panel">
            <label class="input-title" for="name">이름</label>
            <input
                type="text"
                class="input-name sign-up-input"
                name="cust_name"
                id="userName"
                @blur="namePass"
                @input="userName = $event.target.value"
                :class="{ blurBorder: !nameChk }"
            />
            <div class="name-error sign-up-error">
              {{ nameErrorMsg }}
            </div>
          </div>

          <div class="input-panel email-panel">
            <label class="input-title" for="email">이메일</label>
            <div class="custom-input-group">
              <input
                  type="text"
                  class="input-email-local sign-up-input"
                  id="emailId"
                  @blur="emailPass($event.target.value, 'id')"
                  @input="emailPass($event.target.value, 'id')"
                  :class="{ blurBorder: !emailChk }"
              />
              <span class="input-separator">@</span>
              <select
                  class="input-email-domain sign-up-input"
                  id="domain"
                  v-model="domain"
                  @change="changeDomainInp"
                  :class="{ blurBorder: !emailChk, hide: isDomainInp }"
              >
                <option disabled value="">선택해주세요</option>
                <option value="naver.com">naver.com</option>
                <option value="hanmail.net">hanmail.net</option>
                <option value="daum.net">daum.net</option>
                <option value="gmail.com">gmail.com</option>
                <option value="nate.com">nate.com</option>
                <option value="hotmail.com">hotmail.com</option>
                <option value="outlook.com">outlook.com</option>
                <option value="icloud.com">icloud.com</option>
                <option value="1">직접입력</option>
              </select>
              <div
                  class="domainTextPanel sign-up-input"
                  :class="{ show: isDomainInp, blurBorder: !emailChk }"
              >
                <input
                    type="text"
                    class="domain-text sign-up-input"
                    @blur="emailPass($event.target.value, 'domain')"
                    @input="emailPass($event.target.value, 'domain')"
                />
                <span
                    class="domain-text-close"
                    @click="(isDomainInp = false), (domain = '')"
                >&#10006;</span
                >
              </div>
              <input
                  type="hidden"
                  name="cust_email"
                  id="userEmail"
                  v-model="email"
              />
            </div>
            <div class="email-error sign-up-error">
              {{ emailErrorMsg }}
            </div>
            <!-- <button
              type="button"
              class="sign-btn sign-up-btn email-auth"
              v-bind:disabled="!isEmailChk"
              :class="{ accessDisabled: !isEmailChk }"
              @click="test"
            >
              이메일인증하기
            </button> -->
          </div>

          <div class="input-panel">
            <label class="input-title" for="">휴대폰번호</label>
            <div class="custom-input-group">
              <input
                  type="text"
                  class="sign-up-input input-phone"
                  maxlength="4"
                  @blur="phonePass($event.target.value, 1)"
                  @input="phonePass($event.target.value, 1)"
                  :class="{ blurBorder: !phoneChk }"
              />
              <span class="input-separator">-</span>
              <input
                  type="text"
                  class="sign-up-input input-phone"
                  maxlength="4"
                  @blur="phonePass($event.target.value, 2)"
                  @input="phonePass($event.target.value, 2)"
                  :class="{ blurBorder: !phoneChk }"
              />
              <span class="input-separator">-</span>
              <input
                  type="text"
                  class="sign-up-input input-phone"
                  maxlength="4"
                  @blur="phonePass($event.target.value, 3)"
                  @input="phonePass($event.target.value, 3)"
                  :class="{ blurBorder: !phoneChk }"
              />
              <input type="hidden" name="cust_tel" id="phone" v-model="phone"/>
            </div>
          </div>
          <div class="sign-up-error">
            {{ phoneErrorMsg }}
          </div>

          <div class="input-panel">
            <label class="input-title" for="">주소</label>
            <div class="custom-input-group">
              <input
                  type="text"
                  class="sign-up-input input-post"
                  name="post"
                  readonly
                  v-model="addressArr[0]"
                  @input="addressPass"
                  :class="{ blurBorder: !addressChk }"
              />
              <button
                  type="button"
                  class="sign-up-input sign-up-btn find-address-btn"
                  @click="findAddress"
              >
                주소찾기
              </button>
            </div>
            <input
                type="text"
                class="sign-up-input input-address"
                name="address1"
                readonly
                v-model="addressArr[1]"
                @input="addressPass"
                :class="{ blurBorder: !addressChk }"
            />
            <input
                type="text"
                class="sign-up-input input-address-detail"
                name="address2"
                v-model="addressArr[2]"
                @blur="addressPass"
                @input="addressPass"
                :class="{ blurBorder: !addressChk }"
            />
            <div class="address-error sign-up-error">
              {{ addressErrorMsg }}
            </div>
          </div>

          <button type="submit" class="sign-up-btn sign-up-access">
            입력 완료
          </button>
        </form>
      </div>
    </div>

    <div class="seller-wrap" v-if="!$store.state.signUpState.isChose && $store.state.signUpState.isSeller">
      <div class="sign-up-panel">
        <div class="sign-up-title">
          <h2>사업자 회원가입</h2>
          <form @submit.prevent="onSellerSubmit">
            <div class="input-panel">
              <div class="id-wrap">
                <label class="input-title" for="id">아이디</label>
                <button @click="duplicateCheck(1)" type="button" class="input-id-check" v-bind:disabled="isIdChk == false"
                        :class="{idCheckActive : isIdChk}">중복확인
                </button>
              </div>
              <input
                  type="text"
                  class="input-id sign-up-input"
                  id="userId"
                  name="cust_id"
                  placeholder="띄어쓰기 없이 영문/숫자 6~10"
                  @blur="idPass"
                  @input="idPass"
                  v-model="userId"
                  :class="{ blurBorder: !idChk}"
              />
              <div class="id-error sign-up-error" :class="{errorCheck : idDuplicateChk}">
                {{ idErrorMsg }}
              </div>
            </div>

            <div class="input-panel">
              <label class="input-title" for="pwd">비밀번호</label>
              <input
                  type="password"
                  class="input-pwd sign-up-input"
                  name="cust_pwd"
                  id="pwd"
                  placeholder="8~15자의 영문자, 숫자, 특수문자 조합"
                  @blur="pwdPass"
                  @input="pwd = $event.target.value"
                  :class="{ blurBorder: !pwdChk }"
              />
              <div class="pwd-error sign-up-error">
                {{ pwdErrorMsg }}
              </div>
            </div>

            <div class="input-panel">
              <label class="input-title" for="pwdChk">비밀번호 확인</label>
              <input
                  type="password"
                  class="input-pwd-chk sign-up-input"
                  id="pwdChk"
                  placeholder="위의 비밀번호를 다시 입력해주세요."
                  @blur="reconfirmPwdPass($event.target.value)"
                  @input="reconfirmPwdPass($event.target.value)"
                  :class="{ blurBorder: !reconfirmPwdChk }"
              />
              <div class="pwdChk-error sign-up-error">
                {{ reconfirmPwdMsg }}
              </div>
            </div>

            <div class="input-panel">
              <label class="input-title" for="company-name">회사이름</label>
              <input class="sign-up-input" @input="companyNamePass" @blur="companyNamePass" v-model="companyName"/>
              <div class="sign-up-error">
                {{ companyNameErrorMsg }}
              </div>
            </div>

            <div class="input-panel">
              <label class="input-title" for="company-name">사업자 등록번호</label>
              <div class="custom-input-group">
                <input class="sign-up-input seller-num1" @blur="sellerNumPass"
                       @input="sellerNumPass" v-model="sellerNumberArr[0]" maxlength="3"/>
                <span class="input-separator">-</span>
                <input class="sign-up-input seller-num2" @blur="sellerNumPass"
                       @input="sellerNumPass" v-model="sellerNumberArr[1]" maxlength="2"/>
                <span class="input-separator">-</span>
                <input class="sign-up-input seller-num3" @blur="sellerNumPass"
                       @input="sellerNumPass" v-model="sellerNumberArr[2]" maxlength="5"/>
              </div>
              <div class="sign-up-error">
                {{ sellerNumErrorMsg }}
              </div>
            </div>

            <div class="input-panel">
              <label class="input-title" for="name">이름</label>
              <input
                  type="text"
                  class="input-name sign-up-input"
                  name="cust_name"
                  id="userName"
                  @blur="namePass"
                  @input="userName = $event.target.value"
                  :class="{ blurBorder: !nameChk }"
              />
              <div class="name-error sign-up-error">
                {{ nameErrorMsg }}
              </div>
            </div>

            <div class="input-panel">
              <label class="input-title" for="">휴대폰번호</label>
              <div class="custom-input-group">
                <input
                    type="text"
                    class="sign-up-input input-phone"
                    maxlength="4"
                    @blur="phonePass($event.target.value, 1)"
                    @input="phonePass($event.target.value, 1)"
                    :class="{ blurBorder: !phoneChk }"
                />
                <span class="input-separator">-</span>
                <input
                    type="text"
                    class="sign-up-input input-phone"
                    maxlength="4"
                    @blur="phonePass($event.target.value, 2)"
                    @input="phonePass($event.target.value, 2)"
                    :class="{ blurBorder: !phoneChk }"
                />
                <span class="input-separator">-</span>
                <input
                    type="text"
                    class="sign-up-input input-phone"
                    maxlength="4"
                    @blur="phonePass($event.target.value, 3)"
                    @input="phonePass($event.target.value, 3)"
                    :class="{ blurBorder: !phoneChk }"
                />
                <input type="hidden" name="cust_tel" id="phone" v-model="phone"/>
              </div>
              <div class="sign-up-error">
                {{ phoneErrorMsg }}
              </div>
            </div>

            <button type="submit" class="sign-up-btn sign-up-access">
              입력 완료
            </button>
          </form>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";
import axios from "axios";
import router from "@/router";
import store from "@/store/modules/store.js";

export default {
  name: "signUp",
  data() {
    return {
      userId: "",
      pwd: "",
      reconfirmPwd: "",
      userName: "",
      email: "",
      emailId: "",
      domain: "",
      phoneArr: ["", "", ""],
      phone: "",
      addressArr: ["", "", ""],
      address: "",

      companyName: "",
      sellerNumberArr: ["", "", ""],
      sellerNum: "",

      idChk: true,
      isIdChk: false,
      idDuplicateChk: false,
      pwdChk: true,
      reconfirmPwdChk: true,
      nameChk: true,
      emailChk: true,
      isDomainInp: false,
      isEmailChk: false,
      phoneChk: true,
      addressChk: true,
      companyNameChk: true,
      sellerNumChk: true,

      idErrorMsg: "",
      pwdErrorMsg: "",
      reconfirmPwdMsg: "",
      nameErrorMsg: "",
      emailErrorMsg: "",
      phoneErrorMsg: "",
      addressErrorMsg: "",
      companyNameErrorMsg: "",
      sellerNumErrorMsg: "",

    };
  },

  created() {
    this.isShow = false;
  },

  methods: {
    clear() {
      this.userId = "";
      this.pwd = "";
      this.reconfirmPwd = "";
      this.userName = "";
      this.email = "";
      this.emailId = "";
      this.domain = "";
      this.phoneArr = ["", "", ""];
      this.phone = "";
      this.addressArr = ["", "", ""];
      this.address = "";
      this.companyName = "";
      this.sellerNumberArr = ["", "", ""];
      this.sellerNum = "";
      this.isIdChk = false;
      this.idChk = true;
      this.idDuplicateChk = false;
      this.pwdChk = true;
      this.reconfirmPwdChk = true;
      this.nameChk = true;
      this.emailChk = true;
      this.isDomainInp = false;
      this.isEmailChk = false;
      this.phoneChk = true;
      this.addressChk = true;
      this.companyNameChk = true;
      this.sellerNumChk = true;
      this.idErrorMsg = "";
      this.pwdErrorMsg = "";
      this.reconfirmPwdMsg = "";
      this.nameErrorMsg = "";
      this.emailErrorMsg = "";
      this.phoneErrorMsg = "";
      this.addressErrorMsg = "";
      this.companyNameErrorMsg = "";
      this.sellerNumErrorMsg = "";
    },

    duplicateCheck(target) {

      const idState = {
        id: this.userId,
        target: target
      }

      axios.post("api/account/idCheck", idState).then(({data}) => {
        if (data == 0) {
          this.idDuplicateChk = true;
          this.idChk = true;
          this.idErrorMsg = "사용할 수 있는 아이디 입니다."
        } else {
          this.idDuplicateChk = false;
          this.idErrorMsg = "사용할 수 없는 아이디 입니다."
        }

      })
    },

    changeSignUp(target) {
      this.clear();
      store.state.signUpState.isChose = false;
      if (target == 0) {
        store.state.signUpState.isCust = true;
      } else {
        store.state.signUpState.isSeller = true;
      }
    },

    onCustSubmit() {
      this.idPass();
      this.pwdPass();
      this.reconfirmPwdPass();
      this.namePass();
      this.emailPass();
      this.addressPass();
      this.phonePass();

      if (
          this.idPass() &&
          this.pwdPass() &&
          this.reconfirmPwdPass() &&
          this.namePass() &&
          this.emailPass() &&
          this.addressPass() &&
          this.phonePass() &&
          this.idDuplicateChk == true
      ) {

        const user = {
          seller_id: "",
          cust_id: this.userId,
          cust_pwd: this.pwd,
          cust_name: this.userName,
          cust_email: this.email,
          cust_tel: this.phone,
          post: parseInt(this.addressArr[0]),
          address1: this.addressArr[1],
          address2: this.addressArr[2],
        }

        const axiosConfig = {
          headers: {
            "Content-Type": "application/json"
          }
        }
        axios.post("/api/account/signUp", JSON.stringify(user), axiosConfig).then(({status}) => {
          if (status == 200) {
            alert("회원가입을 축하드립니다.");
            router.push({path: "/signIn"});
          }
        }).catch(() => {
          alert("서버오류");
        })
      }
    },

    onSellerSubmit(e) {
      this.idPass();
      this.pwdPass();
      this.reconfirmPwdPass();
      this.namePass();
      this.phonePass();
      this.companyNamePass();
      this.sellerNumPass()
      if (
          this.idPass() &&
          this.pwdPass() &&
          this.reconfirmPwdPass() &&
          this.namePass() &&
          this.phonePass() &&
          this.companyNamePass() &&
          this.sellerNumPass()
      ) {
        const seller = {
          cust_id:"",
          seller_id: this.userId,
          seller_pwd: this.pwd,
          seller_number: this.sellerNum,
          seller_name: this.userName,
          company_name: this.companyName,
          seller_tel: this.phone,
        }

        const axiosConfig = {
          headers: {
            "Content-Type": "application/json"
          }
        }
        axios.post("api/account/signUp", JSON.stringify(seller), axiosConfig).then(({status}) => {
          if (status == 200) {
            alert("회원가입을 축하드립니다.");
            router.push({path: "/signIn"});
          }
        }).catch(() => {
          alert("서버오류");
        })
      } else {
        e.preventDefault();
      }
    },

    idPass() {
      console.log(this.isIdChk);
      const idRegex = /^([A-za-z]{0,0})(?=.*[a-zA-Z])(?=.*[0-9]).{6,10}$/g;

      let msg = "";
      this.isIdChk = false;

      if (this.userId == "") {
        msg = "필수 입력 항목입니다.";
      } else if (this.userId.length < 6 || this.userId.length > 10) {
        msg = "아이디 글자 제한 수를 맞춰주세요.";
      } else if (!idRegex.test(this.userId)) {
        msg =
            "아이디는 띄어쓰기 없이 영문, 숫자 6~10자로 입력하세요.";
      } else if (this.idDuplicateChk == false) {
        msg = "아이디 중복확인을 해주세요."
        this.isIdChk = true;
      } else {
        this.idErrorMsg = msg;
        this.idChk = true;
        return true;
      }

      this.idDuplicateChk = false;
      this.idChk = false;
      this.idErrorMsg = msg;

      return false;
    },

    pwdPass() {
      const passwordRegex =
          /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
      if (this.pwd == "") {
        this.pwdErrorMsg = "필수 입력 항목입니다.";
        this.pwdChk = false;
      } else if (!passwordRegex.test(this.pwd)) {
        this.pwdErrorMsg =
            "비밀번호는 영문, 숫자, 특수문자 포함하여 8~15자로 입력하세요.";
        this.pwdChk = false;
      } else {
        this.pwdErrorMsg = "";
        this.pwdChk = true;
        console.log("비밀번호 ok");
        return true;
      }

      return false;
    },

    reconfirmPwdPass(e) {
      if (e != null) {
        this.reconfirmPwd = e;
      }

      if (this.reconfirmPwd == "") {
        this.reconfirmPwdMsg = "확인을 위해 비밀번호를 한 번 더 입력해주세요.";
        this.reconfirmPwdChk = false;
      } else if (this.pwd != this.reconfirmPwd) {
        this.reconfirmPwdMsg = "비밀번호가 일치하지 않습니다.";
        this.reconfirmPwdChk = false;
      } else {
        this.reconfirmPwdMsg = "";
        this.reconfirmPwdChk = true;
        return true;
      }

      return false;
    },

    namePass() {
      if (this.userName == "") {
        this.nameErrorMsg = "필수 입력 항목입니다.";
        this.nameChk = false;
      } else {
        this.nameErrorMsg = "";
        this.nameChk = true;
        return true;
      }
      return false;
    },

    emailPass(value, target) {
      const emailRegex =
          /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      let resultEmail = "";

      if (value != null) {
        if (target == "id") {
          this.emailId = value;
        } else {
          this.domain = value;
        }
      }

      resultEmail = this.emailId + "@" + this.domain;

      if (
          !emailRegex.test(resultEmail) ||
          this.emailId == "" ||
          this.domain == "0"
      ) {
        this.emailErrorMsg = "이메일 형식이 올바르지 않습니다.";
        this.emailChk = false;
        this.isEmailChk = false;
      } else {
        this.emailErrorMsg = "";
        this.emailChk = true;
        this.isEmailChk = true;

        this.email = resultEmail;
        return true;
      }

      return false;
    },

    changeDomainInp() {
      if (this.domain == "1") {
        this.isDomainInp = true;
      } else {
        this.isDomainInp = false;
        this.emailPass(this.domain, "domain");
      }
    },

    phonePass(value, target) {
      const phoneRegex = /01[016789]-[^0][0-9]{2,3}-[0-9]{3,4}/;

      if (target != null) {
        if (target == 1) {
          this.phoneArr[0] = value;
        } else if (target == 2) {
          this.phoneArr[1] = value;
        } else {
          this.phoneArr[2] = value;
        }
      }
      this.phone =
          this.phoneArr[0] + "-" + this.phoneArr[1] + "-" + this.phoneArr[2];

      if (!phoneRegex.test(this.phone)) {
        this.phoneErrorMsg = "잘못된형식입니다.";
        this.phoneChk = false;
      } else {
        this.phoneErrorMsg = "";
        this.phoneChk = true;
        return true;
      }
      return false;
    },

    addressPass() {
      if (this.addressArr[0] == "" || this.addressArr[2] == "") {
        this.addressErrorMsg = "필수 입력 항목입니다.";
        this.addressChk = false;
      } else {
        this.addressErrorMsg = "";
        this.addressChk = true;
        return true;
      }

      return false;
    },

    findAddress() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.addressArr[0] = data.zonecode;
          this.addressArr[1] = data.address;
        },
      }).open();
    },

    companyNamePass() {
      if (this.companyName == "") {
        this.companyNameErrorMsg = "필수 입력 항목입니다.";
        this.companyNameChk = false;
      } else {
        this.companyNameErrorMsg = "";
        this.companyNameChk = true;
        return true;
      }
      return false;
    },

    sellerNumPass() {
      let num1 = this.sellerNumberArr[0].length,
          num2 = this.sellerNumberArr[1].length,
          num3 = this.sellerNumberArr[2].length;

      if (num1 == 0 || num2 == 0 || num3 == 0) {
        this.sellerNumErrorMsg = "필수 입력 항목입니다.";
        this.sellerNumChk = false;
      } else if (num1 > 3 || num2 > 2 || num3 < 5) {
        this.sellerNumErrorMsg = "필수 입력 항목입니다.";
        this.sellerNumChk = false;
      } else {
        this.sellerNum = this.sellerNumberArr[0] + "-" + this.sellerNumberArr[1] + "-" + this.sellerNumberArr[2]
        this.sellerNumErrorMsg = "";
        this.sellerNumChk = true;
        return true;
      }

      return false;
    },

  },
  components: {
    custom_header,
    custom_nav,
  },
};
</script>

<style scoped>
.show {
  display: block !important;
}

.hide {
  display: none;
}

.chose-wrap {
  width: 1240px;
  padding-top: 15rem;
  padding-left: 3rem;
}

.chose-content > div {
  float: left;
  width: 500px;
  color: #3385f096;
  font-size: 36px;
  font-weight: 600;
  border: 1px solid #3385f096;
  border-radius: 5px;
  text-align: center;
  padding: 3rem 0;
  cursor: pointer;
  transition: 0.3s;
}

.chose-content > div:hover {
  box-shadow: 1px 1px 10px 0.5px;
}


.seller-btn {
  margin-left: 4rem;
}

.sign-up-wrap {
  width: 100%;
}

.sign-up-panel {
  width: 410px;
  margin: 0 auto;
  padding: 1rem;
}

.sign-up-title > h2 {
  margin: 6rem 0;
  text-align: center;
}

.input-title {
  font-size: 18px;
  font-weight: bolder;
}

.id-wrap {
  display: flex;
  justify-content: space-between;
}

.input-id-check {
  padding: 5px;
  color: #666666;
  background: #cfcdcd;
  border: 1px solid #aaa9a9;
}

.idCheckActive {
  color: #3385f096;
  background: white;
  border: 1px solid #3385f096;
}

.errorCheck{
    font-weight: 700;
    color: #3385f096 !important;
}

.input-panel {
  margin-top: 2rem;
}

.input-panel > * {
  width: 100%;
}

.sign-up-input {
  margin-top: 5px;
  padding: 8px;
  font-size: 14px;
  border: 1px #ccc solid;
  border-radius: 3px;
}

.custom-input-group {
  display: flex;
}

.input-email-local {
  width: 47%;
}

.input-separator {
  line-height: 38px;
  text-align: center;
  width: 6%;
}

.input-email-domain {
  width: 47%;
  margin-top: 5px;
  padding: 0px 5px;
}

.domainTextPanel {
  display: none;
  padding: 5px;
  width: 50%;
  border: 1px #ccc solid;
}

.domainTextPanel > * {
  display: inline-block;
}

.domain-text {
  width: 88%;
  margin: 0;
  padding: 0px 5px;
  border: none !important;
}

.domain-text:focus {
  outline: none;
}

.domain-text-close {
  cursor: pointer;
}

.input-phone {
  width: 29%;
}

.sign-up-btn {
  margin-top: 5px;
  padding: 8px;
  border: none;
  background-color: #3385f096;
  color: white;
  font-weight: bolder;
  font-size: 15px;
  cursor: pointer;
  transition: all 0.3s;
}

.sign-btn:hover,
.find-address-btn:hover,
.sign-up-access:hover {
  filter: brightness(90%);
}

.email-auth {
  padding: 15px;
}

.accessDisabled {
  cursor: default;
  background-color: #ccc;
}

.input-post {
  width: 30%;
  background-color: #f4f4f4;
}

.find-address-btn {
  margin-left: 3px;
}

.input-address {
  background-color: #f4f4f4;
}

.seller-num1 {
  width: 30%
}

.seller-num2 {
  width: 20%
}

.seller-num3 {
  width: 50%
}

.sign-up-access {
  width: 100%;
  margin-top: 2rem;
  padding: 1.5rem;
}

.sign-up-error {
  margin-top: 5px;
  color: red;
}

.blurBorder {
  border: 1px solid red;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>
