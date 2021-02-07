<template>
  <section>
    <h3>직책추가</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns"/>
    <button v-on:click="addPosition">저장</button>
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
    addPosition: function() {
      const positionRow = this.$refs.tuiGrid.invoke('getData')[0];

      if(positionRow.positionCode==null || positionRow.positionName==null){
          alert("모든 값 필요")
          return
        }

      const data = {
        positionCode : positionRow.positionCode,
        positionName : positionRow.positionName
      }

      const baseURI = 'http://localhost:8080/api/user';
      this.$http.post(`${baseURI}/addPosition`, data)
      .then(res => {
        this.$emit('reRender', 'PositionList')
        this.$emit('reRender', 'PositionInput')
      }).catch(error=>{
        console.log(error)
      });
    }
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '직책코드', name: 'positionCode', editor: 'text' },
        { header: '직책명',   name: 'positionName', editor: 'text' }
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
