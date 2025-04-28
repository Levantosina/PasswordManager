import React, { useState } from 'react';
import { login } from '../services/authService';
import {useNavigate} from "react-router-dom";

const LoginForm = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await login({ userName: email, password });
            console.log('Server response:', response);
            localStorage.setItem('token', response.token);
            navigate('/passwords');
        } catch (error) {
            console.error('Error in login:', error);
            alert('Login failed: ' + error.message);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button onClick={handleLogin}>Login</button>
            <button onClick={() => navigate('/register')}>Register</button>
        </div>
    );
};

export default LoginForm;
