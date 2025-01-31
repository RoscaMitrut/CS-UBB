#pragma once
#include "service.h"
#include <string.h>
#include <assert.h>
#include "sortari.h"

ListaCompleta createListaCompleta() {
	ListaCompleta rez;
	rez.toateCheltuielile = creeaza_lista_goala(distruge_cheltuiala);
	rez.listaUndo = creeaza_lista_goala(distruge_lista);
	return rez;
}

void destroyListaCompleta(ListaCompleta* v) {
	distruge_lista(v->toateCheltuielile);
	distruge_lista(v->listaUndo);
}

int adauga_cheltuiala(ListaCompleta* v, int zi, int suma, char* tip) {
	Cheltuiala* c = creeaza_cheltuiala(zi, suma, tip);
	int succes = valideaza_cheltuiala(c);
	if (succes) {
		int poz = cauta_cheltuiala(v->toateCheltuielile, zi, tip);
		if (poz == -1) {
			Lista* toUndo = copiaza_lista(v->toateCheltuielile, copiaza_cheltuiala);
			adauga_elem(v->toateCheltuielile, c);
			adauga_elem(v->listaUndo, toUndo);
		}
		else {
			distruge_cheltuiala(c);
			Cheltuiala* c1 = get(v->toateCheltuielile, poz);
			int suma_noua = suma + c1->suma;
			modifica_cheltuiala(v, zi, suma_noua, tip);
		}
		return 1;}
	else {
		distruge_cheltuiala(c);
		return 0;
	}
}

int undo(ListaCompleta* v) {
	if (size(v->listaUndo) == 0) {
		return 1;
	}
	Lista* l = removeLast(v->listaUndo);
	distruge_lista(v->toateCheltuielile);
	v->toateCheltuielile = l;
	return 0;
}

int cauta_cheltuiala(Lista* v, int zi, char* tip) {
	int poz = -1;
	for (int i = 0; i < v->lungime; i++) {
		Cheltuiala* c = get(v, i);
		if (c->zi == zi && strcmp(c->tip, tip) == 0) {
			poz = i;
			break;
		}
	}
	return poz;
}

int sterge_cheltuiala(ListaCompleta* v, int zi, char* tip) {
	int poz = cauta_cheltuiala(v->toateCheltuielile, zi, tip);
	if (poz != -1) {
		Lista* toUndo = copiaza_lista(v->toateCheltuielile, copiaza_cheltuiala);
		adauga_elem(v->listaUndo, toUndo);
		Cheltuiala* c = sterge_elem(v->toateCheltuielile, poz);
		distruge_cheltuiala(c);
		return 1;
	}
	return 0;
}

int modifica_cheltuiala(ListaCompleta* v, int zi, int suma_noua, char* tip) {
	int poz = cauta_cheltuiala(v->toateCheltuielile, zi, tip);
	if (poz != -1) {
		Lista* toUndo = copiaza_lista(v->toateCheltuielile, copiaza_cheltuiala);
		adauga_elem(v->listaUndo, toUndo);

		Cheltuiala* c_noua = creeaza_cheltuiala(zi, suma_noua, tip);
		Cheltuiala* c = set(v->toateCheltuielile, poz, c_noua);

		
		distruge_cheltuiala(c);
		return 1;
	}
	return 0;
}

Lista* filtreaza_suma_max(ListaCompleta* v, int suma_max) {
	Lista* lista_filtrata = creeaza_lista_goala(distruge_cheltuiala);
	for (int i = 0;i < size(v->toateCheltuielile);i++) {
		Cheltuiala* c = get(v->toateCheltuielile, i);
		if (c->suma < suma_max) {
			adauga_elem(lista_filtrata, copiaza_cheltuiala(c));
		}
	}
	return lista_filtrata;
}

int filtraza_suma_zi(ListaCompleta* v, int ziua) {
	int suma_zi = 0;
	for (int i = 0;i < size(v->toateCheltuielile);i++) {
		Cheltuiala* c = get(v->toateCheltuielile, i);
		if (c->zi == ziua) {
			suma_zi += c->suma;
		}
	}
	return suma_zi;
}



