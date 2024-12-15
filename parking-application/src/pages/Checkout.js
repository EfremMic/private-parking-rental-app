import React, { useEffect, useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import { loadStripe } from '@stripe/stripe-js';

const stripePromise = loadStripe('your-publishable-key-here'); // Replace with your Stripe publishable key

const Checkout = () => {
    const { id } = useParams(); // parkingSpotId
    const [parkingSpot, setParkingSpot] = useState(null);
    const [loading, setLoading] = useState(true);
    const [clientSecret, setClientSecret] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [paymentStatus, setPaymentStatus] = useState('');

    useEffect(() => {
        // Fetch parking spot details
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((res) => res.json())
            .then((data) => {
                setParkingSpot(data);
                setLoading(false);
            })
            .catch(() => setErrorMessage('Error fetching parking spot details.'));

        // Fetch PaymentIntent client secret
        fetch('http://localhost:8084/api/payment/intent', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                amount: 2142 * 100, // Example: Replace with parkingSpot.price * 100
                currency: 'nok',
                userId: 1, // Replace with actual user ID
                parkingSpotId: id,
            }),
        })
            .then((res) => res.json())
            .then((data) => {
                setClientSecret(data.clientSecret);
            })
            .catch(() => setErrorMessage('Error creating payment intent.'));
    }, [id]);

    const handlePayment = async (e) => {
        e.preventDefault();
        const stripe = await stripePromise;

        const result = await stripe.confirmCardPayment(clientSecret, {
            payment_method: {
                card: {
                    // This assumes you have a Stripe CardElement integrated
                },
            },
        });

        if (result.error) {
            setErrorMessage(result.error.message);
        } else {
            if (result.paymentIntent.status === 'succeeded') {
                setPaymentStatus('Payment successful!');
            }
        }
    };

    if (loading) return <p>Loading...</p>;
    if (errorMessage) return <p>{errorMessage}</p>;

    return (
        <div>
            <h1>Checkout for {parkingSpot?.name}</h1>
            <p>Price: {parkingSpot?.price} NOK</p>

            <form onSubmit={handlePayment}>
                <label>Card Information:</label>
                <div id="card-element" style={{ border: '1px solid #ddd', padding: '10px', borderRadius: '5px' }}>

                    {/* Stripe Card Element would render here */}
                </div>
                <button type="submit" style={{ marginTop: '10px' }}>
                    Pay Now
                </button>
            </form>
            {paymentStatus && <p>{paymentStatus}</p>}
        </div>
    );
};

export default Checkout;
