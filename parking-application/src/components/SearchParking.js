import React, { useState } from 'react';

const SearchParking = ({ onSearch }) => {
    const [city, setCity] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    const handleSearch = () => {
        onSearch(city, startDate, endDate); // Trigger the search with provided input
    };

    return (
        <div>
            <h3>Search Parking Spots</h3>
            <label>
                City:
                <input
                    type="text"
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                    placeholder="Enter city (e.g., Oslo)"
                />
            </label>
            <label>
                Start Date:
                <input
                    type="date"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                />
            </label>
            <label>
                End Date:
                <input
                    type="date"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                />
            </label>
            <button onClick={handleSearch}>Search</button>
        </div>
    );
};

export default SearchParking;
