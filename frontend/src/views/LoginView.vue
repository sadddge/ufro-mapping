<template>
  <div class="h-screen m-auto pt-80 flex flex-col gap-10 overflow-hidden items-center ">
    <div class="bg accent-gray-800 shadow-lg border border-gray-800 rounded-xl w-1/3 p-10 flex flex-col gap-6">
      <h1 class="text-3xl text-center">Login</h1>
      <n-form ref="formRef" :model="model" :rules="rules">
        <n-form-item path="correo" label="Correo">
          <n-input v-model:value="model.correo" placeholder="Ingrese su correo">
            <template #prefix>
              <n-icon size="20">
                <Person12Filled />
              </n-icon>
            </template>
          </n-input>
        </n-form-item>
        <n-form-item path="contrasenia" label="Contraseña">
          <n-input type="password" show-password-on="mousedown" placeholder="Ingrese su contraseña" v-model:value="model.contrasenia">
            <template #prefix>
              <n-icon size="20">
                <LockClosed16Filled />
              </n-icon>
            </template>
          </n-input>
        </n-form-item>
        <div class="flex flex-col gap-5 mt-4 items-center">
          <n-button class="w-1/2" type="primary" secondary @click="login">Login</n-button>
          <router-link :to="{name: 'Register'}">
            <n-button  text >¿No tienes una cuenta?</n-button>
          </router-link>
        </div>
      </n-form>
    </div>

    <div>
      {{error}}
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import AuthService from "@/services/AuthService.js";
import { useAuthStore } from "@/stores/auth.js";
import { LockClosed16Filled , Person12Filled} from '@vicons/fluent';

const error = ref(null)
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
  } catch (err) {
    console.log(err)
    error.value = err.data.error
  }
}
</script>
