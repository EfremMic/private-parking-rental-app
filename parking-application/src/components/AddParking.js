import React, { useState } from 'react';

const AddParking = ({ userId, onParkingAdded }) => {
    const [parkingData, setParkingData] = useState({
        name: '',
        location: '',
        price: ''
    });
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setParkingData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const dataToSend = {
            ...parkingData,
            userId: userId // Ensure userId is passed as expected by the backend
        };

        try {
            const response = await fetch("http://localhost:8082/api/parking/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(dataToSend),
            });

            // Check for specific status codes and handle accordingly
            if (response.status === 201 || response.ok) { // Adjusted to handle 201 Created
                const data = await response.json();
                console.log("Parking spot added:", data);
                setMessage("Parking spot added successfully!");

                // Call the callback to update the parking spots list in the parent component
                onParkingAdded(data);
            } else {
                throw new Error(`Unexpected response status: ${response.status}`);
            }

        } catch (error) {
            console.error("Error adding parking:", error);
            setMessage("Error adding parking.");
        }
    };

    return (
        <div>
            <h2>Add Parking Spot</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input
                        type="text"
                        name="name"
                        value={parkingData.name}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Location:
                    <input
                        type="text"
                        name="location"
                        value={parkingData.location}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Price:
                    <input
                        type="number"
                        name="price"
                        value={parkingData.price}
                        onChange={handleChange}
                        required
                    />
                </label>
                <button type="submit">Add Parking</button>
            </form>
            {message && <p>{message}</p>}
        </div>
    );
};

export default AddParking;
