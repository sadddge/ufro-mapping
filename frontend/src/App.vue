<template>
  <div class="h-screen bg-zinc-900">
    <n-config-provider :theme="darkTheme">
      <RouterView/>
    </n-config-provider>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
import { darkTheme } from 'naive-ui'
</script>

<style scoped>
</style>
