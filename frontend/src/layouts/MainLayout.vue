<template>
    <div class="flex flex-row px-52">
        <div class="w-52 h-screen flex p-0 items-center">
            <n-menu v-model:value="activeKey" :options="menuOptions" responsive class="w-full"/>
        </div>
        <slot></slot>
    </div>
</template>

<script setup>
import { h, ref } from 'vue'
import { Person20Filled, Building16Filled, Class24Filled, Book20Filled, ConferenceRoom20Filled } from '@vicons/fluent';
import { RouterLink, useRoute } from 'vue-router';
const route = useRoute()
const activeKey = ref(route.name)

const menuOptions = [
    
    {
        key: 'usuarios',
        label: () => h(RouterLink, { to: '/usuarios' }, 'Usuarios'),
        icon: () => h(Person20Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    { 
        key: 'edificios',
        label: () => h(RouterLink, { to: '/edificios' }, 'Edificios'),
        icon: () => h(Building16Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    {
        key: 'salas',
        label: () => h(RouterLink, { to: '/salas' }, 'Salas'),
        icon: () => h(ConferenceRoom20Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    {
        key: 'asignaturas',
        label: () => h(RouterLink, { to: '/asignaturas' }, 'Asignaturas'),
        icon: () => h(Book20Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    {
        key: 'clases',
        label: () => h(RouterLink, { to: '/clases' }, 'Clases'),
        icon: () => h(Class24Filled)
    },
]
</script>
