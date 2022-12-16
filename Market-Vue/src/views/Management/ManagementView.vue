<template>
  <ContentCard>
    <div id="option">
      <a class="record" :href="store.state.browseRecordUrl" target="_blank">
        <el-button color="#626aef" plain>用户浏览记录</el-button>
      </a>
      <a class="record" :href="store.state.buyRecordUrl" target="_blank">
        <el-button color="#626aef" plain>用户购买记录</el-button>
      </a>
      <el-button id="add" type="primary" plain @click="addNewVisible = true">新增商品</el-button>
    </div>
    <el-table :data="productList" style="width: 95%; margin: auto">
      <el-table-column fixed prop="code" label="商品编号" width="100"></el-table-column>
      <el-table-column prop="productName" label="商品名称" width="200"></el-table-column>
      <el-table-column prop="productPrice" label="单价" width="200"></el-table-column>
      <el-table-column prop="isShow" label="用户是否可见" width="200"></el-table-column>
      <el-table-column prop="productIntroduction" label="商品简介" width="200"></el-table-column>
      <el-table-column fixed="right" label="操作" width="300">
        <template #default="scope">
          <el-button
            type="success"
            @click="
              detailIndex = scope.$index;
              detailVisible = true;
            "
            width="100px"
          >
            查看详情
          </el-button>
          <el-button
            type="primary"
            @click="
              imgIndex = scope.$index;
              updateForm.id = scope.row.id;
              updateForm.productName = scope.row.productName;
              updateForm.productPrice = scope.row.productPrice;
              updateForm.productCount = scope.row.productCount;
              updateForm.productIntroduction = scope.row.productIntroduction;
              updateForm.isShow = scope.row.isShow;
              updateVisible = true;
            "
            width="100px"
          >
            修改信息
          </el-button>
          <el-button
            type="danger"
            width="100px"
            @click="
              deleteProductId = scope.row.id;
              confirmDelete();
            "
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增商品窗口 -->
    <el-dialog align-center v-model="addNewVisible" title="新增商品" width="30%">
      <span>
        <!-- 表单内容 -->
        <el-form ref="ruleFormRef" :model="newProductForm" :rules="productRules" label-width="120px" class="demo-ruleForm">
          <el-form-item label="商品名称" prop="productName">
            <el-input v-model="newProductForm.productName"></el-input>
          </el-form-item>
          <el-form-item label="商品价格" prop="productPrice">
            <el-input v-model="newProductForm.productPrice"></el-input>
          </el-form-item>
          <el-form-item label="商品数量" prop="productCount">
            <el-input v-model="newProductForm.productCount"></el-input>
          </el-form-item>
          <el-form-item label="商品简介" prop="productIntroduction">
            <el-input type="textarea" v-model="newProductForm.productIntroduction"></el-input>
          </el-form-item>
          <el-form-item label="是否展示给用户" prop="isShow">
            <el-switch v-model="newProductForm.isShow"></el-switch>
          </el-form-item>
          <div style="text-indent: 30px">（商品图片在修改界面添加）</div>
        </el-form>
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button
            @click="
              addNewVisible = false;
              resetAddForm();
            "
          >
            取消
          </el-button>
          <el-button
            type="primary"
            @click="
              addNewVisible = false;
              addNewProductFunc();
            "
          >
            添加
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 查看详情窗口 -->
    <el-dialog align-center v-model="detailVisible" title="修改商品信息" width="30%">
      <span>
        <!-- 商品信息 -->
        <el-form ref="ruleFormRef" v-model="productList" label-width="120px" class="demo-ruleForm">
          <el-form-item label="参考图片" prop="productName">
            <img
              :src="productList[detailIndex].photoUrl === null ? null : productList[detailIndex].photoUrl + '?x=' + new Date().getTime()"
              alt="无参考图片"
              height="150"
              width="150"
            />
          </el-form-item>
          <el-form-item label="商品名称" prop="productName">
            <el-input v-model="productList[detailIndex].productName" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="商品价格" prop="productPrice">
            <el-input v-model="productList[detailIndex].productPrice" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="商品数量" prop="productCount">
            <el-input v-model="productList[detailIndex].productCount" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="商品简介" prop="productIntroduction">
            <el-input type="textarea" v-model="productList[detailIndex].productIntroduction" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="是否展示给用户" prop="isShow">
            <el-switch v-model="productList[detailIndex].isShow" disabled="disabled"></el-switch>
          </el-form-item>
          <el-form-item label="创建者" prop="createName">
            <el-input v-model="productList[detailIndex].createName" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="创建时间" prop="createTime">
            <el-input v-model="productList[detailIndex].createTime" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="更新者" prop="updateName">
            <el-input v-model="productList[detailIndex].updateName" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="更新时间" prop="updateName">
            <el-input v-model="productList[detailIndex].updateTime" disabled="disabled"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="detailVisible = false">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 修改商品信息窗口 -->
    <el-dialog align-center v-model="updateVisible" title="修改商品信息" width="30%">
      <span>
        <!-- 表单内容 -->
        <el-form-item label="参考图片" prop="productName">
          <img
            :src="productList[imgIndex].photoUrl === null ? null : productList[imgIndex].photoUrl + '?x=' + new Date().getTime()"
            alt="无参考图片"
            height="150"
            width="150"
          />
          <input name="file" type="file" accept="image/*" @change="e => updatePhoto(e, productList[imgIndex].id)" />
        </el-form-item>
        <el-form ref="ruleFormRef" :model="updateForm" :rules="productRules" label-width="120px" class="demo-ruleForm">
          <el-form-item label="商品名称" prop="productName">
            <el-input v-model="updateForm.productName"></el-input>
          </el-form-item>
          <el-form-item label="商品价格" prop="productPrice">
            <el-input v-model="updateForm.productPrice"></el-input>
          </el-form-item>
          <el-form-item label="商品数量" prop="productCount">
            <el-input v-model="updateForm.productCount"></el-input>
          </el-form-item>
          <el-form-item label="商品简介" prop="productIntroduction">
            <el-input type="textarea" v-model="updateForm.productIntroduction"></el-input>
          </el-form-item>
          <el-form-item label="是否展示给用户" prop="isShow">
            <el-switch v-model="updateForm.isShow"></el-switch>
          </el-form-item>
        </el-form>
      </span>
      <template #footer>
        <span class="dialog-footer">
          <el-button
            @click="
              updateVisible = false;
              resetUpdateForm();
            "
          >
            取消
          </el-button>
          <el-button
            type="primary"
            @click="
              updateVisible = false;
              updateProductFunc();
            "
          >
            更新
          </el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 使用 ELMessage 替代 -->
    <!-- 响应消息窗口 -->
    <!-- <el-dialog align-center v-model="resultVisible" title="响应结果" width="30%">
      <span>{{ resultInfo }}</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="resultVisible = false">确认</el-button>
        </span>
      </template>
    </el-dialog> -->
  </ContentCard>
