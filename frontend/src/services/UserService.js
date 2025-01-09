import axiosInstance from '../plugins/axios';

class UserService {

    async register(user) {
        const response = await axiosInstance.post('/auth/register', user);
        return response.data;
    }

    async getUsers() {
        const response = await axiosInstance.get('/usuarios');
        return response.data;
    }

    async getUserById(id) {
        const response = await axiosInstance.get(`/usuarios/${id}`);
        return response.data;
    }

    async deleteUser(id) {
        const response = await axiosInstance.delete(`usuarios/${id}`);
        return response.data;
    }

    async updateUser(id, usuario) {
        const response = await axiosInstance.put(`usuarios/${id}`, usuario);
        return response.data;
    }

    async getAsignaturasByUserId(id) {
        const response = await axiosInstance.get(`usuarios/${id}/asignaturas`);
        return response.data;
    }

    async getHorarioByUserId(id) {
        const response = await axiosInstance.get(`usuarios/${id}/horario`);
        return response.data;
    }

    async registerAsignatura(id, asignaturaId) {
        const response = await axiosInstance.post(`usuarios/${id}/asignaturas/${asignaturaId}`);
        return response.data;
    }

    async deleteAsignatura(id, asignaturaId) {
        const response = await axiosInstance.delete(`usuarios/${id}/asignaturas/${asignaturaId}`);
        return response.data;
    }

    async nextLocations(id) {
        const response = await axiosInstance.get(`clases/${id}/nextLocations`);
        return response.data;
    }

    async isInCurrentPeriod(id) {
        const response = await axiosInstance.get(`clases/${id}/isInCurrentPeriod`);
        return response.data;
    }
}

export default new UserService();