import React from 'react';
import axios from 'axios';
import Breadcrumb from '../components/Breadcrumb';
class CompanyPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            company : []
        };
    }

    async componentDidMount() {
        const id = this.props.match.params.id;
        const company = await axios.get(`/api/company/${id}`);
        this.setState({company : company.data});
    }

    render() {
        return (
            <div>
                <div className="row">
                    <div className="col-8">
                        <Breadcrumb items={['Companies', this.state.company.name]}/>
                    </div>
                    <div className="col-4">
                        &nbsp;
                    </div>
                </div>

                <h1> {this.state.company.name}</h1>
            </div>
        )
    }
}

export default CompanyPage;


