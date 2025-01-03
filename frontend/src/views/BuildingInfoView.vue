<template>
  <BuildingInfo :id="id" class="absolute top-0"/>
</template>

<script setup>
import BuildingInfo from "@/components/BuildingInfo.vue";
import BuildingsService from "@/services/BuildingsService.js";
import { useMapStore } from "@/stores/map.js";
import { useRoute } from "vue-router";

const route = useRoute();
const store = useMapStore();
const id = route.params.id;

onMounted(() => {
  BuildingsService.getBuildingById(id).then((building) => {
    store.moveMap(building.longitud, building.latitud);
  });
});
</script>