<template>
  <div class="flex flex-col gap-4">
    <div class="flex flex-row w-full justify-between">
      <div class="text-2xl font-semibold">Clases</div>
      <n-button quaternary circle @click="newDialog">
        <template #icon>
          <n-icon>
            <Add20Filled/>
          </n-icon>
        </template>
      </n-button>
    </div>
    <n-data-table :columns="columns"
                  :data="data" :single-line="false"
                  :pagination="pagination"
                  :paginate-single-page="false"
                  :render-cell="renderCell"
                  :loading="loading"
    >
    </n-data-table>
    <SelectCalendar :lecture="lecture" :visible="selecting" @close="closeCalendar" @save="saveNewLectures" :add="add"
                    :id="lecture.sala.id" type="classroom"/>
  </div>
</template>

<script setup>
import LecturesService from '@/services/LecturesService';
import CourseService from '@/services/CourseService';
import ClassroomService from '@/services/ClassroomService';
import {NButton, NText, useDialog} from 'naive-ui';
import {onMounted, ref} from 'vue';
import Buttons from '@/components/jsComp/Buttons.js';
import {openEditDialog, openNewEntityDialog} from "@/components/jsComp/Dialogs.js";
import BuildingsService from "@/services/BuildingsService.js";
import {lectureContent} from "@/components/jsComp/Contents.js";
import SelectCalendar from "@/components/SelectCalendar.vue";
import {Add20Filled} from "@vicons/fluent";

const dialog = useDialog();
const add = ref(false);
const courses = ref([]);
const classrooms = ref([]);
const buildings = ref([]);
const data = ref([]);
const selecting = ref(false);
const loading = ref(true);
const pagination = ref({
  pageSize: 10,

});

const lecture = reactive({
  id: null,
  sala: {
    id: null,
    nombre: null,
    edificio: {
      id: null,
      nombre: null,
      alias: null,
    }
  },
  asignatura: {
    id: null,
    nombre: null,
    codigo: null,
  },
  diaSemana: null,
  periodo: null,
  docente: null,
  modulo: null,
});

const dayMap = {
  1: 'Lunes',
  2: 'Martes',
  3: 'Miércoles',
  4: 'Jueves',
  5: 'Viernes',
  6: 'Sábado'
};

const closeCalendar = () => {
  selecting.value = false;
  add.value = false;
  lecture.id = null;
  lecture.sala.id = null;
  lecture.sala.nombre = null;
  lecture.sala.edificio.id = null;
  lecture.sala.edificio.nombre = null;
  lecture.sala.edificio.alias = null;
  lecture.asignatura.id = null;
  lecture.asignatura.nombre = null;
  lecture.asignatura.codigo = null;
  lecture.diaSemana = null;
  lecture.periodo = null;
  lecture.docente = null;
  lecture.modulo = null;
}

const getBuildingOptions = () => {
  return buildings.value.map(building => {
    return {
      label: building.alias ? building.alias : building.nombre,
      value: building,
    };
  });
};

const getCourseOptions = () => {
  return courses.value.map(course => {
    return {
      label: course.nombre,
      value: {
        id: course.id,
        nombre: course.nombre,
        codigo: course.codigo,
      },
    };
  });
};

const getClassroomOptions = (buildingId) => {
  return classrooms.value.filter(classroom => classroom.edificio.id === buildingId).map(classroom => {
    return {
      label: classroom.nombre,
      value: {
        id: classroom.id,
        nombre: classroom.nombre,
      },
    };
  });
};
const editDialog = (lecture) => {
  openEditDialog(dialog, lecture, "Clase", (entity) => lectureContent(entity, getCourseOptions(), getBuildingOptions(), getClassroomOptions), saveEdit);
}
const newDialog = () => {
  openNewEntityDialog(dialog, lecture, "Crear clases", () => lectureContent(lecture, getCourseOptions(), getBuildingOptions(), getClassroomOptions), selectCalendar);
}
const saveEdit = async (lecture) => {
  const entity = LecturesService.lectureToRequest(lecture);
  await LecturesService.updateLecture(lecture.id, entity);
  await fetchAll();
}

const selectCalendar = () => {
  selecting.value = true;
  add.value = true;
}

const saveNewLectures = async (lectures) => {
  for (const lecture1 of lectures) {
    await LecturesService.createLecture(lecture1);
  }
  await fetchAll();
}

const renderCell = (value) => {
  if (!value) {
    return h(NText, {depth: 3}, {default: () => "-"});
  }
  return value;
}


const columns = ref([
  {key: 'id', title: 'ID', align: 'center', width: '50px'},
  {key: 'nombreEdificio', title: 'Edificio', width: '100px', className: 'truncate'},
  {key: 'sala.nombre', title: 'Sala', width: '100px', align: 'center'},
  {key: 'asignatura.nombre', title: 'Asignatura', width: '150px'},
  {key: 'docente', title: 'Docente', width: '150px'},
  {key: 'modulo', title: 'Módulo', width: '80px', align: 'center'},
  {key: 'dia', title: 'Día', width: '100px'},
  {key: 'periodo', title: 'Periodo', width: '80px', align: 'center'},
  {
    key: 'actions', title: 'Acciones', render(row) {
      if (!row.id) {
        return null;
      }
      return [Buttons.editButton(row, editDialog), Buttons.deleteButton(row, null)];
    },
  }
]);


const fetchAll = async () => {
  loading.value = true;
  data.value = await LecturesService.getLectures();
  courses.value = await CourseService.getCourses();
  classrooms.value = await ClassroomService.getClassrooms();
  buildings.value = await BuildingsService.getBuildings();
  data.value.forEach(lecture => {
    lecture.dia = dayMap[lecture.diaSemana];
    lecture.nombreEdificio = lecture.sala.edificio.alias ? lecture.sala.edificio.alias : lecture.sala.edificio.nombre;
  });
  while (data.value.length % pagination.value.pageSize !== 0) {
    data.value.push({});
  }
  loading.value = false;
}

onMounted(async () => {
  await fetchAll();
});
</script>