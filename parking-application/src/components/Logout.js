import React from 'react';
import '../css/Logout.css';

const Logout = ({ onLogout }) => {
    const handleLogout = () => {
        // Clear the session by redirecting to the logout URL
        window.location.href = 'http://localhost:8081/logout';
        onLogout(); // Optionally clear the user state in the frontend
    };

    return (
        <div className="logout-container">
            <button className="logout-button" onClick={handleLogout}>
                Logout
            </button>
        </div>
    );
};

export default Logout;
