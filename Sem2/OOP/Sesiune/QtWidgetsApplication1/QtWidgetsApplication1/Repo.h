#pragma once

#include "Domain.h"
#include "vector"
#include <string>
using std::string;
using std::vector;

class Repository {
private:
    vector<Melodie> all;
    string fileName;
    void loadFromFile();
public:
    Repository(const Repository& ot) = delete;

    //Constructor
    explicit Repository(string fileName) : fileName(fileName) {
        loadFromFile();
    }

    //returneaza lista de melodii
    //return: lista respectiva
    vector<Melodie> getAll() {
        return all;
    }

    //salveaza lista de melodii in fisier
    //input : lista pe care dorim sa o salvam
    void saveToFile(vector<Melodie> produse);

    //adauga o melodie in lista de melodii
    //input : melodia pe care dorim sa o adaugam
    void adaugare(Melodie p);

    //sterge din lista melodia care se afla la un anumit index
    //input : indexul de la care dorim sa stergem
    void stergere(int index);
};

void test_repo();