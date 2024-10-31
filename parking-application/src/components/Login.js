import React from 'react';

function Login() {
    const handleLogin = () => {
        // Redirect the user to the OAuth2 login
        window.location.href = 'http://localhost:8081/oauth2/authorization/google';
    };

    return (
        <div>
            <h2>Login with Google h</h2>
            <button onClick={handleLogin}>Login with Google</button>
        </div>
    );
}

export default Login;
