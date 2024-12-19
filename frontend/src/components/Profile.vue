<template>

  <div
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
  >
    <div class="absolute inset-0 backdrop-blur-sm"></div>
    <div class="bg-zinc-800  dark:bg-gray-800 rounded-lg p-6 w-[90%] max-w-sm relative z-10">
      <router-link :to = "{name: 'Home'}" class="absolute top-4 right-4">
        <n-button text>
          <n-icon size="20">
            <Dismiss20Regular/>
          </n-icon>
        </n-button>
      </router-link>
      <h2 class="text-xl font-bold mb-4">Opciones</h2>
      <div class="flex flex-col space-y-4">

        <n-button tertiary @click="showProfile()"  >
          <n-icon size="20"><PersonIcon /></n-icon> Perfil
        </n-button>
        <n-button tertiary @click="logout">
          <n-icon size="20" class="text-current" ><SignOutIcon /></n-icon> Logout
        </n-button>

      </div>
    </div>
  </div>

  <div
      v-if="showModal"
      class="fixed inset-0 flex items-center justify-center z-50"
  >
    <div class="absolute inset-0 backdrop-blur-sm"></div>
    <div class="bg-zinc-800 dark:bg-gray-800 rounded-lg p-6 w-[90%] max-w-lg relative z-10">
      <n-button text class="absolute top-4 right-4" @click="closeModal">
        <n-icon size="20">
          <Dismiss20Regular/>
        </n-icon>
      </n-button>
      <div class="flex items-center mb-4">
        <h2 class="text-xl font-bold">Perfil</h2>
        <div class="ml-5 flex space-x-4">

          <div v-if="!editing" class="pr-4 flex gap-5 ">
            <n-button ghost
                      @click="startEditing" text class="text-gray-400">
              <template #icon>
              </template>
              Editar Perfil
            </n-button>
            <n-button ghost @click="showChangePasswordModal = true" text class="text-gray-400">
              <template #icon>
              </template>
              Cambiar Contraseña
            </n-button>

          </div>
        </div>
      </div>
      <form class="flex flex-col space-y-4">
        <div>
          <label for="name" class="block text-sm font-medium">Nombre de usuario</label>
          <div v-if="!editing">
            <h1>{{ profile.nombre }}</h1>
          </div>
          <div v-else>
            <input
                v-model="editableProfile.nombre"
                type="text"
                class="w-full border rounded-md p-2 text-gray-800"
            />
          </div>
        </div>
        <div>
          <label for="correo" class="block text-sm font-medium">Correo</label>
          <div v-if="!editing">
            <h1>{{ profile.correo }}</h1>
          </div>
          <div v-else>
            <input
                v-model="editableProfile.correo"
                type="email"
                class="w-full border rounded-md p-2 text-gray-800"
            />
          </div>
        </div>
      </form>
      <div v-if="editing" class="flex justify-end space-x-4 mt-4">
        <button
            @click="cancelEditing"
            class="bg-gray-500 text-white py-2 px-4 rounded-md hover:bg-gray-600"
        >
          Cancelar
        </button>
        <button
            @click="saveChanges"
            class="bg-[#63e2b7] text-black py-2 px-4 rounded-md hover:bg-blue-600"
        >
          Guardar
        </button>
      </div>
    </div>
  </div>

  <div
      v-if="showChangePasswordModal"
      class="fixed inset-0  flex items-center justify-center z-50"
  >
    <div class="absolute inset-0 backdrop-blur-sm"></div>
    <div class="bg-zinc-800 dark:bg-gray-800 rounded-lg p-6 w-[90%] max-w-lg relative z-10">
      <n-button text class="absolute top-4 right-4" @click="showChangePasswordModal = false">
        <n-icon size="20">
          <Dismiss20Regular/>
        </n-icon>
      </n-button>
      <h2 class="text-xl font-bold mb-4">Cambiar Contraseña</h2>
      <n-form ref="formRef" :model="model" :rules="rules">
        <n-form-item path="oldpassword" label="Contraseña Actual">
          <n-input
              v-model:value="model.oldpassword"
              type="password"
              @input="handlePasswordInput"
              @keydown.enter.prevent
              placeholder="Contraseña Actual"
          />
        </n-form-item>
        <n-form-item path="password" label="Nueva Contraseña">
          <n-input
              v-model:value="model.password"
              type="password"
              @keydown.enter.prevent
              placeholder="Nueva Contraseña"
          />
        </n-form-item>
        <n-form-item
            ref="rePasswordFormItemRef"
            path="reenteredPassword"
            label="Confirmar Contraseña"
        >
          <n-input
              v-model:value="model.reenteredPassword"
              type="password"
              @keydown.enter.prevent
              placeholder="Confirmar Contraseña"
          />
        </n-form-item>
        <div class="flex justify-end mt-4">
          <n-button
              :disabled="!model.password || !model.reenteredPassword"
              round
              type="primary"
              @click="handleValidateButtonClick"
          >
            Guardar
          </n-button>
        </div>
      </n-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { useAuthStore } from "@/stores/auth.js";
