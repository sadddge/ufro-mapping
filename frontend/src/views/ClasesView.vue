<template>
    <AdminLayout>
        <div class="flex flex-col gap-4 m-auto items-end">
          <LectureTable />
        </div>
    </AdminLayout>
</template>

<script setup>
import LectureTable from '@/components/LectureTable.vue';
import AdminLayout from '@/layouts/AdminLayout.vue';
</script>
