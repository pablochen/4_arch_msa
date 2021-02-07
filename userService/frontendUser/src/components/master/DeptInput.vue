<template>
  <section>
    <h3>부서추가</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns"/>
    <button v-on:click="addDept">저장</button>
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
    addDept: function() {
      const deptRow = this.$refs.tuiGrid.invoke('getData')[0];

      if(deptRow.deptCode==null || deptRow.deptName==null){
          alert("모든 값 필요")
          return
        }

      const data = {
        deptCode : deptRow.deptCode,
        deptName : deptRow.deptName
      }

      const baseURI = 'http://localhost:8080/api/user';
      this.$http.post(`${baseURI}/addDept`, data)
      .then(res => {
        this.$emit('reRender', 'DeptList')
        this.$emit('reRender', 'DeptInput')
      }).catch(error=>{
        console.log(error)
      });
    }
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '부서코드', name: 'deptCode', editor: 'text' },
        { header: '부서명',   name: 'deptName', editor: 'text' }
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
