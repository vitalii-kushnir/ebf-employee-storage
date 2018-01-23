import React, {Component} from "react";

const Breadcrumb = (props) => (
    <nav aria-label="breadcrumb">
        <ol className="breadcrumb">
            {props.items.map( item => <li key={item} className="breadcrumb-item active" aria-current="page">{item}</li>)}
        </ol>
    </nav>
);

export default Breadcrumb;