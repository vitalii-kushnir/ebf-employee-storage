import React, {Component} from 'react';
import axios from "axios/index";

/**
 * Component for editing information about company.
 */
class CompanyForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            name : '',
            id : null
        };
    }

    async componentDidMount() {
        const id = this.props.id;
        if (id) {
            const resp = await axios.get(`/api/company/${id}`);
            this.setState(resp.data);
        }
    }

    async onSaveHandler(e) {
        e.preventDefault();
        if (this.state.id) {
            await axios.put(`/api/company/${this.state.id}`, {
                id : this.state.id,
                name : this.state.name
            });
            window.location = '/#';
        } else {
            await axios.post(`/api/company`, {
                name : this.state.name
            });
            window.location = '/#';
        }
    }

    onNameChange(e) {
        const name = e.target.value;
        this.setState(() => ({name}));
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
                    <div className="col-sm-4">&nbsp;</div>
                    <div className="col-sm-4">
                        <button>Save Company</button>
                    </div>
                </div>

            </form>
        )
    }
}

export default CompanyForm;