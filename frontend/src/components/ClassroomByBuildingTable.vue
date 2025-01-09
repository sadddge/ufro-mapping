<template>
    <div class="flex flex-col gap-4 items-end">
        <n-button quaternary circle @click="openNewClassroomForBuilding">
            <template #icon>
                <n-icon>
                    <Add20Filled />
                </n-icon>
            </template>
        </n-button>
        <n-data-table 
            :columns="columns"
            :data="data"
            :single-line="false"
            :pagination="pagination"
            :paginate-single-page="false"
            :loading="loading"
            >
        </n-data-table>
    </div>
    
</template>

<script setup>
import BuildingsService from '@/services/BuildingsService';
import Buttons from '@/components/jsComp/Buttons.js';
import ClassroomService from '@/services/ClassroomService';
import {Add20Filled } from '@vicons/fluent';
import { NButton, useDialog } from 'naive-ui';
import { reactive, watch } from 'vue';
import {openDeleteDialog, openNewEntityDialog} from "@/components/jsComp/Dialogs.js";
import {newClassroomForBuildingContent} from "@/components/jsComp/Contents.js";

const dialog = useDialog();
const loading = ref(false);
const data = ref([]);
const pagination = ref({
    pageSize: 10
});
const props = defineProps({
    buildingId: {
        type: Number,
        required: true
    }
});

const openNewClassroomForBuilding = () => {
    if (props.buildingId === 0) {
        dialog.error({
            title: 'Error',
            content: 'No se ha seleccionado un edificio',
            positiveText: 'Aceptar',
            closable: false,
        });
        return;
    }
    const classroom = reactive({
        nombre: '',
        edificioId: props.buildingId
    });
    openNewEntityDialog(dialog, classroom, 'Crear nueva sala para edificio ' + props.buildingId, newClassroomForBuildingContent, confirmNewClassroom);
};

const confirmNewClassroom = (classroom) => {
    ClassroomService.createClassroom(classroom).then(getClassromsByBuilding);
};

const deleteDialog = (classroom) => {
    openDeleteDialog(dialog, classroom, classroom.nombre, () => ClassroomService.deleteClassroom(classroom.id).then(getClassromsByBuilding));
};

const columns = ref([
    { key: 'id', title: 'ID', align: 'center', width: '50px' },
    { key: 'nombre', title: 'Nombre', width: '150px' },
    { key: 'actions', title: 'Acciones', width: '80px', align: 'center', render(row) {
        return Buttons.deleteButton(row, deleteDialog);
    }, }
]);

const getClassromsByBuilding = async () => {
    loading.value = true;
    data.value = await BuildingsService.getRoomsByBuildingId(props.buildingId);
    loading.value = false;
};

watch(() => props.buildingId, () => {
    getClassromsByBuilding();
});

</script>