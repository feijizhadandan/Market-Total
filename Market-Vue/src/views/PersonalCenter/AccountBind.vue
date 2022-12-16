<template>
  <div class="container">
    <div class="row" id="top-div">
      <div class="row">
        <div class="col-md-6 one-info">
          <label for="username">手机</label>
          <input
            type="text"
            class="form-control"
            id="username"
            :value="store.state.personalInfo.userDetails.phone"
            disabled
          />
        </div>
        <div class="col-md-3 one-info">
          <button type="button" class="btn btn-outline-primary changeButton">修改绑定手机</button>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6 one-info">
          <label for="introduction">邮箱</label>
          <input
            type="text"
            class="form-control"
            id="introduction"
            :value="store.state.personalInfo.userDetails.email"
            disabled
          />
        </div>
        <div class="col-md-3 one-info">
          <button type="button" class="btn btn-outline-primary changeButton">修改绑定邮箱</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useStore } from 'vuex';
const store = useStore();
</script>

<style scoped>
#top-div {
  margin-top: 50px;
  margin-left: 60px;
}
.one-info {
  margin: 10px;
}
.changeButton {
  margin-top: 25px;
}
</style>