import { useRouter } from "vue-router";
import UserService from "../services/UserService";
import { useMessage } from "naive-ui";
import {Dismiss20Regular, SignOut20Filled as SignOutIcon} from "@vicons/fluent";
import { Person20Filled as PersonIcon } from "@vicons/fluent";
import AuthService from "@/services/AuthService.js";


const store = useAuthStore();
const router = useRouter();
const showModal = ref(false);
const showOptions = ref(false);
const showChangePasswordModal = ref(false);
const editing = ref(false);
const formRef = ref(null);
const rPasswordFormItemRef = ref(null);
const message = useMessage();
const model = ref({
  oldpassword: null,
  password: null,
  reenteredPassword: null
});


const profile = reactive({
  nombre: "",
  correo: "",
  contrasenia: "",
});
const editableProfile = ref({ ...profile });

onMounted(async () => {
  await getProfileInfo();
});

const getProfileInfo = async () => {
  try {
    const user = await UserService.getUserById(store.userId);
    profile.nombre = user.nombre;
    profile.correo = user.correo;
    profile.contrasenia = user.contrasenia;
  } catch (error) {
    console.error("Error al obtener el perfil:", error);
    alert("Hubo un error al obtener el perfil");
  }
};


const logout = () => {
  store.logout();
  router.push("/login");
};

const showProfile = () => {
  showOptions.value = false;
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
  editing.value = false;
};

const startEditing = () => {
  editableProfile.value = { ...profile };
  editing.value = true;
};

const cancelEditing = () => {
  editableProfile.value = { ...profile };
  editing.value = false;
};
const saveChanges = async () => {
  try {
    await UserService.updateUser(store.userId, editableProfile.value);
    await getProfileInfo();
    editing.value = false;
    alert('Perfil actualizado correctamente');
  } catch (error) {
    console.error('Error al actualizar el perfil:', error);
    alert('Hubo un error al actualizar el perfil, nombre o correo invalido.');
  }
};

const validatePasswordSame = (rule, value) => {
  return value === model.value.password;
};

const rules = ref({

  password: [
    {
      required: true,
      message: "Necesitas ingresar una contraseña",
      trigger: ["input", "blur"]
    }
  ],
  reenteredPassword: [
    {
      required: true,
      message: "Confirmar la contraseña es necesario ",
      trigger: ["input", "blur"]
    },
    {
      validator: validatePasswordSame,
      message: "La contraseña nueva no es la misma que la confirmacion de contraseña",
      trigger: ["blur", "password-input"]
    }
  ],
  oldpassword: [
    {
      required: true,
      message: "Necesitas ingresar tu contraseña actual"
    }
  ]
});

const handlePasswordInput = () => {
  if (model.value.reenteredPassword) {
    rPasswordFormItemRef.value?.validate({ trigger: "password-input" });
  }
};

const handleValidateButtonClick = (e) => {
  e.preventDefault();
  formRef.value?.validate((errors) => {
    if (!errors) {
      message.success("Valid");
      AuthService.changePassword(store.userId, model.value.oldpassword, model.value.password).then(() => {
        message.success("Contraseña cambiada correctamente");
      });
      showChangePasswordModal.value = false;

    } else {
      console.log(errors);
      message.error("Invalid");
    }
  });
};

</script>

