#include "cheltuiala.h"

#include <stdlib.h>
#include <string.h>
#include <assert.h>


Cheltuiala createCheltuiala(int numar, int suma, char* tip) {
	Cheltuiala rez;
	rez.numar = numar;
	rez.suma = suma;

	int nrC = (int)strlen(tip) + 1;
	rez.tip = malloc(nrC * sizeof(char));
	strcpy_s(rez.tip,nrC, tip);

	return rez;
}//

Cheltuiala copyCheltuiala(Cheltuiala* c) {
	return createCheltuiala(c->numar, c->suma, c->tip);
}

void destroyCheltuiala(Cheltuiala* c) {
	free(c->tip);
	c->numar = -1;
	c->suma = -1;
	c->tip = NULL;
}//

int valideaza(Cheltuiala c) {
	if (c.numar < 0) {
		return 1;
	}
	if (c.suma < 0) {
		return 2;
	}
	if (strlen(c.tip) == 0) {
		return 3;
	}
	return 0;
}//

void testCreateDestroyValidate() {
	Cheltuiala c = createCheltuiala(1, 150, "incalzire");
	assert(c.numar == 1);
	assert(c.suma == 150);
	assert(strcmp(c.tip, "incalzire") == 0);
	assert(valideaza(c) == 0);

	Cheltuiala rea1 = createCheltuiala(-1, 150, "gaz");
	assert(valideaza(rea1) == 1);
	Cheltuiala rea2 = createCheltuiala(7, -13, "gaz");
	assert(valideaza(rea2) == 2);
	Cheltuiala rea3 = createCheltuiala(7, 150, "");
	assert(valideaza(rea3) == 3);

	destroyCheltuiala(&c);
	destroyCheltuiala(&rea1);
	destroyCheltuiala(&rea2);
	destroyCheltuiala(&rea3);
	
	assert(c.numar == -1);
	assert(c.suma == -1);
	assert(c.tip == NULL);
	assert(valideaza(c) != 0);
	
}