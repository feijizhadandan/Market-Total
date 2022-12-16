<template>
  <ContentCard>
    <el-table :data="cartList" style="width: 85%; margin: auto" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="60"></el-table-column>
      <el-table-column fixed prop="productName" label="商品名称" width="200"></el-table-column>
      <el-table-column prop="productPrice" label="单价" width="200"></el-table-column>
      <el-table-column prop="count" label="数量" width="200"></el-table-column>
      <el-table-column prop="totalPrice" label="总价格" width="200"></el-table-column>
      <el-table-column fixed="right" label="操作" width="210">
        <template #default="scope">
          <el-button
            type="success"
            @click="
              cartForm.count = scope.row.count;
              cartForm.productId = scope.row.id;
              dialogVisible = true;
            "
            width="100px"
          >
            修改数量
          </el-button>
          <el-button type="danger" width="100px" @click="deleteCartItem(scope.row)">移除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div id="pay">
      <el-button type="primary" round @click="payChoose">购买选中商品</el-button>
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
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button
            type="primary"
            @click="
              dialogVisible = false;
              changeCount();
            "
          >
            修改
          </el-button>
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

// 存放购物车内容
const cartList = ref();
// 选中的购物车内容
const chooseList = ref([]);
// 对话框
const dialogVisible = ref(false);
// 修改数量信息
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

// 选择框变动
function handleSelectionChange(val) {
  chooseList.value = val;
}

// 每次进入购物车都获取一次购物车内容
onMounted(() => {
  axios
    .get('/api/cart', {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    .then(res => {
      cartList.value = res.data.data;
    });
});

// 重新获取数据
function refreshData() {
  axios
    .get('/api/cart', {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    .then(res => {
      cartList.value = res.data.data;
    });
}

// 修改购物车数量
function changeCount() {
  axios
    .put(
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
      if (res.data.code === 200) {
        // 重新获取数据
        axios
          .get('/api/cart', {
            headers: {
              Authorization: store.state.personalInfo.token,
            },
          })
          .then(res => {
            cartList.value = res.data.data;
            // 刷新购物车
            refreshData();
            ElMessage({
              type: res.data.code === 200 ? 'success' : 'error',
              message: `${res.data.msg}`,
            });
          });
      }
    });
}

// 删除购物车项目
function deleteCartItem(itemInfo) {
  axios
    .delete('/api/cart/' + itemInfo.id, {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    .then(res => {
      // 刷新购物车
      refreshData();
      ElMessage({
        type: res.data.code === 200 ? 'success' : 'error',
        message: `${res.data.msg}`,
      });
    });
}

// 支付选中商品
function payChoose() {
  if (chooseList.value.length === 0) {
    ElMessage({
      type: 'warning',
      message: `请选择支付商品`,
    });
  } else {
    axios
      .post('/api/cart/pay', chooseList.value, {
        headers: {
          Authorization: store.state.personalInfo.token,
        },
      })
      .then(res => {
        // 刷新购物车
        refreshData();
        ElMessage({
          type: res.data.code === 200 ? 'success' : 'error',
          message: `${res.data.msg}`,
        });
      });
  }
}
</script>

<style scoped>
#pay {
  margin-top: 30px;
  text-align: center;
}
</style>
