<template>
  <transition name="fade">
    <SubjectList v-if="modeEdit" class="top-0 z-20" @updateCourses="handleUpdateCourses"
                 @initializeIds="handleInitializeIds"/>
  </transition>
  <SelectCalendar :visible="visible" type="schedule" :schedule="selectedCourses" @updateModeEdit="handleUpdateModeEdit"
                  @saveEdit="handleSaveEdit" @close="router.push({ name: 'Home'})"
  />
</template>

<script setup>
import {onMounted, ref, watch} from "vue";
import SubjectList from "@/components/SubjectList.vue";
import SelectCalendar from "@/components/SelectCalendar.vue";
import UserService from "@/services/UserService";
import CourseService from "@/services/CourseService";
import {useAuthStore} from "@/stores/auth";
import {useRouter} from "vue-router";

const authStore = useAuthStore();
const visible = ref(false);
const selectedCoursesIds = ref([]);
const selectedCourses = ref([]);
const originalCoursesIds = ref([]);
const originalCourses = ref([]);
const router = useRouter();
const modeEdit = ref(router.currentRoute.value.path.includes('edit'));

function handleUpdateModeEdit(newMode) {
  if (newMode) {
    router.push('/user/schedule/edit')
  } else {
    router.push('/user/schedule')
  }
}

function handleInitializeIds(initializeCoursesIds) {
  originalCoursesIds.value = initializeCoursesIds;
}

async function handleUpdateCourses(coursesSelectedIdsData) {
  const searchNewCourses = await Promise.all(coursesSelectedIdsData.map(async (courseSelectedIdData) => {
    return CourseService.getHorarioByCourseId(courseSelectedIdData)
  }));
  selectedCoursesIds.value = coursesSelectedIdsData;
  selectedCourses.value = searchNewCourses.flat();
};

function handleSaveEdit() {
  originalCoursesIds.value.forEach(originalCourseId => {
    if (!selectedCoursesIds.value.includes(originalCourseId)) {
      originalCoursesIds.value = originalCoursesIds.value.filter(originalCourseIdFilter => originalCourseIdFilter !== originalCourseId);
      UserService.deleteAsignatura(authStore.userId, originalCourseId);
    }
  });
  selectedCoursesIds.value.forEach(selectedCourseId => {
    if (!originalCoursesIds.value.includes(selectedCourseId)) {
      originalCoursesIds.value.push(selectedCourseId);
      UserService.registerAsignatura(authStore.userId, selectedCourseId);
    }
  })
};

watch(() => router.currentRoute.value.path, (newPath) => {
  console.log('modeEdit actual:', modeEdit.value);
  console.log('Ruta actual:', newPath);
  modeEdit.value = newPath.includes('edit');
  console.log('modeEdit:', modeEdit.value);
})

onMounted(async () => {
  originalCourses.value = await UserService.getHorarioByUserId(authStore.userId);
  selectedCourses.value = await UserService.getHorarioByUserId(authStore.userId);
  visible.value = true;
})
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>