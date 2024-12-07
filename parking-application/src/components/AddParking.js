import React, { useState } from 'react';

const AddParking = ({ userId, onParkingAdded }) => {
    const initialParkingData = {
        name: '',
        region: '',
        price: '',
        availableStartDate: '',
        availableEndDate: '',
        description: '',
        location: {
            addressName: '',
            gateNumber: '',
            postBoxNumber: '',
            city: '',
        },
    };

    const [parkingData, setParkingData] = useState(initialParkingData);
    const [message, setMessage] = useState('');
    const [error, setError] = useState(null); // For detailed error handling

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name in parkingData.location) {
            setParkingData((prevData) => ({
                ...prevData,
                location: {
                    ...prevData.location,
                    [name]: value,
                },
            }));
        } else {
            setParkingData((prevData) => ({
                ...prevData,
                [name]: value,
            }));
        }
    };

    const resetForm = () => {
        setParkingData(initialParkingData);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const dataToSend = { ...parkingData, userId };

        try {
            const response = await fetch('http://localhost:8082/api/parking/add', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dataToSend),
            });

            if (response.ok) {
                const newParkingSpot = await response.json();
                onParkingAdded(newParkingSpot); // Notify parent of new parking spot
                resetForm();
                setMessage('Parking spot added successfully!');
                setError(null); // Clear any previous errors
            } else {
                const errorDetails = await response.json();
                throw new Error(errorDetails.message || 'Failed to add parking spot');
            }
        } catch (err) {
            console.error('Error adding parking:', err);
            setError(err.message);
            setMessage('Error adding parking. Please check your input and try again.');
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
                    Region:
                    <input
                        type="text"
                        name="region"
                        value={parkingData.region}
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
                <label>
                    Available Start Date:
                    <input
                        type="date"
                        name="availableStartDate"
                        value={parkingData.availableStartDate}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Available End Date:
                    <input
                        type="date"
                        name="availableEndDate"
                        value={parkingData.availableEndDate}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Description:
                    <textarea
                        name="description"
                        value={parkingData.description}
                        onChange={handleChange}
                        required
                    />
                </label>
                <fieldset>
                    <legend>Location Details:</legend>
                    <label>
                        Address Name:
                        <input
                            type="text"
                            name="addressName"
                            value={parkingData.location.addressName}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    <label>
                        Gate Number:
                        <input
                            type="text"
                            name="gateNumber"
                            value={parkingData.location.gateNumber}
                            onChange={handleChange}
                        />
                    </label>
                    <label>
                        Post Box Number:
                        <input
                            type="text"
                            name="postBoxNumber"
                            value={parkingData.location.postBoxNumber}
                            onChange={handleChange}
                        />
                    </label>
                    <label>
                        City:
                        <input
                            type="text"
                            name="city"
                            value={parkingData.location.city}
                            onChange={handleChange}
                        />
                    </label>
                </fieldset>
                <button type="submit">Add Parking</button>
            </form>
            {message && <p>{message}</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
};

export default AddParking;
