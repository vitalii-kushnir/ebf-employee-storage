import React, {Component} from 'react';
import {Link} from 'react-router-dom';

/**
 * Header component
 */
class Header extends Component {

    render() {
        return (
            <nav className="navbar navbar-expand-sm navbar-light bg-faded">

                <span className="navbar-brand" href="#">EBF Employee Storage</span>

                <div className="collapse navbar-collapse ">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link to="/" className="nav-link">Main</Link>
                        </li>
                        <li className="nav-item">
                            <Link to="/company/create" className="nav-link">Add Company</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        )
    }
}

export default Header;