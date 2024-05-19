#pragma once

#include <vector>

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class Nod;
typedef Nod* PNod;

class Nod {
private:
	TElem elem;
	PNod left, right;
public:
	friend class MDO;
	friend class IteratorMDO;
	Nod(TElem, PNod, PNod);
	TElem element();
	PNod stanga();
	PNod dreapta();
};

typedef void (*PFunctie)(TElem);


class MDO {
	friend class IteratorMDO;
    private:
		PNod rad;
		PNod adauga_rec(PNod p, TElem e);
		void cauta_rec(PNod, TCheie, vector<TValoare>&) const;
		void sterge_rec(PNod, TElem, PNod);
		void distrug_rec(PNod);
		pair<PNod, PNod> minim_drept(PNod, PNod);
		void det_recursiv(PNod p, vector<TValoare>& v) const;
		int size;
		Relatie r;
    public:

	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	//vector<TValoare> colectiaValorilor() const;

	int adaugaInexistente(MDO& mdo);

	// adauga o pereche (cheie, valoare) in MDO
	void adauga(TCheie c, TValoare v);

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
