#include "IteratorMDO.h"
#include "MDO.h"

// complexitate Theta(1)
IteratorMDO::IteratorMDO(const MDO& d) : dict(d){
	this->curent = dict.rad;
}

// complexitate O(n)
void IteratorMDO::prim(){
	this->curent = dict.rad;
	while (curent != nullptr)
	{
		s.push(curent);
		curent = curent->left;
	}
}

// complexitate O(n)
void IteratorMDO::urmator(){
	this->curent = s.top()->right;
	s.pop();
	while (curent != nullptr)
	{
		s.push(curent);
		curent = curent->left;
	}
}

// complexitate Theta(1)
bool IteratorMDO::valid() const{
	return (s.size()>0 || curent!=nullptr && dict.size>0);
}

// complexitate Theta(1)
TElem IteratorMDO::element() const{
	if (!this->valid())
		throw exception();
	else {
		PNod n = s.top();
		return n->elem;
	}
}

