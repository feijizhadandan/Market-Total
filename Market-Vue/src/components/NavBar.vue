<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
      <router-link class="navbar-brand" :to="{ name: 'home' }">VueWeb</router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarText"
        aria-controls="navbarText"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'home' }">首页</router-link>
          </li>
          <!-- <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'personal' }">个人中心</router-link>
          </li> -->
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'market' }">商城</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'cart' }">购物车</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'management' }">管理</router-link>
          </li>
        </ul>
        <ul class="navbar-nav" v-if="!store.state.personalInfo.hasLogin">
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'login' }">登录</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'register' }">注册</router-link>
          </li>
        </ul>
        <ul class="navbar-nav" v-if="store.state.personalInfo.hasLogin">
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'login' }" @click="logout">注销</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import store from '@/store';
import axios from 'axios';
import { ElMessage } from 'element-plus';

// 注销账号
function logout() {
  axios
    .post(
      '/api/out',
      {},
      {
        headers: {
          Authorization: store.state.personalInfo.token,
        },
      }
    )
    .then(res => {
      // 注销成功
      if (res.data.code === 200) {
        store.commit('changeLoginStatus', false);
        localStorage.removeItem('token');
        ElMessage({
          type: res.data.code === 200 ? 'success' : 'error',
          message: `${res.data.msg}`,
        });
      } else {
        ElMessage({
          type: 'warning',
          message: `登录信息已过期`,
        });
      }
    });
}
</script>

<!-- scoped: 当前组件所写的css选择器不会影响到其他组件 -->
<style scoped></style>
