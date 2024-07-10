
import React from  'react';
import './ExcursieApp.css'

function ExcursieRow({excursie, deleteFunc}){
    function handleDelete(event){
        console.log('delete button pentru '+excursie.id);
        deleteFunc(excursie.id);
    }
    return (
        <tr>
            <td>{excursie.id}</td>
            <td>{excursie.obiectivVizitat}</td>
            <td>{excursie.oraPlecare}</td>
            <td>{excursie.firmaTransport}</td>
            <td>{excursie.pret}</td>
            <td>{excursie.locuriDisponibile}</td>
            <td><button  onClick={handleDelete}>Delete</button></td>
        </tr>
    );
}
export default function ExcursieTable({excursiiList, deleteFunc}){
    console.log("In ExcursieTable");
    console.log(excursiiList);
    let rows = [];
    let functieStergere=deleteFunc;
    excursiiList.forEach(function(excursie) {
        rows.push(<ExcursieRow excursie={excursie}  key={excursie.id} deleteFunc={functieStergere} />);
    });


    return (
        <div className="ExcursieTable">
            <table className="center">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Obiectiv Vizitat</th>
                    <th>Ora Plecare</th>
                    <th>Firma Transport</th>
                    <th>Pret</th>
                    <th>Locuri Disponibile</th>
                </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>

        </div>
    );
}