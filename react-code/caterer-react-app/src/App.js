import './App.css';
import React from "react";
import { Route, Switch } from "react-router-dom";
import About from './About';
import ListCaterer from './ListCaterer';
import Error from './Error';
import AddCaterer from './AddCaterer';

const App = () => {
    return (
    <Switch>
      <Route exact path='/' component={ About } />
      <Route path='/listcaterer' component={ ListCaterer } />
      <Route path='/addcaterer' component={ AddCaterer } />
      <Route path='/about' component={ About } />
      <Route component={ Error } />
    </Switch>
    );
};

export default App;