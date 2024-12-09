import React, { useEffect, useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import Home from './pages/Home';
import Welcome from './components/Welcome';
import Login from './components/Login';
import ErrorBoundary from './components/ErrorBoundary';
import ParkingDetails from './pages/ParkingDetails';

function App() {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8081/api/users/me', {
            method: 'GET',
            credentials: 'include',
        })
            .then((response) => {
                if (!response.ok) throw new Error('User not logged in');
                return response.json();
            })
            .then((data) => {
                setUser(data);

                const redirectParkingSpotId = sessionStorage.getItem('redirectParkingSpotId');
                if (redirectParkingSpotId) {
                    sessionStorage.removeItem('redirectParkingSpotId');
                    console.log('Redirecting to parking details for:', redirectParkingSpotId);
                    navigate(`/parking/${redirectParkingSpotId}`);
                } else {
                    navigate('/welcome');
                }
            })
            .catch((error) => {
                console.error('Error fetching user:', error);
            });
    }, [navigate]);

    const handleLogout = () => {
        setUser(null);
        navigate('/'); // Redirect to Home after logout
    };

    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login setUser={setUser} />} />
            <Route
                path="/welcome"
                element={
                    <ErrorBoundary>
                        <Welcome user={user} onLogout={handleLogout} />
                    </ErrorBoundary>
                }
            />
            <Route
                path="/parking/:id"
                element={
                    <ErrorBoundary>
                        <ParkingDetails />
                    </ErrorBoundary>
                }
            />
        </Routes>
    );
}

export default App;
