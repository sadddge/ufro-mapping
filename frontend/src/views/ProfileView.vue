<script setup>

import Profile from "@/components/Profile.vue";
import MainLayout from "@/layouts/MainLayout.vue";
</script>

<template>

  <div class="w-full h-screen relative">
    <MainLayout/>
  </div>
  <n-message-provider>
    <Profile />
  </n-message-provider>
</template>

<style scoped>

</style>