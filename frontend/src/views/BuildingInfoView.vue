<template>
  <BuildingInfo :id="$route.params.id" class="absolute top-0"/>
</template>

<script setup>
import BuildingInfo from "@/components/BuildingInfo.vue";
</script>