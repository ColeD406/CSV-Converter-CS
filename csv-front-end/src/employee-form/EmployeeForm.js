import React, {useEffect, useState} from 'react';
import {useRef} from "react";
import "../main.css"
import Select from "react-select";
import ConvertData from "../components/ConvertData";

const fieldOptions = [
    { value: 'EmployeeFName', label: 'First Name' },
    { value: 'EmployeeLName', label: 'Last Name' },
    { value: 'EmployeeID', label: 'Employee ID' },
    { value: 'EmployeeEmail', label: 'Employee Email' },
    { value: 'Customer', label: 'Customer' },
    { value: 'Job', label: 'Job' },
    { value: 'JobNumber', label: 'Job Number' },
    { value: 'Task', label: 'Task' },
    { value: 'TaskCode', label: 'Task Code' },
    { value: 'StartDate', label: 'Start Date' },
    { value: 'StartTime', label: 'End Time' },
    { value: 'EndDate', label: 'End Date' },
    { value: 'EndTime', label: 'End Time' },
    { value: 'MinutesBillable', label: 'Billable Minutes' },
    { value: 'TotalTime', label: 'Total Time' },
    { value: 'Regular', label: 'Regular' },
    { value: 'OverTime', label: 'OverTime' },
    { value: 'DoubleTime', label: 'DoubleTime' },
    { value: 'Time Off', label: 'Time Off' },
    { value: 'Unpaid Time Off', label: 'Unpaid Time Off' },
];

const EmployeeForm = () => {
    const fileInputRef = useRef();
    const [fieldMappings, setFieldMappings] = useState(Array(21).fill({ field: '', value: '' }));
    const [companyID, setCompanyID] = useState("");
    const [email, setEmail] = useState("");
    const [csvUpload, setCSVUpload] = useState("Upload CSV");
    const [formSubmit, setFormSubmit] = useState("Submit")
    const [payComponent, setPayComponent] = useState("Pay Component")

    const handleCompanyIDChange = (event) => {
        setCompanyID(event.target.value);
    }

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    }

    const handleFieldChange = (index, selectedOption) => {
        const newMappings = [...fieldMappings];
        newMappings[index] = { ...newMappings[index], field: selectedOption.value };
        setFieldMappings(newMappings);
    };

    const handleValueChange = (index, event) => {
        const newMappings = [...fieldMappings];
        newMappings[index] = { ...newMappings[index], value: event.target.value };
        setFieldMappings(newMappings);
    };

    const onCSVSubmit = () => {
        setCSVUpload("CSV Uploaded!")
    }
    const onFormSubmit = (message) => {
        setFormSubmit(message)
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!fileInputRef.current.files[0]) {
            alert('Please upload a CSV file!');
            return;
        }
        if (!companyID) {
            alert('Please enter a Company ID!');
            return;
        }
        if (!email) {
            alert('Please enter an Email to send CSV to!')
            return;
        }

        const map = ConvertData(fieldMappings);

        const formData = new FormData();
        formData.append('csvFile', fileInputRef.current.files[0]);
        formData.append('formValues', JSON.stringify(map));
        formData.append('companyID', companyID);
        formData.append('email', email);
        formData.append('payComponent', payComponent);

        const requestOptions = {
            method: 'POST',
            body: formData,
        };

        fetch('http://localhost:8080/csv-new', requestOptions)
            .then((response) => {
                if (response.ok) {
                    onFormSubmit("Submitted!");
                }
                else {
                    onFormSubmit("Error submitting")
                }
                return response.json();
            })
            .then((data) => console.log(data))
            .catch((error) => {
                console.error('Error:', error);
                // Reset the button text if there's an error
                setFormSubmit("Error!");
            });
    };

    const handlePayComponentChange = (event) => {
        if (event.target.checked) {
            setPayComponent(true)
        } else {
            setPayComponent(false)
        }
    }
    return (
        <div className="wrapper">
            <h1>CSV File Upload</h1>
            <form className="form-container" onSubmit={handleSubmit}>
                <div className="form-section">
                    <label htmlFor="csv-upload" className="csv-upload-label">
                        {csvUpload}
                    </label>
                    <label> Check for Pay Component Format
                        <input id="checkbox_id"
                                  type="checkbox"
                                  onChange={handlePayComponentChange}
                    />
                    </label>
                    <input ref={fileInputRef} type="file" accept=".csv" id="csv-upload" hidden onChange={onCSVSubmit} />
                </div>
                <div className="form-section">
                    {fieldMappings.map((mapping, index) => (
                        <div key={index} className="field-container">
                            <div className="select-container">
                                <Select
                                    options={fieldOptions}
                                    onChange={(selectedOption) =>
                                        handleFieldChange(index, selectedOption)
                                    }
                                />
                            </div>
                            <div className="input-container">
                                <input
                                    type="text"
                                    value={mapping.value}
                                    onChange={(event) => handleValueChange(index, event)}
                                />
                            </div>
                        </div>
                    ))}

                </div>
                <div className={"form-section"}>
                    <label htmlFor="companyIdInput">Company ID:</label>
                    <input
                        type="text"
                        id="companyIdInput"
                        value={companyID}
                        onChange={handleCompanyIDChange}
                    />
                </div>
                <div className={"form-section"}>
                    <label htmlFor="emailInput">Email for CSV recipient:</label>
                    <input
                        type="text"
                        id="emailInput"
                        value={email}
                        onChange={handleEmailChange}
                    />
                </div>
                <div className="form-section">
                    <button type="submit" onChange={onFormSubmit}>{formSubmit}</button>
                </div>
            </form>
        </div>
    );
}

export default EmployeeForm;