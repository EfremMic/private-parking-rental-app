import React, { useEffect, useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import Home from './pages/Home';
import Welcome from './components/Welcome';
import Login from './components/Login';
import ErrorBoundary from './components/ErrorBoundary';

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
                    navigate('/welcome'); // Redirect to Welcome if user is authenticated
                }
            })
            .catch(error => {
                console.error('Error fetching user:', error);
            });
    }, [navigate]);

    const handleLogout = () => {
        setUser(null);
        navigate('/'); // Redirect to Home after logout
    };

    return (
        <Routes>
            {/* Home is now the default main page */}
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route
                path="/welcome"
                element={
                    <ErrorBoundary>
                        <Welcome user={user} onLogout={handleLogout} />
                    </ErrorBoundary>
                }
            />
        </Routes>
    );
}

export default App;
