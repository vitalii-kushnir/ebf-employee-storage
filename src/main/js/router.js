import React from "react";
import {Route, Switch, HashRouter} from 'react-router-dom';
import CompaniesPage from './pages/CompaniesPage'
import CompanyPage from './pages/CompanyPage'

const AppRouter = () => (
    <HashRouter>
        <div>
            <Switch>
                <Route path="/" component={CompaniesPage} exact/>
                <Route path="/company/:id" component={CompanyPage}/>
            </Switch>
        </div>
    </HashRouter>
);

export default AppRouter;