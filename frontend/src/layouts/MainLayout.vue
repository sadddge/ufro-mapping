<template>
  <div class="flex flex-col gap-2 absolute top-10 right-10 items-center">
    <router-link :to = "{name: 'Profile'}">
      <n-button circle size="large">
        <n-icon size="40"><PersonIcon class="rounded-full border-2 border-white shadow-lg cursor-pointer"/></n-icon>
      </n-button>
    </router-link>

    <router-link :to = "{name: 'Profile'}">
      <n-button circle size="medium">
        <n-icon size="30"><calendar-icon class="rounded-full border-2 border-white shadow-lg cursor-pointer p-0.5"/></n-icon>
      </n-button>
    </router-link>
  </div>
  <div class="absolute left-5 top-5">
    <SearchableTree/>
  </div>
</template>

<script setup>
import {CalendarMonth20Filled as CalendarIcon} from "@vicons/fluent";
import { Person20Filled as PersonIcon } from "@vicons/fluent";
import SearchableTree from "@/components/SearchableTree.vue";
</script>
