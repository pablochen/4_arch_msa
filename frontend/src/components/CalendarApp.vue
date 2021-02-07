
<template>
  <div>
    <CalendarHeader></CalendarHeader>
    <CalendarList :key="calendarListKey"
      v-bind:users="users"
      v-bind:vacationHists="vacationHists"
      ></CalendarList>
    <CalendarFooter></CalendarFooter>
  </div>
</template>

<script>
/* eslint-disable */
import CalendarHeader from './calendar/CalendarHeader.vue'
import CalendarList from './calendar/CalendarList.vue'
import CalendarFooter from './calendar/CalendarFooter.vue'

export default {
  name: 'CalendarApp',
  data() {
    return {
      calendarListKey: 'calendarListKey0',
      users: [],
      vacationHists: []
    };
  },
  components: {
    'CalendarHeader': CalendarHeader,
    'CalendarList': CalendarList,
    'CalendarFooter': CalendarFooter
  },
  methods: {
    getUsers: function() {
      const baseURI = 'http://localhost:8080/api/user';
      this.$http.get(`${baseURI}/getUsers`)
      .then((result) => {
        this.users = result.data.content
        this.reRender('CalendarList')
      }).catch(error=>{
        console.log(error)
      });
    },
    getVacationHists: function(key) {
      const data = { params:{ userId : 0 } }
      const baseURI = 'http://localhost:8081/api/vacation';
      this.$http.get(`${baseURI}/getVacationHists`, data)
      .then((result) => {
        this.vacationHists = result.data.content
        this.reRender('CalendarList')
      }).catch(error=>{
        console.log(error)
      });
    },
    reRender(compName) {
      switch (compName) {
        case 'CalendarList':
          this.calendarListKey += 1
          break;
        default:
      }
    }
  },
  created() {
    this.getUsers();
    this.getVacationHists();
  }
}
</script>

<style>
</style>
