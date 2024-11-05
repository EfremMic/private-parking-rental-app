import React, { useState } from 'react';

const AddParking = ({ userId }) => {
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

    const handleSubmit = (e) => {
        e.preventDefault();

        const dataToSend = {
            ...parkingData,
            userId: userId  // Attach userId from the props
        };

        fetch("http://localhost:8080/api/parking/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(dataToSend),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Parking spot added:", data);
                setMessage("Parking spot added successfully!");
            })
            .catch((error) => {
                console.error("Error adding parking:", error);
                setMessage("Error adding parking.");
            });
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
