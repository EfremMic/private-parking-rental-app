import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { loadStripe } from '@stripe/stripe-js';

const Checkout = () => {
    const { id } = useParams(); // parkingSpotId
    const [parkingSpot, setParkingSpot] = useState(null);
    const [paymentStatus, setPaymentStatus] = useState('');
    const [loading, setLoading] = useState(true);

    const stripePromise = loadStripe('your-publishable-key-here');

    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((res) => res.json())
            .then(setParkingSpot)
            .catch(() => setPaymentStatus('Error fetching parking spot.'))
            .finally(() => setLoading(false));
    }, [id]);

    const handlePayment = async () => {
        const stripe = await stripePromise;

        // Call the backend to create a PaymentIntent
        const response = await fetch('http://localhost:8084/api/payment', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                parkingSpotId: id,
                userId: 42, // Replace with dynamic user ID
            }),
        });
        const { clientSecret } = await response.json();

        // Confirm payment with Stripe
        const { error } = await stripe.confirmCardPayment(clientSecret);

        if (error) {
            setPaymentStatus('Payment failed. Please try again.');
        } else {
            setPaymentStatus('Payment successful!');
        }
    };

    if (loading) return <p>Loading...</p>;

    return (
        <div>
            <h1>Checkout for {parkingSpot?.name}</h1>
            <p>Price: {parkingSpot?.price} NOK</p>
            <button onClick={handlePayment}>Proceed to Payment</button>
            {paymentStatus && <p>{paymentStatus}</p>}
        </div>
    );
};

export default Checkout;
