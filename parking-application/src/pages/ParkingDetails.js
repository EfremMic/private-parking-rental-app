import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom'; // Import useNavigate
import '../css/ParkingDetails.css'; // Import the CSS file


const ParkingDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate(); // Initialize useNavigate hook
    const [parkingSpot, setParkingSpot] = useState(null);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');

    // Fetch parking spot details
    useEffect(() => {
        const fetchParkingSpot = async () => {
            try {
                const response = await fetch(`http://localhost:8082/api/parking/${id}`);
                if (!response.ok) throw new Error('Failed to fetch parking spot details');
                const data = await response.json();
                setParkingSpot(data);
            } catch (error) {
                console.error('Error fetching parking spot:', error);
                setErrorMessage('Failed to fetch parking spot details.');
            } finally {
                setLoading(false);
            }
        };
        fetchParkingSpot();
    }, [id]);

    // Navigate to Checkout page
    const handleProceedToPayment = () => {
        navigate(`/checkout/${id}`); // Redirect to /checkout/:id
    };

    if (loading) return <p>Loading parking spot details...</p>;
    if (errorMessage) return <p className="error-message">{errorMessage}</p>;

    return (
        <div className="parking-details-container">
            <h1>{parkingSpot.name}</h1>
            <p><strong>Price:</strong> {parkingSpot.price} NOK / day</p>
            <p><strong>Description:</strong> {parkingSpot.description || 'No description provided.'}</p>
            <p><strong>Location:</strong> {parkingSpot.location?.addressName}, {parkingSpot.location?.city}</p>
            <p><strong>Region:</strong> {parkingSpot.region}</p>
            <p><strong>Available:</strong> {parkingSpot.availableStartDate} - {parkingSpot.availableEndDate}</p>

            <button
                className="proceed-button"
                onClick={handleProceedToPayment} // Call navigation function here
            >
                Proceed to Payment
            </button>
        </div>
    );
};

export default ParkingDetails;
