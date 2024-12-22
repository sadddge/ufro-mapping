<template>
  <MainLayout/>
  <BuildingInfo :id="$route.params.id" class="absolute top-0"/>
</template>

<script setup>
import BuildingInfo from "@/components/BuildingInfo.vue";
import MainLayout from "@/layouts/MainLayout.vue";
</script>