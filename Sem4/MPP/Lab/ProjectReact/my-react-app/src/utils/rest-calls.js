
import {PROJECT_BASE_URL} from './consts';

function status(response) {
    console.log('response status '+response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

export function GetExcursii(){
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let myInit = { method: 'GET',
        headers: headers,
        mode: 'cors'
    };
    let request = new Request(PROJECT_BASE_URL, myInit);

    console.log('Inainte de fetch GET pentru '+PROJECT_BASE_URL)

    return fetch(request)
        .then(status)
        .then(json)
        .then(data=> {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });

}

export function DeleteExcursie(id){
    console.log('inainte de fetch delete')
    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    let antet = { method: 'DELETE',
        headers: myHeaders,
       mode: 'cors'
    };

    const excursieDelUrl=PROJECT_BASE_URL+'/'+id;
    console.log('URL pentru delete   '+excursieDelUrl)
    return fetch(excursieDelUrl,antet)
        .then(status)
        .then(response=>{
            console.log('Delete status '+response.status);
            return response.text();
        }).catch(e=>{
            console.log('error '+e);
            return Promise.reject(e);
        });

}

export function AddExcursie(excursie){
    console.log('inainte de fetch post'+JSON.stringify(excursie));

    let myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    let antet = { method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(excursie)};

    return fetch(PROJECT_BASE_URL,antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch(error=>{
            console.log('Request failed', error);
            return Promise.reject(error);
        });


}

