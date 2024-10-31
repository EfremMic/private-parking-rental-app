import React, { useEffect, useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import Welcome from './components/Welcome';
import Login from './components/Login';
import ErrorBoundary from './components/ErrorBoundary'; // Import ErrorBoundary

function App() {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8081/api/users/me', {
            method: 'GET',
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch user data');
                }
                return response.json();
            })
            .then(data => {
                setUser(data);
                if (data) {
                    navigate('/welcome');
                }
            })
            .catch(error => {
                console.error('Error fetching user:', error);
            });
    }, [navigate]);

    const handleLogout = () => {
        setUser(null);
        window.location.href = 'http://localhost:8080/logout';
    };

    return (
        <Routes>
            <Route path="/" element={<Login />} />
            <Route
                path="/welcome"
                element={
                    <ErrorBoundary>  {/* Wrap Welcome in ErrorBoundary */}
                        <Welcome user={user} onLogout={handleLogout} />
                    </ErrorBoundary>
                }
            />
        </Routes>
    );
}

export default App;
