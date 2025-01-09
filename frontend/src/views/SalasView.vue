<template>
    <AdminLayout>
        <div class="flex flex-row w-fit m-auto gap-10">
            <ClassroomTable />
        </div>
    </AdminLayout>
</template>

<script setup>
import ClassroomTable from '@/components/ClassroomTable.vue';
import AdminLayout from '@/layouts/AdminLayout.vue';
</script>
