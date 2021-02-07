
<template>
  <div>
    <VacationHeader></VacationHeader>
    <UserList :key="userListKey"
        v-on:setUserInfo="setUserInfo"
        v-on:reRender="reRender"
        ref="ulIn"></UserList>
    <VacationInput :key="vacationInputKey"
        v-bind:vacationItems="vacationItems"
        v-bind:selectUserInfo="selectUserInfo"
        v-on:reRender="reRender"
        ref="vacIn"></VacationInput>
    <VacationHistList :key="vacationHistListKey"
        v-on:reRender="reRender"
        ref="vacHistIn"></VacationHistList>
    <VacationFooter></VacationFooter>
  </div>
</template>

<script>
/* eslint-disable */
import VacationHeader from './vacation/VacationHeader.vue'
import UserList from './vacation/UserList.vue'
import VacationInput from './vacation/VacationInput.vue'
import VacationHistList from './vacation/VacationHistList.vue'
import VacationFooter from './vacation/VacationFooter.vue'

export default {
  name: 'VacationApp',
  data() {
    return {
      userListKey: 'userListKey0',
      vacationInputKey: 'vacationInputKey0',
      vacationItems: [],
      selectUserInfo: [],
      vacationHistListKey: 'vacationHistListKey0'
    };
  },
  components: {
    'VacationHeader': VacationHeader,
    'UserList': UserList,
    'VacationInput': VacationInput,
    'VacationHistList': VacationHistList,
    'VacationFooter': VacationFooter
  },
  methods: {
    getVacations: function() {
      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.get(`${baseURI}/getVacations`)
      .then((result) => {
        this.vacationItems = result.data.content
        this.reRender('VacationInput')
      }).catch(error=>{
        console.log(error)
      });
    },
    reRender(compName) {
      switch (compName) {
        case 'UserList':
          console.log('ul')
          this.userListKey += 1
          break;
        case 'VacationInput':
          console.log('vaca')
          this.vacationInputKey += 1
          break;
        case 'VacationHistList':
          this.vacationHistListKey += 1
          break;
        default:
      }
    },
    setUserInfo(key) {
      this.selectUserInfo = key
      this.$refs.vacIn.setUser(key)
      this.$refs.vacHistIn.getVacationHists(key)
    }
  },
  created() {
    this.getVacations()
  }
}
</script>

<style>
</style>
