<template>
<div class=" h-screen m-auto flex flex-col gap-10 overflow-hidden items-center justify-center">
  <div class="shadow-lg border border-[rgb(48,47,52)] rounded-xl w-1/3 p-10 flex flex-col gap-6">
    <h1 class="text-3xl text-center">Registrar</h1>
    <n-form ref="formRef" :model="model" :rules="rules">
      <n-form-item path="nombre" label="Nombre">
        <n-input v-model:value="model.nombre" placeholder="Ingrese su nombre">
          <template #prefix>
            <n-icon size="20">
              <Person12Filled />
            </n-icon>
          </template>
        </n-input>
      </n-form-item>


      <n-form-item path="correo" label="Correo">
        <n-input v-model:value="model.correo" placeholder="Ingrese su correo">
          <template #prefix>
            <n-icon size="20">
              <Mail20Filled />
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

      <n-form-item path="nuevaContrasenia" label="Confirme Contraseña">
        <n-input type="password" show-password-on="mousedown" placeholder="Confirme su contraseña" v-model:value="model.nuevaContrasenia">
          <template #prefix>
            <n-icon size="20">
              <LockClosed16Filled />
            </n-icon>
          </template>
        </n-input>
      </n-form-item>

      <div class="flex flex-col items-center gap-2">
        <n-button class="w-1/2" type="primary" secondary @click="register">Register</n-button>
        <router-link class="w-fit" :to="{name: 'Login'}">
          <n-button text>Ya tengo una cuenta</n-button>
        </router-link>
      </div>

    </n-form>
  </div>
</div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import AuthService from "@/services/AuthService.js";
import {Mail20Filled, Person12Filled, LockClosed16Filled} from '@vicons/fluent';

const router = useRouter()
const model = ref({
  nombre: null,
  correo: null,
  contrasenia: null,
  nuevaContrasenia: null
})
const rules = {
  nombre: {
    required: true,
    message: 'Nombre es requerido',
    trigger: ['blur', 'input']
  },
  correo: {
    required: true,
    message: 'Correo es requerido',
    trigger: ['blur', 'input']
  },
  contrasenia: {
    required: true,
    message: 'Contraseña es requerida',
    trigger: ['blur', 'input']
  },
  nuevaContrasenia: {
      required: true,
      validator: (rule, value) => value === model.value.contrasenia,
      message: 'Las contraseñas deben ser iguales',
      trigger: ['blur', 'input']
    }
}
const register = () => {
  try {
    if (model.value.contrasenia !== model.value.nuevaContrasenia) {
      return
    }
    AuthService.register(model.value.nombre, model.value.correo, model.value.contrasenia).then((response) => {
      if (response.status === 200) {
        router.push('/login')
      }
    })
  } catch (error) {
    console.log(error)
  }
}

</script>