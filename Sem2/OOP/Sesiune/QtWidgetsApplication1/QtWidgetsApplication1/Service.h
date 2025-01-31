#pragma once

#include "Repo.h"

class Service {
private:
    Repository& repository;
public:
    //Constructor
    explicit Service(Repository& repository) : repository(repository) {}

    //returneaza lista de melodii, sortat
    //return : lista respectiva
    vector<Melodie> getAll() {
        return sortare(repository.getAll());
    }

    //sorteaza o lista de melodii data ca parametru
    //returneaza lista, sortata
    vector<Melodie> sortare(vector<Melodie> melodii);

    //adauga la lista de melodii o melodie noua, si ii genereaza un id unic
    //input : titlul, artistul si genul melodiei respective
    string adaugare(const string& titlu, const string& artist, const string& gen);

    //sterge o melodie de la un anumit index din lista
    //input : indexul de la care se sterge
    void deleter(int index);
};

void test_service();

