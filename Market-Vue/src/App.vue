<template>
  <div id="background">
    <img id="img" :src="imgSrc" />
  </div>
  <div id="content">
    <NavBar />
    <!-- NavBar的路由展示区 -->
    <router-view />
  </div>
</template>

<!-- 引入Bootstrap -->
<script setup>
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import { onMounted, ref } from 'vue';
import NavBar from './components/NavBar.vue';
import router from './router';
import store from './store';

// 背景图片url
const imgSrc = ref('http://47.106.122.140:19000/store/productphoto_1671025864398_background2.png');

// 刷新后用于验证用户登录状态
onMounted(() => {
  axios
    .post(
      '/api/check',
      {},
      {
        headers: {
          Authorization: store.state.personalInfo.token,
        },
      }
    )
    .then(res => {
      // 已登录
      if (res.data.code === 200) {
        // 修改已登录状态
        store.commit('changeLoginStatus', true);
      }
      // 登录状态已过期
      // 会导致一个情况：没登录状态下刷新都会跳转到主页面
      else {
        localStorage.removeItem('token');
        store.commit('changeLoginStatus', false);
        router.push('/');
      }
    });
  store.state.personalInfo.token;
});
</script>

<style>
#background {
  /* 宽高100%是为了div铺满屏幕 */
  width: 100%;
  height: 100%;
  /* 透明度 */
  opacity: 0.2;
  z-index: 2;
  position: absolute;
  /* 穿透鼠标事件 */
  pointer-events: none;
  /* 让背景元素不随滚轮滚动 */
  position: fixed;
}
#img {
  /* 宽高100%是为了图片铺满div */
  width: 100%;
  height: 100%;
}
#content {
  z-index: 1;
}
</style>
