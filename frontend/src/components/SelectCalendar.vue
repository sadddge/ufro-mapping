<template>
  <transition name="fade">
    <div class="absolute inset-0 overflow-auto text-[rgba(255,255,255,0.9)] bg-[rgba(0,0,0,0.5)] z-10 select-none"
         v-if="visible">
      <div class="flex flex-col w-1/2 mx-auto mt-12 items-end gap-4">
        <div v-if="!add" class="flex space-x-2">
          <div class="flex space-x-2" v-if="type==='schedule'">
            <transition name="fade">
              <n-button strong secondary circle type="error" class="w-fit px-2.5" v-if="modeEdit"
                        @click="handleCancelEdit">
                <template #icon>
                  <n-icon>
                    <PresenceBlocked16Regular/>
                  </n-icon>
                </template>
                Cancelar
              </n-button>
            </transition>
            <transition name="fade">
              <n-button strong secondary circle type="success" class="w-fit px-2.5" v-if="modeEdit"
                        @click="handleSaveEdit">
                <template #icon>
                  <n-icon>
                    <Save20Regular/>
                  </n-icon>
                </template>
                Guardar
              </n-button>
            </transition>
            <n-button quaternary circle @click="handleModeEdit">
              <template #icon>
                <n-icon>
                  <Add12Regular
                  />
                </n-icon>
              </template>
            </n-button>
          </div>
          <n-button quaternary circle @click="close">
            <template #icon>
              <n-icon>
                <Dismiss20Regular/>
              </n-icon>
            </template>
          </n-button>
        </div>
        <div class="overflow-hidden rounded-lg border border-[rgba(45,45,48,1)]" v-if="visible">
          <table class="w-full border-collapse">
            <thead>
            <tr>
              <th class="text-center border border-[rgba(45,45,48,1)] w-28 p-2 bg-[rgb(38,38,42)]">Período</th>
              <th
                  v-for="(dia, i) in dias"
                  :key="`dia-${i}`"
                  class="p-2 text-center border border-[rgba(45,45,48,1)] bg-[rgb(38,38,42)]"
              >
                {{ dia }}
              </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(periodo, i) in periodos" :key="`periodo-${i}`" class="h-16 max-h-16">
              <td class="text-center text-xs border border-[rgba(45,45,48,1)] box-border bg-zinc-900">
                <p>{{ i + 1 + "°" }}</p>
                <p>{{ periodo }}</p>
              </td>
              <td
                  v-for="j in 6"
                  :key="`modulo-${i}-${j}`"
                  class="border border-[rgba(45,45,48,1)] text-center w-36 text-xs bg-zinc-900 p-0 space-y-0"
              >
                <CalendarSlot :lectures="getLectures(i + 1, j)"
                              :add="add" :slot="[i+1,j]"
                              @add="addLecture"
                              @del="delLecture"
                              @toggleExpand="handleExpand"
                              :isExpanded="isExpanded([i+1,j])"
                />
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="flex gap-2" v-if="add || edit">
          <n-button @click="close">Cancelar</n-button>
          <n-button type="primary" @click="save">Guardar</n-button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
import {Dismiss20Regular, Add12Regular, Save20Regular, PresenceBlocked16Regular} from "@vicons/fluent";
import UserService from "@/services/UserService";
import CourseService from "@/services/CourseService";
import ClassroomService from "@/services/ClassroomService";
import LecturesService from "@/services/LecturesService";
import {ref} from "vue";
import CalendarSlot from "@/components/CalendarSlot.vue";

const props = defineProps({
  id: {
    type: Number,
    required: false
  },
  type: {
    type: String,
    required: true
  },
  add: {
    type: Boolean,
    default: false
  },
  edit: {
    type: Boolean,
    default: false
  },
  lecture: {
    type: Object,
    required: false
  },
  visible: {
    type: Boolean,
    required: true
  },
  schedule: {
    type: Array,
    required: false
  },
});
const emits = defineEmits(["close", "save", "updateModeEdit", "saveEdit", "initializeIds"]);

const periodos = ref([
  "08:30 - 09:30", "09:40 - 10:40", "10:50 - 11:50",
  "12:00 - 13:00", "13:10 - 14:10", "14:30 - 15:30",
  "15:40 - 16:40", "16:50 - 17:50", "18:00 - 19:00",
  "19:10 - 20:10", "20:20 - 21:20"
]);
const dias = ref(["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"]);
const newLectures = ref([]);
const originalLectures = ref([]);
const expandedSlot = ref(null);
const modeEdit = ref(false);

const handleExpand = (slot) => {
  if (expandedSlot.value) {
    expandedSlot.value = expandedSlot.value[0] === slot[0] && expandedSlot.value[1] === slot[1] ? null : slot;
  } else {
    expandedSlot.value = slot;
  }
};
const isExpanded = (slot) => {
  return expandedSlot.value && expandedSlot.value[0] === slot[0] && expandedSlot.value[1] === slot[1];
};
const addLecture = (array) => {
  newLectures.value.push({
    ...LecturesService.lectureToRequest(props.lecture),
    periodo: array[0],
    diaSemana: array[1],
  });
  originalLectures.value.push({
    ...LecturesService.lectureToHorarioLecture(props.lecture),
    periodo: array[0],
    diaSemana: array[1],
    newLecture: true
  });
};
const delLecture = (array) => {
  const index = newLectures.value.findIndex(lecture => lecture.periodo === array[0] && lecture.diaSemana === array[1]);
  if (index !== -1) newLectures.value.splice(index, 1);
  const index2 = originalLectures.value.findIndex(lecture => lecture.periodo === array[0] && lecture.diaSemana === array[1]);
  if (index2 !== -1) originalLectures.value.splice(index2, 1);
};
const getLectures = (periodo, dia) => {
  return originalLectures.value.filter(lecture => lecture.periodo === periodo && lecture.diaSemana === dia);
};
const close = () => {
  emits("close");
  newLectures.value = [];
};
const save = () => {
  emits("save", newLectures.value);
  close();
};

function handleModeEdit() {
  modeEdit.value = !modeEdit.value;
  emits('updateModeEdit', modeEdit.value);
}

function handleCancelEdit() {
  modeEdit.value = false;
  emits('updateModeEdit', modeEdit.value);
}

function handleSaveEdit() {
  emits("saveEdit");
}

watch(() => [props.id, props.visible, props.schedule], async () => {
  newLectures.value = [];
  if (!props.visible) return;
  switch (props.type) {
    case "course":
      originalLectures.value = await CourseService.getHorarioByCourseId(props.id);
      break;
    case "classroom":
      originalLectures.value = await ClassroomService.getHorarioByClassroomId(props.id);
      break;
    case "user":
      originalLectures.value = await UserService.getHorarioByUserId(props.id);
      break;
    case "schedule":
      originalLectures.value = props.schedule;
      break;
  }
});

onMounted(() => {
  addEventListener("keydown", (e) => {
    if (e.key === "Escape") close();
  });
  document.addEventListener("click", (e) => {
    if (e.target.classList.contains("absolute")) close();
  });
})
onUnmounted(() => {
  removeEventListener("keydown", (e) => {
    if (e.key === "Escape") close();
  });
  document.removeEventListener("click", (e) => {
    if (e.target.classList.contains("absolute")) close();
  });
});
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>


