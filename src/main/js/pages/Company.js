import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";

/**
 * Component for rendering a list of employees in a company.
 */
class CompanyPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            company : {},
            employees : []
        };
    }

    async handleDeleteClick(id) {
        const companyId = this.props.match.params.id;
        await axios.delete(`/api/employee/${id}`);
        const employees = await axios.get(`/api/employee/company/${companyId}`);
        this.setState({employees : employees.data});
    }

    async componentDidMount() {
        const id = this.props.match.params.id;

        let company;
        let employees;

        try {
            company = await axios.get(`/api/company/${id}`);
        } catch (e) {
            this.setState({
                error : 'Cannot find company with a given id.',
            });
            return;
        }

        try {
            employees = await axios.get(`/api/employee/company/${id}`);
        } catch (e) {
            this.setState({
                error : 'Cannot list of employees of the company.',
            });
            return
        }

        this.setState({
            company : company.data,
            employees : employees.data
        });
    }


    renderEmployee(employee, rowId) {
        const companyId = this.props.match.params.id;
        return (
            <tr key={rowId}>
                <th scope="row">{rowId + 1}</th>
                <td>{employee.name}</td>
                <td>{employee.surname}</td>
                <td className="text-center">
                    <Link to={`/employee/${employee.id}`} className="btn btn-outline-info btn-sm">Info</Link>
                </td>
                <td className="text-center">
                    <Link to={`/company/${companyId}/employee/${employee.id}`}
                          className="btn btn-outline-primary btn-sm">
                        Edit
                    </Link>
                </td>
                <td className="text-center">
                    <button onClick={() => {
                        this.handleDeleteClick(employee.id)
                    }} className="btn btn-outline-danger btn-sm">Delete
                    </button>
                </td>
            </tr>
        );
    }

    render() {

        const company = this.state.company;
        const employees = this.state.employees;
        const error = this.state.error;

        let title;
        let disabled;
        if (error) {
            title = error;
            disabled = error ? 'disabled' : '';
        } else {
            title = `List of Emloyees of the "${company.name}"`;
        }

        return (
            <div>
                <h1>{title}</h1>
                <table className="table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>&nbsp;</th>
                        <th>&nbsp;</th>
                        <th className="text-center">
                            <Link to={`/company/${company.id}/add-employee`}
                                  className={`btn btn-outline-primary btn-sm ${disabled}`}>
                                Add New
                            </Link>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {employees.map((employee, id) => this.renderEmployee(employee, id))}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default CompanyPage;


