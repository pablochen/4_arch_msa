<template>
  <section>
    <h3>휴가 신청</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns" @afterChange="calDays"/>
    <button v-on:click="addVacationHist">저장</button>
  </section>
</template>

<script>
/* eslint-disable */
import 'tui-grid/dist/tui-grid.css'
import { Grid } from '@toast-ui/vue-grid'

export default {
  props: ['vacationItems','selectUserInfo'],
  data() {
     return {
     }
  },
  components: {
    'grid': Grid
  },
  methods: {
    setUser: function(user) {
      this.$refs.tuiGrid.invoke('setValue', 0, 'userId', user.userId)
      this.$refs.tuiGrid.invoke('setValue', 0, 'userCode', user.userCode)
      this.$refs.tuiGrid.invoke('setValue', 0, 'userName', user.userName)
      this.$refs.tuiGrid.invoke('setValue', 0, 'remainVacCnt', user.remainVacCnt)
    },
    addVacationHist: function() {
      const vacaRow = this.$refs.tuiGrid.invoke('getData')[0];

      if(vacaRow.userId==null || vacaRow.userCode==null || vacaRow.userName==null || vacaRow.remainVacCnt==null ||
        vacaRow.startDate==null || vacaRow.endDate==null || vacaRow.vacationId==null || vacaRow.days==null){
          alert("모든 값 필요")
          return
      }

      if(Number(vacaRow.startDate) > Number(vacaRow.endDate)){
          alert("시작일이 종료일보다 늦습니다")
          return
      }

      if(Number(vacaRow.remainVacCnt) < Number(vacaRow.days)){
          alert("연차 부족")
          return
      }

      let vacaId = vacaRow.vacationId.split(':')[0]

      const data = {
        userId : vacaRow.userId,
        vacationId : Number(vacaId),
        startDate : vacaRow.startDate,
        endDate : vacaRow.endDate,
        days : vacaRow.days
      }

      const headers = {
        "Content-Type": "application/json"
      }

      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.post(`${baseURI}/addVacationHist`, data, headers)
      .then(res => {
        let userId = res.data.userId
        let days = res.data.days
        this.addUserDays(userId, days)
        this.$emit('reRender', 'UserList')
        this.$emit('reRender', 'VacationInput')
        this.$emit('reRender', 'VacationHistList')
      }).catch(error=>{
        console.log(error)
      });
    },
    calDays: function() {
      let thisRow = this.$refs.tuiGrid.invoke('getRow', 0)
      if(thisRow.startDate != null && thisRow.startDate != '' &&
         thisRow.endDate != null && thisRow.endDate != '' &&
         thisRow.vacationId != null && thisRow.vacationId != '' &&
         Number(thisRow.startDate) < Number(thisRow.endDate) ){
        let dateBetween = this.getDateBetween(thisRow.startDate, thisRow.endDate)
        let days = Number(thisRow.vacationId.split(':')[1])

        this.$refs.tuiGrid.invoke('setValue', 0, 'days', dateBetween*days, false)
      }
    },
    getDateBetween: function(startDate, endDate){
      /* 공휴일, 휴일 제외 로직 추가하면 좋음 */
      var startObj = new Date(startDate.substr(0,4), startDate.substr(4,2), startDate.substr(6,2))
      var endObj = new Date(endDate.substr(0,4), endDate.substr(4,2), endDate.substr(6,2))
      var betweenDay = (endObj.getTime() - startObj.getTime())/1000/60/60/24 + 1;
      return betweenDay;
    },
    addUserDays: function(userId, days) {
        let data ={ userId : userId, days : -1*days}
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
    let vacations = []
    this.vacationItems.forEach((vacation)=>{
      vacations.push({text:vacation.vacationName, value:vacation.vacationId + ":" + vacation.days});
    })

    this.gridProps = {
      columns: [
        { header: '사원ID',   name: 'userId'},
        { header: '사번',     name: 'userCode'},
        { header: '사원명',   name: 'userName'},
        { header: '잔여연차', name: 'remainVacCnt'},
        { header: '시작일',   name: 'startDate',    editor: 'text',},
        { header: '종료일',   name: 'endDate',      editor: 'text'},
        /* 그리드 오류 : 휴가선택 셀 포커스 아웃 시, 헤더가 아닌 밸류 세팅 */
        { header: '휴가선택', name: 'vacationId',   editor: { type: 'select', options: { listItems: vacations } }, onAfterChange(ev) { return; /* console.log(ev)} */ } },
        { header: '사용일수', name: 'days'}
      ],
      data: []
    }
  },
  mounted() {
    this.$refs.tuiGrid.invoke('appendRow', [], [])
  }
}
</script>

<style scoped>
button {
  width: 100%;
  height: 50px;
}
</style>
