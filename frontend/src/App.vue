<template>
  <div class="h-full w-screen bg-zinc-900">
    <n-config-provider :theme="darkTheme" class="w-full h-full">
      <n-message-provider>
        <n-dialog-provider>
          <MainLayout v-if="mapStore.showMap"/>
          <RouterView />
        </n-dialog-provider>
      </n-message-provider>
    </n-config-provider>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
import { darkTheme } from 'naive-ui'
import { useMapStore } from "@/stores/map.js";
import MainLayout from "@/layouts/MainLayout.vue";
const mapStore = useMapStore()
</script>

<style scoped>
</style>