Lista* filtreaza_cheltuieli(ListaCompleta* v, int optiune, int zi_sau_suma, char* tip) {
	if (optiune == 1 && zi_sau_suma != -1) {
		Lista* lista_filtrata = creeaza_lista_goala(distruge_cheltuiala);
		for (int i = 0; i < size(v->toateCheltuielile); i++) {
			Cheltuiala* c = get(v->toateCheltuielile, i);
			if (c->zi == zi_sau_suma) {
				adauga_elem(lista_filtrata, copiaza_cheltuiala(c));
			}
		}
		return lista_filtrata;
	}
	if (optiune == 2 && zi_sau_suma != -1) {
		Lista* lista_filtrata = creeaza_lista_goala(distruge_cheltuiala);
		for (int i = 0; i < size(v->toateCheltuielile); i++) {
			Cheltuiala* c = get(v->toateCheltuielile, i);
			if (c->suma == zi_sau_suma) {
				adauga_elem(lista_filtrata, copiaza_cheltuiala(c));
			}
		}
		return lista_filtrata;
	}
	if (optiune == 3 && strcmp(tip, "") != 0) {
		Lista* lista_filtrata = creeaza_lista_goala(distruge_cheltuiala);
		for (int i = 0; i < size(v->toateCheltuielile); i++) {
			Cheltuiala* c = get(v->toateCheltuielile, i);
			if (strcmp(c->tip, tip) == 0) {
				adauga_elem(lista_filtrata, copiaza_cheltuiala(c));
			}
		}
		return lista_filtrata;
	}
	return copiaza_lista(v->toateCheltuielile,copiaza_cheltuiala);
}

int cmp_suma(Cheltuiala* c1, Cheltuiala* c2) {
	return (c2->suma > c1->suma);
}

int cmp_tip(Cheltuiala* c1, Cheltuiala* c2) {
	return (strcmp(c2->tip, c1->tip));
}

Lista* sorteaza_cheltuieli(ListaCompleta* v, int optiune) {
	Lista* lista_sortata = copiaza_lista(v->toateCheltuielile,copiaza_cheltuiala);
	if (optiune == 1) {
		sort(lista_sortata, cmp_suma);
	}
	if (optiune == 2) {
		sort(lista_sortata, cmp_tip);
	}
	return lista_sortata;
}



void test_service_adauga_sterge() {
	ListaCompleta v = createListaCompleta();
	
	int succes1 = adauga_cheltuiala(&v, 13, 31, "altele");
	assert(succes1 == 1);
	int succes2 = adauga_cheltuiala(&v, 13, 31, "medicamente");
	assert(succes2 == 0);
	int succes3 = adauga_cheltuiala(&v, -13, 31, "altele");
	assert(succes3 == 0);
	int succes4 = adauga_cheltuiala(&v, 23, 341, "mancare");
	assert(succes4 == 1);

	assert(size(v.toateCheltuielile) == 2);

	Cheltuiala* c = get(v.toateCheltuielile, 0);
	assert(c->zi == 13);
	assert(c->suma == 31);
	assert(strcmp(c->tip, "altele") == 0);
	
	succes4 = adauga_cheltuiala(&v, 23, 9, "mancare");
	assert(succes4 == 1);
	assert(size(v.toateCheltuielile) == 2);
	
	succes1 = sterge_cheltuiala(&v, 13, "altele");
	assert(succes1 == 1);
	succes2 = sterge_cheltuiala(&v, 15, "altele");
	assert(succes2 == 0);
	assert(size(v.toateCheltuielile) == 1);

	c = get(v.toateCheltuielile, 0);
	assert(c->zi == 23);
	assert(c->suma == 350);
	assert(strcmp(c->tip, "mancare") == 0);

	destroyListaCompleta(&v);
}

void test_service_cauta() {
	ListaCompleta v = createListaCompleta();
	adauga_cheltuiala(&v, 7, 700, "transport");
	adauga_cheltuiala(&v, 9, 18, "mancare");
	adauga_cheltuiala(&v, 21, 78, "telefon&internet");
	int poz = cauta_cheltuiala(v.toateCheltuielile, 9, "mancare");
	assert(poz == 1);
	Cheltuiala* c = get(v.toateCheltuielile, 1);
	assert(c->zi == 9);
	assert(c->suma == 18);
	assert(strcmp(c->tip, "mancare")==0);
	poz = cauta_cheltuiala(v.toateCheltuielile, 18, "altele");
	assert(poz == -1);
	destroyListaCompleta(&v);
}

