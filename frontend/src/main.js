import { createApp } from 'vue'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'

const pinia = createPinia()
const vuetify = createVuetify({
  components,
  directives,
})
const app = createApp(App)

app.use(router)
    .use(pinia)
    .use(vuetify)
    .mount('#app')
