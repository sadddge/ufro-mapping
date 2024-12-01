<template>
  <div class="h-full w-screen bg-zinc-900">
    <n-config-provider :theme="darkTheme" class="w-full h-full">
      <n-dialog-provider>
        <RouterView/>
      </n-dialog-provider>
    </n-config-provider>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
import { darkTheme } from 'naive-ui'
</script>

<style scoped>
</style>
