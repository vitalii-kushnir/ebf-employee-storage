import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";

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

    render() {
        return (
            <div>
                <ol>
                    {this.state.companies.map(c => (
                        <li key={c.id}>
                            <Link to={`/company/${c.id}`}>{c.name}</Link>
                        </li>
                    ))}
                </ol>
            </div>
        )
    }
}

export default CompaniesPage;