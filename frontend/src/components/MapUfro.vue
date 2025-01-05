<template>
    <div id="map" class="w-full h-full overflow-hidden" :class="{'border rounded-xl' : !full}"></div>
</template>

<script setup>
import mapboxgl from 'mapbox-gl'
import BuildingsService from "@/services/BuildingsService.js";
import UserService from '@/services/UserService';
import 'mapbox-gl/dist/mapbox-gl.css'
import { useMapStore } from "@/stores/map.js";
import { useRouter } from "vue-router";
import {onMounted} from 'vue'


const router = useRouter()
const props = defineProps({
  edit : {
    type : Boolean,
    default : false
  },
  full : {
    type : Boolean,
    default : false
  }
})

const nextLocations = ref([])
const isInCurrentPeriod = ref(false)
const store = useMapStore()
const locations = ref([])
const markers = defineModel('markers', { default: () => [] });

let map
mapboxgl.accessToken = 'pk.eyJ1Ijoic2FkZGRnZSIsImEiOiJjbHltOWE4ejUxY21lMmtvY2s3ZG5iN2JiIn0.UtJHjn2nuEqAbHkLSOh4pA'
function loadMapOptions() {
  map.on('load', () => {
    const bounds = [
      [-72.618526, -38.749286], // Southwest coordinates
      [-72.616962, -38.747043], // Northeast coordinates
    ]
    map.addControl(new mapboxgl.FullscreenControl(), 'bottom-right')
    map.addControl(new mapboxgl.NavigationControl(), 'bottom-right')
    map.doubleClickZoom.disable()
  })
}
function loadMap() {
  map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/sadddge/cm3jf11rm00hr01ry6y6aa2hz',
    maxBounds: [
      [
        -72.62066302172195,
        -38.74978398069317
      ], // Southwest coordinates
      [
        -72.61417286108734,
        -38.745525470109335
      ], // Northeast coordinates
    ],
    minZoom: 16.68,
    pitch: 12,
  })
}

const labels = () => {
  return {
    type : 'FeatureCollection',
    features : locations.value.map((location) => {
      return {
        type : 'Feature',
        geometry : {
          type : 'Point',
          coordinates : [location.longitud, location.latitud]
        },
        properties : {
          description : location.aliasEdificio ? location.aliasEdificio : location.nombreEdificio,
        }
      }
    })
  }
}

const loadMarkers = () => {
  locations.value.forEach((location) => {
    const name = location.aliasEdificio ? location.aliasEdificio : location.nombreEdificio

    const isNextLocation = nextLocations.value.some(nextLocation => nextLocation.id === location.idEdificio);

    const popup = new mapboxgl.Popup({ closeButton: false
    }).setHTML(`<div class="bg-zinc-900 p-4 border rounded-md hover:pointer">${name}</div>`)

    let markerColor = '';
    if(isInCurrentPeriod.value) {
      markerColor = 'green';
    } else if (isNextLocation) {
      markerColor = 'yellow';
    } 

    const marker = new mapboxgl.Marker({
      draggable: props.edit,
      scale: 0.75,
      color: markerColor
    })
        .setLngLat([location.longitud, location.latitud])
        .addTo(map)

    if (props.edit) {
      marker.setPopup(popup)
      popup.addTo(map)
    } else {

      marker.getElement().addEventListener('click', () => {
        router.push({
          name: 'Edificio',
          params: { id: location.idEdificio },
        })
      })
    }

    markers.value.push({
      marker : marker,
      id : location.idEdificio
    })
  })
}

const removeMarkers = () => {
  markers.value.forEach((marker) => {
    marker.marker.remove()
  })
  markers.value = []
}

async function loadBuildings() {
  locations.value = await BuildingsService.getLocations()
}

onMounted(async () => {
  loadBuildings().then(() => {
    loadMap()
    loadMapOptions()
    map.on('load', () => {
      map.resize();
      map.addSource('locations', {
        type: 'geojson',
        data: labels(),
      })
      map.addLayer({
        id: 'poi-labels',
        type: 'symbol',
        source: 'locations',
        layout: {
          'text-field': ['get', 'description'],
          'text-variable-anchor': ['top', 'bottom', 'left', 'right'],
          'text-radial-offset': 0.5,
          'text-justify': 'auto',
          'icon-image': ['get', 'icon'],
        },
        paint: {
          'text-color': '#fff',
          'text-halo-color': '#000',
          'text-halo-width': 1,
        },
      })
      loadMarkers()
    })
    map.resize()
    store.setMap(map)
  })
  nextLocations.value = await UserService.getNextLocations();
  isInCurrentPeriod.value = await UserService.getIsCurrentPeriod();
})

watch(() => props.edit, (value) => {
  if (value) {
    loadMarkers()
  } else {
    removeMarkers()
  }
})
</script>

<style>
.mapboxgl-popup-content {
  background-color: transparent;
  padding: 0;
}

.mapboxgl-popup-tip {
  display: none;
}
</style>
