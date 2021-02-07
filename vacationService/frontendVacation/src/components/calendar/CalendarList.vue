<template>
  <section>
    <full-calendar :key="calendarListKey" :events="fcEvents" locale="en"></full-calendar>
  </section>
</template>

<script>
/* eslint-disable */
import FullCalendar from 'vue-fullcalendar'

let calendarEvents = []
let items = []

export default {
  props: ['users','vacationHists'],
  data () {
    return {
      calendarListKey : 'calendarListKey0',
      fcEvents : []
    }
  },
  components: {
    'full-calendar': FullCalendar
  },
  methods: {
  },
  created() {
    items = []
    let users = this.users
    let vacationHists = this.vacationHists


    console.log("TIK")
    console.log(users)
    console.log(vacationHists)

    const map = new Map()


    vacationHists.forEach((vacationHist, i) => {
      users.forEach((user, i) => {
        if(vacationHist.userId == user.userId){
          vacationHist.userName = user.userName
        }
      });
    });
    //console.log(vacationHists)

    vacationHists.forEach((vacationHist, index)=>{
      const titleStr = vacationHist.userName+'('+vacationHist.vacationName+')'
      const startStr = vacationHist.startDate.substr(0,4)+'-'+
                      vacationHist.startDate.substr(4,2)+'-'+
                      vacationHist.startDate.substr(6,2)
      const endStr =  vacationHist.endDate.substr(0,4)+'-'+
                      vacationHist.endDate.substr(4,2)+'-'+
                      vacationHist.endDate.substr(6,2)
      console.log("titleStr :"  + titleStr)
      console.log("startStr :"  + startStr)
      console.log("endStr :"  + endStr)

      if(vacationHist.useYn == "Y"){
        items.push({
            title: titleStr,
            start: startStr,
            end: endStr
          })
      }
    })

    console.log("items")
    console.log(items)
    this.fcEvents = items

  }
}

</script>

<style scoped>
button {
  width: 100%;
  height: 50px;
}

.vueconf {
  background-color: #00a65a !important;
}
</style>
