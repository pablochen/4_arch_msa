<template>
  <section>
    <h3>사원정보</h3>
    <grid ref="tuiGrid" :data="gridProps.data" :columns="gridProps.columns" :rowHeaders="gridProps.rowHeaders"/>
    <button v-on:click="setUsers">수정</button>
    <button v-on:click="removeUsers">삭제</button>
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
    getUsers: function() {
      const baseURI = 'http://localhost:8080/api/user';
      this.$http.get(`${baseURI}/getUsers`)
      .then((result) => {
        this.$refs.tuiGrid.invoke('appendRows', result.data.content);
      })
    },
    removeUsers: function() {
        let keys = this.$refs.tuiGrid.invoke('getCheckedRows')
        let userIds = []
        for (let i in keys){ userIds.push(keys[i].userId)}
        const data = {userIds : userIds}
        console.log(data);
        const baseURI = 'http://localhost:8080/api/user';
        this.$http.put(`${baseURI}/removeUsers`, data)
        .then((result) => {
          this.$refs.tuiGrid.invoke('removeCheckedRows');
        })
        .catch(error=>{
          console.log(error)
        });
    },
    setUsers: function() {
        let keys = this.$refs.tuiGrid.invoke('getModifiedRows')
        let data = keys.updatedRows
        const baseURI = 'http://localhost:8080/api/user';
        this.$http.put(`${baseURI}/setUsers`, data)
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
    this.getUsers()
  },
  created() {
    this.gridProps = {
      columns: [
        { header: '사원ID',   name: 'userId'},
        { header: '사번',     name: 'userCode',      editor: 'text'},
        { header: '사원명',   name: 'userName',      editor: 'text'},
        { header: '부서코드', name: 'deptCode'},
        { header: '부서명',   name: 'deptName'},
        { header: '직책코드', name: 'positionCode'},
        { header: '직책명',   name: 'positionName'},
        { header: '보유연차', name: 'totalVacCnt',   editor: 'text'},
        { header: '잔여연차', name: 'remainVacCnt',  editor: 'text'},
        { header: '입사일',   name: 'joinDate',      editor: 'text'}
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
