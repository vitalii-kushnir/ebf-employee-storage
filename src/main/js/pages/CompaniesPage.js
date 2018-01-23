import React from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';
import CompaniesList from '../components/CompaniesList';
import Breadcrumb from '../components/Breadcrumb';

class CompaniesPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            companies : []
        };
    }

    async componentDidMount() {
        const companies = await axios.get('/api/company');
        this.setState({companies : companies.data});
    }

    async handleDeleteClick(id) {
        await axios.delete(`/api/company/${id}`);
        const companies = await axios.get('/api/company');
        this.setState({companies : companies.data});
    }

    render() {
        return (
            <div>
                <div className="row">
                    <div className="col-8">
                        <Breadcrumb items={['Companies']}/>
                    </div>
                    <div className="col-4">
                        <Link to="/company/create" className="btn btn-info">Crete New Company</Link>
                    </div>
                </div>

                <CompaniesList companies={this.state.companies} onDeleteClick={this.handleDeleteClick.bind(this)}/>
            </div>
        )
    }
}

export default CompaniesPage;