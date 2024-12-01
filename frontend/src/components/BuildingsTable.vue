<template>
    <div class="flex flex-col gap-4 items-end">
      <div class="flex flex-row w-full justify-between">
        <div class="text-2xl font-semibold">Edificios</div>
        <div class="flex flex-row gap-2">
          <n-button quaternary @click="goToMap">
            <template #icon>
              <n-icon>
                <Map20Filled/>
              </n-icon>
            </template>
          </n-button>
          <n-button quaternary circle @click="newBuildingDialog">
            <template #icon>
              <n-icon>
                <Add20Filled/>
              </n-icon>
            </template>
          </n-button>
        </div>
      </div>
        <n-data-table 
        :columns="columns"
        :data="data"
        :single-line="false"
        :pagination="pagination"
        v-model:checked-row-keys="buildingId"
        :paginate-single-page="false"
        :loading="loading"
        :render-cell="renderCell"
        >
        </n-data-table>
    </div>
</template>

<script setup>
import BuildingsService from '@/services/BuildingsService';
import Buttons from '@/components/jsComp/Buttons.js';
import { useRouter } from 'vue-router';
import { Add20Filled, Map20Filled } from '@vicons/fluent';
import { NButton, NText, useDialog } from 'naive-ui';
import { onMounted, reactive, ref, watch } from 'vue';
import {openDeleteDialog, openEditDialog, openNewEntityDialog} from "@/components/jsComp/Dialogs.js";
import {buildingContent} from "@/components/jsComp/Contents.js";

const dialog = useDialog();
const buildingId = ref([0]);
const loading = ref(true);
const data = ref([]);
const pagination = ref({
    pageSize: 10
});

const router = useRouter();

const goToMap = () => {
    router.push({ name: 'Map' });
};

const renderCell = (value) => {
    if (!value) {
        return h(NText, { depth: 3 }, { default: () => "-" });
    }
    return value;
}

//Dialogs
const newBuildingDialog = () => {
    let building = reactive({
        nombre: '',
        alias: '',
        tipo: '',
        latitud: -38.7481645,
        longitud: -72.617744,
    });
    openNewEntityDialog(dialog, building, "edificio", buildingContent, confirmNewBuildingDialog);
}

const editDialog = (building) => openEditDialog(dialog, building, "edificio", buildingContent, confirmEditDialog);

const deleteDialog = (building) => openDeleteDialog(dialog, building, "edificio", confirmDeleteDialog);

//ConfirmDialogs     
const confirmEditDialog = async (building) => {
    await BuildingsService.patchBuilding(building.id, building);
    data.value = await BuildingsService.getBuildings();
};

const confirmNewBuildingDialog = async (building) => {
    await BuildingsService.createBuilding(building);
    data.value = await BuildingsService.getBuildings();
};

const confirmDeleteDialog = async (building) => {
    await BuildingsService.deleteBuilding(building.id);
    data.value = await BuildingsService.getBuildings();
};

const columns = ref([
    { type: 'selection', multiple: false },
    { key: 'id', title: 'ID', align: 'center', width: '50px' },
    { key: 'nombre', title: 'Nombre', width: '250px' },
    { key: 'alias', title: 'Alias' },
    { key: 'tipo', title: 'Tipo' },
    { key: 'actions', title: 'Acciones', width: '120px', render(row) {
        return [Buttons.editButton(row, editDialog), Buttons.deleteButton(row, deleteDialog)];
    }, }
]);

const getBuildings = async () => {
    loading.value = true;
    await BuildingsService.getBuildings().then((buildings) => {
        data.value = buildings.map((building) => {
            return {
                key: building.id,
                id: building.id,
                nombre: building.nombre,
                alias: building.alias,
                tipo: building.tipo,
            };
        });
    });
    loading.value = false;
};

const emit = defineEmits(['update:buidingId']);

watch(buildingId, (newValue) => {
    emit('update:buidingId', newValue[0]);
});

onMounted(async () => {
    await getBuildings();
});

</script>