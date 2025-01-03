<template>
  <AdminLayout>
    <div class="flex flex-col gap-4 w-4/5 h-[90vh] m-auto items-end">
      <MapUfro :markers="markers" :edit="edit"/>
      <div v-if="edit" class="flex flex-row gap-2">
        <n-button secondary @click="cancelEdit">
          Cancelar
        </n-button>
        <n-button type="primary" @click="saveEdit">
          Guardar
        </n-button>
      </div>
      <div v-else>
        <n-button secondary @click="edit = true">
          Editar
        </n-button>

      </div>
    </div>
  </AdminLayout>
</template>

<script setup>
import AdminLayout from "@/layouts/AdminLayout.vue";
import BuildingsService from "@/services/BuildingsService.js";
import MapUfro from "@/components/MapUfro.vue";


const markers = ref([])
const edit = ref(false);

const cancelEdit = () => {
  edit.value = false;
}

const saveEdit = () => {
  edit.value = false;
  markers.value.forEach((marker) => {
    const lnglat = marker.marker.getLngLat();
    BuildingsService.updateBuilding(marker.id, {latitud: lnglat.lat, longitud: lnglat.lng})
  })
}

</script>

