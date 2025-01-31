#pragma once

#include <QDebug>
#include <utility>
#include "Service.h"
#include "algorithm"
#include "functional"
#include "exception"

vector<Melodie> Service::sortare(vector<Melodie> melodii) {
    sort(melodii.begin(), melodii.end(), [this](Melodie& p1, Melodie& p2) {
        return p1.get_artist() < p2.get_artist();
        });
    return melodii;
}

string Service::adaugare(const string& titlu, const string& artist, const string& gen) {
    string exceptii;
    if (titlu.empty())
        exceptii.append("Titlul este invalid!  ");
    if (artist.empty())
        exceptii.append("Numele este invalid!  ");
    if (gen.empty())
        exceptii.append("Genul este invalid!  ");

    int id_nou=0;
    bool neluat = false;

    while(neluat==false){
        for (auto& p : getAll()) {
            if (p.get_id() == id_nou) {
                id_nou++;
            }
            else {
                neluat = true;
            }
        }
    }

    if (!exceptii.empty())
        return exceptii;
    else {
        repository.adaugare(Melodie(id_nou, titlu, artist, gen));
    }
    return "";

}

void Service::deleter(int index) {
    repository.stergere(index);
}

void test_service() {
    
    Repository repo_teste{ "input_teste.txt" };
    Service serv_teste{ repo_teste };

    assert(serv_teste.adaugare("","","")!="");

    assert(serv_teste.adaugare("Primavara", "Ana", "pop")=="");
    assert(repo_teste.getAll().size() == 1);

    assert(serv_teste.adaugare("Toamna", "Vasile", "disco")=="");
    assert(repo_teste.getAll().size() == 2);

    serv_teste.deleter(1);
    assert(repo_teste.getAll().size() == 1);
    
    
}
