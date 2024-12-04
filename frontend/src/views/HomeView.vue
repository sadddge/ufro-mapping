<template>
  <h1>USERS VIEW</h1>
  <n-button type="primary" secondary @click="logout">Logout</n-button>
</template>

<script setup>
import { useAuthStore } from "@/stores/auth.js";
import { useRouter } from "vue-router";

const store = useAuthStore()
const router = useRouter()
const logout = () => {
  store.logout()
  router.push('/login')
}
</script>
