<template>
  <section>
    <h3>부서정보</h3>
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
    getDepts: function() {
      const baseURI = 'http://localhost:8080/api/user';
      this.$http.get(`${baseURI}/getDepts`)
      .then((result) => {
        this.$refs.tuiGrid.invoke('appendRows', result.data.content)
        this.$emit('reDeptItems', result.data.content)
      }).catch(error=>{
        console.log(error)
      });
    },
    removeDepts: function() {
        let keys = this.$refs.tuiGrid.invoke('getCheckedRows')
        let deptIds = []
        for (let i in keys){ deptIds.push(keys[i].deptId)}
        const data = {deptIds : deptIds}
        console.log(data);
        const baseURI = 'http://localhost:8080/api/user';
        this.$http.put(`${baseURI}/removeDepts`, data)
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
        this.$http.put(`${baseURI}/setDepts`, data)
        .then((result) => {
          this.$emit('reRender', 'UserInput')
          this.$emit('reRender', 'UserList')
        })
        .catch(error=>{
          console.log(error)
        });
    }
  },
  beforeMount(){
    this.getDepts()
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '부서ID',   name: 'deptId'},
        { header: '부서코드', name: 'deptCode', editor: 'text'},
        { header: '부서명',   name: 'deptName', editor: 'text'}
      ],
      data: [],
      rowHeaders: ['checkbox']
    }
  }
}

</script>

<style>
</style>
