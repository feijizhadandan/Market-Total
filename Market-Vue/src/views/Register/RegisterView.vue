<template>
  <ContentCard>
    <div class="row">
      <div class="col-md-6" id="login-card">
        <h2 id="title">注册中心</h2>

        <el-form ref="formRef" :model="registerInfo" :rules="registerRules" label-width="80px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input placeholder="请输入用户名" v-model="registerInfo.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input placeholder="请输入密码" type="password" v-model="registerInfo.password"></el-input>
          </el-form-item>
          <el-form-item label="重复密码" prop="repeatPassword">
            <el-input placeholder="请重复输入密码" type="password" v-model="repeatPassword"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input placeholder="请输入手机号" v-model="registerInfo.phone"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input placeholder="请输入邮箱（用于发货提醒）" v-model="registerInfo.email"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="registerInfo.sex">
              <el-radio label="0">男</el-radio>
              <el-radio label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="clickSubmit">Create</el-button>
            <el-button @click="resetForm">Reset</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </ContentCard>
</template>

<script setup>
import ContentCard from '@/components/ContentCard.vue';
import router from '@/router';
import { ref, inject, unref } from 'vue';
import { ElMessage } from 'element-plus';

// 注入全局 axios
const axios = inject('axios');

// 获取HTML元素中的DOM元素
const formRef = ref(null);

// 注册信息
const registerInfo = ref({
  username: '',
  password: '',
  phoneNumber: null,
  email: '',
  sex: '0',
});
// 重复密码
const repeatPassword = ref('');
// 重复密码校验
const validatePassword = (rule, value, callback) => {
  if (repeatPassword.value === '') {
    callback(new Error('请再次输入密码'));
    // password 是表单上绑定的字段
  } else if (registerInfo.value.password !== repeatPassword.value) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};
// 注册信息校验
const registerRules = ref({
  username: [{ required: true, message: '请输入账号名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  repeatPassword: [{ required: true, validator: validatePassword, trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/, message: '手机号不合法，请重新输入', trigger: 'blur' },
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { pattern: /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/, message: '请输入正确的邮箱', trigger: 'blur' },
  ],
});

// 点击注册后的校验
const clickSubmit = async () => {
  const form = unref(formRef);
  if (!form) {
    return;
  }
  try {
    await form.validate();
    submitInfo();
  } catch (err) {
    ElMessage({
      type: 'error',
      message: `填写信息有误`,
    });
  }
};

// 提交注册信息
function submitInfo() {
  axios.post('/api/register', registerInfo.value).then(res => {
    // 注册成功
    if (res.data.code === 200) {
      ElMessage({
        type: res.data.code === 200 ? 'success' : 'error',
        message: `${res.data.msg}`,
      });
      router.push('/login');
    }
    // 注册失败(可能是名字重复了)
    else {
      ElMessage({
        type: 'error',
        message: `${res.data.msg}`,
      });
    }
  });
}
// 重置注册信息
function resetForm() {
  const form = unref(formRef);
  form.resetFields();
  repeatPassword.value = '';
}
</script>

<style scoped>
#title {
  text-align: center;
  position: relative;
  bottom: 40px;
  font-weight: bold;
}
#login-card {
  margin: 100px auto;
}
#operation {
  margin-top: 40px;
  display: flex;
  align-items: center;
}
#submit-button {
  margin: 0 auto;
}
</style>
