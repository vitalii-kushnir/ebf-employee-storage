import React from "react";
import {HashRouter, Route, Switch} from 'react-router-dom';
import CompaniesPage from './pages/CompaniesPage'
import CompanyPage from './pages/CompanyPage'
import CompanyEditPage from './pages/CompanyEditPage'
import CompanyCreatePage from './pages/CompanyCreatePage'
import Header from './components/Header';

const AppRouter = () => (
    <HashRouter>
        <div className="container">
            <Header/>
            <Switch>
                <Route path="/" component={CompaniesPage} exact/>
                <Route path="/company/create" component={CompanyCreatePage}/>
                <Route path="/company/:id/edit" component={CompanyEditPage}/>
                <Route path="/company/:id" component={CompanyPage}/>
            </Switch>
        </div>
    </HashRouter>
);

export default AppRouter;