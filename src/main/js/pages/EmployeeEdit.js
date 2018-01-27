import React from 'react';
import EmployeeForm from '../components/EmployeeForm';

/**
 * Component fer editing information about employee.
 */
class EmployeeEdit extends React.Component {

    render() {
        const {companyId, employeeId} = this.props.match.params;
        return (
            <div>
                <h1>Edit Information About Employee</h1>
                <EmployeeForm companyId={companyId} employeeId={employeeId}/>
            </div>
        )
    }
}

export default EmployeeEdit;


