import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Logout from './Logout';
import AddParking from './AddParking';
import '../css/Welcome.css';

const Welcome = ({ user, onLogout }) => {
    const [userParkingSpots, setUserParkingSpots] = useState([]);
    const [allParkingSpots, setAllParkingSpots] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            console.log("User is not logged in. Redirecting to login.");
            navigate('/login');
            return;
        }

        if (!user?.id) return;
        // Fetch parking spots for the user
        console.log("Fetching parking spots for user:", user);
        Promise.all([
            fetch(`http://localhost:8082/api/parking/user/${user.id}/list`)
                .then((response) => {
                    if (!response.ok) throw new Error('Failed to fetch user parking spots');
                    return response.json();
                })
                .then((data) => setUserParkingSpots(data)),

            fetch('http://localhost:8082/api/parking/list')
                .then((response) => {
                    if (!response.ok) throw new Error('Failed to fetch parking spots');
                    return response.json();
                })
                .then((data) => setAllParkingSpots(data)),
        ])
            .catch((error) => {
                console.error('Error fetching data:', error);
                setMessage('Error fetching parking spots. Please try again later.');
            })
            .finally(() => {
                console.log("Finished loading parking spots");
                setLoading(false);
            });
    }, [user, navigate]);

    const handleAction = (parkingSpotId) => {
        try {
            console.log('User is navigating to parking spot:', parkingSpotId);
            navigate(`/parking/${parkingSpotId}`);
        } catch (error) {
            console.error('Error during action:', error);
            setMessage('Failed to perform action. Please try again later.');
        }
    };

    if (loading) {
        return <div className="loading">Loading your parking spots...</div>;
    }



    return (
        <div className="welcome-container">
            <header className="welcome-header">
                <h1>Welcome, {user.name}</h1>
                <Logout onLogout={onLogout} />
            </header>

            <section className="form-section">
                <AddParking  user={user}
                             userId={user.id} onParkingAdded={(newParkingSpot) => {
                    setUserParkingSpots((prev) => [...prev, newParkingSpot]);
                    setAllParkingSpots((prev) => [...prev, newParkingSpot]);
                }} />
            </section>

            <section className="spots-section">
                <h2>Your Added Parking Spots</h2>
                {userParkingSpots.length > 0 ? (
                    <div className="spots-container">
                        {userParkingSpots.map((spot) => (
                            <div key={spot.id} className="spot-card">
                                <h3>{spot.name}</h3>
                                <p>{spot.location?.addressName}, {spot.location?.city}</p>
                                <p>Price: {spot.price} NOK</p>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>You haven't added any parking spots yet.</p>
                )}
            </section>

            <section className="spots-section">
                <h2>All Available Parking Spots</h2>
                {message && <p className="error-message">{message}</p>}
                {allParkingSpots.length > 0 ? (
                    <div className="spots-container">
                        {allParkingSpots.map((spot) => (
                            <div key={spot.id} className="spot-card">
                                <h3>{spot.name}</h3>
                                <p>{spot.location?.addressName}, {spot.location?.city}</p>
                                <p>Price: {spot.price} NOK</p>
                                    <button
                                        className="rent-button"
                                    onClick={() => handleAction(spot.id)}
                                    disabled={user && user.id === spot.userId} // Disable if the user owns this spot
                                    title={user && user.id === spot.userId ? "You cannot rent your own parking spot" : "Rent or contact the owner"}
                                >
                                    {user && user.id === spot.userId ? "Your Spot" : "Rent/Contact Owner"}
                                </button>

                            </div>
                        ))}
                    </div>
                ) : (
                    <p>No parking spots available.</p>
                )}
            </section>
        </div>
    );
};

export default Welcome;
