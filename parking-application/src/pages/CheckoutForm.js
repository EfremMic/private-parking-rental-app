import React, { useState } from "react";
import { CardElement, useStripe, useElements } from "@stripe/react-stripe-js";

const CheckoutForm = ({ parkingSpot }) => {
    const stripe = useStripe();
    const elements = useElements();
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!stripe || !elements) return;

        setLoading(true);

        // Create payment method
        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: "card",
            card: elements.getElement(CardElement),
        });

        if (error) {
            setError(error.message);
            setLoading(false);
            return;
        }

        // Send payment request to backend
        fetch("http://localhost:8084/api/payment/charge", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                token: paymentMethod.id,
                amount: parkingSpot.price * 100, // Amount in cents
                currency: "nok",
                description: `Payment for parking spot: ${parkingSpot.name}`,
            }),
        })
            .then((response) => {
                if (!response.ok) throw new Error("Payment failed");
                return response.json();
            })
            .then((data) => {
                console.log("Payment succeeded:", data);
                alert("Payment succeeded!");
            })
            .catch((error) => {
                console.error("Error during payment:", error);
                setError("Payment failed. Please try again.");
            })
            .finally(() => setLoading(false));
    };

    return (
        <form onSubmit={handleSubmit}>
            <CardElement />
            {error && <p style={{ color: "red" }}>{error}</p>}
            <button type="submit" disabled={!stripe || loading}>
                {loading ? "Processing..." : "Pay Now"}
            </button>
        </form>
    );
};

export default CheckoutForm;
