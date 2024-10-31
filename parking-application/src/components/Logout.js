import React from 'react';

const Logout = ({ onLogout }) => {
    const handleLogout = () => {
        // Clear the session by redirecting to the logout URL
        window.location.href = 'http://localhost:8081/logout';
        onLogout(); // Optionally clear the user state in the frontend
    };

    return (
        <button onClick={handleLogout}>Logout</button>
    );
};

export default Logout;
