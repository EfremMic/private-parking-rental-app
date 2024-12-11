import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import '../css/Home.css';

const RentForm = () => {
    const { id } = useParams();
    const [parkingSpot, setParkingSpot] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((response) => response.json())
            .then((data) => setParkingSpot(data))
            .catch((error) => console.error('Error fetching parking spot:', error));
    }, [id]);

    if (!parkingSpot) return <p>Loading...</p>;

    return (
        <div>
            <h1>Rent Parking Spot</h1>
            <p>{parkingSpot.name}</p>
            <p>Location: {parkingSpot.location.addressName}, {parkingSpot.location.city}</p>
            <p>Price: ${parkingSpot.price}</p>
            <form>
                <h3>Fill your details to rent:</h3>
                <input type="text" name="name" placeholder="Your Name" required />
                <input type="email" name="email" placeholder="Your Email" required />
                <button type="submit">Checkout</button>
            </form>
        </div>
    );
};

export default RentForm;
