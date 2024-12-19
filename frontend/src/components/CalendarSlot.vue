<template>
  <div class="relative h-16">
    <div
        v-if="lectures.length>=1"
        :class="[
        'transition-all h-full duration-300 flex flex-col items-center justify-center',
        moreThanOne  ? 'bg-yellow-300 bg-opacity-20 cursor-pointer' : '',
        moreThanOne && isExpanded  ? 'opacity-50' : '',
        !moreThanOne && lectures[0].isPreview ? 'bg-green-300 bg-opacity-50' : '',
        ]"
        @click="toggleExpand"
    >
      <div>{{ getCode(lectures[0]) }}</div>
      <div>{{ lectures[0].nombreAsignatura }}</div>
      <div>{{ lectures[0].nombreSala }}</div>
      <div v-if="lectures[0].newLecture" class="absolute -top-2 -right-2">
        <n-button secondary circle size="small" type="error" @click="delLecture">
          <template #icon>
            <n-icon>
              <Delete20Regular/>
            </n-icon>
          </template>
        </n-button>
      </div>
    </div>
    <div class="h-full" v-else-if="add">
      <n-button quaternary class="w-full h-full p-0 opacity-0 hover:opacity-70 rounded-none" @click="addLecture">
        <template #icon>
          <n-icon>
            <Add20Filled/>
          </n-icon>
        </template>
      </n-button>
    </div>
    <transition-group
        name="staggered-expand"
        tag="div"
    >
      <div
          v-if="isExpanded"
          v-for="(lecture, i) in lectures"
          :key="i"
          class="mt-1 relative transition-transform duration-300 h-full z-10"
          :style="{ '--delay': `${i * 100}ms` }"

      >
        <div class="hover:scale-110 border-zinc-600 border w-full duration-300"
             :class="[lecture.isPreview ? 'bg-green-300 bg-opacity-50' : 'bg-zinc-800']"
        >
          <div>{{ getCode(lecture) }}</div>
          <div>{{ lecture.nombreAsignatura }}</div>
          <div>{{ lecture.nombreSala }}</div>
        </div>
      </div>
    </transition-group>
  </div>
</template>

<script setup>
import {Add20Filled, Delete20Regular } from "@vicons/fluent";

const props = defineProps({
  lectures: {
    type: Array,
    required: true,
  },
  slot: {
    type: Array,
    required: true,
  },
  editable: {
    type: Boolean,
    default: false,
  },
  add: {
    type: Boolean,
    default: false,
  },
  isExpanded: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["add", "del", "toggleExpand"]);

const toggleExpand = () => {
  if (!moreThanOne.value) return;
  emit("toggleExpand", props.slot);
};

const addLecture = () => {
  emit("add", props.slot);
};

const delLecture = () => {
  emit("del", props.slot);
};

const getCode = (lecture) => {
  return `${lecture.codigoAsignatura}` + (lecture.modulo ? `-${lecture.modulo}` : "");
};

const moreThanOne = computed(() => {
  return props.lectures.length > 1;
});
</script>

<style scoped>
.staggered-expand-enter-active {
  transition: all 0.3s ease-out;
}

.staggered-expand-leave-active {
  transition: all 0.3s ease-in;
}

.staggered-expand-enter-from {
  opacity: 0;
  transform: translateY(-20px);
}

.staggered-expand-enter-to {
  opacity: 1;
  transform: translateY(0);
}

.staggered-expand-leave-from {
  opacity: 1;
  transform: translateY(0);
}

.staggered-expand-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}

div[style*="--delay"] {
  transition-delay: var(--delay);
}
</style>