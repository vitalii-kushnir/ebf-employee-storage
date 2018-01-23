import React from 'react';
import CompanyListItem from './CompaniesListItem';

class CompaniesList extends React.Component {
    render() {
        return (
            <div>
                <ol>
                    {this.props.companies.map(company => (
                        <CompanyListItem key={company.id} {...company} onDeleteClick={this.props.onDeleteClick}/>
                    ))}
                </ol>
            </div>
        )
    }
}

export default CompaniesList;