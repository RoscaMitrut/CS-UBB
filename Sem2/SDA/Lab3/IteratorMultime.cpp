#include <iostream>
#include "IteratorMultime.h"
#include "Multime.h"


IteratorMultime::IteratorMultime(const Multime& m) : multime(m) {
	this->prim();
}


void IteratorMultime::prim() {
	p = multime.primul;
}


void IteratorMultime::urmator() {
	if (!this->valid()){
		throw std::exception();
	}
	p = p->urmator;
}


TElem IteratorMultime::element() const {
	if (this->valid()) {
		return p->valoare;
	}
	throw std::exception();
}

bool IteratorMultime::valid() const {
	return p != NULL;
}
