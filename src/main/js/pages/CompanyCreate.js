import React from 'react';
import CompanyForm from '../components/CompanyForm';

/**
 * Component for creating of a new company.
 */
class CompanyCreateP extends React.Component {

    render() {
        return (
            <div>
                <h1>Add a New Company</h1>
                <CompanyForm/>
            </div>
        )
    }
}

export default CompanyCreateP;


