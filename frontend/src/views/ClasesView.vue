<template>
    <MainLayout>
        <div class="flex flex-col gap-4 m-auto items-end">
          <LectureTable />
        </div>
    </MainLayout>
</template>

<script setup>
import LectureTable from '@/components/LectureTable.vue';
import MainLayout from '@/layouts/MainLayout.vue';
import CalendarSlot from "@/components/CalendarSlot.vue";
</script>
