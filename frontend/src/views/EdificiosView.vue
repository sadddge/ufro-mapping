<template>
    <MainLayout>
        <div class="flex flex-row w-fit m-auto gap-10">
            <BuildingsTable @update:buidingId="updateBuildingId" />
            <ClassroomByBuildingTable :buildingId="buildingId" />
        </div>
    </MainLayout>
</template>

<script setup>
import MainLayout from '@/layouts/MainLayout.vue';
import BuildingsTable from '@/components/BuildingsTable.vue';
import ClassroomByBuildingTable from '@/components/ClassroomByBuildingTable.vue';
const buildingId = ref(0);
const updateBuildingId = (id) => buildingId.value = id;
</script>
