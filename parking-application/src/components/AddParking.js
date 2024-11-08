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
            userId: userId
        };

        try {
            const response = await fetch("http://localhost:8082/api/parking/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(dataToSend),
            });

            if (!response.ok) {
                throw new Error("Network response was not ok");
            }

            const data = await response.json();
            console.log("Parking spot added:", data);
            setMessage("Parking spot added successfully!");

            // Call the callback to update the parking spots list
            onParkingAdded(data);

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
