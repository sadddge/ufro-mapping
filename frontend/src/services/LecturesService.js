import axios from 'axios';

class LecturesService {

    async getLectures() {
        const response = await axios.get('http://localhost:8080/ufro_mapping/api/clases');
        return response.data;
    }

    async getLectureById(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/clases/${id}`);
        return response.data;
    }

    async createLecture(lecture) {
        const response = await axios.post('http://localhost:8080/ufro_mapping/api/clases', lecture);
        return response.data;
    }

    async updateLecture(id, lecture) {
        const response = await axios.put(`http://localhost:8080/ufro_mapping/api/clases/${id}`, lecture);
        return response.data;
    }
    async deleteLecture(id) {
        const response = await axios.delete(`http://localhost:8080/ufro_mapping/api/clases/${id}`);
        return response.data;
    }

    lectureToHorarioLecture(lecture) {
        return {
            idClase: lecture.id,
            nombreAsignatura: lecture.asignatura.nombre,
            codigoAsignatura: lecture.asignatura.codigo,
            modulo: lecture.modulo,
            nombreSala: lecture.sala.nombre,
            diaSemana: lecture.diaSemana,
            periodo: lecture.periodo,
        }
    }

    lectureToRequest(lecture) {
        return {
            id: lecture.id,
            salaId: lecture.sala.id,
            asignaturaId: lecture.asignatura.id,
            diaSemana: lecture.diaSemana,
            periodo: lecture.periodo,
            docente: lecture.docente,
            modulo: lecture.modulo,
        }
    }

}

export default new LecturesService();