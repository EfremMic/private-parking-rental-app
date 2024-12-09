import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const ParkingDetails = () => {
    const { id } = useParams();
    const [parkingSpot, setParkingSpot] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((response) => response.json())
            .then((data) => setParkingSpot(data))
            .catch((error) => console.error('Error fetching parking details:', error));
    }, [id]);

    if (!parkingSpot) return <p>Loading...</p>;

    return (
        <div>
            <h2>{parkingSpot.name}</h2>
            <p>Price: ${parkingSpot.price}</p>
            <button>Rent Now</button>
        </div>
    );
};

export default ParkingDetails;
