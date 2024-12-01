import axios from 'axios';
class BuildingService {

    async getBuildings() {
        const response = await axios.get('http://localhost:8080/ufro_mapping/api/edificios');
        return response.data;
    }

    async getBuildingById(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/edificios/${id}`);
        return response.data;
    }

    async createBuilding(building) {
        const response = await axios.post('http://localhost:8080/ufro_mapping/api/edificios', building);
        return response.data;
    }

    async updateBuilding(id, building) {
        const response = await axios.put(`http://localhost:8080/ufro_mapping/api/edificios/${id}`, building);
        return response.data;
    }

    async patchBuilding(id, building) {
        const response = await axios.patch(`http://localhost:8080/ufro_mapping/api/edificios/${id}`, building);
        console.log(response.data);
        return response.data;
    }

    async deleteBuilding(id) {
        const response = await axios.delete(`http://localhost:8080/ufro_mapping/api/edificios/${id}`);
        return response.data;
    }

    async getRoomsByBuildingId(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/edificios/${id}/salas`);
        return response.data;
    }

    async getLocations() {
        const response = await axios.get('http://localhost:8080/ufro_mapping/api/edificios/map/locations');
        console.log(response.data);
        return response.data;
    }
}

export default new BuildingService();