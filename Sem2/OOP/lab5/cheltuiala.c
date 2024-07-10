#pragma once
#include "cheltuiala.h"
#include <string.h>
#include <assert.h>
#include <stdlib.h>

Cheltuiala* creeaza_cheltuiala(int zi, int suma, char* tip) {
	Cheltuiala* c = malloc(sizeof(Cheltuiala));
	c->zi = zi;
	c->suma = suma;
	int nr_chars = (int)strlen(tip) + 1;
	c->tip = malloc(nr_chars * sizeof(char));
	strcpy_s(c->tip, nr_chars, tip);
	return c;
}

void distruge_cheltuiala(Cheltuiala* c) {
	free(c->tip);
	free(c);
}

int valideaza_cheltuiala(Cheltuiala* c) {
	if (c->zi < 1 || c->zi>31) return 0;
	if (c->suma < 1) return 0;
	if (strcmp(c->tip, "mancare") == 0) return 1;
	if (strcmp(c->tip, "transport") == 0) return 1;
	if (strcmp(c->tip, "telefon&internet") == 0) return 1;
	if (strcmp(c->tip, "imbracaminte") == 0) return 1;
	if (strcmp(c->tip, "altele") == 0) return 1;
	return 0;
}

Cheltuiala* copiaza_cheltuiala(Cheltuiala* c) {
	return creeaza_cheltuiala(c->zi, c->suma, c->tip);
}

void test_creeaza_distruge_cheltuiala() {
	Cheltuiala* c = creeaza_cheltuiala(4, 150, "imbracaminte");
	assert(c->zi == 4);
	assert(c->suma == 150);
	assert(strcmp(c->tip, "imbracaminte") == 0);
	distruge_cheltuiala(c);
}

void test_valideaza() {
	Cheltuiala* c1 = creeaza_cheltuiala(5, 200, "telefon&internet");
	int succes1 = valideaza_cheltuiala(c1);
	assert(succes1 == 1);

	Cheltuiala* c2 = creeaza_cheltuiala(-5, 200, "telefon&internet");
	int succes2 = valideaza_cheltuiala(c2);
	assert(succes2 == 0);

	Cheltuiala* c3 = creeaza_cheltuiala(5, -200, "telefon&internet");
	int succes3 = valideaza_cheltuiala(c3);
	assert(succes3 == 0);

	Cheltuiala* c4 = creeaza_cheltuiala(5, 200, "Telefon&internet");
	int succes4 = valideaza_cheltuiala(c4);
	assert(succes4 == 0);

	distruge_cheltuiala(c1);
	distruge_cheltuiala(c2);
	distruge_cheltuiala(c3);
	distruge_cheltuiala(c4);
}

void test_copiaza() {
	Cheltuiala* c = creeaza_cheltuiala(9, 500, "mancare");
	Cheltuiala* copie = copiaza_cheltuiala(c);
	assert(copie->zi == 9);
	assert(copie->suma == 500);
	assert(strcmp(copie->tip, "mancare") == 0);
	distruge_cheltuiala(c);
	distruge_cheltuiala(copie);
}
