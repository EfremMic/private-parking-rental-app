import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const ParkingDetails = ({ user }) => {
    const { id } = useParams(); // Get parking ID from URL
    const [parking, setParking] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((response) => response.json())
            .then((data) => setParking(data))
            .catch((error) => console.error('Error fetching parking details:', error));
    }, [id]);

    if (!parking) return <p>Loading parking details...</p>;

    return (
        <div>
            <h2>{parking.name}</h2>
            <p>Region: {parking.region}</p>
            <p>Price: ${parking.price}</p>
            <p>Description: {parking.description}</p>
            <p>
                Location: {parking.location?.addressName}, {parking.location?.city}
            </p>
            {user && <button onClick={() => console.log('Renting...')}>Rent</button>}
        </div>
    );
};

export default ParkingDetails;
