import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import '../css/ParkingDetails.css'; // Import the CSS file


/*
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
            <p><strong>Price:</strong> {parkingSpot.price} NOK / dag</p>
            <p><strong>Calculated price:</strong> </p>
            <p><strong>Description:</strong> {parkingSpot.description || 'No description provided.'}</p>
            <button className="proceed-button">Proceed to Payment</button>
        </div>
    );
};

export default ParkingDetails;
*/



/*
const ParkingDetails = () => {
    const { id } = useParams();
    const [parkingSpot, setParkingSpot] = useState(null);
    const [paymentStatus, setPaymentStatus] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8082/api/parking/${id}`)
            .then((response) => {
                if (!response.ok) throw new Error('Failed to fetch parking spot details');
                return response.json();
            })
            .then((data) => setParkingSpot(data))
            .catch((error) => console.error(error));
    }, [id]);

    const handlePayment = () => {
        // Send payment request to RabbitMQ via backend
        fetch('http://localhost:8083/api/payments/request', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                userId: parkingSpot.userId,
                parkingSpotId: parkingSpot.id,
                amount: parkingSpot.price,
                currency: 'NOK',
            }),
        })
            .then((response) => {
                if (!response.ok) throw new Error('Failed to send payment request');
                return response.json();
            })
            .then((data) => setPaymentStatus('Payment request sent successfully!'))
            .catch(() => setPaymentStatus('Failed to initiate payment.'));
            .catch(() => setPaymentStatus('Failed to initiate payment.'));
    };

    if (!parkingSpot) return <p>Loading...</p>;

    return (
        <div className="parking-details-container">
            <h1>{parkingSpot.name}</h1>
            <p><strong>Price:</strong> {parkingSpot.price} NOK / dag</p>
            <p><strong>Description:</strong> {parkingSpot.description || 'No description provided.'}</p>
            <button className="proceed-button" onClick={handlePayment}>
                Proceed to Payment
            </button>
            {paymentStatus && <p>{paymentStatus}</p>}
        </div>
    );
};

export default ParkingDetails;
*/







/***********************************************************************************************
const ParkingDetails = () => {
    const { id } = useParams();
    const [parkingSpot, setParkingSpot] = useState(null);

    useEffect(() => {
        // Fetch parking spot details
        fetch(`http://localhost:8082/api/parking/${id}`)
    .then((response) => {
            if (!response.ok) throw new Error('Failed to fetch parking spot details');
            return response.json();
        })
            .then((data) => setParkingSpot(data))
            .catch((error) => console.error(error));
    }, [id]);

    const handlePayment = () => {
        console.log("Proceeding to payment...");

        const paymentRequest = {
            token: "tok_visa", // Dummy Stripe token for testing
            amount: parkingSpot.price * 100, // Amount in cents
            currency: "nok",
            description: "Payment for parking spot ${parkingSpot.name}",
        };

        fetch('http://localhost:8084/api/payment/charge', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(paymentRequest),
        })
            .then((response) => {
                if (!response.ok) throw new Error('Payment request failed');
                return response.json();
            })
            .then((data) => console.log('Payment succeeded:', data))
            .catch((error) => console.error('Error during payment:', error));
    };
        console.log("Sending request to: http://localhost:8084/api/payment/charge");




    if (!parkingSpot) return <p>Loading...</p>;

    return (
        <div className="parking-details-container">
            <h1>{parkingSpot.name}</h1>
            <p><strong>Price:</strong> {parkingSpot.price} NOK / day</p>
            <p><strong>Description:</strong> {parkingSpot.description || 'No description provided.'}</p>
            <button className="proceed-button" onClick={handlePayment}>
                Proceed to Payment
            </button>
        </div>
    );
};

export default ParkingDetails;
 ***********************************************************************************************/

import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import CheckoutForm from "../pages/CheckoutForm";

const ParkingDetails = () => {
    const { id } = useParams();
    const [stripePromise, setStripePromise] = useState(null);
    const [parkingSpot, setParkingSpot] = useState(null);
    const [loadingStripeKey, setLoadingStripeKey] = useState(true);
    const [loadingParkingSpot, setLoadingParkingSpot] = useState(true);

    // Fetch Stripe Publishable Key
    useEffect(() => {
        const fetchStripeKey = async () => {
            try {
                console.log("Fetching Stripe key...");
                const response = await fetch(`http://localhost:8084/api/payment/stripe-key`);
                console.log("Stripe key fetch response:", response);

                if (!response.ok) {
                    console.error("Failed to fetch Stripe key:", response.status, response.statusText);
                    throw new Error("Failed to fetch Stripe key");
                }

                const key = await response.text();
                setStripePromise(loadStripe(key));
                console.log("Stripe key fetched successfully");
            } catch (error) {
                console.error("Error fetching Stripe key:", error);
            } finally {
                setLoadingStripeKey(false);
            }
        };

        fetchStripeKey();
    }, []);

    // Fetch parking spot details
    useEffect(() => {
        const fetchParkingSpot = async () => {
            try {
                console.log(`Fetching details for parking spot ID: ${id}`);
                const response = await fetch(`http://localhost:8082/api/parking/${id}`);
                console.log("Parking spot fetch response:", response);

                if (!response.ok) {
                    console.error("Failed to fetch parking spot details:", response.status, response.statusText);
                    throw new Error("Failed to fetch parking spot details");
                }

                const data = await response.json();
                setParkingSpot(data);
                console.log("Parking spot details fetched successfully:", data);
            } catch (error) {
                console.error("Error fetching parking spot:", error);
            } finally {
                setLoadingParkingSpot(false);
            }
        };

        fetchParkingSpot();
    }, [id]);

    if (loadingStripeKey || loadingParkingSpot) return <p>Loading payment system...</p>;

    if (!stripePromise || !parkingSpot) {
        return <p>Unable to load the payment system. Please try again later.</p>;
    }

    return (
        <Elements stripe={stripePromise}>
            <div className="parking-details-container">
                <h1>{parkingSpot.name}</h1>
                <p><strong>Price:</strong> {parkingSpot.price} NOK / day</p>
                <p><strong>Description:</strong> {parkingSpot.description || "No description provided."}</p>
                <CheckoutForm parkingSpot={parkingSpot} />
            </div>
        </Elements>
    );
};

export default ParkingDetails;
