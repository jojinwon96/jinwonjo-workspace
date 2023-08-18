<template>
  <nav>
    <div class="nav-wrap">
      <ul class="nav-category">
        <li><a>꽃선물</a></li>
        <li><a>개업화분</a></li>
        <li><a>승진취임</a></li>
        <li><a>결혼장례</a></li>
        <li><a>꽃병/화분</a></li>
        <li><a>이벤트</a></li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Nav",
};
</script>

<style>
nav {
  border-bottom: 1px solid black;
  margin-top: 3rem;
  background: white;
}

.nav-wrap {
  margin: 0 auto;
  width: 1240px;
  padding-top: 4rem;
  padding-left: 4.5rem;
}

.nav-category {
  display: flex;
  justify-content: start;
}

.nav-category > li {
  padding: 0px 15px;
}

.nav-category > li:hover {
  color: #3385f096;
}

.nav-category a {
  font-size: 15px;
  cursor: pointer;
}

@media screen and (max-width: 768px) {
  .nav-wrap {
    padding-left: 5px;
    padding-right: 5px;
  }

  .nav-category > li {
    padding: 0px 5px;
  }
}
</style>
