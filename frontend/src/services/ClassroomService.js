import axiosInstance from '../plugins/axios.js'
class ClassroomService {

    async getClassrooms() {
        const response = await axiosInstance.get('/salas');
        return response.data;
    }

    async getClassroomById(id) {
        const response = await axiosInstance.get(`/salas/${id}`);
        return response.data;
    }

    async createClassroom(classroom) {
        const response = await axiosInstance.post('/salas', classroom);
        return response.data;
    }

    async updateClassroom(id, classroom) {
        const response = await axiosInstance.put(`/salas/${id}`, classroom);
        return response.data;
    }

    async deleteClassroom(id) {
        const response = await axiosInstance.delete(`/salas/${id}`);
        return response.data;
    }

    async getHorarioByClassroomId(id) {
        const response = await axiosInstance.get(`/salas/${id}/horarios`);
        return response.data;
    }

}

export default new ClassroomService();