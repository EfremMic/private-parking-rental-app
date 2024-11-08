import React, { useEffect, useState } from 'react';
import Logout from "./Logout";
import AddParking from "./AddParking";

const Welcome = ({ user, onLogout }) => {
    const [parkingSpots, setParkingSpots] = useState([]);
    const [message, setMessage] = useState('');

    useEffect(() => {
        // Fetch all parking spots for all users
        fetch("http://localhost:8082/api/parking/list")
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch parking spots');
                }
                return response.json();
            })
            .then(data => {
                console.log("All parking spots:", data);
                setParkingSpots(data); // Set the fetched parking spots to state
            })
            .catch(error => {
                console.error("Error fetching parking spots:", error);
                setMessage("Error fetching parking spots.");
            });
    }, []);

    if (!user) {
        return <h1>Loading...</h1>;  // Display a loading message until user data is fetched
    }

    return (
        <div>
            <h1>Welcome, {user.name}!</h1>
            <img src={user.profileImageUrl} alt={`${user.name}'s profile`} />
            <p>Email: {user.email}</p>
            <Logout onLogout={onLogout} />
            <AddParking userId={user.id} />

            <h2>All Parking Spots</h2>
            {message && <p>{message}</p>}
            <ul>
                {parkingSpots.length > 0 ? (
                    parkingSpots.map(parkingSpot => (
                        <li key={parkingSpot.id}>
                            {parkingSpot.name} - {parkingSpot.location} - ${parkingSpot.price}
                        </li>
                    ))
                ) : (
                    <li>No parking spots found.</li>  // Message for no parking spots
                )}
            </ul>
        </div>
    );
};

export default Welcome;
