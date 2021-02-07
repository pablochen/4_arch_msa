import Vue from 'vue'
import Router from 'vue-router'
import MasterApp from '@/components/MasterApp'
import VacationApp from '@/components/VacationApp'
import CalendarApp from '@/components/CalendarApp'

Vue.config.productionTip = false

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/masterApp',
      name: 'MasterApp',
      component: MasterApp
    },
    {
      path: '/vacationApp',
      name: 'VacationApp',
      component: VacationApp
    },
    {
      path: '/calendarApp',
      name: 'CalendarApp',
      component: CalendarApp
    }
  ]
})
