import React, { useEffect, useState } from 'react';
import { Routes, Route, useNavigate } from 'react-router-dom';
import Home from './pages/Home';
import Welcome from './components/Welcome';
import Login from './components/Login';
import ErrorBoundary from './components/ErrorBoundary';
import ParkingDetails from './pages/ParkingDetails';
import RentForm from './pages/RentForm';
import ContactOwner from './pages/ContactOwner';

function App() {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8081/api/users/me', {
            method: 'GET',
            credentials: 'include',
        })
            .then((response) => {
                if (!response.ok) {
                    console.log('User not logged in'); // Debug log
                    throw new Error('Failed to fetch user data');
                }
                return response.json();
            })
            .then((data) => {
                console.log('Logged-in user data:', data); // Debug log
                setUser(data);

                // Redirect user to their intended route after login
                const redirectPath = sessionStorage.getItem('redirectAfterLogin') || '/welcome';
                console.log('Redirecting to:', redirectPath); // Debug log
                sessionStorage.removeItem('redirectAfterLogin'); // Clear after use
                navigate(redirectPath);
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
            <Route path="/login" element={<Login />} />
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
                        <ParkingDetails user={user} />
                    </ErrorBoundary>
                }
            />
            <Route
                path="/rent/:id"
                element={
                    <ErrorBoundary>
                        <RentForm user={user} />
                    </ErrorBoundary>
                }
            />
            <Route
                path="/contact/:id"
                element={
                    <ErrorBoundary>
                        <ContactOwner user={user} />
                    </ErrorBoundary>
                }
            />
        </Routes>
    );
}

export default App;
