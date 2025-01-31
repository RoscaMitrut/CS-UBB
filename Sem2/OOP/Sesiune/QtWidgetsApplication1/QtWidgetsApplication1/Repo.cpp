#pragma once
#include "Repo.h"
#include <fstream>
#include <sstream>
#include "Domain.h"
using std::ofstream;
using namespace std;

void Repository::loadFromFile() {
    std::ifstream in(fileName);
    while (!in.eof()) {
        int id;
        std::string titlu, artist,gen;
        if (in.eof())break;
        in >> id;
        if (in.eof())break;
        in >> titlu;
        if (in.eof())break;
        in >> artist;
        if (in.eof())break;
        in >> gen;
        Melodie l{ id,titlu,artist,gen };
        Repository::adaugare(l);
    }
    in.close();
}

void Repository::saveToFile(vector<Melodie> melodii) {
    ofstream fout(fileName);
    for (auto& m : melodii) {
        fout << m.get_id();
        fout << std::endl;
        fout << m.get_titlu();
        fout << std::endl;
        fout << m.get_artist();
        fout << std::endl;
        fout << m.get_gen();
        fout << std::endl;
    }
    fout.close();
}

void Repository::adaugare(Melodie m) {
    all.push_back(m);
    saveToFile(all);
}

void Repository::stergere(int index) {

    all.erase(all.begin() + index);
    saveToFile(all);
}

void test_repo() {
    Repository repo_test{ "test.txt" };
    Melodie test(1, "Primavara", "Ana", "rock");
    Melodie test2(2, "Toaman", "Vasile", "pop");
    Melodie test3(3, "Iarna", "Ion", "disco");
    repo_test.adaugare(test);
    repo_test.adaugare(test2);
    repo_test.adaugare(test3);
    repo_test.stergere(1);
}