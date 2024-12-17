import React, { useState } from 'react';
import '../css/AddParking.css';

const AddParking = ({ userId, user, onParkingAdded }) => {
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

        const dataToSend = {
            ...parkingData,
            userId,
            publisherName: user?.name || 'Unknown User',
            publisherEmail: user?.email || 'Unknown Email',
        };

        try {
            const response = await fetch('http://localhost:8082/api/parking/add', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dataToSend),
            });

            if (response.ok) {
                const newParkingSpot = await response.json();
                onParkingAdded(newParkingSpot);
                resetForm();
                setMessage('Parking spot added successfully!');
            } else {
                const errorDetails = await response.json();
                setMessage(`Error: ${errorDetails.message || 'Something went wrong.'}`);
            }
        } catch (err) {
            console.error(err);
            setMessage('Error adding parking. Please try again.');
        }
    };

    return (
        <div className="add-parking-form">
            <h2>Add Parking Spot</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Name <span className="required">*</span>
                    <input
                        type="text"
                        name="name"
                        value={parkingData.name}
                        onChange={handleChange}
                        placeholder="Enter parking spot name"
                        required
                    />
                </label>
                <label>
                    Region <span className="required">*</span>
                    <select
                        name="region"
                        value={parkingData.region}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select Region</option>
                        <option value="Oslo">Oslo</option>
                        <option value="Vestland">Vestland</option>
                        {/* Add all Norwegian regions */}
                    </select>
                </label>
                <label>
                    Price <span className="required">*</span>
                    <input
                        type="number"
                        name="price"
                        value={parkingData.price}
                        onChange={handleChange}
                        placeholder="Enter price in NOK"
                        required
                    />
                </label>
                <label>
                    Available Start Date <span className="required">*</span>
                    <input
                        type="date"
                        name="availableStartDate"
                        value={parkingData.availableStartDate}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Available End Date <span className="required">*</span>
                    <input
                        type="date"
                        name="availableEndDate"
                        value={parkingData.availableEndDate}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Description
                    <textarea
                        name="description"
                        value={parkingData.description}
                        onChange={handleChange}
                        placeholder="Add a short description (e.g., close to downtown)"
                    />
                </label>
                <fieldset>
                    <legend>Location Details</legend>
                    <label>
                        Address Name <span className="required">*</span>
                        <input
                            type="text"
                            name="addressName"
                            value={parkingData.location.addressName}
                            onChange={handleChange}
                            placeholder="e.g., Sletten veien"
                            required
                        />
                    </label>
                    <label>
                        Gate Number
                        <input
                            type="text"
                            name="gateNumber"
                            value={parkingData.location.gateNumber}
                            onChange={handleChange}
                            placeholder="e.g., 123"
                        />
                    </label>
                    <label>
                        Post Box Number
                        <input
                            type="text"
                            name="postBoxNumber"
                            value={parkingData.location.postBoxNumber}
                            onChange={handleChange}
                            placeholder="e.g., 5678"
                        />
                    </label>
                    <label>
                        City <span className="required">*</span>
                        <input
                            type="text"
                            name="city"
                            value={parkingData.location.city}
                            onChange={handleChange}
                            placeholder="e.g., Bergen"
                            required
                        />
                    </label>
                </fieldset>
                <button className="primary-button" type="submit">
                    Add Parking Spot
                </button>
            </form>
            {message && <p className="message">{message}</p>}
        </div>
    );
};

export default AddParking;
