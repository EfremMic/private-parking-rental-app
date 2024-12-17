import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom'; // Added useNavigate
import { loadStripe } from '@stripe/stripe-js';
import { CardElement, Elements, useStripe, useElements } from '@stripe/react-stripe-js';
import '../css/Checkout.css';

const stripePromise = loadStripe('pk_test_51PMaPVK8PnEMKrfFz0f9n1Hd9GLAbyJYqsz05cSbBsTQR0eZO3qqTfBakWiUsSgOU733DHQE3krIhnD7iAfhCIry00YylHxDs1');

const Checkout = () => {
    const { id } = useParams(); // parkingSpotId
    const [parkingSpot, setParkingSpot] = useState(null);
    const [paymentStatus, setPaymentStatus] = useState('');
    const [loading, setLoading] = useState(true);
    const stripe = useStripe();
    const elements = useElements();
    const navigate = useNavigate(); // New: useNavigate for navigation

    // Fetch parking spot details from backend
    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((res) => res.json())
            .then(setParkingSpot)
            .catch(() => setPaymentStatus('Error fetching parking spot.'))
            .finally(() => setLoading(false));
    }, [id]);

    // Handle the payment process
    const handlePayment = async () => {
        const userId = localStorage.getItem('userId'); // Replace with actual user id from auth
        let clientSecret;

        try {
            const response = await fetch('http://localhost:8084/api/payments/charge', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    parkingSpotId: id,
                    userId: userId,
                    amount: parkingSpot.price * (parkingSpot.duration || 1) * 100, // Stripe requires amounts in cents
                    currency: 'NOK',
                    description: `Payment for parking spot ${parkingSpot.name}`,
                }),
            });

            if (!response.ok) {
                throw new Error('Payment API failed');
            }

            const data = await response.json();
            clientSecret = data.clientSecret;
        } catch (error) {
            console.error('Payment request failed:', error);
            setPaymentStatus('Payment request failed. Please try again.');
            return;
        }

        const { error } = await stripe.confirmCardPayment(clientSecret, {
            payment_method: {
                card: elements.getElement(CardElement),
            },
        });

        if (error) {
            setPaymentStatus('Payment failed. Please try again.');
        } else {
            setPaymentStatus('Payment successful!');
        }
    };

    // Handle cancel button click (navigate to profile)
    const handleCancel = () => {
        navigate('/welcome'); // Redirect user to the profile page
    };

    if (loading) return <p>Loading...</p>;

    return (
        <div className="checkout-container">
            <h1>Checkout for {parkingSpot?.name}</h1>

            <p className="price"><strong>Price:</strong> {parkingSpot?.price} NOK</p>

            <div className="location">
                <strong>Location:</strong>
                <ul>
                    <li><strong>Address Name:</strong> {parkingSpot?.location?.addressName}</li>
                    <li><strong>Gate Number:</strong> {parkingSpot?.location?.gateNumber}</li>
                    <li><strong>Post Box Number:</strong> {parkingSpot?.location?.postBoxNumber}</li>
                    <li><strong>City:</strong> {parkingSpot?.location?.city}</li>
                </ul>
            </div>

            <p className="duration"><strong>Duration:</strong> {parkingSpot?.duration || 1} hours</p>
            <p className="total"><strong>Total:</strong> {parkingSpot?.price * (parkingSpot.duration || 1)} NOK</p>

            <h2>Payment</h2>
            <p>Enter your payment details to complete the booking.</p>

            <div className="card-input">
                <CardElement className="card-element" />
            </div>

            <div className="button-group">
                <button className="payment-button" onClick={handlePayment}>Proceed to Payment</button>
                <button className="cancel-button" onClick={handleCancel}>Cancel</button>
            </div>

            {paymentStatus && <p className="payment-status">{paymentStatus}</p>}
        </div>
    );
};

const CheckoutWrapper = () => (
    <Elements stripe={stripePromise}>
        <Checkout />
    </Elements>
);

export default CheckoutWrapper;
