import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/Login.css';

const Login = ({ setUser }) => {
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        console.log('%c[Login] Checking Session Storage', 'color: green; font-weight: bold;');
        console.log('redirectParkingSpotId:', sessionStorage.getItem('redirectParkingSpotId'));

        fetch('http://localhost:8081/api/users/me', {
            method: 'GET',
            credentials: 'include',
        })
            .then((response) => {
                if (!response.ok) throw new Error('User not authenticated');
                return response.json();
            })
            .then((data) => {
                console.log('%c[Login] User authenticated', 'color: green; font-weight: bold;', data);
                setUser(data);
                const redirectParkingSpotId = sessionStorage.getItem('redirectParkingSpotId');
                if (redirectParkingSpotId) {
                    console.log('%c[Login] Redirecting to parking details for spot:', 'color: blue;', redirectParkingSpotId);
                    sessionStorage.removeItem('redirectParkingSpotId');
                    navigate(`/parking/${redirectParkingSpotId}`);
                } else {
                    console.log('%c[Login] No parking spot in session. Redirecting to /welcome', 'color: blue;');
                    navigate('/welcome');
                }
            })
            .catch((e) => {
                console.error('Error during login:', e);
                setError('Failed to authenticate. Please try again.');
            });
    }, [navigate, setUser]);

    const handleLogin = () => {
        window.location.href = 'http://localhost:8081/oauth2/authorization/google';
    };

    return (
        <div className="login-container">
            <button
                className="login-button"
                onClick={handleLogin}
                aria-label="Login with Google"
            >
                Login with Google
            </button>

        </div>
    );
};

export default Login;
