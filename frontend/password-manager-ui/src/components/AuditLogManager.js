import React, { useEffect, useState } from 'react';
import axios from "axios";

const AuditLogPage = () => {
    const [logs, setLogs] = useState([]);

    const fetchAuditLogs = async () => {
        const token = localStorage.getItem('token');
        try {
            const response = await axios.get('http://localhost:8080/api/v1/auditLog', {
                headers: { Authorization: `Bearer ${token}` },
            });
            setLogs(response.data);
        } catch (error) {
            console.error('Error fetching audit logs:', error);
            alert('Failed to fetch audit logs: ' + error.message);
        }
    };

    useEffect(() => {
        fetchAuditLogs();
    }, []);

    return (
        <div>
            <h2>Audit Logs</h2>
            <table>
                <thead>
                <tr>
                    <th>Action</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                {logs.map((log, index) => (
                    <tr key={index}>
                        <td>{log.action}</td>
                        <td>{log.timestamp}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default AuditLogPage;