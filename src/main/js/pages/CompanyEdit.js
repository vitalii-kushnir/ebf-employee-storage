import React from 'react';
import CompanyForm from '../components/CompanyForm';

/**
 * Component for editing information about company.
 */
class CompanyEdit extends React.Component {

    render() {
        return (
            <div>
                <h1>Edit Company</h1>
                <CompanyForm id={this.props.match.params.id}/>
            </div>
        )
    }
}

export default CompanyEdit;


