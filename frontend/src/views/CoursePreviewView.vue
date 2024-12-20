<template>
  <MainLayout/>
  <div class="absolute inset-0 overflow-auto backdrop-blur-sm bg-black bg-opacity-50 z-10 select-none flex justify-center items-center">
    <div class="flex flex-col items-start gap-4" v-if="visible">
      <n-button circle quaternary @click="visible = false">
        <n-icon size="20">
          <ArrowLeft20Filled/>
        </n-icon>
      </n-button>
      <ScheduleDefault :lectures="lectures"/>
      <div class="flex flex-row gap-4 self-end">
        <n-button @click="visible = false">Cerrar</n-button>
        <n-button type="primary" @click="handleInscribir">Inscribir</n-button>
      </div>
    </div>
    <CoursePreview v-else :id="route.params.id" @inscribir="inscribir" @desinscribir="loadData"/>
  </div>
</template>

<script setup>
  import CoursePreview from "@/components/CoursePreview.vue";
  import ScheduleDefault from "@/components/ScheduleDefault.vue";
  import { ref } from "vue";
  import {ArrowLeft20Filled} from "@vicons/fluent";
  import UserService from "@/services/UserService.js";
  import {useAuthStore} from "@/stores/auth.js";
  import CourseService from "@/services/CourseService.js";
  import { useRoute } from "vue-router";
  import MainLayout from "@/layouts/MainLayout.vue";

  const route = useRoute();
  const store = useAuthStore();
  const visible = ref(false);
  const lectures = ref([]);
  const inscribir = () => {
    visible.value = true;
  };

  const handleInscribir = () => {
    UserService.registerAsignatura(store.userId, route.params.id).then(async () => {
      visible.value = false;
    });
  };

  const loadData = async () => {
    lectures.value = await UserService.getHorarioByUserId(store.userId);
    CourseService.getHorarioByCourseId(route.params.id).then((data) => {
      data.forEach((lecture) => {
        lecture.isPreview = true;
        lectures.value.push(lecture);
      });
    });
  };

  onMounted(async () => {
    await loadData();
  });
</script>

