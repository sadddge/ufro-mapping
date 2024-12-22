<template>
    <div 
    class="w-80 space-y-3 p-3 rounded-xl opacity-90 overflow-x-auto"
    @mouseleave="changeShowTree(false)"
    @click="changeShowTree(true)"
    >
        <n-input v-model:value="pattern" placeholder="Search" 
            class="rounded-full border shadow-sm"/>
        <n-tree
            v-if="showTree"
            :show-irrelevant-nodes="showIrrelevantNodes"
            :pattern="pattern"
            :data="data"
            block-line
            @update:selected-keys="handleSelectionNode"
            class="rounded-xl p-2 bg-zinc-800"
        />
    </div>
</template>
  
<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth";
import ClassroomService from '@/services/ClassroomService'
import BuildingsService from '@/services/BuildingsService'
import CourseService from '@/services/CourseService'
  
const router = useRouter();
const showTree = ref(false);
const pattern = ref("");
const showIrrelevantNodes = ref(false);
const edificios = ref([]);
const asignaturas = ref([]);
const salas = ref([]);
const store = useAuthStore();
const data = ref([]);
const allowed = store.userRole === 'ADMIN' || store.userRole === 'USER';
  
async function fetchEdificios() {
    edificios.value = await BuildingsService.getBuildings();
}

async function fetchSalas() {
    salas.value = await ClassroomService.getClassrooms();
}

async function fetchAsignaturas() {
    asignaturas.value = await CourseService.getCourses();
}

function changeShowTree(isVisible) {
    showTree.value = isVisible;
}

function handleSelectionNode(selectedKey) {  
    const partsKey = selectedKey[0].split("-");

    router.push({
        path: `/${partsKey[0]}/${partsKey[1]}`
    });
}

function updateTreeData() {
    data.value = [
        {
            label: "Edificios",
            key: "0",
            children: edificios.value.map(edificio => ({
            label: edificio.alias ? edificio.alias : edificio.nombre,
            key: `building-${edificio.id}`,
            children: salas.value
            .filter(sala => sala.edificio.id === edificio.id)
                .map(sala => ({
                    label: sala.nombre,
                    key: `building-${edificio.id}`,
                }))
            })),
        },
        {
            label: "Asignaturas",
            key: "1",
            children: asignaturas.value.map(asignatura => ({
                label: asignatura.nombre,
                key: `course-${asignatura.id}`
            }))
        }
    ];
}

onMounted(async () => {
  try {
    if (allowed) {
      await fetchEdificios();
      await fetchSalas();
      await fetchAsignaturas();
    } else {
      await fetchEdificios();
    }
    updateTreeData();
  } catch (error) {
    console.error('Error al cargar los datos:', error);
  }
});
</script>