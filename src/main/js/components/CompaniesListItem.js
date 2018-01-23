import React from "react";
import {Link} from "react-router-dom";

const CompaniesList = (props) => (
    <li>
        <Link to={`/company/${props.id}`}>{props.name}</Link>
        &nbsp;
        <Link to={`/company/${props.id}/edit`}>Edit</Link>
        &nbsp;
        <span onClick={() => props.onDeleteClick(props.id)}>Delete</span>
    </li>
);

export default CompaniesList;