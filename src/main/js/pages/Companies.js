import React from 'react';
import axios from 'axios';
import {Link} from "react-router-dom";

/**
 * Component for rendering list of companies.
 */
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

    renderEmployee(company, id) {
        return (
            <tr key={id}>
                <th scope="row">{id + 1}</th>
                <td>{company.name}</td>
                <td className="text-center">
                    <Link to={`/company/${company.id}`} className="btn btn-outline-info btn-sm">Info</Link>
                </td>
                <td className="text-center">
                    <Link to={`/company/${company.id}/edit`} className="btn btn-outline-primary btn-sm">Edit</Link>
                </td>
                <td className="text-center">
                    <button onClick={() => {
                        this.handleDeleteClick(company.id)
                    }} className="btn btn-outline-danger btn-sm">Delete
                    </button>
                </td>
            </tr>
        );
    }

    render() {
        return (
            <div>
                <h1>List of Companies</h1>
                <table className="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th className="text-center">
                            <Link to={`/company/create`} className="btn btn-outline-primary btn-sm">Add New</Link>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.companies.map((company, id) => this.renderEmployee(company, id))}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default CompaniesPage;