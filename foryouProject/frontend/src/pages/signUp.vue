<template>
  <div class="content">
    <div class="sign-up-wrap">
      <div class="sign-up-panel">
        <div class="sign-up-title">
          <h2>회원가입</h2>
        </div>

        <div class="input-panel">
          <label class="input-title" for="id">아이디</label>
          <input
            type="text"
            class="input-id sign-up-input"
            name="userId"
            id="userId"
            placeholder="띄어쓰기 없이 영문/숫자 6~10"
          />
          <div class="id-error sign-up-error"></div>
        </div>

        <div class="input-panel">
          <label class="input-title" for="pwd">비밀번호</label>
          <input
            type="password"
            class="input-pwd sign-up-input"
            name="pwd"
            id="pwd"
            placeholder="8~15자의 영문자, 숫자, 특수문자 조합"
          />
          <div class="pwd-error sign-up-error"></div>
        </div>

        <div class="input-panel">
          <label class="input-title" for="pwdChk">비밀번호 확인</label>
          <input
            type="password"
            class="input-pwd-chk sign-up-input"
            name="pwdChk"
            id="pwdChk"
            placeholder="위의 비밀번호를 다시 입력해주세요."
          />
          <div class="pwdChk-error sign-up-error"></div>
        </div>

        <div class="input-panel">
          <label class="input-title" for="name">이름</label>
          <input
            type="text"
            class="input-name sign-up-input"
            name="userName"
            id="userName"
          />
          <div class="name-error sign-up-error"></div>
        </div>

        <div class="input-panel email-panel">
          <label class="input-title" for="email">이메일</label>
          <div class="custom-input-group">
            <input
              type="text"
              class="input-email-local sign-up-input"
              id="emailId"
            />
            <span class="input-separator">@</span>
            <select class="input-email-domain sign-up-input" id="domain">
              <option selected>선택해주세요</option>
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
            <div class="domain-text-panel sign-up-input">
              <input type="text" class="domain-text sign-up-input" />
              <span class="domain-text-close">&#10006;</span>
            </div>
            <input type="hidden" name="userEmail" id="userEmail" />
          </div>
          <div class="email-error sign-up-error"></div>
          <button
            type="button"
            class="sign-btn sign-up-btn email-auth access-disabled"
            onclick="emailCheck();"
            disabled
          >
            이메일인증하기
          </button>
        </div>

        <div class="input-panel">
          <label class="input-title" for="">휴대폰번호</label>
          <div class="custom-input-group">
            <input
              type="text"
              class="sign-up-input input-phone"
              maxlength="4"
              oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
            />
            <span class="input-separator">-</span>
            <input
              type="text"
              class="sign-up-input input-phone"
              maxlength="4"
              oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
            />
            <span class="input-separator">-</span>
            <input
              type="text"
              class="sign-up-input input-phone"
              maxlength="4"
              oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
            />
            <input type="hidden" name="phone" id="phone" />
          </div>
        </div>

        <div class="input-panel">
          <label class="input-title" for="">주소</label>
          <div class="custom-input-group">
            <input
              type="text"
              class="sign-up-input input-post"
              name="post"
              readonly
            />
            <button
              type="button"
              class="sign-up-input sign-up-btn find-address-btn"
              onclick="findAddress();"
            >
              주소찾기
            </button>
          </div>
          <input
            type="text"
            class="sign-up-input input-address"
            name="address1"
            readonly
          />
          <input
            type="text"
            class="sign-up-input input-address-detail"
            name="address2"
          />
          <div class="address-error sign-up-error"></div>
        </div>

        <button
          type="button"
          class="sign-up-btn sign-up-access"
          onclick="signUpAccess();"
        >
          입력 완료
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "signUp",
  components: {},
};
</script>

<style scoped>
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
  padding: 8;
  padding: 0px 5px;
}

.domain-text-panel {
  display: none;
  width: 50%;
  margin-top: 5px;
  border: 1px #ccc solid;
}

.domain-text-panel > * {
  display: inline-block;
}

.domain-text {
  margin: 0;
  width: 88%;
  padding: 0px 5px;
  height: 100%;
  border: none;
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

.access-disabled {
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

.blur-border {
  border: 1px solid red;
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
</style>
