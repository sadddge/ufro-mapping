import axiosInstance from '../plugins/axios';
class AuthService {
        async validateSession() {
            const response = await axiosInstance.get('/auth/validate-session').catch(() => {});
            return response && response.status === 200;
        }
        async login(email, password) {
            return await axiosInstance.post('/auth/login',
                {
                    correo: email,
                    contrasenia: password
                });
        }
}

export default new AuthService();