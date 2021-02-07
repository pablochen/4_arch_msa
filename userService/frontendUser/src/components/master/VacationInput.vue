<template>
  <section>
    <h3>휴가추가</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns"/>
    <button v-on:click="addVacation">저장</button>
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
    addVacation: function() {
      const vacationRow = this.$refs.tuiGrid.invoke('getData')[0];
      if(vacationRow.vacationCode==null || vacationRow.vacationName==null || vacationRow.days==null){
          alert("모든 값 필요")
          return
        }

      const data = {
        vacationCode : vacationRow.vacationCode,
        vacationName : vacationRow.vacationName,
        days         : vacationRow.days
      }

      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.post(`${baseURI}/addVacation`, data)
      .then(res => {
        this.$emit('reRender', 'VacationList')
        this.$emit('reRender', 'VacationInput')
      }).catch(error=>{
        console.log(error)
      });
    }
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '휴가코드', name: 'vacationCode',  editor: 'text' },
        { header: '휴가명',   name: 'vacationName',  editor: 'text' },
        { header: '소요일수', name: 'days',          editor: 'text' }
      ],
      data: []
    }
  },
  mounted() {
    this.$refs.tuiGrid.invoke('appendRow', [], []);
  }
}
</script>

<style scoped>
button {
  width: 100%;
  height: 50px;
}
</style>
