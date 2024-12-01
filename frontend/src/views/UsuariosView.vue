<template>
    <MainLayout>
        <div class="flex flex-row mx-auto pt-24 gap-10">
            <UsersTable @update:userId="handleUserSelected" />
            <RegisteredCoursesTable :id="userId" />
        </div>
    </MainLayout>
</template>

<script setup>
import MainLayout from '@/layouts/MainLayout.vue';
import UsersTable from '@/components/UsersTable.vue';
import RegisteredCoursesTable from '@/components/RegisteredCoursesTable.vue';
import { ref } from 'vue';
const userId = ref(0);
const handleUserSelected = (id) => {
    userId.value = id;
};
</script>
