<template>
  <section>
    <h3>사원추가</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns"/>
    <button v-on:click="addUser">저장</button>
  </section>
</template>

<script>
/* eslint-disable */
import 'tui-grid/dist/tui-grid.css'
import { Grid } from '@toast-ui/vue-grid'

export default {
  props: ['deptItems', 'positionItems'],
  data() {
     return {
       depts: this.deptItems,
       positions: this.positionItems
     }
  },
  components: {
    'grid': Grid
  },
  methods: {
    addUser: function() {
      const userRow = this.$refs.tuiGrid.invoke('getData')[0];

      if(userRow.userCode==null || userRow.userName==null || userRow.deptId==null ||
          userRow.positionId==null || userRow.joinDate==null){
          alert("모든 값 필요")
          return
      }

      const data = {
        userCode :   userRow.userCode,
        userName :   userRow.userName,
        deptId :     Number(userRow.deptId),
        positionId : Number(userRow.positionId),
        joinDate :   userRow.joinDate
      }

      const baseURI = 'http://localhost:8080/api/user';
      this.$http.post(`${baseURI}/addUser`, data)
      .then(res => {
        this.$emit('reRender', 'UserList')
        this.$emit('reRender', 'UserInput')
      }).catch(error=>{
        console.log(error)
      });
    }
  },
  created() {
    let deptItems = [];
    this.depts.forEach((dept)=>{
      deptItems.push({text:dept.deptName, value:String(dept.deptId)});
    })

    let positionItems = [];
    this.positions.forEach((position)=>{
      positionItems.push({text:position.positionName, value:String(position.positionId)});
    })

    this.gridProps = {
      columns: [
        { header: '사번',     name: 'userCode',     editor: 'text' },
        { header: '사원명',   name: 'userName',     editor: 'text' },
        { header: '부서',     name: 'deptId',       editor: { type: 'select', options: { listItems: deptItems } },     formatter: 'listItemText'},
        { header: '직책',     name: 'positionId',   editor: { type: 'select', options: { listItems: positionItems } }, formatter: 'listItemText'},
        { header: '입사일',   name: 'joinDate',     editor: 'text' }
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