void test_service_modifica() {
	ListaCompleta v = createListaCompleta();
	adauga_cheltuiala(&v, 7, 700, "transport");
	adauga_cheltuiala(&v, 9, 18, "mancare");
	adauga_cheltuiala(&v, 21, 78, "telefon&internet");
	Cheltuiala* c = get(v.toateCheltuielile, 1);
	assert(c->zi == 9);
	assert(c->suma == 18);
	assert(strcmp(c->tip, "mancare")==0);
	int succes = modifica_cheltuiala(&v, 9, 318, "mancare");
	assert(succes == 1);
	c = get(v.toateCheltuielile, 1);
	assert(c->zi == 9);
	assert(c->suma == 318);
	assert(strcmp(c->tip, "mancare")==0);
	succes = modifica_cheltuiala(&v, 15, 100, "altele");
	assert(succes == 0);
	destroyListaCompleta(&v);
}

void test_service_filtreaza() {
	ListaCompleta v = createListaCompleta();
	adauga_cheltuiala(&v, 31, 78, "telefon&internet");
	adauga_cheltuiala(&v, 21, 700, "transport");
	adauga_cheltuiala(&v, 9, 78, "mancare");
	adauga_cheltuiala(&v, 21, 78, "telefon&internet");
	assert(size(v.toateCheltuielile) == 4);
	Lista* v1 = filtreaza_cheltuieli(&v, 1, 21, "");
	assert(size(v1) == 2);
	Lista* v2 = filtreaza_cheltuieli(&v, 2, 78, "");
	assert(size(v2) == 3);
	Lista* v3 = filtreaza_cheltuieli(&v, 3, -1, "mancare");
	assert(size(v3) == 1);
	Lista* v4 = filtreaza_cheltuieli(&v, 2, -1, "");
	assert(size(v4) == 4);
	destroyListaCompleta(&v);
	distruge_lista(v1);
	distruge_lista(v2);
	distruge_lista(v3);
	distruge_lista(v4);

}

void test_service_sorteaza() {
	ListaCompleta v = createListaCompleta();
	adauga_cheltuiala(&v, 7, 700, "transport");
	adauga_cheltuiala(&v, 9, 18, "mancare");
	adauga_cheltuiala(&v, 21, 78, "telefon&internet");
	Lista* v1 = sorteaza_cheltuieli(&v, 1);
	Cheltuiala* c;
	c = get(v1, 0);
	assert(c->suma == 18);
	c = get(v1, 1);
	assert(c->suma == 78);
	c = get(v1, 2);
	assert(c->suma == 700);
	Lista* v2 = sorteaza_cheltuieli(&v, 2);
	c = get(v2, 0);
	assert(strcmp(c->tip, "mancare") == 0);
	c = get(v2, 1);
	assert(strcmp(c->tip, "telefon&internet") == 0);
	c = get(v2, 2);
	assert(strcmp(c->tip, "transport") == 0);
	destroyListaCompleta(&v);
	distruge_lista(v1);
	distruge_lista(v2);
}

void test_undo() {
	ListaCompleta v = createListaCompleta();
	adauga_cheltuiala(&v, 7, 700, "transport");
	adauga_cheltuiala(&v, 9, 18, "mancare");
	adauga_cheltuiala(&v, 21, 78, "telefon&internet");
	undo(&v);
	undo(&v);
	assert(size(v.toateCheltuielile) == 1);
	destroyListaCompleta(&v);
}

void test_filtrare_suma_suma_max() {
	ListaCompleta v = createListaCompleta();
	adauga_cheltuiala(&v, 7, 700, "transport");
	adauga_cheltuiala(&v, 9, 18, "mancare");
	adauga_cheltuiala(&v, 7, 78, "telefon&internet");

	Lista* lista_filtrata = filtreaza_suma_max(&v, 80);
	
	assert(size(lista_filtrata) == 2);

	Cheltuiala* c;
	c = get(lista_filtrata, 1);
	assert(c->suma == 78);
	c = get(lista_filtrata, 0);
	assert(c->suma == 18);

	int suma_zi = filtraza_suma_zi(&v, 7);
	assert(suma_zi == 778);
	
	distruge_lista(lista_filtrata);
	destroyListaCompleta(&v);
}