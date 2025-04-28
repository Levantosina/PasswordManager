import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import PasswordManager from './components/PasswordManager';
import AuditLogManager from "./components/AuditLogManager";
import RegisterForm from "./components/RegisterForm";
import ProtectedRoute from "./components/ProtectedRoute";


const App = () => {
    return (
        <Router>
            <div className="app-container">
                <h1>Password Management App</h1>
                <Routes>
                    <Route path="/register" element={<RegisterForm />} />
                    <Route path="/login" element={<LoginForm />} />

                    <Route
                        path="/passwords"
                        element={
                            <ProtectedRoute>
                                <PasswordManager />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/audit-logs"
                        element={
                            <ProtectedRoute>
                                <AuditLogManager />
                            </ProtectedRoute>
                        }
                    />

                </Routes>
            </div>
        </Router>
    );
};

export default App;
