#pragma once

#define INITIAL_CAPACITY 10
#define NULL_TVALOARE -1

#include <vector>
#include <utility>

typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO {
public:
	friend class IteratorMDO;

private:
	// reprezentare:

	int cp;

	TElem* elems;

	int* urm;

	int prim;

	int primLiber;

	Relatie rel;

	// metode private, valabile doar in implementare
	// definesc operatii auxiliare care implementeaza comportamentul
	// listei inlantuite cu legaturile prin pointeri in reprezentarea inlantuirilor pe tablou
			
	// complexitate : teta(1)
	int aloca();

	// complexitate : teta(1)
	void dealoca(int i);

	// complexitate : teta(n)
	void redim();

	// complexitate : O(n)   posibil sa fie nevoie de redimensionare
	int creeazaNod(TElem e);

public:

	// constructorul implicit al MDO
	// complexitate : teta(1)
	MDO(Relatie r);

	// adauga o pereche (cheie, valoare) in MDO
	// complexitate : O(n)
	void adauga(TCheie c, TValoare v);

	// cauta o cheie si returneaza vectorul de valori asociate
	// complexitate : O(n)
	vector<TValoare> cauta(TCheie c) const;

	// sterge o cheie si o valoare 
	// returneaza adevarat daca s-a gasit cheia si valoarea de sters
	// complexitate : O(n)
	bool sterge(TCheie c, TValoare v);

	// returneaza numarul de perechi (cheie, valoare) din MDO
	// complexitate : teta(n)
	int dim() const;

	// verifica daca MultiDictionarul Ordonat e vid
	// complexitate : teta(1) ( sau teta(n) daca folosim metoda dim )
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	// complexitate : teta(1)
	IteratorMDO iterator() const;


	// destructorul
	// complexitate : teta(1)
	~MDO();


	// complexitate : O(n^2)
	TValoare ceaMaiFrecventaValoare() const;
};
