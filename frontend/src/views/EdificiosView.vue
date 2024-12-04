<template>
    <AdminLayout>
        <div class="flex flex-row w-fit m-auto gap-10">
            <BuildingsTable @update:buidingId="updateBuildingId" />
            <ClassroomByBuildingTable :buildingId="buildingId" />
        </div>
    </AdminLayout>
</template>

<script setup>
import AdminLayout from '@/layouts/AdminLayout.vue';
import BuildingsTable from '@/components/BuildingsTable.vue';
import ClassroomByBuildingTable from '@/components/ClassroomByBuildingTable.vue';
const buildingId = ref(0);
const updateBuildingId = (id) => buildingId.value = id;
</script>
