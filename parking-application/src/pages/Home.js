import React, { useEffect, useState } from 'react';
import Login from '../components/Login';
import SearchParking from '../components/SearchParking'; // Import the SearchParking component
import { useNavigate } from 'react-router-dom';


const Home = () => {
    const [parkingSpots, setParkingSpots] = useState([]);
    const [filteredParkingSpots, setFilteredParkingSpots] = useState([]); // State for filtered spots
    const [message, setMessage] = useState('');
    const [user, setUser] = useState(null); // To track logged-in user
    const navigate = useNavigate();

    useEffect(() => {
        fetch("http://localhost:8082/api/parking/list")
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch parking spots');
                return response.json();
            })
            .then(data => {
                setParkingSpots(data);
                setFilteredParkingSpots(data); // Initialize filtered spots with all spots
            })
            .catch(() => setMessage('Error fetching parking spots.'));
    }, []);

    const handleSearch = (city, startDate, endDate) => {
        // Filter parking spots based on city and date range
        const filtered = parkingSpots.filter(spot => {
            const matchesCity = city ? spot.location?.city?.toLowerCase().includes(city.toLowerCase()) : true;
            const matchesDate =
                (!startDate || new Date(spot.availableStartDate) <= new Date(startDate)) &&
                (!endDate || new Date(spot.availableEndDate) >= new Date(endDate));

            return matchesCity && matchesDate;
        });

        setFilteredParkingSpots(filtered);
    };

    const handleRentOrContact = (parkingSpot) => {
        const isLoggedIn = !!localStorage.getItem('user'); // Check login status
        if (!isLoggedIn) {
            localStorage.setItem('selectedParking', JSON.stringify(parkingSpot)); // Save the parking spot
            navigate('/login'); // Redirect to login
        } else {
            navigate(`/parking/${parkingSpot.id}`); // Go to parking details
        }
    };

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

            {/* Add SearchParking component */}
            <SearchParking onSearch={handleSearch} />

            <h2>Parking Spots Available for Rent</h2>
            {message && <p>{message}</p>}
            <ul>
                {filteredParkingSpots.length > 0 ? (
                    filteredParkingSpots.map(parkingSpot => (
                        <li key={parkingSpot.id}>
                            <h3>{parkingSpot.name}</h3>
                            <p>Region: {parkingSpot.region}</p>
                            <p>Price: ${parkingSpot.price}</p>
                            <p>
                                Availability: {parkingSpot.availableStartDate} -{' '}
                                {parkingSpot.availableEndDate}
                            </p>
                            <p>Description: {parkingSpot.description || 'No description provided.'}</p>
                            <h4>Location:</h4>
                            <ul>
                                <li>Address: {parkingSpot.location?.addressName || 'Not provided'}</li>
                                <li>Gate Number: {parkingSpot.location?.gateNumber || 'N/A'}</li>
                                <li>Post Box: {parkingSpot.location?.postBoxNumber || 'N/A'}</li>
                                <li>City: {parkingSpot.location?.city || 'Not provided'}</li>
                            </ul>
                            <button onClick={() => handleRentOrContact(parkingSpot)}>
                                Rent/Contact Owner
                            </button>
                        </li>
                    ))
                ) : (
                    <p>No parking spots match your search criteria.</p>
                )}
            </ul>
        </div>
    );
};

export default Home;