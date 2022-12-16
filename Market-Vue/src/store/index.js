import { createStore } from 'vuex'
import personalInfo from './PersonalInfo/index.js'

export default createStore({
  state: {
    buyRecordUrl: "http://feijizhadan.cn:9999/jmreport/view/762276277052473344",
    browseRecordUrl: "http://feijizhadan.cn:9999/jmreport/view/762276609908244480",
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    personalInfo
  }
})
