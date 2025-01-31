#pragma once
template <typename ElementT>
class IteratorVector;

template <typename ElementT>
class VectorDinamic
{
public:
	//Constructor default
	//Alocam loc pentru 5 elemente
	VectorDinamic();

	//Constructor de copiere
	VectorDinamic(const VectorDinamic& obiect); //rule of 3

	//Eliberam memoria
	~VectorDinamic();//rule of 3

	/*
	Operator assignment
	elibereaza ce era in obiectul curent (this)
	aloca spatiu pentru elemente
	copieaza elementele din obiect in this
	*/
	VectorDinamic& operator=(const VectorDinamic& obiect);//rule of 3

	/*
	Move constructor
	Apelat daca construim un nou vector dintr-un r-value (ex temporary, expresie de la return)
	Obiectul obiect nu se mai foloseste astfel se poate refolosi interiorul lui
	*/
	VectorDinamic(VectorDinamic&& obiect); //rule of 5 

	/*
	Move assignment
	Similar cu move constructor
	Folosit la assignment
	*/
	VectorDinamic& operator=(VectorDinamic&& obiect); //rule of 5 


	void push_back(const ElementT& el);

	ElementT& get(int poz) const;

	void set(int poz, const ElementT& el);

	int size() const noexcept;

	void erase(int poz);

	friend class IteratorVector<ElementT>;
	//functii care creaza iteratori
	IteratorVector<ElementT> begin() const;
	IteratorVector<ElementT> end() const;


private:
	int lg;//numar elemente
	int cap;//capacitate
	ElementT* elems;//elemente

	void ensureCapacity();
};

/*
Constructor default
Alocam loc pentru 5 elemente
*/
template<typename ElementT>
VectorDinamic<ElementT>::VectorDinamic() :elems{ new ElementT[5] }, cap{ 5 }, lg{ 0 } {

}

/*
Constructor de copiere
Obiectul current (this) acum se creaza
aloca spatiu pentru elemente
copieaza elementele din obiect in this
*/
template<typename ElementT>
VectorDinamic<ElementT>::VectorDinamic(const VectorDinamic<ElementT>& obiect) {
	elems = new ElementT[obiect.cap];
	//copiez elementele
	for (int i = 0; i < obiect.lg; i++) {
		elems[i] = obiect.elems[i];  //assignment din Pet
	}
	lg = obiect.lg;
	cap = obiect.cap;
}

/*
Operator assgnment
elibereaza ce era in obiectul curent (this)
aloca spatiu pentru elemente
copieaza elementele din obiect in this
*/
template<typename ElementT>
VectorDinamic<ElementT>& VectorDinamic<ElementT>::operator=(const VectorDinamic<ElementT>& obiect) {
	if (this == &obiect) {
		return *this;//s-a facut l=l;
	}
	delete[] elems;
	elems = new ElementT[obiect.cap];
	//copiez elementele
	for (int i = 0; i < obiect.lg; i++) {
		elems[i] = obiect.elems[i];  //assignment din Pet
	}
	lg = obiect.lg;
	cap = obiect.cap;
	return *this;
}

/*
Eliberam memoria
*/
template<typename ElementT>
VectorDinamic<ElementT>::~VectorDinamic() {
	delete[] elems;
}


/*
Move constructor
Apelat daca construim un nou vector dintr-un r-value (ex temporary)
Obiectul obiect nu se mai foloseste astfel se poate refolosi interiorul lui
*/
template<typename ElementT>
VectorDinamic<ElementT>::VectorDinamic(VectorDinamic&& obiect) {
	// Copy the data pointer from other
	elems = obiect.elems;
	lg = obiect.lg;
	cap = obiect.cap;

	// Release the data pointer from the source object so that  
	// the destructor does not free the memory multiple times.  
	obiect.elems = nullptr;
	obiect.lg = 0;
	obiect.cap = 0;
}

