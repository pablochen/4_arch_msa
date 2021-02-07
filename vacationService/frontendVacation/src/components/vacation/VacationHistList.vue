<template>
  <section>
    <h3>휴가 내역</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns" :rowHeaders="gridProps.rowHeaders"/>
    <button v-on:click="removeVacationHists">취소</button>
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
    getVacationHists: function(key) {
      const data = {
        params:{
          userId : key.userId
        }
      }

      const headers = {
        "Content-Type": "application/json"
      }

      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.get(`${baseURI}/getVacationHists`, data, headers)
      .then((result) => {
        this.$refs.tuiGrid.invoke('clear');
        this.$refs.tuiGrid.invoke('appendRows', result.data.content);
      }).catch(error=>{
        console.log(error)
      });
    },
    removeVacationHists: function() {
      let keys = this.$refs.tuiGrid.invoke('getCheckedRows')
      let vacationHistIds = []
      let addDaysUser;
      let addDays = 0
      keys.forEach((item, i) => {
        if(item.useYn != "N"){
          vacationHistIds.push(item.vacationHistId)
          addDaysUser = item.userId
          addDays = addDays + item.days
        }
      });

      const data = { vacationHistIds : vacationHistIds }
      const headers = { "Content-Type": "application/json" }
      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.put(`${baseURI}/removeVacationHists`, data, headers)
      .then(res => {
        this.addUserDays(addDaysUser, addDays)

        this.$emit('reRender', 'UserList')
        this.$emit('reRender', 'VacationInput')
        this.$emit('reRender', 'VacationHistList')
      })

    },
    addUserDays: function(userId, days) {
      let data = { userId : userId, days : days}
      const baseURI = 'http://localhost:8080/api/user';
      this.$http.put(`${baseURI}/addUserDays`, data)
      .then(result => {
        this.$emit('reRender', 'UserList')
      })
      .catch(error => {
        console.log(error)
      });
    }
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '휴가내역ID', name: 'vacationHistId' },
        { header: '휴가코드',   name: 'vacationCode' },
        { header: '휴가명',     name: 'vacationName' },
        { header: '시작일',     name: 'startDate'},
        { header: '종료일',     name: 'endDate'},
        { header: '사용일수',   name: 'days'},
        { header: '사용',       name: 'useYn' }
      ],
      data: [],
      rowHeaders: ['checkbox']
    }
  },
  beforeMount(){
    this.getVacationHists()
  }
}

</script>

<style>
button {
  width: 100%;
  height: 50px;
}
</style>
