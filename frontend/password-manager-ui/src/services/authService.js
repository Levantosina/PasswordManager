import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/auth';

export const login = async (credentials) => {
    try {
        const response = await axios.post(`${API_URL}/login`, credentials, {
            headers: { 'Content-Type': 'application/json' },
        });
        return response.data;
    } catch (error) {
        console.error('Error in login:', error.response);
        throw error;
    }
};