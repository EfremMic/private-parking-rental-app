import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const ParkingDetails = ({ user }) => {
    const { id } = useParams(); // Get the parking spot ID from the URL
    const [parkingSpot, setParkingSpot] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((response) => {
                if (!response.ok) throw new Error('Failed to fetch parking spot details');
                return response.json();
            })
            .then((data) => setParkingSpot(data))
            .catch((error) => console.error('Error:', error));
    }, [id]);

    if (!parkingSpot) {
        return <p>Loading parking spot details...</p>;
    }

    return (
        <div>
            <h2>{parkingSpot.name}</h2>
            <p>Region: {parkingSpot.region}</p>
            <p>Price: ${parkingSpot.price}</p>
            <p>Availability: {parkingSpot.availableStartDate} - {parkingSpot.availableEndDate}</p>
            <p>Description: {parkingSpot.description}</p>
            <p>Added By: {parkingSpot.publisherName || parkingSpot.userId}</p>
            <h3>Location:</h3>
            <ul>
                <li>Address: {parkingSpot.location?.addressName || 'Not provided'}</li>
                <li>City: {parkingSpot.location?.city || 'Not provided'}</li>
            </ul>
            <button>Rent Now</button>
            <button>Contact Owner</button>
        </div>
    );
};

export default ParkingDetails;
