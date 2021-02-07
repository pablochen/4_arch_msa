
<template>
  <div>
    <MasterHeader></MasterHeader>
    <UserInput :key="userInputKey" v-on:reRender="reRender" v-bind:deptItems="deptItems" v-bind:positionItems="positionItems"></UserInput>
    <UserList :key="userListKey"></UserList>
    <div style="width: 30%; float:left; padding: 15px;">
      <DeptInput :key="deptInputKey" v-on:reRender="reRender"></DeptInput>
      <DeptList :key="deptListKey" v-on:reDeptItems="reDeptItems"></DeptList>
    </div>
    <div style="width: 30%; float:left; padding: 15px;">
      <PositionInput :key="positionInputKey" v-on:reRender="reRender"></PositionInput>
      <PositionList :key="positionListKey" v-on:rePositionItems="rePositionItems"></PositionList>
    </div>
    <div style="width: 30%; float:left; padding: 15px;">
      <VacationInput :key="vacationInputKey" v-on:reRender="reRender"></VacationInput>
      <VacationList :key="vacationListKey"></VacationList>
    </div>
    <MasterFooter></MasterFooter>
  </div>
</template>

<script>
/* eslint-disable */
import MasterHeader from './master/MasterHeader.vue'
import UserList from './master/UserList.vue'
import UserInput from './master/UserInput.vue'
import DeptList from './master/DeptList.vue'
import DeptInput from './master/DeptInput.vue'
import PositionList from './master/PositionList.vue'
import PositionInput from './master/PositionInput.vue'
import VacationList from './master/VacationList.vue'
import VacationInput from './master/VacationInput.vue'
import MasterFooter from './master/MasterFooter.vue'

export default {
  name: 'MasterApp',
  data() {
    return {
      userInputKey: 'userInputKey0',
      userListKey: 'userListKey0',
      deptInputKey: 'deptInputKey0',
      deptListKey: 'deptListKey0',
      deptItems: [],
      positionInputKey: 'positionInputKey0',
      positionListKey: 'positionListKey0',
      positionItems: [],
      vacationInputKey: 'vacationInputKey0',
      vacationListKey: 'vacationListKey0'
    };
  },
  components: {
    'MasterHeader': MasterHeader,
    'UserInput': UserInput,
    'UserList': UserList,
    'DeptInput': DeptInput,
    'DeptList': DeptList,
    'PositionInput': PositionInput,
    'PositionList': PositionList,
    'VacationInput': VacationInput,
    'VacationList': VacationList,
    'MasterFooter': MasterFooter
  },
  methods: {
    reRender(compName) {
      switch (compName) {
        case 'UserList':
          this.userListKey += 1
          break;
        case 'UserInput':
          this.userInputKey += 1
          break;
        case 'DeptList':
          this.deptListKey += 1
          break;
        case 'DeptInput':
          this.deptInputKey += 1
          break;
        case 'PositionList':
          this.positionListKey += 1
          break;
        case 'PositionInput':
          this.positionInputKey += 1
          break;
        case 'VacationList':
          this.vacationListKey += 1
          break;
        case 'VacationInput':
          this.vacationInputKey += 1
          break;
        default:
      }
    },
    reDeptItems(depts){
      this.deptItems = depts
      this.reRender('UserInput')
    },
    rePositionItems(positions){
      this.positionItems = positions
      this.reRender('UserInput')
    }
  }
}
</script>

<style>
</style>
