import React, {  useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/Login.css';

const Login = ({ setUser }) => {
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8081/api/users/me', {
            method: 'GET',
            credentials: 'include',
        })
            .then((response) => {
                if (!response.ok) throw new Error('User not authenticated');
                return response.json();
            })
            .then((data) => {
                setUser(data); // Set user after login
                const redirectPath = sessionStorage.getItem('redirectAfterLogin') || '/welcome';
                sessionStorage.removeItem('redirectAfterLogin'); // Clear session storage
                navigate(redirectPath); // Redirect to the intended parking spot or welcome page
            })
            .catch((error) => {
                console.error('Error during login:', error);
            });
    }, [navigate, setUser]);

    const handleLogin = () => {
        window.location.href = 'http://localhost:8081/oauth2/authorization/google';
    };

    return (
        <div className="login-container">
            <button className="login-button" onClick={handleLogin}>
                Login with Google
            </button>
        </div>
    );
};

export default Login;
