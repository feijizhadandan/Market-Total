<template>
  <div class="row">
    <div class="col-2" id="selection-box">
      <div class="list-group" id="list-tab" role="tablist">
        <a
          class="list-group-item list-group-item-action active"
          id="list-home-list"
          data-bs-toggle="list"
          href="#list-home"
          role="tab"
          aria-controls="list-home"
        >
          个人主页
        </a>
        <a
          class="list-group-item list-group-item-action"
          id="list-profile-list"
          data-bs-toggle="list"
          href="#list-profile"
          role="tab"
          aria-controls="list-profile"
        >
          编辑信息
        </a>
        <a
          class="list-group-item list-group-item-action"
          id="list-messages-list"
          data-bs-toggle="list"
          href="#list-messages"
          role="tab"
          aria-controls="list-messages"
        >
          账号绑定
        </a>
        <a
          class="list-group-item list-group-item-action"
          id="list-settings-list"
          data-bs-toggle="list"
          href="#list-settings"
          role="tab"
          aria-controls="list-settings"
        >
          设置
        </a>
      </div>
    </div>
    <div class="col-10">
      <div class="tab-content" id="nav-tabContent">
        <div class="tab-pane fade show active" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
          <HomePage></HomePage>
        </div>
        <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">
          <UserInfo></UserInfo>
        </div>
        <div class="tab-pane fade" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">
          <AccountBind></AccountBind>
        </div>
        <div class="tab-pane fade" id="list-settings" role="tabpanel" aria-labelledby="list-settings-list">...</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import HomePage from './HomePage.vue';
import UserInfo from './UserInfo.vue';
import AccountBind from './AccountBind.vue';
</script>

<style scoped>
#selection-box {
  margin-top: 50px;
  text-align: center;
}
</style>
