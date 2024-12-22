<template>
  <div class="h-full w-screen bg-zinc-900">
    <n-config-provider :theme="darkTheme" class="w-full h-full">
      <n-message-provider>
        <n-dialog-provider>
          <MapUfro full class="h-screen" v-if="mapStore.showMap"/>
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
import MapUfro from "@/components/MapUfro.vue";
const mapStore = useMapStore()
</script>

<style scoped>
</style>
