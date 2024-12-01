<template>
    <div class="flex flex-col gap-4 items-end">
        <n-button quaternary circle @click="openNewInscripcionDialog">
            <template #icon>
                <n-icon>
                    <Add20Filled />
                </n-icon>
            </template>
        </n-button>
        <n-data-table :columns="columns" :data="data" :single-line="false" :pagination="pagination"
        :paginate-single-page="false">
        </n-data-table>
    </div>
</template>

<script setup>
import UserService from '@/services/UserService';
import CourseService from '@/services/CourseService';
import {NButton, useDialog} from 'naive-ui';
import {Add20Filled} from '@vicons/fluent';
import {ref, watch} from 'vue';
import Buttons from '@/components/jsComp/Buttons.js';
import {openNewEntityDialog} from "@/components/jsComp/Dialogs.js";
import {newInscripcionContent} from "@/components/jsComp/Contents.js";

const dialog = useDialog();
const data = ref([]);
const asignaturas = ref([]);
const props = defineProps({
    id: Number,
});
const pagination = ref({
    pageSize: 10
});

const getAsignaturasOptions = () => {
    const asignaturasRegistradas = data.value.map(asignatura => asignatura.id);
  return asignaturas.value.map(asignatura => {
      return {
        label: asignatura.nombre,
        value: asignatura.id,
        disabled: asignaturasRegistradas.includes(asignatura.id),
      };
    });
};

//Dialogs
const openDeleteDialog = (asignatura) => {
    dialog.error({
        title: 'Eliminar ramo',
        content: '¿Estás seguro de que deseas eliminar este ramo?',
        positiveText: 'Eliminar',
        negativeText: 'Cancelar',
        closable: false,
        onPositiveClick: () => {
            UserService.deleteAsignatura(props.id, asignatura.id).then(() => {
                dialog.success({
                    title: 'Ramo eliminado',
                    content: 'El ramo ha sido eliminado exitosamente',
                    positiveText: 'Aceptar',
                    closable: false,
                });
                getAsignaturasByUserId(props.id);
            });
        }
    });
};

const openNewInscripcionDialog = () => {
    if (props.id === 0) {
        dialog.error({
            title: 'Error',
            content: 'Debes seleccionar un usuario para registrar asignaturas',
            positiveText: 'Aceptar',
            closable: false,
        });
    } else {
        const inscripcion = reactive({
            asignaturaId: null,
        });
        openNewEntityDialog(dialog, inscripcion, "Registrar Asignatura", () => newInscripcionContent(inscripcion, getAsignaturasOptions()), confirmNewInscripcion);
    }
}

const confirmNewInscripcion = (inscripcion) => {
    if (!inscripcion.asignaturaId) {
        dialog.error({
            title: 'Error',
            content: 'Debes seleccionar una asignatura',
            positiveText: 'Aceptar',
            closable: false,
        });
        return;
    }
    UserService.registerAsignatura(props.id, inscripcion.asignaturaId).then(() => {
        getAsignaturasByUserId(props.id);
    })
};


const columns = ref([
    { key: 'id', title: 'ID', align: 'center', width: '50px' },
    { key: 'nombre', title: 'Nombre', width: '200px' },
    { key: 'codigo', title: 'Codigo', align: 'center' },
    { key: 'sct', title: 'SCT' },
    {
        key: 'actions', title: 'Acciones', render(row) {
            return Buttons.deleteButton(row, openDeleteDialog);
        },
    }
]);

const getAsignaturasByUserId = async (id) => {
    data.value = await UserService.getAsignaturasByUserId(id);
};

watch(() => props.id, async () => {
    if (props.id !== 0) {
        await getAsignaturasByUserId(props.id);
    }
});

onMounted(async () => {
    asignaturas.value = await CourseService.getCourses();
});
</script>