</template>

<script setup>
import ContentCard from '@/components/ContentCard.vue';
import { onMounted, inject, ref } from 'vue';
import store from '@/store';
import router from '@/router';
import { ElMessage, ElMessageBox } from 'element-plus';

// 注入全局 axios
const axios = inject('axios');
// 商品信息
const productList = ref([]);

// 响应消息窗口
// const resultVisible = ref(false);
// 响应消息内容
// const resultInfo = ref('');

// 新增商品窗口
const addNewVisible = ref(false);
// 新增商品的信息
const newProductForm = ref({
  productName: '',
  productPrice: 0,
  productCount: 0,
  productIntroduction: '',
  isShow: true,
});
// 重置表单信息
function resetAddForm() {
  newProductForm.value = {
    productName: '',
    productPrice: 0,
    productCount: 0,
    productIntroduction: '',
    isShow: true,
  };
}
// 新增商品表单验证
const productRules = ref({
  productName: [{ required: true, message: '请输入内容', trigger: 'blur' }],
  productPrice: [
    { required: true, message: '请输入内容', trigger: 'blur' },
    { type: 'number', message: '请输入数字', trigger: 'blur', transform: value => Number(value) },
  ],
  productCount: [
    { required: true, message: '请输入内容', trigger: 'blur' },
    { pattern: /^\+?[1-9]\d*$/, message: '请输入大于0的正整数' },
  ],
  productIntroduction: [{ required: true, message: '请输入内容', trigger: 'blur' }],
});
// 新增商品函数
function addNewProductFunc() {
  axios
    .post('/api/product/manager', newProductForm.value, {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    .then(res => {
      // 刷新商品列表
      getProductData();
      ElMessage({
        type: res.data.code === 200 ? 'success' : 'error',
        message: `${res.data.msg}`,
      });
    });
}

// 商品详情窗口
const detailVisible = ref(false);
// 商品详细信息下标
const detailIndex = ref(0);
// 专门用于记录修改图片的下标
const imgIndex = ref(0);
// 上传图片函数
function updatePhoto(e, productId) {
  const photoFile = e.target.files[0];
  axios
    .post(
      '/api/product/manager/photo/' + productId,
      {
        photoFile: photoFile,
      },
      {
        headers: {
          Authorization: store.state.personalInfo.token,
          'Content-Type': 'multipart/form-data',
        },
      }
    )
    .then(res => {
      // 刷新商品列表
      getProductData();
      ElMessage({
        type: res.data.code === 200 ? 'success' : 'error',
        message: `${res.data.msg}`,
      });
    });
}

// 更新商品窗口
const updateVisible = ref(false);
// 更新商品信息表单
const updateForm = ref({
  id: 0,
  productName: '',
  productPrice: 0,
  productCount: 0,
  productIntroduction: '',
  isShow: true,
});
// 重置更新表单
function resetUpdateForm() {
  updateForm.value = {
    id: 0,
    productName: '',
    productPrice: 0,
    productCount: 0,
    productIntroduction: '',
    isShow: true,
  };
}
// 更新函数
function updateProductFunc() {
  axios
    .put('/api/product/manager', updateForm.value, {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    .then(res => {
      // 刷新商品列表
      getProductData();
      ElMessage({
        type: res.data.code === 200 ? 'success' : 'error',
        message: `${res.data.msg}`,
      });
    });
}

// 临时存储删除的商品信息
const deleteProductId = ref(0);
// 确认删除函数
function confirmDelete() {
  ElMessageBox({
    title: 'Tips', //MessageBox 标题
    message: '是否确认删除该商品', //MessageBox 消息正文内容
    confirmButtonText: '删除', // 删除按钮文本
    showCancelButton: true, //是否显示取消按钮
    cancelButtonText: 'Cancel', // 取消按钮文本
    closeOnClickModal: false, //是否可通过点击周围遮罩关闭
    type: 'warning', //消息类型，用于显示图标
  })
    // 点击确认触发该函数
    .then(() => {
      deleteFunc();
    });
  // 点击取消触发该函数
  // .catch(() => {
  //   ElMessage({
  //     type: 'info',
  //     message: '取消删除',
  //   });
  // });
}
// 删除函数
function deleteFunc() {
  axios
    .delete('/api/product/manager/' + deleteProductId.value, {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    // 不要想着把异步变同步，而是可以将渲染函数放在回调函数中，就不会发生先渲染，后数据才到的事情。
    .then(res => {
      // 刷新商品列表
      getProductData();
      ElMessage({
        type: res.data.code === 200 ? 'success' : 'error',
        message: `${res.data.msg}`,
      });
    });
}

// 获取所有商品信息
onMounted(() => {
  getProductData();
});

// 获取所有商品信息
function getProductData() {
  axios
    .get('/api/product/manager', {
      headers: {
        Authorization: store.state.personalInfo.token,
      },
    })
    .then(res => {
      // 权限不足
      if (res.data.code === 403) {
        ElMessageBox({
          title: 'Tips', //MessageBox 标题
          message: '权限不足，不允许访问', //MessageBox 消息正文内容
          confirmButtonText: '确定', //确定按钮的文本内容
          closeOnClickModal: false, //是否可通过点击遮罩关闭
          type: 'warning', //消息类型，用于显示图标
        }).then(() => {
          ElMessage.success('已跳转至商城页面');
        });
        router.push('/market');
      } else {
        productList.value = res.data.data;
      }
    });
}
</script>

<style scoped>
#option {
  margin-top: 20px;
  margin-bottom: 20px;
}

#add {
  float: right;
  margin-right: 100px;
}
/* 未访问：蓝色、无下划线 */
a:link {
  color: blue;
  text-decoration: none;
}
.record {
  margin-left: 50px;
}
</style>
