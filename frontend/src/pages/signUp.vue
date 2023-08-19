<template>
  <custom_header/>
  <custom_nav/>
  <div class="content">
    <div class="sign-up-wrap">
      <div class="sign-up-panel">
        <div class="sign-up-title">
          <h2>회원가입</h2>
        </div>
        <form @submit.prevent="onSubmit">
          <div class="input-panel">
            <label class="input-title" for="id">아이디</label>
            <input
                type="text"
                class="input-id sign-up-input"
                id="userId"
                name="cust_id"
                placeholder="띄어쓰기 없이 영문/숫자 6~10"
                @blur="idPass"
                @input="userId = $event.target.value"
                :class="{ blurBorder: !idChk }"
            />
            <div class="id-error sign-up-error">
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
  </div>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";
import axios from "axios";
import router from "@/router";

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

      idChk: true,
      pwdChk: true,
      reconfirmPwdChk: true,
      nameChk: true,
      emailChk: true,
      isDomainInp: false,
      isEmailChk: false,
      phoneChk: true,
      addressChk: true,

      idErrorMsg: "",
      pwdErrorMsg: "",
      reconfirmPwdMsg: "",
      nameErrorMsg: "",
      emailErrorMsg: "",
      phoneErrorMsg: "",
      addressErrorMsg: "",
    };
  },

  created() {
    this.isShow = false;
  },

  methods: {
    onSubmit(e) {
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
          this.phonePass()
      ) {

        const user = {
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
        axios.post("api/account/signUp", JSON.stringify(user), axiosConfig).then(({status}) => {
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
      const idRegex = /^[a-zA-Z][0-9a-zA-Z]{4,7}$/;

      if (this.userId == "") {
        this.idErrorMsg = "필수 입력 항목입니다.";
        this.idChk = false;
      } else if (this.userId.length < 6 || this.userId.length > 10) {
        this.idErrorMsg = "아이디 글자 제한 수를 맞춰주세요.";
        this.idChk = false;
      } else if (!idRegex.test(this.userId)) {
        this.idErrorMsg =
            "아이디는 띄어쓰기 없이 영문, 숫자 6~10자로 입력하세요.";
        this.idChk = false;
      } else {
        this.idErrorMsg = "";
        this.idChk = true;
        console.log(this.userId);
        return true;
      }

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

.sign-up-wrap {
  width: 100%;
}

.sign-up-panel {
  width: 410px;
  margin: 0 auto;
  padding: 1rem;
}

.sign-up-title > h2 {
  margin: 5rem;
  text-align: center;
}

.input-title {
  font-size: 18px;
  font-weight: bolder;
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
