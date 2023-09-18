<template>
  <custom_header/>
  <custom_nav/>
  <div class="login-wrap">
    <div class="login-panel">
      <div class="login-header">
      </div>
      <div class="login-content">
        <div class="target-wrap">
          <div>
            <input id="user" type="radio" v-model="state.form.target" value="user">
            <label for="user">사용자 로그인</label>
          </div>
          <div>
            <input id="seller" type="radio" v-model="state.form.target" value="seller">
            <label for="seller">판매자 로그인</label>
          </div>
        </div>
        <div class="login-input">
          <input autofocus @keyup.enter="submit()" type="text" class="form-group id" name="id" id="id" placeholder="아이디"
                 v-model="state.form.id"/>
          <input @keyup.enter="submit()" type="password" class="form-group pwd" name="pwd" id="pwd" placeholder="비밀번호"
                 v-model="state.form.pwd"/>
        </div>

        <button type="button" class="form-group sign-btn" @click="submit()">로그인</button>

        <div class="login-util">
          <div class="login-join">
            <a href="#">회원가입</a>
          </div>
          <div class="login-find">
            <a href="#">아이디찾기</a>
            <span></span>
            <a href="#" class="find-pwd">비밀번호찾기</a>
          </div>
        </div>

        <div class="sns">
          <a href="">
            <img class="sns-icon" src="../assets/img/naver.png"/>
          </a>
          <a href="">
            <img class="sns-icon" src="../assets/img/kakko.png"/>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import custom_header from "../components/Header.vue";
import custom_nav from "../components/Nav.vue";
import {reactive} from "vue";
import axios from "axios";
import store from "@/store/modules/store.js";
import router from "@/router";

export default {
  setup() {

    const state = reactive({
      form: {
        id: "",
        pwd: "",
        target: "",
      },

    })

    const submit = () => {
      axios.post("/api/account/login", state.form).then(({data}) => {
        let msg = "";

        if (data == 2) {
          msg = "존재하지 않는 회원입니다.";
        } else if (data == 1) {
          msg = '비밀번호를 다시 확인해주세요.';
        } else {
          msg = "성공적으로 로그인 되었습니다."
          store.commit('setAccount', data);
          sessionStorage.setItem("loginUser", JSON.stringify(data));
          router.replace({path: "/"});
        }

        alert(msg);

        console.log('---------------스토어---------------------');
        console.log(store.state.account);
      }).catch(() => {
        alert("야이디와 비밀번호를 확인해주세요.")
      })
    }

    return {state, submit}
  },
  created() {
    this.isShow = false;
  },
  components: {
    custom_header,
    custom_nav,
  },
};
</script>

<style>
.login-wrap {
  margin: 0 auto;
  width: 1240px;
}

.login-panel {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  box-sizing: border-box;
  padding: 150px 0px;
}

.logo {
  display: block;
  background-image: url(../assets/img/logo.png);
  background-size: 250px;
  width: 250px;
  height: 70px;
  background-repeat: no-repeat;
  cursor: pointer;
  margin-bottom: 30px;
}

.target-wrap {
  display: flex;
  justify-content: space-around;
  margin-bottom: 10px;
  font-weight: bolder;
}

.form-group {
  display: block;
  width: 350px;
  padding: 20px;
}

.login-input {
  margin-bottom: 20px;
}

.login-input > input {
  border: 1px solid lightgray;
  outline: none;
}

.login-input > input:focus {
  border: 1px solid cornflowerblue;
}

.id {
  border-top: none;
}


.sign-btn {
  width: 100%;
  border: none;
  background-color: #3385f096;
  border-radius: 5px;
  color: white;
  font-weight: bolder;
  font-size: 20px;
  cursor: pointer;

  transition: all 0.3s;
}

.sign-btn:hover {
  filter: brightness(90%);
}

.login-util {
  margin-top: 7px;
  font-size: 12px;
}

.login-util a {
  text-decoration: none;
  color: black;
  font-weight: bolder;
}

.login-join {
  float: left;
}

.login-find {
  float: right;
}

.login-find > span {
  position: absolute;
  margin-left: 2px;
  height: 15px;
  width: 1px;
  background-color: lightslategray;
  cursor: pointer;
}

.find-pwd {
  margin-left: 5px;
}

.sns {
  display: flex;
  justify-content: center;
  margin-top: 100px;
}

.sns-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin: 5px;
}
</style>
