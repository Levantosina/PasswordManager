import axios from "axios";

const BASE_URL = 'http://localhost:8080/api/v1';


const getToken = () => localStorage.getItem('token');

export const fetchPasswords = async () => {
    const token = getToken();
    return await axios.get(`${BASE_URL}/passwords/user/get`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const createNewPassword = async (newPasswordData) => {
    const token = getToken();
    return await axios.post(`${BASE_URL}/passwords/user/password`, {
        serviceName: newPasswordData.serviceName,
        encryptedPassword: newPasswordData.password
    }, {
        headers: { Authorization: `Bearer ${token}` },
    });
};

export const updatePassword = async (updateData) => {
    const token = getToken();
    return await axios.put(`${BASE_URL}/passwords/update`, updateData, {
        headers: { Authorization: `Bearer ${token}` },
    });
};


export const deletePassword = async (passwordId) => {
    const token = getToken();
    return await axios.delete(`${BASE_URL}/passwords/user/delete/${passwordId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });
};
