<template>
  <div class="overflow-hidden rounded-lg border border-[rgba(45,45,48,1)]">
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
                        :slot="[i+1,j]"
                        @toggleExpand="handleExpand"
                        :isExpanded="isExpanded([i+1,j])"
          />
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import {ref} from "vue";
import CalendarSlot from "@/components/CalendarSlot.vue";

const props = defineProps({
  lectures: {
    type: Array,
    required: true,
  }
});

const periodos = ref([
  "08:30 - 09:30", "09:40 - 10:40", "10:50 - 11:50",
  "12:00 - 13:00", "13:10 - 14:10", "14:30 - 15:30",
  "15:40 - 16:40", "16:50 - 17:50", "18:00 - 19:00",
  "19:10 - 20:10", "20:20 - 21:20"
]);
const dias = ref(["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"]);
const expandedSlot = ref(null);

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

const getLectures = (periodo, dia) => {
  return props.lectures.filter(lecture => lecture.periodo === periodo && lecture.diaSemana === dia);
};

</script>



