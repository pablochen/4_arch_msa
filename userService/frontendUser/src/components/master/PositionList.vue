<template>
  <section>
    <h3>직책정보</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns" :rowHeaders="gridProps.rowHeaders"/>
    <button v-on:click="setDepts">수정</button>
    <button v-on:click="removeDepts">삭제</button>
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
    getPositionList: function() {
      const baseURI = 'http://localhost:8080/api/user';
      this.$http.get(`${baseURI}/getPositions`)
      .then((result) => {
        this.$refs.tuiGrid.invoke('appendRows', result.data.content);
        this.$emit('rePositionItems', result.data.content)
      }).catch(error=>{
        console.log(error)
      });
    },
    removeDepts: function() {
        let keys = this.$refs.tuiGrid.invoke('getCheckedRows')
        let positionIds = []
        for (let i in keys){ positionIds.push(keys[i].positionId)}
        const data = {positionIds : positionIds}
        console.log(data);
        const baseURI = 'http://localhost:8080/api/user';
        this.$http.put(`${baseURI}/removePositions`, data)
        .then((result) => {
          this.$refs.tuiGrid.invoke('removeCheckedRows');
          this.$emit('reRender', 'UserInput')
          this.$emit('reRender', 'UserList')
        })
        .catch(error=>{
          console.log(error)
        });
    },
    setDepts: function() {
        let keys = this.$refs.tuiGrid.invoke('getModifiedRows')
        let data = keys.updatedRows
        const baseURI = 'http://localhost:8080/api/user';
        this.$http.put(`${baseURI}/setPositions`, data)
        .then((result) => {
          this.$refs.tuiGrid.invoke('removeCheckedRows');
          this.$emit('reRender', 'UserInput')
          this.$emit('reRender', 'UserList')
        })
        .catch(error=>{
          console.log(error)
        });
    }
  },
  beforeMount(){
    this.getPositionList()
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '직책ID',   name: 'positionId',    editor: 'text' },
        { header: '직책코드', name: 'positionCode',  editor: 'text' },
        { header: '직책명',   name: 'positionName',  editor: 'text' }
      ],
      data: [],
      rowHeaders: ['checkbox'],
      bodyHeight: 300
    }
  }
}

</script>

<style>
grid {
  height: 300px;
}
</style>
