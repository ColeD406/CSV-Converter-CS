import React, { useState, useRef } from "react";
import "../main.css"

function SavedCID() {
    const [companyId, setCompanyId] = useState("");
    const fileInputRef = useRef(null);
    const [submit, setSubmit] = useState("Submit")

    const onSubmit = (message) => {
        setSubmit(message);
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!fileInputRef.current.files[0]) {
            alert('Please upload a CSV file.')
            return;
        }
        if (!companyId) {
            alert('Please enter a Company ID')
            return;
        }

        const formData = new FormData();
        formData.append("csvFile", fileInputRef.current.files[0]);
        formData.append("companyID", companyId);

        fetch("http://localhost:8080/csv-saved", {
            method: "POST",
            body: formData,
        }).then((response) => {
            if (response.ok) {
                onSubmit("Submitted!");
            }
            else {
                onSubmit("Error submitting")
            }
            return response.json();
        })
            .then((data) => console.log(data))
            .catch((error) => {
                console.error('Error:', error);
                // Reset the button text if there's an error
                setSubmit("Error submitting");
            });

    };

    return (
        <div className="form-container">
            <form onSubmit={handleSubmit} className="form-section">
                <div>
                    <div className="csv-upload-text">
                        Csv Upload
                    </div>
                    <input
                        type="file"
                        id="csvFile"
                        accept=".csv"
                        ref={fileInputRef}
                    />
                </div>
                <div>
                    <label htmlFor="companyId">Company ID:</label>
                    <input
                        type="text"
                        id="companyId"
                        value={companyId}
                        onChange={(e) => setCompanyId(e.target.value)}
                    />
                </div>
                <button type="submit" onClick={handleSubmit}>{submit}</button>
            </form>
        </div>
    );
}

export default SavedCID;
