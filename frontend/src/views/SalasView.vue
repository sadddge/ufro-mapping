<template>
    <MainLayout>
        <div class="flex flex-row w-fit m-auto gap-10">
            <ClassroomTable />
        </div>
    </MainLayout>
</template>

<script setup>
import ClassroomTable from '@/components/ClassroomTable.vue';
import MainLayout from '@/layouts/MainLayout.vue';
</script>
