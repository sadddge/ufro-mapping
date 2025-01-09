import { defineStore } from 'pinia'
export const useMapStore = defineStore('map', () => {
    const showMap = ref(false)
    const mapRef = ref(null)

    const setMap = (map) => {
        mapRef.value = map
    }
    const moveMap = (lng, lat) => {
        mapRef.value.flyTo({ center : [lng, lat], zoom: 19.54, speed: 0.75, curve: 2.42, easing: (t) => t })
    }

    return { showMap, mapRef, setMap, moveMap }
})