#include "IteratorMDO.h"
#include "MDO.h"
#include <exception>

using std::exception;

IteratorMDO::IteratorMDO(const MDO& d) : dict(d){
	this->curent = d.prim;
}

void IteratorMDO::prim(){
	this->curent = this->dict.prim;
}

void IteratorMDO::urmator(){
	if (this->curent == -1) {
		throw exception("Iterator invalid!\n");
	}
	this->curent = this->dict.urm[curent];
}

bool IteratorMDO::valid() const{
	return(this->curent != -1);
}

TElem IteratorMDO::element() const{
	if (!valid()) {
		throw exception("Iterator invalid!\n");
	}
	return this->dict.elems[curent];
}


