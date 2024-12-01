import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import HomeView from '@/views/HomeView.vue'
import UsuariosView from '@/views/UsuariosView.vue'
import EdificiosView from '@/views/EdificiosView.vue'
import SalasView from '@/views/SalasView.vue'
import AsignaturasView from '@/views/AsignaturasView.vue'
import ClasesView from '@/views/ClasesView.vue'
import MapView from "@/views/MapView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/edificios',
      name: 'edificios',
      component: EdificiosView,
    },
    {
      path: '/salas',
      name: 'salas',
      component: SalasView,
    },
    {
      path: '/usuarios',
      name: 'usuarios',
      component: UsuariosView,
    },
    {
      path: '/asignaturas',
      name: 'asignaturas',
      component: AsignaturasView,
    },
    {
      path: '/clases',
      name: 'clases',
      component: ClasesView,
    },
    {
      path: "/mapa",
      name: "Map",
      component: MapView,
    }
  ],
})

export default router
