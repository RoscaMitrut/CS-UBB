#pragma once

typedef int TElem;

typedef bool(*Relatie)(TElem, TElem);

//in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool rel(TElem, TElem);

class IteratorMultime;

class Multime {

	friend class IteratorMultime;

private:
	int capacitate;
	int lungime;
	TElem* elems;


public:
		//constructorul implicit
		//
		//Teta(1)
		Multime();

		//adauga un element in multime
		//returneaza adevarat daca elementul a fost adaugat (nu exista deja in multime)
		//
		//CF Teta(1): elem trebuie adaugat intr-o multime goala + nu e nevoie de redimensionare
		//CD Teta(n): elem trebuie adugat pe ultima pozitie din multime + e nevoie de redimensionare
		//CM Teta(n)
		//CT O(n)
		bool adauga(TElem e);

		//sterge un element din multime
		//returneaza adevarat daca elementul a existat si a fost sters
		//
		//CF Teta(1): elem care trebuie sters se afla pe prima pozitie din multime
		//CD Teta(n): elem care trebuie sters nu se afla in multime
		//CM Teta(n)
		//CT O(n)
		bool sterge(TElem e);

		//verifica daca un element se afla in multime
		//
		//CF Teta(1) multimea e goala
		//CD Teta(n) elem cautat nu se afla in multime
		//CM Teta(n)
		//CT O(n)
		bool cauta(TElem elem) const;

		//intoarce elementul aflat pe pozita i
		//Teta(1) CF=CD=CM=CT
		TElem element(int i) const;

		// păstrează doar elementele care și în mulțimea b
		//
		//CF Teta(1) Multimea A este goala
		//CD=CM Teta(n^2) avem numar fix de pasi 
		//CT O(n^2)
		void intersectie(const Multime& b);

		//intoarce numarul de elemente din multime;
		// 
		//Teta(1) CF=CD=CM=CT
		int dim() const;

		//verifica daca multimea e vida;
		// 
		//Teta(1) CF=CD=CM=CT
		bool vida() const;

		//returneaza un iterator pe multime
		//
		// Teta(1)
		IteratorMultime iterator() const;

		// destructorul multimii
		//
		// Teta(1)
		~Multime();

};

