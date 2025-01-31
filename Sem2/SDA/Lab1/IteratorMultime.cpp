#include "IteratorMultime.h"
#include "Multime.h"


IteratorMultime::IteratorMultime(const Multime& m) : mult(m) {
	pozitie = 0;
}

TElem IteratorMultime::element() const {
	return mult.element(pozitie);
}

bool IteratorMultime::valid() const {
	return pozitie < mult.dim() && pozitie >= 0;
}

void IteratorMultime::urmator() {
	pozitie++;
}

void IteratorMultime::prim() {
	pozitie = 0;
}

