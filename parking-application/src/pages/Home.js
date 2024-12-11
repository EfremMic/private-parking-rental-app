import React, { useEffect, useState } from 'react';
import Login from '../components/Login';
import SearchParking from '../components/SearchParking'; // Import the SearchParking component
import { useNavigate } from 'react-router-dom';
import '../css/Home.css'; // Import CSS for styling


/*
const Home = () => {
    const [parkingSpots, setParkingSpots] = useState([]);
    const [filteredParkingSpots, setFilteredParkingSpots] = useState([]);
    const [message, setMessage] = useState('');
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch all parking spots
        fetch("http://localhost:8082/api/parking/list")
            .then((response) => {
                if (!response.ok) throw new Error('Failed to fetch parking spots');
                return response.json();
            })
            .then((data) => {
                setParkingSpots(data);
                setFilteredParkingSpots(data); // Initialize filtered spots with all spots
            })
            .catch(() => setMessage('Error fetching parking spots.'));
    }, []);

    const handleRentOrContact = (parkingSpotId) => {
        sessionStorage.setItem('redirectParkingSpotId', parkingSpotId); // This saves the spot ID for later redirection
        navigate('/login'); // Send user to login page
    };


    const handleSearch = (city, startDate, endDate) => {
        // Filter parking spots based on city and date range
        const filtered = parkingSpots.filter((spot) => {
            const matchesCity = city ? spot.location?.city?.toLowerCase().includes(city.toLowerCase()) : true;
            const matchesDate =
                (!startDate || new Date(spot.availableStartDate) <= new Date(startDate)) &&
                (!endDate || new Date(spot.availableEndDate) >= new Date(endDate));

            return matchesCity && matchesDate;
        });

        setFilteredParkingSpots(filtered);
    };

    return (
        <div className="home-container">
            {!user ? (
                // Show login component if not logged in
                <div className="login-container">
                    <Login setUser={setUser} />
                </div>
            ) : (
                <div className="user-info">
                    <h1>Welcome, {user.name}!</h1>
                    <p>Email: {user.email}</p>
                </div>
            )}


            <SearchParking onSearch={handleSearch} />

            <h2 className="section-title">Parking Spots Available for Rent</h2>
            {message && <p className="error-message">{message}</p>}
            <div className="parking-list">
                {filteredParkingSpots.length > 0 ? (
                    filteredParkingSpots.map((parkingSpot) => (
                        <div key={parkingSpot.id} className="parking-card">
                            <h3 className="parking-name">{parkingSpot.name}</h3>
                            <p>
                                <strong>Region:</strong> {parkingSpot.region}
                            </p>
                            <p>
                                <strong>Price:</strong> {parkingSpot.price} NOK
                            </p>
                            <p>
                                <strong>Availability:</strong> {parkingSpot.availableStartDate} -{' '}
                                {parkingSpot.availableEndDate}
                            </p>
                            <p className="description">
                                <strong>Description:</strong> {parkingSpot.description || 'No description provided.'}
                            </p>
                            <div className="location-info">
                                <p>
                                    <strong>Address:</strong> {parkingSpot.location?.addressName || 'Not provided'}
                                </p>
                                <p>
                                    <strong>City:</strong> {parkingSpot.location?.city || 'Not provided'}
                                </p>
                            </div>
                            <button
                                className="action-button"
                                onClick={() =>  handleRentOrContact(parkingSpot.id)}
                            >
                                Rent/Contact Owner
                            </button>
                        </div>
                    ))
                ) : (
                    <p className="no-results">No parking spots match your search criteria.</p>
                )}
            </div>
        </div>
    );
};

export default Home;
*/




const Home = () => {
    const [parkingSpots, setParkingSpots] = useState([]);
    const [filteredParkingSpots, setFilteredParkingSpots] = useState([]);
    const [message, setMessage] = useState('');
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch all parking spots
        fetch("http://localhost:8082/api/parking/list")
            .then((response) => {
                if (!response.ok) throw new Error('Failed to fetch parking spots');
                return response.json();
            })
            .then((data) => {
                setParkingSpots(data);
                setFilteredParkingSpots(data);
            })
            .catch(() => setMessage('Error fetching parking spots.'));
    }, []);

    const handleRentOrContact = (parkingSpotId) => {
        sessionStorage.setItem('redirectAfterLogin', `/parking/${parkingSpotId}`); // Store /parking/1, /parking/2, etc.
        navigate('/login'); // Send user to login page
    };

    const handleSearch = (city, startDate, endDate) => {
        const filtered = parkingSpots.filter((spot) => {
            const matchesCity = city ? spot.location?.city?.toLowerCase().includes(city.toLowerCase()) : true;
            const matchesDate =
                (!startDate || new Date(spot.availableStartDate) <= new Date(startDate)) &&
                (!endDate || new Date(spot.availableEndDate) >= new Date(endDate));

            return matchesCity && matchesDate;
        });

        setFilteredParkingSpots(filtered);
    };

    return (
        <div className="home-container">
            {!user ? (
                <div className="login-container">
                    <Login setUser={setUser} />
                </div>
            ) : (
                <div className="user-info">
                    <h1>Welcome, {user.name}!</h1>
                    <p>Email: {user.email}</p>
                </div>
            )}

            <SearchParking onSearch={handleSearch} />

            <h2 className="section-title">Parking Spots Available for Rent</h2>
            {message && <p className="error-message">{message}</p>}
            <div className="parking-list">
                {filteredParkingSpots.length > 0 ? (
                    filteredParkingSpots.map((parkingSpot) => (
                        <div key={parkingSpot.id} className="parking-card">
                            <h3 className="parking-name">{parkingSpot.name}</h3>
                            <p><strong>Region:</strong> {parkingSpot.region}</p>
                            <p><strong>Price:</strong> {parkingSpot.price} NOK</p>
                            <p><strong>Availability:</strong> {parkingSpot.availableStartDate} - {parkingSpot.availableEndDate}</p>
                            <button
                                className="action-button"
                                onClick={() => handleRentOrContact(parkingSpot.id)}
                            >
                                Rent/Contact Owner
                            </button>
                        </div>
                    ))
                ) : (
                    <p className="no-results">No parking spots match your search criteria.</p>
                )}
            </div>
        </div>
    );
};

export default Home;
