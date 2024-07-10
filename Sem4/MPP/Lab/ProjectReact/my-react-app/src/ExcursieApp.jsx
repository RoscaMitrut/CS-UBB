import React,{useEffect} from  'react';
import { useState } from 'react';
import ExcursieTable from './ExcursieTable.jsx';
import './ExcursieApp.css'
import ExcursieForm from "./ExcursieForm.jsx";
import {GetExcursii, DeleteExcursie, AddExcursie} from './utils/rest-calls'

export default function ExcursieApp() {
  const [excursii, setExcursii] = useState([{
  "obiectivVizitat":"a",
  "oraPlecare":"a",
  "firmaTransport":"a",
  "pret":"a",
  "locuriDisponibile":"a",
  "id":"a"}]);

	function addFunc(excursie){
		console.log('inside add Func '+excursie);
		AddExcursie(excursie)
			.then(res=> GetExcursii())
			.then(excursii=>setExcursii(excursii))
			.catch(erorr=>console.log('eroare add ',erorr));
	}

	function deleteFunc(excursie){
		console.log('inside deleteFunc '+excursie);
		DeleteExcursie(excursie)
			.then(res=> GetExcursii())
			.then(excursii=>setExcursii(excursii))
			.catch(error=>console.log('eroare delete', error));
	}

	useEffect(()=>{
		console.log("inside useEffect")
		GetExcursii().then(excursii=>setExcursii(excursii));},[]);

	return (<div className="ExcursieApp">
		<h1>New Excursie Management App </h1>
		<ExcursieForm addFunc={addFunc}/>
		<br/>
		<br/>
		<ExcursieTable excursiiList={excursii} deleteFunc={deleteFunc}/>
	</div>);
}