import './assets/main.css'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import VueTheMask from 'vue-the-mask';
import Vuelidate from 'vuelidate'
import App from './App.vue'
import router from './router'

library.add(fas)


const app = createApp(App)

app.use(VueTheMask);

app.use(ElementPlus)
app.use(router)
app.use(Vuelidate)

app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app')
