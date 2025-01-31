#pragma once

#define NULL_TELEM -1
typedef int TElem;

class IteratorMultime;
struct Nod {
	TElem valoare;
	Nod* urmator;
};

class Multime {
	friend class IteratorMultime;

    private:
		/* aici e reprezentarea */
		Nod* primul;
		Nod* ultimul;
    public:
 		//constructorul implicit
		// complexitate : teta(1)
		Multime();

		//adauga un element in multime
		//returneaza adevarat daca elementul a fost adaugat (nu exista deja in multime)
		// CF: teta(1) - lista goala
		// CD: teta(n) - ultima pozitie
		// CM: teta(n) - oriunde
		// complexitate : O(n)
		bool adauga(TElem e);

		//sterge un element din multime
		//returneaza adevarat daca elementul a existat si a fost sters
		// CF: teta(1) - prima pozitie
		// CD: teta(n) - ultima pozitie
		// CM: teta(n) - oriunde
		// complexitate : O(n)
		bool sterge(TElem e);

		// CF: teta(1) - lista goala / prima poziite	
		// CD: teta(n) - ultima pozitie / nu exista
		// CM: teta(n)
		// complexitate : O(n)
		//verifica daca un element se afla in multime
		bool cauta(TElem elem) const;

		//intoarce numarul de elemente din multime;
		// complexitate : O(n)
		int dim() const;

		//verifica daca multimea e vida;
		// complexitate : O(n)
		bool vida() const;

		//returneaza un iterator pe multime
		// complexitate : teta(1)
		IteratorMultime iterator() const;

		// destructorul multimii
		// complexitate : teta(n)
		~Multime();
};




