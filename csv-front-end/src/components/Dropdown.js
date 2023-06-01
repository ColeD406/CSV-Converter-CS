import React, { useState, useEffect } from 'react';

const Dropdown = ({ options, usedOptions, onOptionSelect, placeholder }) => {
    const [availableOptions, setAvailableOptions] = useState([]);

    useEffect(() => {
        setAvailableOptions(options.filter(option => !usedOptions.includes(option)));
    }, [options, usedOptions]);

    const handleChange = (e) => {
        onOptionSelect(e.target.value);
    };

    return (
        <select onChange={handleChange} defaultValue="">
            <option value="" disabled>{placeholder}</option>
            {availableOptions.map(option => (
                <option key={option} value={option}>{option}</option>
            ))}
        </select>
    );
};

export default Dropdown;
