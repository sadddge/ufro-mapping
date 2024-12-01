<template>
  <div class="flex flex-col gap-4">
    <div class="flex flex-row w-full justify-between">
      <div class="text-2xl font-semibold">Usuarios</div>
      <n-button quaternary circle @click="newUserDialog">
        <template #icon>
          <n-icon>
            <Add20Filled/>
          </n-icon>
        </template>
      </n-button>
    </div>
    <n-data-table
        :columns="columns"
        :data="data"
        :single-line="false"
        :pagination="pagination"
        v-model:checked-row-keys="userId"
        :paginate-single-page="false"
        :render-cell="renderCell"
        :loading="loading">
    </n-data-table>
  </div>
</template>

<script setup>
import UserService from '@/services/UserService';
import {NButton, NText, useDialog} from 'naive-ui';
import {Add20Filled} from '@vicons/fluent';
import {ref, onMounted, watch} from 'vue';
import Buttons from '@/components/jsComp/Buttons.js';
import {openEditDialog, openDeleteDialog, openNewEntityDialog} from '@/components/jsComp/Dialogs.js';
import {editUserContent, newUserContent} from "@/components/jsComp/Contents.js";

const loading = ref(true);
const dialog = useDialog();
const userId = ref([0]);
const pagination = ref({
  pageSize: 10
});

const renderCell = (value) => {
  if (!value) {
    return h(NText, {depth: 3}, {default: () => "-"});
  }
  return value;
}
const editDialog = (user) => openEditDialog(dialog, user, "usuario", editUserContent, saveEditDialog);
const deleteDialog = (user) => openDeleteDialog(dialog, user, "usuario", confirmDeleteDialog);

const newUserDialog = () => {
  const user = reactive({
    nombre: '',
    correo: '',
    contrasenia: '',
  });
  openNewEntityDialog(dialog, user, "usuario", newUserContent, confirmNewUserDialog);
}

//ConfirmDialogs
const saveEditDialog = async (user) => {
  const response = await UserService.patchUser(user.id, user);
  console.log(response);

  if (response.error) {
    dialog.error({
      title: 'Error',
      content: response.error,
    });
  } else {
    dialog.success({
      title: 'Usuario actualizado',
      content: 'El usuario ha sido actualizado exitosamente',
    });
    await getUsers();
  }
};
const confirmDeleteDialog = (user) => {
  const response = UserService.deleteUser(user.id).then(() => getUsers());
  if (response.error) {
    dialog.error({
      title: 'Error',
      content: 'No se pudo eliminar el usuario',
      positiveText: 'Aceptar',
      closable: false,
    });
  }
};
const confirmNewUserDialog = async (user) => {
  const response = await UserService.register(user);
  if (response.error) {
    dialog.error({
      title: 'Error',
      content: response.error,
      positiveText: 'Aceptar',
      closable: false,
    });
  } else {
    dialog.success({
      title: 'Usuario creado',
      content: 'El usuario ha sido creado exitosamente',
      positiveText: 'Aceptar',
      closable: false,
    });
    await getUsers();
  }
};


//Table
const columns = ref([
  {type: 'selection', multiple: false},
  {key: 'id', title: 'ID', align: 'center', width: '50px'},
  {key: 'nombre', title: 'Nombre', width: '150px'},
  {key: 'correo', title: 'Correo', width: '150px'},
  {
    key: 'actions', title: 'Acciones', width: '120px', align: 'center', render(row) {
      return [Buttons.editButton(row, editDialog), Buttons.deleteButton(row, deleteDialog)];
    },
  }
]);
const data = ref([]);

const getUsers = async () => {
  await UserService.getUsers().then((users) => {
    data.value = [];
    users.forEach((user) => {
      data.value.push({
        key: user.id,
        id: user.id,
        nombre: user.nombre,
        correo: user.correo,
      });
    });
    while (data.value.length % pagination.value.pageSize !== 0) {
      data.value.push({});
    }
  });
};

const emit = defineEmits(['update:userId']);

watch(userId, (newValue) => {
  emit('update:userId', newValue[0]);
});

onMounted(() => {
  getUsers().then(() => (loading.value = false));
});


</script>
