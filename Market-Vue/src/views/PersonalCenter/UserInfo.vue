<template>
  <div class="container">
    <div class="row" id="top-div">
      <form>
        <div class="form-row">
          <div class="form-group col-md-6 one-info">
            <label for="username">名称</label>
            <input
              type="text"
              class="form-control"
              id="username"
              :value="store.state.personalInfo.userDetails.userName"
            />
          </div>
          <div class="form-group col-md-6 one-info">
            <label for="introduction">个性签名</label>
            <input
              type="text"
              class="form-control"
              id="introduction"
              :value="store.state.personalInfo.userDetails.introduction"
            />
          </div>
        </div>
        <div class="form-group one-info">
          <label for="address">地址</label>
          <input
            type="text"
            class="form-control"
            id="address"
            :value="store.state.personalInfo.userDetails.address"
          />
        </div>
        <div class="form-row one-info">
          <div class="form-group col-md-4">
            <label for="birthday">生日</label>
            <input type="date" class="form-control" id="birthday" />
          </div>
        </div>
        <div class="form-group one-info">
          <label>性别</label>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="gender"
              id="man"
              value="man"
              :checked="isMan"
            />
            <label class="form-check-label" for="man"> 男 </label>
          </div>
          <div class="form-check">
            <input
              class="form-check-input"
              type="radio"
              name="gender"
              id="woman"
              value="woman"
              :checked="!isMan"
            />
            <label class="form-check-label" for="woman"> 女 </label>
          </div>
        </div>
        <div class="form-group one-info">
          <button type="submit" class="btn btn-primary">保存</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { computed } from '@vue/reactivity';
import { useStore } from 'vuex';
const store = useStore();

// 性别
const isMan = computed(() => {
  if (store.state.personalInfo.userDetails.gender == 1) {
    return true;
  } else if (store.state.personalInfo.userDetails.gender == 2) {
    return false;
  }
});
</script>

<style scoped>
#top-div {
  margin-top: 50px;
  margin-left: 60px;
}
.one-info {
  margin: 10px;
}
</style>
