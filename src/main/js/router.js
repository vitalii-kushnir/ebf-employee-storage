import React from "react";
import {HashRouter, Route, Switch} from 'react-router-dom';
import Companies from './pages/Companies'
import CompanyCreate from './pages/CompanyCreate'
import CompanyEdit from './pages/CompanyEdit'
import Company from './pages/Company'
import Employee from './pages/Employee'
import EmployeeCreate from './pages/EmployeeCreate'
import EmployeeEdit from './pages/EmployeeEdit'
import Header from './components/Header';

const AppRouter = () => (
    <HashRouter>
        <div className="container">
            <Header/>
            <Switch>
                <Route path="/" component={Companies} exact/>
                <Route path="/company/create" component={CompanyCreate}/>
                <Route path="/company/:id/add-employee" component={EmployeeCreate}/>
                <Route path="/company/:id/edit" component={CompanyEdit}/>
                <Route path="/company/:companyId/employee/:employeeId" component={EmployeeEdit}/>
                <Route path="/company/:id" component={Company}/>
                <Route path="/employee/:id" component={Employee}/>
            </Switch>
        </div>
    </HashRouter>
);

export default AppRouter;