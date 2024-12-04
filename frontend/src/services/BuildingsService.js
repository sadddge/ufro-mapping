import axiosInstance from '../plugins/axios';
class BuildingService {

    async getBuildings() {
        const response = await axiosInstance.get('/edificios');
        return response.data;
    }

    async getBuildingById(id) {
        const response = await axiosInstance.get(`/edificios/${id}`);
        return response.data;
    }

    async createBuilding(building) {
        const response = await axiosInstance.post('/edificios', building);
        return response.data;
    }

    async updateBuilding(id, building) {
        const response = await axiosInstance.put(`/edificios/${id}`, building);
        return response.data;
    }

    async deleteBuilding(id) {
        const response = await axiosInstance.delete(`/edificios/${id}`);
        return response.data;
    }

    async getRoomsByBuildingId(id) {
        const response = await axiosInstance.get(`/edificios/${id}/salas`);
        return response.data;
    }

    async getLocations() {
        const response = await axiosInstance.get('/ubicaciones');
        return response.data;
    }
}

export default new BuildingService();