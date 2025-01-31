#include <stdlib.h>
#include "MyList.h"
#include <assert.h>
#include <string.h>

MyList createEmpty() {
	MyList rez;
	rez.capacitate = 2;
	rez.lg = 0;
	rez.elems = malloc(sizeof(ElemType) * rez.capacitate);
	return rez;
}//

void destroy(MyList* l) {
	for (int i = 0;i < l->lg;i++) {
		destroyCheltuiala(&l->elems[i]);
	}
	free(l->elems);

	l->elems = NULL;
	l->lg = 0;
}//

ElemType get(MyList* l, int poz) {
	return l->elems[poz];
}//

ElemType setElem(MyList* l, int poz, ElemType el) {
	ElemType replacedElement = l->elems[poz];
	l->elems[poz] = el;
	return replacedElement;
}//

int size(MyList* l) {
	return l->lg;
}//

void add(MyList* l, ElemType el) {
	if (l->lg == l->capacitate) {
		int newCapacitate = l->capacitate * 2;
		ElemType* aux_elem = malloc(sizeof(ElemType) * newCapacitate);
		for (int i = 0;i < l->lg;i++) {
			aux_elem[i] = l->elems[i];
		}
		free(l->elems);
		l->elems = aux_elem;
		l->capacitate = newCapacitate;
	}
	l->elems[l->lg] = el;
	l->lg++;
}//

MyList copyList(MyList* l) {
	MyList rez = createEmpty();
	for (int i = 0;i < size(l);i++) {
		ElemType c = get(l, i);
		add(&rez, copyCheltuiala(&c));
	}
	return rez;
}//

ElemType delete(MyList* l, int poz) {
	ElemType el = l->elems[poz];
	for (int i = poz;i < l->lg - 1;i++) {
		l->elems[i] = l->elems[i + 1];
	}
	l->lg--;
	return el;
}//

void swap(MyList* l, int i, int j) {
	Cheltuiala c1, c2;
	c1 = get(l, i);
	c2 = setElem(l, j, c1);
	setElem(l, i, c2);
}//


void testCreateList() {
	MyList l = createEmpty();
	assert(size(&l) == 0);
	destroy(&l);
}

void testIterateList() {
	MyList l = createEmpty();
	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));
	assert(size(&l) == 2);
	Cheltuiala c = get(&l, 0);

	assert(strcmp(c.tip, "incalzire") == 0);
	c = get(&l, 1);
	assert(c.suma==250);
	destroy(&l);
	assert(size(&l) == 0);
}

void testCopyList() {
	MyList l = createEmpty();
	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));

	MyList l2 = copyList(&l);
	assert(size(&l2) == 2);
	Cheltuiala c = get(&l2, 0);
	assert(c.suma == 150);
	
	destroy(&l);
	destroy(&l2);
}

void testSetElem() {
	MyList l = createEmpty();
	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));
	Cheltuiala c = createCheltuiala(3, 323, "apa");
	c = setElem(&l, 0, c);
	destroyCheltuiala(&c);
	Cheltuiala noua = get(&l, 0);
	assert(noua.numar == 3);
	assert(noua.suma == 323);

	destroy(&l);
}

void testdelete() {
	MyList l = createEmpty();
	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));
	add(&l, createCheltuiala(3, 323, "apa"));
	Cheltuiala c1 = delete(&l, 1);
	destroyCheltuiala(&c1);
	Cheltuiala c = get(&l, 1);
	assert(c.numar == 3);
	assert(c.suma == 323);
	destroy(&l);
}

void testswap() {
	MyList l = createEmpty();
	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));
	add(&l, createCheltuiala(3, 323, "apa"));
	swap(&l, 0, 2);
	Cheltuiala c = get(&l, 2);
	assert(c.numar == 1);
	assert(c.suma == 150);
	destroy(&l);
}