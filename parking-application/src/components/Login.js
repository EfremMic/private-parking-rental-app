import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Login({ setUser }) {
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = () => {
        try {
            // Redirect user to the OAuth login
            window.location.href = 'http://localhost:8081/oauth2/authorization/google';
        } catch (e) {
            setError('Failed to initiate login. Please try again.');
            console.error('Error during login:', e);
        }
    };

    // After login success, use useEffect to handle redirect
    useEffect(() => {
        fetch('http://localhost:8081/api/users/me', {
            method: 'GET',
            credentials: 'include',
        })
            .then((response) => {
                if (response.ok) return response.json();
                throw new Error('User not authenticated');
            })
            .then((data) => {
                setUser(data); // Update the user state in the parent component
                const redirectPath = sessionStorage.getItem('redirectAfterLogin') || '/welcome';
                sessionStorage.removeItem('redirectAfterLogin'); // Clean up after use
                navigate(redirectPath); // Redirect to the intended path or welcome page
            })
            .catch(() => {}); // Ignore errors for unauthenticated users
    }, [navigate, setUser]);

    return (
        <div>
            <h2>Login</h2>
            <button onClick={handleLogin}>Login with Google</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
}

export default Login;
