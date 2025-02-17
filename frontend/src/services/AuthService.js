import axiosInstance from '../plugins/axios';
class AuthService {
        async validateSession() {
            const response = await axiosInstance.get('/auth/validate-session');
            return response && response.status === 200;
        }
        async login(email, password) {
            return await axiosInstance.post('/auth/login',
                {
                    usernameOrEmail: email,
                    contrasenia: password
                });
        }
        async logout() {
            await axiosInstance.post('/auth/logout');
        }
        async getUserInfo() {
            const response = await axiosInstance.get('/auth/user-info');
            return response.data;
        }

        async changePassword(userId, oldPassword, newPassword) {

        }

        register(username, email, password) {
            return axiosInstance.post('/auth/register', {
                nombre: username,
                correo: email,
                contrasenia: password
            });
        }
}

export default new AuthService();