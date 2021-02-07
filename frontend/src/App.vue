
<template>
  <div id="app">
    <div class="nav-bar"><router-link to="/masterApp"><button>마스터 정보 설정</button></router-link></div>
    <div class="nav-bar"><router-link to="/vacationApp"><button>휴가 신청</button></router-link></div>
    <div class="nav-bar"><router-link to="/calendarApp"><button>휴가 달력</button></router-link></div>
    <router-view></router-view>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  data() {
    return {
    };
  },
  methods: {
  }
}
</script>

<style>
.nav-bar {
  width: 30%;
  padding: 15px;
  float: left;
}

button {
  width: 100%;
  height: 50px;
}
</style>
