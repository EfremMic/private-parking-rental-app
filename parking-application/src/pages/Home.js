import React, { useEffect, useState } from 'react';
import Login from '../components/Login';

const Home = () => {
    const [parkingSpots, setParkingSpots] = useState([]);
    const [message, setMessage] = useState('');
    const [user, setUser] = useState(null); // To track logged-in user

    useEffect(() => {
        // Fetch all parking spots for public view
        fetch("http://localhost:8082/api/parking/list")
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch parking spots');
                }
                return response.json();
            })
            .then(data => {
                console.log("Publicly visible parking spots:", data);
                setParkingSpots(data);
            })
            .catch(error => {
                console.error("Error fetching parking spots:", error);
                setMessage("Error fetching parking spots.");
            });

        // Check if the user is logged in
        fetch("http://localhost:8081/api/user/me", { credentials: 'include' })
            .then(response => response.json())
            .then(userData => setUser(userData))
            .catch(() => setUser(null)); // Handle case where user is not logged in
    }, []);

    return (
        <div>
            {!user ? (
                // Show login component if not logged in
                <Login />
            ) : (
                <div>
                    <h1>Welcome, {user.name}!</h1>
                    <p>Email: {user.email}</p>
                </div>
            )}

            <h2>Parking Spots Available for Rent</h2>
            {message && <p>{message}</p>}
            <ul>
                {parkingSpots.length > 0 ? (
                    parkingSpots.map(parkingSpot => (
                        <li key={parkingSpot.id}>
                            <strong>{parkingSpot.name}</strong> - {parkingSpot.location} - ${parkingSpot.price}
                        </li>
                    ))
                ) : (
                    <li>No parking spots available for rent.</li>
                )}
            </ul>
        </div>
    );
};

export default Home;
