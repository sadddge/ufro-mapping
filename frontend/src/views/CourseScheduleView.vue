<template>
  <div class="absolute inset-0 overflow-auto backdrop-blur-sm bg-black bg-opacity-50 z-10 select-none flex justify-center items-center">
    <div class="flex flex-col items-start gap-4">
      <RouterLink :to="{ name : 'CoursePreview', params : { id : route.params.id } }">
        <n-button circle quaternary>
          <n-icon size="20">
            <ArrowLeft20Filled/>
          </n-icon>
        </n-button>
      </RouterLink>
      <ScheduleDefault :lectures="lectures"/>
    </div>
  </div>
</template>

<script setup>
import ScheduleDefault from "@/components/ScheduleDefault.vue";
import {ArrowLeft20Filled} from "@vicons/fluent";
import {ref} from "vue";
import {useRoute} from "vue-router";
import CourseService from "@/services/CourseService.js";


const route = useRoute();
const lectures = ref([]);

onMounted(async () => {
  lectures.value = await CourseService.getHorarioByCourseId(route.params.id);
});
</script>
