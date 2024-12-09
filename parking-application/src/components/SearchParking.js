import React, { useState } from 'react';
import '../css/SearchParking.css';

const SearchParking = ({ onSearch }) => {
    const [city, setCity] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    const handleSearch = () => {
        onSearch(city, startDate, endDate); // Trigger the search with provided input
    };

    return (
        <div className="search-container">
            <h3 className="search-title">Search Parking Spots</h3>
            <div className="form-group">
                <label className="form-label">City:</label>
                <input
                    type="text"
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                    placeholder="Enter city (e.g., Oslo)"
                    className="form-input"
                />
            </div>
            <div className="form-group">
                <label className="form-label">Start Date:</label>
                <input
                    type="date"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                    className="form-input"
                />
            </div>
            <div className="form-group">
                <label className="form-label">End Date:</label>
                <input
                    type="date"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                    className="form-input"
                />
            </div>
            <button onClick={handleSearch} className="search-button">Search</button>
        </div>
    );
};

export default SearchParking;
