<template>
  <section>
    <h3>휴가정보</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns" :rowHeaders="gridProps.rowHeaders"/>
    <button v-on:click="setVacations">수정</button>
    <button v-on:click="removeVacations">삭제</button>
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
    getVacationList: function() {
      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.get(`${baseURI}/getVacations`)
      .then((result) => {
        this.$refs.tuiGrid.invoke('appendRows', result.data.content);
      }).catch(error=>{
        console.log(error)
      });
    },
    removeVacations: function() {
      let keys = this.$refs.tuiGrid.invoke('getCheckedRows')
      let vacationIds = []
      for (let i in keys){ vacationIds.push(keys[i].vacationId)}
      const data = {vacationIds : vacationIds}
      console.log(data);
      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.put(`${baseURI}/removeVacations`, data)
      .then((result) => {
        this.$refs.tuiGrid.invoke('removeCheckedRows');
      })
      .catch(error=>{
        console.log(error)
      });
    },
    setVacations: function() {
      let keys = this.$refs.tuiGrid.invoke('getModifiedRows')
      let data = keys.updatedRows

      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.put(`${baseURI}/setVacations`, data)
      .then((result) => {
        console.log(result)
      })
      .catch(error=>{
        console.log(error)
      });
    }
  },
  beforeMount(){
    this.getVacationList()
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '휴가ID',   name: 'vacationId' },
        { header: '휴가코드', name: 'vacationCode', editor: 'text'},
        { header: '휴가명',   name: 'vacationName', editor: 'text'},
        { header: '소요일수', name: 'days',         editor: 'text'}
      ],
      data: [],
      rowHeaders: ['checkbox']
    }
  }
}

</script>

<style>
</style>
