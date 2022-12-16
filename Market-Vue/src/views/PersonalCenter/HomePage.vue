<template>
  <div class="container">
    <div class="row" id="top-div">
      <div class="col-md-3" id="avatar">
        <img id="avatar-photo" :src="store.state.personalInfo.userDetails.photoUrl" alt="" />
        <div id="avatar-change">
          <button type="button" class="btn btn-dark">修改头像</button>
        </div>
      </div>

      <div class="col-md-7">
        <div id="name">
          {{ $store.state.personalInfo.userDetails.userName }}
        </div>
        <div id="introduction">
          <span id="introduction-icon" style="cursor: pointer">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-brush-fill"
              viewBox="0 0 16 16"
            >
              <path
                d="M15.825.12a.5.5 0 0 1 .132.584c-1.53 3.43-4.743 8.17-7.095 10.64a6.067 6.067 0 0 1-2.373 1.534c-.018.227-.06.538-.16.868-.201.659-.667 1.479-1.708 1.74a8.118 8.118 0 0 1-3.078.132 3.659 3.659 0 0 1-.562-.135 1.382 1.382 0 0 1-.466-.247.714.714 0 0 1-.204-.288.622.622 0 0 1 .004-.443c.095-.245.316-.38.461-.452.394-.197.625-.453.867-.826.095-.144.184-.297.287-.472l.117-.198c.151-.255.326-.54.546-.848.528-.739 1.201-.925 1.746-.896.126.007.243.025.348.048.062-.172.142-.38.238-.608.261-.619.658-1.419 1.187-2.069 2.176-2.67 6.18-6.206 9.117-8.104a.5.5 0 0 1 .596.04z"
              />
            </svg>
          </span>
          {{ $store.state.personalInfo.userDetails.introduction }}
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
#avatar-photo {
  border: solid 3px burlywood;
  border-radius: 50%;
  height: 100px;
  width: 100px;
}
#avatar-change {
  margin-top: 10px;
}
#top-div {
  margin-top: 60px;
  text-align: center;
}
#name {
  text-align: left;
  line-height: 70px;
  font-weight: bold;
  font-size: 180%;
}
#introduction {
  text-align: left;
}
</style>
