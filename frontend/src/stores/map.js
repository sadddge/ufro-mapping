import { defineStore } from 'pinia'
export const useMapStore = defineStore('map', () => {
    const showMap = ref(true)
    return { showMap }
})