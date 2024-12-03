<template>
    <div class="flex flex-row px-52">
        <div class="w-52 h-screen flex p-0 items-center">
            <n-menu :options="menuOptions" responsive class="w-full"/>
        </div>
        <slot></slot>
    </div>
</template>

<script setup>
import { h } from 'vue'
import { Person20Filled, Building16Filled, Class24Filled, Book20Filled, ConferenceRoom20Filled, SignOut20Filled } from '@vicons/fluent';
import { RouterLink, useRouter } from 'vue-router';
import {useAuthStore} from "@/stores/auth.js";

const router = useRouter()
const store = useAuthStore()

const menuOptions = [
    
    {
        key: 'usuarios',
        label: () => h(RouterLink, { to : { name: 'Usuarios' } }, 'Usuarios'),
        icon: () => h(Person20Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    { 
        key: 'edificios',
        label: () => h(RouterLink, { to : { name: 'Edificios' } }, 'Edificios'),
        icon: () => h(Building16Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    {
        key: 'salas',
        label: () => h(RouterLink, { to : { name: 'Salas' } }, 'Salas'),
        icon: () => h(ConferenceRoom20Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    {
        key: 'asignaturas',
        label: () => h(RouterLink, { to : { name: 'Asignaturas' } }, 'Asignaturas'),
        icon: () => h(Book20Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    {
        key: 'clases',
        label: () => h(RouterLink, { to : { name: 'Clases' } }, 'Clases'),
        icon: () => h(Class24Filled)
    },
    {
        type: 'divider',
        key: 'divider'
    },
    {
        key: 'logout',
        label: 'Logout',
        icon: () => h(SignOut20Filled),
        onClick: () => {
            store.logout()
            router.push('/login')
        }
    }
]
</script>
