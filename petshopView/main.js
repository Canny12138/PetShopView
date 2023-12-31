import App from './App'

import uView from "uview-ui";
Vue.use(uView);

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
// Vue.prototype.$baseUrl = "http://10.0.2.2:9001"
// Vue.prototype.$baseUrl = "http://127.0.0.1:9001"
// Vue.prototype.$baseUrl = "http://localhost:88/renren-admin"
Vue.prototype.$baseUrl = "/api"
App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif