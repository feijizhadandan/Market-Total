import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// Element-UI
import Element from "element-plus";
import 'element-plus/dist/index.css'
// axios
import axios from 'axios';
import VueAxios from 'vue-axios'

const app = createApp(App)
// 要在组件中使用 axios，必须使用 provide('axios', app.config.globalProperties.axios) 共享全局实例属性
app.use(Element).use(store).use(router).use(VueAxios, axios).provide('axios', app.config.globalProperties.axios).mount('#app')
