<template>
    <n-card class="max-w-lg h-fit"
            v-if="course"
            :title="course.nombre"
            closable
            @close="close"
            :segmented="{ content: true, footer: true }">
      <template #header-extra>
        <p class="pr-8">#{{ course.codigo }}</p>
      </template>
      <div class="flex flex-col gap-2">
        <div class="flex flex-row justify-between">
          <p>Descripcion</p>
          <div class="flex flex-row items-center gap-2">
            <h1>Horario:</h1>
            <RouterLink :to="{ name : 'CourseSchedule', params : { id : course.id } }">
              <n-button circle quaternary size="small">
                <n-icon size="20">
                  <CalendarIcon/>
                </n-icon>
              </n-button>
            </RouterLink>
          </div>
        </div>
        <p v-if="course.descripcion">{{ course.descripcion }}</p>
        <p v-else class="text-pretty">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras commodo lacinia
          finibus. Curabitur lectus neque, porta in mi ac, mattis euismod massa. Donec nec molestie ligula. Quisque
          placerat pulvinar augue, eget mattis odio lobortis in. Aenean sed risus vulputate, venenatis nunc in,
          tincidunt nisl. Donec laoreet eros ac turpis laoreet bibendum. Aliquam id nisl dolor. Cras a elit sit amet
          metus iaculis aliquam sit amet in arcu. Phasellus consequat arcu ut lectus dignissim, eget cursus libero
          pharetra. Proin vehicula nisi quam, vitae sollicitudin ante consequat quis. Mauris ultrices mattis sem, et
          commodo elit laoreet sit amet.</p>
        <n-button class="w-1/4 self-end" type="error" secondary v-if="alreadyRegistered" @click="openDesinscribir">
          Desinscribirse
        </n-button>
        <n-button class="w-1/4 self-end" type="primary" secondary v-else @click="emit('inscribir')">
          Inscribirse
        </n-button>
      </div>
      <template #footer>
        <p class="flex justify-center"> SCT: {{ course.sct }}</p>
      </template>
    </n-card>
</template>

<script setup>
import {ref} from 'vue'
import {CalendarMonth20Filled as CalendarIcon} from "@vicons/fluent";
import CourseService from "@/services/CourseService.js";
import UserService from "@/services/UserService.js";
import {useAuthStore} from "@/stores/auth.js";
import {useDialog} from "naive-ui";
import { useRouter } from "vue-router";
import { openErorrDialog } from "@/components/jsComp/Dialogs.js";

const props = defineProps({
  id: {
    type: Number,
    required: true
  }
})

const emit = defineEmits(['inscribir', 'desinscribir'])
const router = useRouter()
const dialog = useDialog()
const authStore = useAuthStore()
const course = ref(null)
const userCourses = ref([])
const alreadyRegistered = computed(() => userCourses.value.some((course) => course.id == props.id))

const openDesinscribir = () => openErorrDialog(dialog, "Desinscribir " + course.value.nombre, "¿Estás seguro que deseas desinscribirte de esta asignatura?", confirmDesinscribir)

const confirmDesinscribir = async () => {
  try {
    await UserService.deleteAsignatura(authStore.userId, props.id)
    await fetchData()
    emit('desinscribir')
  } catch (e) {
    console.error(e)
  }
}

const close = () => {
  router.push({ name: 'Home' })
}

const fetchData = async () => {
  course.value = await CourseService.getCourseById(props.id)
  userCourses.value = await UserService.getAsignaturasByUserId(authStore.userId)
}

onMounted(async () => {
  await fetchData()
})
</script>