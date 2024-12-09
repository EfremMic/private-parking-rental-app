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
        if (user) {
            Promise.all([
                // Fetch parking spots added by the user
                fetch(`http://localhost:8082/api/parking/user/${user.id}/list`)
                    .then((response) => {
                        if (!response.ok) throw new Error('Failed to fetch user parking spots');
                        return response.json();
                    })
                    .then((data) => setUserParkingSpots(data)),

                // Fetch all parking spots
                fetch('http://localhost:8082/api/parking/list')
                    .then((response) => {
                        if (!response.ok) throw new Error('Failed to fetch parking spots');
                        return response.json();
                    })
                    .then((data) => setAllParkingSpots(data)),
            ])
                .catch((error) => {
                    console.error('Error fetching data:', error);
                    setMessage('Error fetching parking spots.');
                })
                .finally(() => setLoading(false));
        }
    }, [user]);

    const handleParkingAdded = (newParkingSpot) => {
        setUserParkingSpots((prev) => [...prev, newParkingSpot]);
        setAllParkingSpots((prev) => [...prev, newParkingSpot]);
    };

    const handleAction = (parkingId) => {
        if (!user) {
            sessionStorage.setItem('redirectAfterLogin', `/parking/${parkingId}`);
            navigate('/login');
        } else {
            navigate(`/parking/${parkingId}`);
        }
    };

    if (!user) {
        return (
            <div className="welcome-container">
                <h1>Please log in to access your dashboard.</h1>
                <button className="primary-button" onClick={() => navigate('/login')}>Go to Login</button>
            </div>
        );
    }

    if (loading) {
        return <div className="loading">Loading your parking spots...</div>;
    }

    return (
        <div className="welcome-container">
            <header className="welcome-header">
                <h1>Welcome, {user.email}!</h1>
                <Logout onLogout={onLogout} />
            </header>

            <section className="form-section">
                <AddParking userId={user.id} onParkingAdded={handleParkingAdded} />
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
                                <p>Price: ${spot.price}</p>
                                {user.id !== spot.userId && (
                                    <button className="primary-button" onClick={() => handleAction(spot.id)}>
                                        Rent/Contact Owner
                                    </button>
                                )}
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
