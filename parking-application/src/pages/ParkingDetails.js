import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import '../css/ParkingDetails.css'; // Import the CSS file

const ParkingDetails = () => {
    const { id } = useParams();
    const [parkingSpot, setParkingSpot] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((response) => {
                if (!response.ok) throw new Error('Failed to fetch parking spot details');
                return response.json();
            })
            .then((data) => setParkingSpot(data))
            .catch((error) => console.error(error));
    }, [id]);

    if (!parkingSpot) return <p>Loading...</p>;

    return (
        <div className="parking-details-container">
            <h1>{parkingSpot.name}</h1>
            <p><strong>Price:</strong> {parkingSpot.price} NOK</p>
            <p><strong>Description:</strong> {parkingSpot.description || 'No description provided.'}</p>
            <button className="proceed-button">Proceed to Payment</button>
        </div>
    );
};

export default ParkingDetails;
