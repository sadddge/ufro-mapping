<template>
    <div class="relative mr-72">
        <transition name="fade">
            <SubjectList v-if="modeEdit" class="absolute z-20" @updateCourses="handleUpdateCourses" @initializeIds="handleInitializeIds"/>
        </transition>
    </div>
   
    <SelectCalendar :visible="visible" type="schedule" :schedule="selectedCourses" @updateModeEdit="handleUpdateModeEdit" @saveEdit="handleSaveEdit"/>
</template>
  
<script setup>
import { onMounted, ref } from "vue";
import SubjectList from "./SubjectList.vue";
import SelectCalendar from "./SelectCalendar.vue";
import UserService from "@/services/UserService";
import CourseService from "@/services/CourseService";
import { useAuthStore } from "@/stores/auth";

const modeEdit = ref(false);
const authStore = useAuthStore();
const visible = ref(false);
const selectedCoursesIds = ref([]);
const selectedCourses = ref([]);
const originalCoursesIds = ref([]);
const originalCourses = ref([]);

function handleUpdateModeEdit(newMode){
    modeEdit.value = newMode;
}

function handleInitializeIds(initializeCoursesIds){
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
        if(!selectedCoursesIds.value.includes(originalCourseId)) {
            console.log("Se elimino la asignatura con el ID:", originalCourseId);
            originalCoursesIds.value = originalCoursesIds.value.filter(originalCourseIdFilter => originalCourseIdFilter !== originalCourseId);
            
            UserService.deleteAsignatura(authStore.userId ,originalCourseId);
        }
    });
    selectedCoursesIds.value.forEach(selectedCourseId => {
        if (!originalCoursesIds.value.includes(selectedCourseId)) {
            console.log("Se agrego la asignatura con el ID:", selectedCourseId);
            originalCoursesIds.value.push(selectedCourseId);

            UserService.registerAsignatura(authStore.userId , selectedCourseId);
        }
    })
};

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
