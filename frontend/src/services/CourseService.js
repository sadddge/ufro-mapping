import axios from 'axios';
class CourseService {

    async getCourses() {
        const response = await axios.get('http://localhost:8080/ufro_mapping/api/asignaturas');
        return response.data;
    }

    async getCourseById(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/asignaturas/${id}`);
        return response.data;
    }

    async createCourse(course) {
        const response = await axios.post('http://localhost:8080/ufro_mapping/api/asignaturas', course);
        return response.data;
    }

    async updateCourse(id, course) {
        const response = await axios.put(`http://localhost:8080/ufro_mapping/api/asignaturas/${id}`, course);
        return response.data;
    }

    async deleteCourse(id) {
        const response = await axios.delete(`http://localhost:8080/ufro_mapping/api/asignaturas/${id}`);
        return response.data;
    }

    async getHorarioByCourseId(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/asignaturas/${id}/horario`)
        return response.data;
    }

}
export default new CourseService();