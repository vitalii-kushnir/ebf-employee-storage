import React from 'react';
import axios from 'axios';

/**
 * Component fer rendering information about employee.
 */
class Employee extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            employee : {},
            error : ''
        }
    }

    async componentDidMount() {
        try {
            const id = this.props.match.params.id;
            const employee = await axios.get(`/api/employee/${id}`);
            this.setState({employee : employee.data});
        } catch (e) {
            this.setState({error : e.message});
        }

    }

    renderEployeeProperty(key, value) {
        return (
            <tr>
                <td><b>{key}</b></td>
                <td>{value}</td>
            </tr>
        )
    }

    render() {
        const employee = this.state.employee;
        const error = this.state.error;

        let title;
        let employeeData;
        if (error) {
            title = 'Cannot find en employee with the given id.';
        } else {
            title = `Information about ${employee.name} ${employee.surname}`;
            employeeData = (
                <table className="table">
                    <tbody>
                    {this.renderEployeeProperty('Name', employee.name)}
                    {this.renderEployeeProperty('Surname', employee.surname)}
                    {this.renderEployeeProperty('Company Name', employee.companyName)}
                    {this.renderEployeeProperty('Email', employee.email)}
                    {this.renderEployeeProperty('Address', employee.address)}
                    {this.renderEployeeProperty('Salary', employee.salary)}
                    </tbody>
                </table>
            )
        }

        return (
            <div>
                <h1>{title}</h1>
                {employeeData}
            </div>
        )
    }
}

export default Employee;