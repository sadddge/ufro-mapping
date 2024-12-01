import axios from 'axios';
class ClassroomService {

    async getClassrooms() {
        const response = await axios.get('http://localhost:8080/ufro_mapping/api/salas');
        return response.data;
    }

    async getClassroomById(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/salas/${id}`);
        return response.data;
    }

    async createClassroom(classroom) {
        const response = await axios.post('http://localhost:8080/ufro_mapping/api/salas', classroom).catch((error) => alert(error.response.data.error));
        return response.data;
    }

    async updateClassroom(id, classroom) {
        const response = await axios.put(`http://localhost:8080/ufro_mapping/api/salas/${id}`, classroom);
        return response.data;
    }

    async deleteClassroom(id) {
        const response = await axios.delete(`http://localhost:8080/ufro_mapping/api/salas/${id}`);
        return response.data;
    }

    async getHorarioByClassroomId(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/salas/${id}/horario`)
        return response.data;
    }

}

export default new ClassroomService();