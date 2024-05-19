#pragma once

#include <vector>

typedef int TCheie;
typedef int TValoare;

#include <utility>

typedef std::pair<TCheie, TValoare> TElem;

using namespace std;
#define NULL_TVALOARE -1
#define MAX 400 //numarul max de locatii din TD

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO {
    friend class IteratorMDO;

private:
    //reprezentare folosind o TD - rezolvare coliziuni: liste intrepatrunse
    int n;
    int m; //nr loc TD
    TElem elemente[MAX]; //vectorul elem (static)
    int urm[MAX]; //vectorul legaturilor
    int PrimPozLibera; // locatia Primei pozitii libere din tabela

    //actualizare PrimPozLibera
    void ActPrimPozLibera();

    //functie dispersie
    int d(TCheie c) const;

public:
    Relatie relatie;

    // constructorul implicit al MultiDictionarului Ordonat
    MDO(Relatie r);

    // adauga o pereche (cheie, valoare) in MDO
    void adauga(TCheie c, TValoare v);

    int adaugaInexistente(MDO& mdo);

    //cauta o cheie si returneaza vectorul de valori asociate
    vector<TValoare> cauta(TCheie c) const;

    //sterge o cheie si o valoare
    //returneaza adevarat daca s-a gasit cheia si valoarea de sters
    bool sterge(TCheie c, TValoare v);

    //returneaza numarul de perechi (cheie, valoare) din MDO
    int dim() const;

    //verifica daca MultiDictionarul Ordonat e vid
    bool vid() const;

    // se returneaza iterator pe MDO
    // iteratorul va returna perechile in ordine in raport cu relatia de ordine
    IteratorMDO iterator() const;

    // destructorul
    ~MDO();

};