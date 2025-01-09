<template>
  <div class="flex flex-col gap-4">
    <div class="flex flex-row w-full justify-between">
      <div class="text-2xl font-semibold">Salas</div>
      <n-button quaternary circle @click="newClassroomDialog">
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
    <SelectCalendar :id="id" :visible="calendarVisible" type="classroom" @close="calendarVisible=false"/>
  </div>
</template>

<script setup>
import ClassroomService from '@/services/ClassroomService';
import BuildingsService from '@/services/BuildingsService';
import {onMounted, ref} from 'vue';
import Buttons from '@/components/jsComp/Buttons.js';
import {Add20Filled } from "@vicons/fluent";
import {NButton, useDialog} from "naive-ui";
import {openDeleteDialog, openEditDialog, openNewEntityDialog} from "@/components/jsComp/Dialogs.js";
import {classroomContent} from "@/components/jsComp/Contents.js";
import SelectCalendar from "@/components/SelectCalendar.vue";

const loading = ref(true);
const id = ref(null);
const calendarVisible = ref(false);
const dialog = useDialog();
const buildings = ref([]);
const data = ref([]);
const pagination = ref({
  pageSize: 10
});

const getBuildingOptions = () => {
  return buildings.value.map(building => {
    return {
      label: building.alias ? building.alias : building.nombre,
      value: building.id,
    };
  });
};

const newClassroomDialog = () => {
  const classroom = reactive({
    nombre: null,
    edificioId: null,
  });
  openNewEntityDialog(dialog, classroom, "Crear Sala", () => classroomContent(classroom, getBuildingOptions()), confirmNewClassroom);
};

const editClassroomDialog = (classroom) => {
  openEditDialog(dialog, classroom, "Sala", () => classroomContent(classroom, getBuildingOptions()), confirmEdit);
};

const deleteDialog = (classroom) => {
  openDeleteDialog(dialog, classroom, "Sala", confirmDelete);
};

const confirmDelete = async (classroom) => {
  await ClassroomService.deleteClassroom(classroom.id);
  await fetch();
};

const confirmEdit = async (classroom) => {
  await ClassroomService.updateClassroom(classroom.id, classroom);
  await fetch();
};

const confirmNewClassroom = async (classroom) => {
  await ClassroomService.createClassroom(classroom);
  await fetch();
};

const openCalendar = (classroom) => {
  calendarVisible.value = true;
  id.value = classroom.id;
};

const columns = ref([
  {key: 'id', title: 'ID', align: 'center', width: '50px'},
  {key: 'nombre', title: 'Nombre', width: '150px'},
  {key: 'edificio', title: 'Edificio', width: '150px', className: 'text-ellipsis'},
  {
    key: 'actions', title: 'Acciones', render(row) {
      return [Buttons.calendarButton(row, openCalendar),Buttons.editButton(row, editClassroomDialog), Buttons.deleteButton(row, deleteDialog)];
    },
  }
]);

const fetch = async () => {
  loading.value = true;
  buildings.value = await BuildingsService.getBuildings();
  data.value = await ClassroomService.getClassrooms();
  data.value = data.value.map(classroom => {
    return {
      ...classroom,
      edificio: classroom.edificio.alias ? classroom.edificio.alias : classroom.edificio.nombre
    };
  });
  loading.value = false;
};

onMounted(async () => {
  await fetch();
});
</script>