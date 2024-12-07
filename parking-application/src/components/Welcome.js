import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Logout from './Logout';
import AddParking from './AddParking';

const Welcome = ({ user, onLogout }) => {
    const [userParkingSpots, setUserParkingSpots] = useState([]);
    const [allParkingSpots, setAllParkingSpots] = useState([]);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            // Fetch parking spots added by the user
            fetch(`http://localhost:8082/api/parking/user/${user.id}/list`)
                .then((response) => {
                    if (!response.ok) throw new Error('Failed to fetch user parking spots');
                    return response.json();
                })
                .then((data) => {
                    console.log("User's parking spots:", data); // Debug log
                    setUserParkingSpots(data);
                })
                .catch(() => setMessage("Error fetching user's parking spots."));
        }

        // Fetch all parking spots
        fetch('http://localhost:8082/api/parking/list')
            .then((response) => {
                if (!response.ok) throw new Error('Failed to fetch parking spots');
                return response.json();
            })
            .then((data) => {
                console.log('All parking spots:', data); // Debug log
                setAllParkingSpots(data);
            })
            .catch(() => setMessage('Error fetching parking spots.'));
    }, [user]);

    const handleParkingAdded = (newParkingSpot) => {
        setUserParkingSpots((prevSpots) => [...prevSpots, newParkingSpot]);
        setAllParkingSpots((prevSpots) => [...prevSpots, newParkingSpot]);
    };

    const handleRent = (parkingId) => {
        if (!user) {
            // If the user is not logged in, save the intended route and redirect to login
            sessionStorage.setItem('redirectAfterLogin', `/parking/${parkingId}`);
            navigate('/login');
        } else {
            // If the user is logged in, navigate directly to the Parking Details page
            navigate(`/parking/${parkingId}`);
        }
    };

    const handleContact = (parkingId) => {
        if (!user) {
            // If the user is not logged in, save the intended route and redirect to login
            sessionStorage.setItem('redirectAfterLogin', `/parking/${parkingId}`);
            navigate('/login');
        } else {
            // If the user is logged in, navigate directly to the Parking Details page
            navigate(`/parking/${parkingId}`);
        }
    };



    if (!user) return <h1>Loading...</h1>;

    return (
        <div>
            <h1>Welcome, {user.email}!</h1>
            <Logout onLogout={onLogout} />
            <AddParking userId={user.id} onParkingAdded={handleParkingAdded} />

            <h2>Your Added Parking Spots</h2>
            {userParkingSpots.length > 0 ? (
                <ul>
                    {userParkingSpots.map((spot) => (
                        <li key={spot.id}>
                            {spot.name} - {spot.location?.addressName}, {spot.location?.city} - ${spot.price}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>You haven't added any parking spots yet.</p>
            )}

            <h2>All Available Parking Spots</h2>
            {message && <p>{message}</p>}
            {allParkingSpots.length > 0 ? (
                <ul>
                    {allParkingSpots.map((spot) => (
                        <li key={spot.id}>
                            {spot.name} - {spot.location?.addressName}, {spot.location?.city} - ${spot.price}
                            {user.id !== spot.userId && (
                                <div>
                                    <button onClick={() => handleRent(spot.id)}>Rent</button>
                                    <button onClick={() => handleContact(spot.id)}>Contact Owner</button>
                                </div>
                            )}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No parking spots available.</p>
            )}
        </div>
    );
};

export default Welcome;