/*
Move assignment
Similar cu move constructor
Folosit la assignment
Elibereaza ce continea obiectul curent (this)
"fura" interiorul lui obiect
pregateste obiect pentru distrugere
*/
template<typename ElementT>
VectorDinamic<ElementT>& VectorDinamic<ElementT>::operator=(VectorDinamic<ElementT>&& obiect) {
	if (this == &obiect) {
		return *this;
	}
	delete[] elems;
	// Copy the data pointer from other
	elems = obiect.elems;
	lg = obiect.lg;
	cap = obiect.cap;

	// Release the data pointer from the source object so that  
	// the destructor does not free the memory multiple times.  
	obiect.elems = nullptr;
	obiect.lg = 0;
	obiect.cap = 0;
	return *this;
}

template<typename ElementT>
void VectorDinamic<ElementT>::push_back(const ElementT& el) {
	ensureCapacity();//daca e nevoie mai alocam memorie
	elems[lg++] = el;
}

template <typename ElementT>
void VectorDinamic<ElementT>::erase(int pozitie)
{
	if (pozitie < 0 || pozitie >= lg) return;
	for (int i = pozitie; i < lg - 1; i++) {
		elems[i] = elems[i + 1];
	}
	lg--;
}

template<typename ElementT>
ElementT& VectorDinamic<ElementT>::get(int poz) const {
	return elems[poz];
}

template<typename ElementT>
void VectorDinamic<ElementT>::set(int poz, const ElementT& el) {
	elems[poz] = el;
}

template<typename ElementT>
int VectorDinamic<ElementT>::size() const noexcept {
	return lg;
}

template<typename ElementT>
void VectorDinamic<ElementT>::ensureCapacity() {
	if (lg < cap) {
		return; //mai avem loc
	}
	cap *= 2;
	ElementT* aux = new ElementT[cap];
	for (int i = 0; i < lg; i++) {
		aux[i] = elems[i];
	}
	delete[] elems;
	elems = aux;
}

template<typename ElementT>
IteratorVector<ElementT> VectorDinamic<ElementT>::begin() const
{
	return IteratorVector<ElementT>(*this);
}

template<typename ElementT>
IteratorVector<ElementT> VectorDinamic<ElementT>::end() const
{
	return IteratorVector<ElementT>(*this, lg);
}




template<typename ElementT>
class IteratorVector {
private:
	const VectorDinamic<ElementT>& v;
	int poz = 0;
public:
	IteratorVector(const VectorDinamic<ElementT>& v) noexcept;
	IteratorVector(const VectorDinamic<ElementT>& v, int poz)noexcept;
	bool valid()const;
	ElementT& element() const;
	void next();
	ElementT& operator*();
	IteratorVector& operator++();
	bool operator==(const IteratorVector& obiect)noexcept;
	bool operator!=(const IteratorVector& obiect)noexcept;
};

template<typename ElementT>
IteratorVector<ElementT>::IteratorVector(const VectorDinamic<ElementT>& v) noexcept :v{ v } {}

template<typename ElementT>
IteratorVector<ElementT>::IteratorVector(const VectorDinamic<ElementT>& v, int poz)noexcept : v{ v }, poz{ poz } {}

template<typename ElementT>
bool IteratorVector<ElementT>::valid()const {
	return poz < v.lg;
}

template<typename ElementT>
ElementT& IteratorVector<ElementT>::element() const {
	return v.elems[poz];
}

template<typename ElementT>
void IteratorVector<ElementT>::next() {
	poz++;
}

template<typename ElementT>
ElementT& IteratorVector<ElementT>::operator*() {
	return element();
}

template<typename ElementT>
IteratorVector<ElementT>& IteratorVector<ElementT>::operator++() {
	next();
	return *this;
}

template<typename ElementT>
bool IteratorVector<ElementT>::operator==(const IteratorVector<ElementT>& obiect) noexcept {
	return poz == obiect.poz;
}

template<typename ElementT>
bool IteratorVector<ElementT>::operator!=(const IteratorVector<ElementT>& obiect)noexcept {
	return !(*this == obiect);
}

