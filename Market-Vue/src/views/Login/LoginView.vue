<template>
  <ContentCard>
    <div class="row">
      <div class="col-md-6" id="login-card">
        <h2 id="title">{{ tips }}</h2>
        <div class="mb-3">
          <label for="username" class="form-label">账号</label>
          <input type="text" class="form-control" id="username" placeholder="请输入用户账号" v-model="username" />
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">密码</label>
          <input type="password" class="form-control" id="password" placeholder="请输入用户密码" v-model="password" />
        </div>
        <div id="operation">
          <button type="submit" class="btn btn-primary" style="margin: 0 auto" @click="loginClick">登录</button>
          <!-- 跳转注册 -->
          <router-link class="btn-link" :to="{ name: 'register' }">注册</router-link>
        </div>
      </div>
    </div>
  </ContentCard>
</template>

<script setup>
import ContentCard from '@/components/ContentCard.vue';
import router from '@/router';
import store from '@/store';
import { ref, inject } from 'vue';
import { ElMessage } from 'element-plus';

// 注入全局 axios
const axios = inject('axios');

// 登录提示信息
const tips = ref('Please Login, My Boy');

// 用户输入的账号密码
const username = ref('');
const password = ref('');

function loginClick() {
  axios
    .post('/api/login', {
      userName: username.value,
      password: password.value,
    })
    .then(res => {
      // 登陆成功
      if (res.data.code === 200) {
        store.commit('setToken', res.data.data.token);
        store.commit('changeLoginStatus', true);
        ElMessage({
          type: res.data.code === 200 ? 'success' : 'error',
          message: `登录成功`,
        });
        // 登录成功后，跳转至主页面
        router.push('/');
      }
      // 登录失败
      else {
        ElMessage({
          type: 'error',
          message: `${res.data.msg}`,
        });
      }
    });
}
</script>

<style scoped>
#title {
  text-align: center;
  position: relative;
  bottom: 80px;
  font-weight: bold;
}
#login-card {
  margin: 200px auto;
}

#operation {
  margin-top: 40px;
  display: flex;
  align-items: center;
}
</style>
