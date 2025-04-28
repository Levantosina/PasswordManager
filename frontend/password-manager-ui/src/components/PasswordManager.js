import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchPasswords, createNewPassword, updatePassword, deletePassword } from '../services/passwordService';

const PasswordManager = () => {
    const [passwords, setPasswords] = useState([]);
    const [newServiceName, setNewServiceName] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const navigate = useNavigate();

    const loadPasswords = async () => {
        try {
            const response = await fetchPasswords();
            setPasswords(response.data);
        } catch (error) {
            console.error('Error fetching passwords:', error);
            alert('Failed to fetch passwords: ' + error.message);
        }
    };

    const handleCreatePassword = async () => {
        if (!newServiceName || !newPassword) {
            alert('Please provide both Service Name and Password');
            return;
        }
        try {
            await createNewPassword({ serviceName: newServiceName, password: newPassword });
            alert('Password created successfully!');
            loadPasswords();
        } catch (error) {
            console.error('Error creating password:', error);
            alert('Failed to create password: ' + error.message);
        }
        setNewServiceName('');
        setNewPassword('');
    };

    const handleUpdatePassword = async (passwordId, currentServiceName) => {
        const newServiceName = prompt("Enter new service name:");
        const newPassword = prompt("Enter new password:");
        if (newServiceName && newPassword) {
            try {
                await updatePassword({
                    passwordId,
                    currentServiceName,
                    newServiceName,
                    newPassword
                });
                alert('Password updated successfully!');
                loadPasswords();
            } catch (error) {
                console.error('Error updating password:', error);
                alert('Failed to update password: ' + error.message);
            }
        } else {
            alert("Both new service name and password are required!");
        }
    };

    const handleDeletePassword = async (passwordId) => {
        try {
            await deletePassword(passwordId);
            alert('Password deleted!');
            loadPasswords();
        } catch (error) {
            console.error('Error deleting password:', error);
            alert('Failed to delete password: ' + error.message);
        }
    };
    const handleLogout = () => {
        localStorage.removeItem('token'); // Clear the token from storage
        alert('Logged out successfully!');
        navigate('/login'); // Redirect to login page
    };

    useEffect(() => {
        loadPasswords();
    }, []);

    return (
        <div>
            <h2>Your Saved Passwords</h2>
            <table>
                <thead>
                <tr>
                    <th>Service Name</th>
                    <th>Password</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {passwords.map((password) => (
                    <tr key={password.passwordId}>
                        <td>{password.serviceName}</td>
                        <td>{password.encryptedPassword}</td>
                        <td>
                            <button onClick={() => handleDeletePassword(password.passwordId)}>Delete</button>
                            <button
                                onClick={() => handleUpdatePassword(password.passwordId, password.serviceName)}>Update
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div>
                <h3>Add New Password</h3>
                <input
                    type="text"
                    placeholder="Service Name"
                    value={newServiceName}
                    onChange={(e) => setNewServiceName(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                />
                <button onClick={handleCreatePassword}>Add Password</button>
            </div>
            <div>
                <h3>Audit Logs</h3>
                <button onClick={() => navigate('/audit-logs')}>View Audit Logs</button>
            </div>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};



export default PasswordManager;
