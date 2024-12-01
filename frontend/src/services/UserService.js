import axios from 'axios';
class UserService {
    async login(email, password) {
        const response = await axios.post('http://localhost:8080/ufro_mapping/api/login', {
            email: email,
            password: password
        });
        return response.data;
    }

    async register(user) {
        try {
            const response = await axios.post('http://localhost:8080/ufro_mapping/api/usuarios', user);
            return response.data;
        } catch (error) {
            if (error.response) {
                return error.response.data;
            }
            return error;
        }
    }

    async getUsers() {
        const response = await axios.get('http://localhost:8080/ufro_mapping/api/usuarios');
        return response.data;
    }

    async getUserById(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/usuarios/${id}`);
        return response.data;
    }

    async deleteUser(id) {
        const response = await axios.delete(`http://localhost:8080/ufro_mapping/api/usuarios/${id}`);
        return response.data;
    }

    async updateUser(id, usuario) {
        const response = await axios.put(`http://localhost:8080/ufro_mapping/api/usuarios/${id}`, usuario);
        return response.data;
    }

    async patchUser(id, usuario) {
        try {
            const response = await axios.patch(`http://localhost:8080/ufro_mapping/api/usuarios/${id}`, usuario);            
            return response.data;
        } catch (error) {
            if (error.response) {
                console.log(error.response.data);
                
                return error.response.data;
            }
        }
    }

    async getAsignaturasByUserId(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/usuarios/${id}/asignaturas`);
        return response.data;
    }

    async getHorarioByUserId(id) {
        const response = await axios.get(`http://localhost:8080/ufro_mapping/api/usuarios/${id}/horario`);
        return response.data;
    }

    async registerAsignatura(id, asignaturaId) {
        try {
            const response = await axios.post(`http://localhost:8080/ufro_mapping/api/usuarios/${id}/asignaturas/${asignaturaId}`);
            return response.data;
        } catch (error) {
            console.log(error.response.data);
            alert(error.response.data.error);
        }
    }

    async deleteAsignatura(id, asignaturaId) {
        const response = await axios.delete(`http://localhost:8080/ufro_mapping/api/usuarios/${id}/asignaturas/${asignaturaId}`);
        return response.data;
    }
}

export default new UserService();