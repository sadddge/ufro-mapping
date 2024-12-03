import { defineStore } from 'pinia'
import AuthService from "@/services/AuthService.js";

export const useAuthStore = defineStore('auth', () => {
    let isAuthenticated = ref(false)
    const validateSession = async () => {
        isAuthenticated.value = await AuthService.validateSession()
    }

    return { isAuthenticated, validateSession }
})

