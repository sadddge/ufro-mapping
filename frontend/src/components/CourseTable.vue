<template>
  <div class="flex flex-col gap-4">
    <div class="flex flex-row w-full justify-between">
      <div class="text-2xl font-semibold">Asignaturas</div>
      <n-button quaternary circle @click="newDialog">
        <template #icon>
          <n-icon>
            <Add20Filled/>
          </n-icon>
        </template>
      </n-button>
    </div>
    <n-data-table
        :columns="columns"
        :data="data"
        :single-line="false"
        :pagination="pagination"
        :paginate-single-page="false"
        :loading="loading"
        >
    </n-data-table>
    <SelectCalendar :id="id" :visible="calendarVisible" type="course" @close="calendarVisible=false"/>
  </div>
</template>

<script setup>
import Buttons from '@/components/jsComp/Buttons.js';
import CourseService from '@/services/CourseService';
import { onMounted, ref } from 'vue';
import { useDialog } from 'naive-ui';
import {openDeleteDialog, openEditDialog, openNewEntityDialog} from "@/components/jsComp/Dialogs.js";
import {courseContent} from "@/components/jsComp/Contents.js";
import {Add20Filled} from "@vicons/fluent";
import SelectCalendar from "@/components/SelectCalendar.vue";

const dialog = useDialog();
const calendarVisible = ref(false);
const id = ref(null);
const loading = ref(true);
const data = ref([]);
const pagination = ref({
    pageSize: 10
});

const editDialog = (course) => openEditDialog(dialog, course, "Asignatura", courseContent, saveEdit)
const deleteDialog = (course) => openDeleteDialog(dialog, course, "Asignatura", confirmDelete)

const newDialog = () => {
  const course = reactive({
    codigo: "",
    nombre: "",
    descripcion: "",
    sct: 0
  });
  openNewEntityDialog(dialog, course, "Crear asignatura", courseContent, saveNew)
}

const saveEdit = async (course) => {
    await CourseService.updateCourse(course.id, course);
}

const saveNew = async (course) => {
    await CourseService.createCourse(course);
    await fetchCourses();
}

const confirmDelete = async (course) => {
    await CourseService.deleteCourse(course.id);
    await fetchCourses();
}

const openCalendar = (classroom) => {
  calendarVisible.value = true;
  id.value = classroom.id;
};

const columns = ref([
    { key: 'id', title: 'ID', align: 'center', width: '50px' },
    { key: 'codigo', title: 'Codigo', width: '100px' },
    { key: 'nombre', title: 'Nombre', width: '250px' },
    { key: 'sct', title: 'SCT', width: '50px', align: "center" },
    { key: 'actions', title: 'Acciones', align: 'center', render(row) {
        return [Buttons.calendarButton(row, openCalendar), Buttons.editButton(row, editDialog), Buttons.deleteButton(row, deleteDialog)];
    }, }
]);

const fetchCourses = async () => {
    loading.value = true;
    const response = await CourseService.getCourses();
    data.value = response.map(item => {
        return {
            key: item,
            id: item.id,
            codigo: item.codigo,
            nombre: item.nombre,
            descripcion: item.descripcion,
            sct: item.sct
        }
    });
    loading.value = false;
}

onMounted(fetchCourses);
</script>