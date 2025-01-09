import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/ufro_mapping/api',
    withCredentials: true,
    timeout: 10000
});

axiosInstance.interceptors.response.use(
    response => {
        return response;
    },
    error => {
        if (error.response.data.message) {
            alert(error.response.data.message);
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;