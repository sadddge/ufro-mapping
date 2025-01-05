<template>
  <div class="absolute inset-0 overflow-auto backdrop-blur-sm bg-black bg-opacity-50 z-10 select-none flex justify-center items-center">
    <div class="flex flex-col items-start gap-4">
        <n-button circle quaternary @click="goBack">
          <n-icon size="20">
            <ArrowLeft20Filled/>
          </n-icon>
        </n-button>
      <ScheduleDefault :lectures="lectures"/>
    </div>
  </div>
</template>

<script setup>
import ScheduleDefault from "@/components/ScheduleDefault.vue";
import {ArrowLeft20Filled} from "@vicons/fluent";
import {ref} from "vue";
import {useRoute, useRouter} from "vue-router";

import ClassroomService from "@/services/ClassroomService.js";

const route = useRoute();
const router = useRouter();
const lectures = ref([]);

const goBack = () => {
  router.push({ name: 'Home' });
};

onMounted(async () => {
  lectures.value = await ClassroomService.getHorarioByClassroomId(route.params.id);
});
</script>
