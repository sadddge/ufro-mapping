import { defineStore } from 'pinia'
import AuthService from "@/services/AuthService.js";

export const useAuthStore = defineStore('auth', () => {
    const isAuthenticated = ref(false)
    const userId = ref(null)
    const userRole = ref(null)
    const validateSession = async () => {
        try {
            isAuthenticated.value = await AuthService.validateSession();
            if (isAuthenticated.value) {
                const userInfo = await AuthService.getUserInfo();
                userId.value = userInfo.id;
                userRole.value = userInfo.rol;
            }
        } catch (error) {
            console.error(error);
        }
    }

    const logout = async () => {
        try {
            await AuthService.logout();
            isAuthenticated.value = false;
            userId.value = null;
            userRole.value = null;
        } catch (error) {
            console.error(error);
        }
    }

    return { isAuthenticated, userId, userRole, validateSession, logout }
})

