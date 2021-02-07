import Vue from 'vue'
import Router from 'vue-router'
import MasterApp from '@/components/MasterApp'

Vue.config.productionTip = false

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'MasterApp',
      component: MasterApp
    }
  ]
})
