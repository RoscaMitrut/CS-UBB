//import React from 'react';

//import ReactDOM from 'react-dom';
import {createRoot} from 'react-dom/client';
import './index.css';
import ExcursieApp from "./ExcursieApp";

const container=document.getElementById('root');
const root=createRoot(container);
root.render( <ExcursieApp/>);
