#pragma once
#include "lista.h"
#include <assert.h>
#include <stdlib.h>
#include <string.h>

Lista* creeaza_lista_goala(DestroyFct f) {
	Lista* v = malloc(sizeof(Lista));
	v->lungime = 0;
	v->capacitate = 2;
	v->elems = malloc(v->capacitate * sizeof(ElemType));
	v->dfnc = f;
	return v;
}

void distruge_lista(Lista* v) {
	for (int i = 0; i < v->lungime; i++) {
		v->dfnc(v->elems[i]);
	} 
	free(v->elems);
	free(v);
}

ElemType get(Lista* v, int poz) {
	return v->elems[poz];
}

ElemType set(Lista* v, int poz, ElemType elem) {
	ElemType elem_inlocuit = v->elems[poz];
	v->elems[poz] = elem;
	return elem_inlocuit;
}

int size(Lista* v) {
	return v->lungime;
}

void adauga_elem(Lista* v, ElemType elem) {
	if (v->lungime == v->capacitate) {
		int capacitate_noua = 2 * v->capacitate;
		ElemType* aux_elem = malloc(capacitate_noua*sizeof(ElemType));
		for (int i = 0; i < v->lungime; i++) {
			aux_elem[i] = v->elems[i];
		}
		free(v->elems);
		v->elems = aux_elem;
		v->capacitate = capacitate_noua;
	}

	v->elems[v->lungime] = elem;
	v->lungime++;
}

ElemType removeLast(Lista* v) {
	ElemType rez = v->elems[v->lungime - 1];
	v->lungime -= 1;
	return rez;
}

Lista* copiaza_lista(Lista* v, CopyFct cf) {
	Lista* copie_lista = creeaza_lista_goala(v->dfnc);
	for (int i = 0; i < v->lungime; i++) {
		ElemType elem = get(v, i);
		adauga_elem(copie_lista, cf(elem));
	}
	return copie_lista;
}


ElemType sterge_elem(Lista* v, int poz) {
	ElemType elem = v->elems[poz];
	for (int i = poz; i < v->lungime-1; i++) {
		v->elems[i] = v->elems[i + 1];
	}
	v->lungime--;
	return elem;
}


void test_creeaza_distruge_lista() {
	Lista* v = creeaza_lista_goala(distruge_cheltuiala);
	assert(size(v) == 0);
	distruge_lista(v);
}

void test_adauga_sterge_elem() {
	Lista* v = creeaza_lista_goala(distruge_cheltuiala);
	
	Cheltuiala* c1 = creeaza_cheltuiala(12, 455, "mancare");
	Cheltuiala* c2 = creeaza_cheltuiala(1, 98, "transport");
	Cheltuiala* c3 = creeaza_cheltuiala(19, 74, "altele");

	adauga_elem(v, c1);
	adauga_elem(v, c2);
	adauga_elem(v, c3);
	assert(size(v) == 3);

	Cheltuiala* c = get(v, 1);
	assert(c->zi == 1);
	assert(c->suma == 98);
	assert(strcmp(c->tip, "transport") == 0);

	Cheltuiala* c4 = sterge_elem(v, 1);
	distruge_cheltuiala(c4);
	assert(size(v)==2);
	
	Cheltuiala* C = get(v, 1);
	assert(C->zi== 19);
	assert(C->suma == 74);
	assert(strcmp(C->tip, "altele") == 0);

	c4 = removeLast(v);
	assert(size(v) == 1);


	distruge_cheltuiala(c4);
	distruge_lista(v);
}

void test_copiaza_lista() {
	Lista* v = creeaza_lista_goala(distruge_cheltuiala);
	Cheltuiala* c1 = creeaza_cheltuiala(12, 455, "mancare");
	Cheltuiala* c2 = creeaza_cheltuiala(1, 98, "transport");
	adauga_elem(v, c1);
	adauga_elem(v, c2);
	Lista* copie = copiaza_lista(v,copiaza_cheltuiala);
	assert(size(copie) == 2);
	Cheltuiala* c = get(copie, 1);
	assert(c->zi == 1);
	assert(c->suma == 98);
	assert(strcmp(c->tip, "transport") == 0);
	distruge_lista(v);
	distruge_lista(copie);
}

void testListaDeListe() {
	Lista* v1 = creeaza_lista_goala(distruge_cheltuiala);
	adauga_elem(v1, creeaza_cheltuiala(12, 455, "mancare"));
	adauga_elem(v1, creeaza_cheltuiala(1, 98, "transport"));
	Lista* v2 = creeaza_lista_goala(distruge_cheltuiala);
	adauga_elem(v2, creeaza_cheltuiala(13, 1455, "transport"));
	adauga_elem(v2, creeaza_cheltuiala(19, 198, "transport"));

	Lista* UndoList = creeaza_lista_goala(distruge_lista);
	adauga_elem(UndoList, copiaza_lista(v1,copiaza_cheltuiala));
	assert(size(UndoList) == 1);
	adauga_elem(UndoList, copiaza_lista(v2,copiaza_cheltuiala));
	assert(size(UndoList) == 2);

	distruge_lista(v1);
	distruge_lista(v2);
	distruge_lista(UndoList);
}