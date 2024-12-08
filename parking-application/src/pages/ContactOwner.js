import React, { useState } from 'react';
import { useParams } from 'react-router-dom';

const ContactOwner = ({ user }) => {
    const { id } = useParams();
    const [message, setMessage] = useState('');
    const [responseMessage, setResponseMessage] = useState('');

    const handleSendMessage = async () => {
        const dataToSend = {
            userId: user.id,
            parkingSpotId: id,
            message,
        };

        try {
            const response = await fetch('http://localhost:8082/api/parking/contact', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dataToSend),
            });

            if (response.ok) {
                setResponseMessage('Message sent to the owner successfully!');
            } else {
                const error = await response.json();
                throw new Error(error.message || 'Failed to send message.');
            }
        } catch (error) {
            console.error('Error sending message:', error);
            setResponseMessage('Error sending message. Please try again.');
        }
    };

    return (
        <div>
            <h2>Contact Owner</h2>
            <textarea
                rows="4"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Write your message to the owner..."
            />
            <button onClick={handleSendMessage}>Send Message</button>
            {responseMessage && <p>{responseMessage}</p>}
        </div>
    );
};

export default ContactOwner;
