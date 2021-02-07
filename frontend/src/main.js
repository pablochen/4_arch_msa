// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuetify from 'vuetify'
import DaySpanVuetify from 'dayspan-vuetify'
import App from './App'
import router from './router'
import axios from 'axios'

Vue.prototype.$http = axios

Vue.config.productionTip = false

Vue.use(Vuetify);

Vue.use(DaySpanVuetify, {
  methods: {
    getDefaultEventColor: () => '#1976d2'
  }
});


// eslint-disable-next-line
var app = new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

global.vm = app;
