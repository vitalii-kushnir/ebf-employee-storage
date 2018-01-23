import React from 'react';
import axios from 'axios';
import CompanyForm from '../components/CompanyForm';
import Breadcrumb from '../components/Breadcrumb';

class CompanyCreatePage extends React.Component {

    render() {
        return (
            <div>
                <Breadcrumb items={['New Company']}/>
                <CompanyForm/>
            </div>
        )
    }
}

export default CompanyCreatePage;


