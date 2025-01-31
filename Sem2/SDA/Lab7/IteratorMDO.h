#pragma once

#include "MDO.h"
#include <queue>

class Stack {
private:
	PNod* elems;
	unsigned int capacity;
	unsigned int _size;

	void resize() {
		PNod* new_elems = new PNod[capacity * 2];
		for (unsigned int i = 0; i < capacity; i++)
			new_elems[i] = elems[i];
		delete[] elems;
		elems = new_elems;
		capacity *= 2;
	}
public:
	Stack() {
		_size = 0;
		capacity = 2;
		elems = new PNod[capacity];
	}

	~Stack() {
		delete[] elems;
	}

	void push(PNod val) {
		elems[_size++] = val;
		if (_size == capacity)
			resize();
	}

	PNod pop() {
		return elems[--_size];
	}

	PNod top() const {
		return elems[_size - 1];
	}

	unsigned int size() const {
		return _size;
	}
};


class IteratorMDO {
	friend class MDO;
private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	IteratorMDO(const MDO& dictionar);

	//contine o referinta catre containerul pe care il itereaza
	const MDO& dict;
	/* aici e reprezentarea  specifica a iteratorului */
	PNod curent;
	Stack s;

public:

	//reseteaza pozitia iteratorului la inceputul containerului
	void prim();

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	void urmator();

	//verifica daca iteratorul e valid (indica un element al containerului)
	bool valid() const;

	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	TElem element() const;
};
