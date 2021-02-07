<template>
  <section>
    <h3>사원정보</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns" :rowHeaders="gridProps.rowHeaders"/>
    <button v-on:click="selectUser">선택</button>
  </section>
</template>

<script>
/* eslint-disable */
import 'tui-grid/dist/tui-grid.css'
import { Grid } from '@toast-ui/vue-grid'

export default {
  components: {
    'grid': Grid
  },
  methods: {
    getUserList: function() {
      const baseURI = 'http://localhost:8080/api/user';
      this.$http.get(`${baseURI}/getUsers`)
      .then((result) => {
        this.$refs.tuiGrid.invoke('appendRows', result.data.content);
      }).catch(error=>{
        console.log(error)
      });
    },
    selectUser: function() {
        let keys = this.$refs.tuiGrid.invoke('getCheckedRows')

        if(keys.length != 1){
          alert("한명만 선택해주세요")
          return;
        }

        this.$emit('setUserInfo', keys[0])
    }
  },
  beforeMount(){
    this.getUserList()
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '사원ID',   name: 'userId' },
        { header: '사번',     name: 'userCode' },
        { header: '사원명',   name: 'userName' },
        { header: '부서코드', name: 'deptId' },
        { header: '부서코드', name: 'deptCode' },
        { header: '부서명',   name: 'deptName' },
        { header: '직책코드', name: 'positionId'},
        { header: '직책코드', name: 'positionCode'},
        { header: '직책명',   name: 'positionName'},
        { header: '보유연차', name: 'totalVacCnt' },
        { header: '잔여연차', name: 'remainVacCnt' },
        { header: '입사일',   name: 'joinDate' }
      ],
      data: [],
      rowHeaders: ['checkbox']
    }
  }
}

</script>

<style>
button {
  width: 100%;
  height: 50px;
}
</style>
