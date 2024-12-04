<template>
  <div class="w-1/4 h-screen m-auto pt-80 flex flex-col gap-10 overflow-hidden">
    <h1 class="text-xl">Login</h1>
    <n-form ref="formRef" :model="model" :rules="rules">
      <n-form-item path="correo" label="Correo">
        <n-input v-model:value="model.correo" placeholder="Ingrese su correo"/>
      </n-form-item>
      <n-form-item path="contrasenia" label="Contraseña">
        <n-input type="password" placeholder="Ingrese su contraseña" v-model:value="model.contrasenia"/>
      </n-form-item>
      <n-button type="primary" secondary @click="login">Login</n-button>
    </n-form>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import AuthService from "@/services/AuthService.js";
import { useAuthStore } from "@/stores/auth.js";

const store = useAuthStore()
const router = useRouter()
const formRef = ref(null)
const model = ref({
  correo: null,
  contrasenia: null
})

const rules = {
  correo: {
    required: true,
    message: 'Email is required',
    trigger: ['blur', 'input']
  },
  contrasenia: {
    required: true,
    message: 'Password is required',
    trigger: ['blur', 'input']
  }
}

const login = () => {
  try {
    AuthService.login(model.value.correo, model.value.contrasenia).then((response) => {
      if (response.status === 200) {
        store.validateSession().then(() => {
          if (store.userRole === 'ADMIN') {
            router.push('/admin')
          } else {
            router.push('/home')
          }
        })
      }
    })
  } catch (error) {
    console.log(error)
  }
}
</script>
