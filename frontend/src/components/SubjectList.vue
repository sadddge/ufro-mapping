<template>
    <n-transfer
            v-model:value="value"
            :options="options"
            :render-source-list="renderSourceList"
            source-filterable
            class="flex flex-col w-96  h-[calc(83.3333%-8px)] absolute bg-[rgba(0,0,0,1)] ml-14 mt-24"
            />
</template>

<script setup>
import UserService from "@/services/UserService";
import CourseService from "@/services/CourseService";
import { NTree } from "naive-ui";
import { useAuthStore } from '@/stores/auth';
import { onMounted, watch } from "vue";

const options = ref([]);
const value = ref([]);
const authStore = useAuthStore();

const emits = defineEmits(['updateCourses', 'initializeIds']);

function addCourse(courseData){
    emits('updateCourses', courseData);
}

async function obtenerAsignaturas() {
    const data = await CourseService.getCourses(); 
    options.value = formatearDatos(data);

    const asignaturasInscritas = await UserService.getAsignaturasByUserId(authStore.userId);
    value.value = asignaturasInscritas.map(asignaturasInscrita => asignaturasInscrita.id);
}

function formatearDatos (data) {
    return data.map(asignatura => ({
        label: asignatura.nombre,
        value: asignatura.id,
    }))
}

const renderSourceList = ({ onCheck, pattern }) => {
    return h(NTree, {
        style: "margin: 0 4px;",
        keyField: "value",
        checkable: true,
        selectable: false,
        blockLine: true,
        checkOnClick: true,
        data: options.value,
        pattern,
        checkedKeys: value.value,
        onUpdateCheckedKeys: (checkedKeys) => {
            onCheck(checkedKeys);
        },
    });
};

watch(value, (newValue) => {
    addCourse(newValue);
})

onMounted(async () => {
    await obtenerAsignaturas();
    emits("initializeIds", value.value);
})
</script>