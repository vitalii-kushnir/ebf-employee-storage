import React, {Component} from 'react';
import axios from "axios/index";

class Header extends Component {

    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <a className="navbar-brand" href="#">EBF Employees Storage App</a>
            </nav>
        )
    }
}

export default Header;