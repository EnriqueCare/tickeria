import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './assets/css/main.css' // <-- Asegúrate de que apunte aquí

const app = createApp(App)
app.use(router)
app.mount('#app')