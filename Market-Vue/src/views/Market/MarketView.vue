<template>
  <ContentCard>
    <el-form :inline="true" :model="keywordSearch" class="demo-form-inline" style="text-align: center; margin-top: 20px">
      <el-form-item label="商品关键字">
        <el-input v-model="keywordSearch.keyword" placeholder="关键字"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchByKeyword">查询</el-button>
      </el-form-item>
    </el-form>

    <div class="row">
      <div class="col-4" v-for="p in productList" :key="p" style="margin-bottom: 15px">
        <div class="card" style="margin: auto; width: 300px; background-color: rgb(220, 225, 230)">
          <!-- 判断 photoUrl 是否有值 -->
          <img
            :src="p.photoUrl === null ? null : p.photoUrl + '?x=' + new Date().getTime()"
            class="card-img-top img"
            alt="无参考图片"
            width="200"
            height="200"
          />
          <div class="card-body">
            <h5 class="card-title">{{ p.productName }}</h5>
            <span class="card-text">Price:</span>
            <span style="float: right">{{ p.productPrice }}￥</span>
            <br />
            <div class="option-button">
              <el-button type="primary" @click="getDetail(p)">查看详情</el-button>
              <el-button
                type="primary"
                style="float: right"
                @click="
                  dialogVisible = true;
                  cartForm.productId = p.id;
                "
              >
                添加至购物车
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-dialog align-center v-model="dialogVisible" title="请输入数量" width="30%">
      <span>
        <!-- 表单内容 -->
        <el-form ref="ruleFormRef" :model="cartForm" :rules="addCartRules" label-width="120px" class="demo-ruleForm">
          <el-form-item label="数量" prop="count">
            <el-input v-model="cartForm.count"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button
            @click="
              dialogVisible = false;
              cartForm.count = 1;
            "
          >
            取消
          </el-button>
          <el-button
            type="primary"
            @click="
              dialogVisible = false;
              addCartFunction();
              cartForm.count = 1;
            "
          >
            加入购物车
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 使用 ELMessage 替代 -->
    <el-dialog align-center v-model="detailVisible" title="商品详情" width="30%">
      <span>
        <!-- 商品信息 -->
        <el-form ref="ruleFormRef" v-model="detailData" label-width="120px" class="demo-ruleForm">
          <el-form-item label="参考图片" prop="productName">
            <img :src="detailData.photoUrl === null ? null : detailData.photoUrl + '?x=' + new Date().getTime()" alt="无参考图片" height="150" width="150" />
          </el-form-item>
          <el-form-item label="商品名称" prop="productName">
            <el-input v-model="detailData.productName" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="商品价格" prop="productPrice">
            <el-input v-model="detailData.productPrice" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="商品库存" prop="productCount">
            <el-input v-model="detailData.productCount" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="商品简介" prop="productIntroduction">
            <el-input type="textarea" v-model="detailData.productIntroduction" disabled="disabled"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="detailVisible = false">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </ContentCard>
</template>

<script setup>
import ContentCard from '@/components/ContentCard.vue';
import { onMounted, inject, ref } from 'vue';
import store from '@/store';
import { ElMessage } from 'element-plus';

// 注入全局 axios
const axios = inject('axios');
const productList = ref();

// 每次进入商城页面都要获取一次后端数据
onMounted(() => {
  axios.get('/api/product/buyer').then(res => {
    productList.value = res.data.data;
    // console.log(productList.value);
  });
});

const keywordSearch = ref({
  keyword: '',
});
// 根据关键字查询
function searchByKeyword() {
  if (keywordSearch.value.keyword === '') {
    axios.get('/api/product/buyer').then(res => {
      productList.value = res.data.data;
      // console.log(productList.value);
    });
  } else {
    axios.get('/api/product/buyer/search/' + keywordSearch.value.keyword).then(res => {
      productList.value = res.data.data;
      // console.log(productList.value);
    });
  }
}

// 商品详情
const detailData = ref();
// 响应窗口是否弹出
const detailVisible = ref(false);
// 向后台请求详情数据
function getDetail(p) {
  axios
    .get('/api/product/buyer/' + p.id, {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    .then(res => {
      if (res.data.code === 200) {
        detailData.value = res.data.data;
        detailVisible.value = true;
      } else {
        ElMessage({
          type: 'warning',
          message: `${res.data.msg}`,
        });
      }
    });
}

// 弹出框是否可见参数
const dialogVisible = ref(false);
// 添加数量
const cartForm = ref({
  count: 1,
  productId: 0,
});

// 表单验证规则
const addCartRules = ref({
  count: [
    { required: true, message: '请输入内容', trigger: 'blur' },
    { pattern: /^\+?[1-9]\d*$/, message: '请输入大于0的正整数' },
  ],
});
// 向后台提交新增购物车内容
function addCartFunction() {
  axios
    .post(
      '/api/cart',
      {
        count: cartForm.value.count,
        productId: cartForm.value.productId,
      },
      {
        headers: {
          Authorization: store.state.personalInfo.token,
        },
      }
    )
    .then(res => {
      ElMessage({
        type: res.data.code === 200 ? 'success' : 'error',
        message: `${res.data.msg}`,
      });
    });
}
</script>

<style scoped>
#cartNumber {
  width: 60px;
}
.option-button {
  margin-top: 15px;
}
</style>
