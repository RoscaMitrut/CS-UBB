import React from  'react';

import { useState } from 'react';
export default function ExcursieForm({addFunc}){
    

    const [obiectivVizitat, setobiectivVizitat] = useState('');
    const [oraPlecare, setoraPlecare] = useState('');
    const [firmaTransport, setfirmaTransport] = useState('');
    const [pret, setpret] = useState('');
    const [locuriDisponibile, setlocuriDisponibile] = useState('');
    const [id, setid] = useState('');

   function handleSubmit (event){

        let excursie={
            obiectivVizitat:obiectivVizitat,
            oraPlecare:oraPlecare,
            firmaTransport:firmaTransport,
            pret:pret,
            locuriDisponibile:locuriDisponibile,
            id:id
        }
        console.log('Excursie submitted: ');
        console.log(excursie);
        addFunc(excursie);
        event.preventDefault();
    }
    return(
    <form onSubmit={handleSubmit}>
        <label>
        Obiectiv Vizitat:
            <input type="text" value={obiectivVizitat} onChange={e=>setobiectivVizitat(e.target.value)} />
        </label><br/>
        <label>
        Ora Plecare:
            <input type="text" value={oraPlecare} onChange={e=>setoraPlecare(e.target.value)} />
        </label><br/>
        <label>
        Firma Transport:
            <input type="text" value={firmaTransport} onChange={e=>setfirmaTransport(e.target.value)} />
        </label><br/>
        <label>
        Pret:
            <input type="text" value={pret} onChange={e=>setpret(e.target.value)} />
        </label><br/>
        <label>
        Locuri Disponibile:
            <input type="text" value={locuriDisponibile} onChange={e=>setlocuriDisponibile(e.target.value)} />
        </label><br/>
        <label>
        Id:
            <input type="text" value={id} onChange={e=>setid("999")} />
        </label><br/>
        <input type="submit" value="Add excursie" />
    </form>);
}