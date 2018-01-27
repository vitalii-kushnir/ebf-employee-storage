import React, {Component} from 'react';
import axios from "axios/index";

/**
 * Component for editing information about employee.
 */
class CompanyForm extends Component {

    constructor(props) {
        const {companyId} = props;
        super(props);
        this.state = {
            name : '',
            surname : '',
            email : '',
            address : '',
            salary : '',
            companyId : companyId,
            id : null
        };
    }

    async componentDidMount() {
        const {employeeId, companyId} = this.props;
        if (employeeId) {
            const resp = await axios.get(`/api/employee/${employeeId}`);
            this.setState(resp.data);
        }
    }

    async onSaveHandler(e) {
        e.preventDefault();
        const {companyId} = this.state;
        if (this.state.id) {
            await axios.put(`/api/employee/${this.state.id}`, this.state);
            window.location = `#/company/${companyId}`;
        } else {
            console.log(this.state);
            debugger
            await axios.post(`/api/employee`, this.state);
            window.location = `#/company/${companyId}`;
        }
    }

    onNameChange(e) {
        const name = e.target.value;
        this.setState(() => ({name}));
    };

    onSurnameChange(e) {
        const surname = e.target.value;
        this.setState(() => ({surname}));
    };

    onAddressChange(e) {
        const address = e.target.value;
        this.setState(() => ({address}));
    };

    onSalaryChange(e) {
        const salary = e.target.value;
        this.setState(() => ({salary}));
    };

    onEmailChange(e) {
        const email = e.target.value;
        this.setState(() => ({email}));
    };

    render() {
        return (
            <form onSubmit={this.onSaveHandler.bind(this)}>
                <div className="form-group row">
                    <label htmlFor="name" className="col-sm-4 col-form-label text-right">Name</label>
                    <div className="col-sm-4">
                        <input type="text" className="form-control" id="name"
                               value={this.state.name}
                               required={true}
                               onChange={this.onNameChange.bind(this)}/>
                    </div>
                </div>

                <div className="form-group row">
                    <label htmlFor="surname" className="col-sm-4 col-form-label text-right">Surname</label>
                    <div className="col-sm-4">
                        <input type="text" className="form-control" id="surname"
                               value={this.state.surname}
                               required={true}
                               onChange={this.onSurnameChange.bind(this)}/>
                    </div>
                </div>

                <div className="form-group row">
                    <label htmlFor="email" className="col-sm-4 col-form-label text-right">Email</label>
                    <div className="col-sm-4">
                        <input type="email" className="form-control" id="email"
                               value={this.state.email}
                               required={true}
                               onChange={this.onEmailChange.bind(this)}/>
                    </div>
                </div>

                <div className="form-group row">
                    <label htmlFor="address" className="col-sm-4 col-form-label text-right">Address</label>
                    <div className="col-sm-4">
                        <input type="text" className="form-control" id="address"
                               value={this.state.address}
                               required={true}
                               onChange={this.onAddressChange.bind(this)}/>
                    </div>
                </div>

                <div className="form-group row">
                    <label htmlFor="salary" className="col-sm-4 col-form-label text-right">Salary</label>
                    <div className="col-sm-4">
                        <input type="number" className="form-control" id="salary" step="0.01"
                               value={this.state.salary}
                               required={true}
                               onChange={this.onSalaryChange.bind(this)}/>
                    </div>
                </div>

                <div className="form-group row">
                    <div className="col-sm-4">&nbsp;</div>
                    <div className="col-sm-4">
                        <button>Save Employee</button>
                    </div>
                </div>

            </form>
        )
    }
}

export default CompanyForm;