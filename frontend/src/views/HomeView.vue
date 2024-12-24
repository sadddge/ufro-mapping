<template>
</template>

<script setup>
import { useMapStore } from "@/stores/map.js";
const mapStore = useMapStore()
if (!mapStore.showMap) {
  mapStore.showMap = true
}
</script>
