import React from 'react';
import { Link } from 'react-router-dom';


export default function CreateProjectButton() {
    return (
        <React.Fragment>
            <Link to="/addProject" href="ProjectForm.html" className="btn btn-lg btn-info">
                Create a Project
            </Link>
        </React.Fragment>
    )
}
