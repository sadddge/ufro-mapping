import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import AdminHomeView from '@/views/AdminHomeView.vue'
import UsuariosView from '@/views/UsuariosView.vue'
import EdificiosView from '@/views/EdificiosView.vue'
import SalasView from '@/views/SalasView.vue'
import AsignaturasView from '@/views/AsignaturasView.vue'
import ClasesView from '@/views/ClasesView.vue'
import MapView from "@/views/MapView.vue";
import HomeView from "@/views/HomeView.vue";
import { useAuthStore} from "@/stores/auth.js";
import PageNotFoundView from "@/views/PageNotFoundView.vue";
import RegisterView from "@/views/RegisterView.vue";
import ProfileView from "@/views/ProfileView.vue";
import ScheduleView from '@/views/ScheduleView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect : '/home'
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginView,
    },
    {
      path: '/home',
      name: 'Home',
      component: HomeView,
        meta: { requiresAuth: true },
    },
    {
      path: '/admin',
      name: 'AdminHome',
      component: AdminHomeView,
      meta: { requiresAuth: true,  role : 'ADMIN' },
    },
    {
      path: '/admin/edificios',
      name: 'Edificios',
      component: EdificiosView,
      meta: { requiresAuth: true,  role : 'ADMIN' },
    },
    {
      path: '/admin/salas',
      name: 'Salas',
      component: SalasView,
      meta: { requiresAuth: true,  role : 'ADMIN' },
    },
    {
      path: '/admin/usuarios',
      name: 'Usuarios',
      component: UsuariosView,
      meta: { requiresAuth: true, role : 'ADMIN' },
    },
    {
      path: '/admin/asignaturas',
      name: 'Asignaturas',
      component: AsignaturasView,
      meta: { requiresAuth: true, role : 'ADMIN' },
    },
    {
      path: '/admin/clases',
      name: 'Clases',
      component: ClasesView,
      meta: { requiresAuth: true },
    },
    {
      path: "/admin/mapa",
      name: "Map",
      component: MapView,
      meta: { requiresAuth: true },
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: PageNotFoundView,
    },
    {
      path: '/register',
      name: 'Register',
      component: RegisterView,
    },
    {
      path: "/profile",
      name: "Profile",
      component: ProfileView,
    },
    {
      path: "/user/schedule",
      name: "Schedule",
      component: ScheduleView,
    },
    {
      path: "/user/schedule/edit",
      name: "scheduleEdit",
      component: ScheduleView
    }
  ],
})

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore();
  await new Promise((resolve) => setTimeout(resolve, 150));
  if ((to.name === 'Login' || to.name === 'Register') && authStore.isAuthenticated) {
    next({ name: 'AdminHome'});
  } else if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next({ name: 'Login' });
  } else if (to.meta.role && to.meta.role !== authStore.userRole) {
    next({ name: 'Home' });
  } else {
    next();
  }
});

export default router
