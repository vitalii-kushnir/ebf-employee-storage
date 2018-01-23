import React from 'react';
import CompanyForm from '../components/CompanyForm';
import Breadcrumb from '../components/Breadcrumb';

class CompanyPage extends React.Component {

    render() {
        return (
            <div>
                <div className="row">
                    <div className="col-8">
                        <Breadcrumb items={['Edit Company']}/>
                    </div>
                </div>
                <CompanyForm id={this.props.match.params.id}/>
            </div>
        )
    }
}

export default CompanyPage;


