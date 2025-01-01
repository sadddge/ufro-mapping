<template>
  <n-card :title="building.nombre" class="min-w-[300px] max-w-[350px] h-screen rounded-none" :bordered="false">
    {{ building.alias }}
    <router-link :to="{ name: 'Home' }">
      <n-button text class="absolute top-4 right-4">
        <n-icon>
          <Dismiss20Filled />
        </n-icon>
      </n-button>
    </router-link>
    <n-tabs type="line" animated>
      <n-tab-pane name="descripcion general" tab="Descripción general">
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid assumenda culpa dolorum est facere harum in
          magnam molestias natus necessitatibus non odio optio, quidem quo repellendus sequi tenetur veniam
          voluptatibus?</p>
      </n-tab-pane>
      <n-tab-pane name="salas de clases" tab="Salas de clases">
        <div v-for="room in rooms">
          <div class="flex flex-row items-center justify-between px-[10px]">
            {{ room.nombre }}
            <n-button secondary circle>
              <template #icon>
                <n-icon>
                  <CalendarIcon />
                </n-icon>
              </template>
            </n-button>
          </div>
          <div class="h-[1px] w-[100%] bg-[#2d2d30] my-[10px]"></div>
        </div>
      </n-tab-pane>
      <n-tab-pane name="salas de utilidad" tab="Salas de utilidad">
        dependiendo del "tipo" de sala
      </n-tab-pane>
    </n-tabs>
  </n-card>
</template>

<script setup>
import BuildingsService from '@/services/BuildingsService';
import { onMounted } from 'vue';
import {Dismiss20Filled, CalendarMonth20Filled as CalendarIcon} from '@vicons/fluent';

const props = defineProps({
  id: {
    type: Number,
    required: true
  }
});

const building = ref({});
const rooms = ref([]);

onMounted(async () => {
  building.value = await BuildingsService.getBuildingById(props.id);
  rooms.value = await BuildingsService.getRoomsByBuildingId(props.id);
  rooms.value.sort((a, b) => {

    const isNumericA = !isNaN(a.nombre);
    const isNumericB = !isNaN(b.nombre);

    if (isNumericA && isNumericB) {
      return Number(a.nombre) - Number(b.nombre); // Orden numérico
    } else if (isNumericA) {
      return -1; // Números antes que textos
    } else if (isNumericB) {
      return 1; // Textos después de números
    } else {
      return a.nombre.localeCompare(b.nombre); // Orden alfabético
    }
  });
});

</script>

<style scoped>
.card-tabs .n-tabs-nav--bar-type {
  padding-left: 4px;
}
</style>