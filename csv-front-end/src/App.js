import React from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import EmployeeForm from './employee-form/EmployeeForm.js';
import SavedCid from './saved-cid/SavedCID';

function App() {
    return (
        <Router>
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/employee-form">Employee Form</Link>
                        </li>
                        <li>
                            <Link to="/saved-cid">Saved CID</Link>
                        </li>
                    </ul>
                </nav>

                <Routes>
                    <Route path="/employee-form" element={<EmployeeForm />} />
                    <Route path="/saved-cid" element={<SavedCid />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;