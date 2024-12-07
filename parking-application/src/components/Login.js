import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

function Login() {
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
                // Redirect to the saved path or a default page
                const redirectPath = sessionStorage.getItem('redirectAfterLogin') || '/welcome';
                sessionStorage.removeItem('redirectAfterLogin');
                navigate(redirectPath);
            })
            .catch(() => {});
    }, [navigate]);

    return (
        <div>
            <h2>Login</h2>
            <button onClick={handleLogin}>Login with Google</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
}

export default Login;
