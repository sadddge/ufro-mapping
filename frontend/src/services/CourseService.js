import axiosInstance from '../plugins/axios.js'
class CourseService {

    async getCourses() {
        const response = await axiosInstance.get('/asignaturas');
        return response.data;
    }

    async getCourseById(id) {
        const response = await axiosInstance.get(`/asignaturas/${id}`);
        return response.data;
    }

    async createCourse(course) {
        const response = await axiosInstance.post('/asignaturas', course);
        return response.data;
    }

    async updateCourse(id, course) {
        const response = await axiosInstance.put(`/asignaturas/${id}`, course);
        return response.data;
    }

    async deleteCourse(id) {
        const response = await axiosInstance.delete(`/asignaturas/${id}`);
        return response.data;
    }

    async getHorarioByCourseId(id) {
        const response = await axiosInstance.get(`/asignaturas/${id}/horario`);
        return response.data;
    }

}
export default new CourseService();