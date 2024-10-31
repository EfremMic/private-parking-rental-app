import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
        <div>
            <h2>Welcome to the Parking App</h2>
            <Link to="/login">Login</Link>
            <br />
            <Link to="/parking-list">View Parking List</Link>
        </div>
    );
};

export default Home;
