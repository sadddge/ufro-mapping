<template>
  <MapUfro full class="h-screen"/>
  <div v-if="store.isAuthenticated" class="flex flex-col gap-2 absolute top-10 right-10 items-center">
    <router-link :to = "{name: 'Profile'}">
      <n-button circle size="large">
        <n-icon size="40"><PersonIcon class="rounded-full border-2 border-white shadow-lg cursor-pointer"/></n-icon>
      </n-button>
    </router-link>
    <router-link :to = "{name: 'Schedule'}">
      <n-button circle size="medium">
        <n-icon size="30"><calendar-icon class="rounded-full border-2 border-white shadow-lg cursor-pointer p-0.5"/></n-icon>
      </n-button>
    </router-link>
    <router-link v-if="store.userRole==='ADMIN'" :to = "{name: 'AdminHome'}">
      <n-button circle size="medium">
        <n-icon size="30"><AdminIcon class="rounded-full border-2 border-white shadow-lg cursor-pointer p-0.5"/></n-icon>
      </n-button>
    </router-link>
  </div>
  <div v-else class="absolute top-10 right-5 flex gap-4">
    <router-link :to = "{name: 'Login'}">
      <n-button type="primary">Iniciar Sesión</n-button>
    </router-link>
    <router-link :to="{name: 'Register'}">
      <n-button>Registrarse</n-button>
    </router-link>
  </div>
  <div class="absolute left-5 top-5">
    <SearchableTree/>
  </div>
</template>

<script setup>
import { Person20Filled as PersonIcon, PersonSettings20Filled as AdminIcon, CalendarMonth20Filled as CalendarIcon} from "@vicons/fluent";
import SearchableTree from "@/components/SearchableTree.vue";
import MapUfro from "@/components/MapUfro.vue";
import { useAuthStore } from "@/stores/auth.js";
const store = useAuthStore();
</script>
