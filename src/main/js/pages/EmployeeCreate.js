import React from 'react';
import axios from "axios/index";
import EmployeeForm from '../components/EmployeeForm';

/**
 * Component fer creating of a new employee.
 */
class EmployeeCreate extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            company : {
                id : props.match.params.id
            },
            error : ''
        };
    }

    async componentDidMount() {
        const id = this.props.match.params.id;
        try {
            const company = await axios.get(`/api/company/${id}`);
            this.setState({
                company : company.data,
            });
        } catch (e) {
            this.setState({
                error : 'Cannot find company with a given id.',
            });
        }
    }

    render() {
        return (
            <div>
                <h1>Add a New Employee to the "{this.state.company.name}"</h1>
                <EmployeeForm companyId={this.state.company.id}/>
            </div>
        )
    }
}

export default EmployeeCreate;


