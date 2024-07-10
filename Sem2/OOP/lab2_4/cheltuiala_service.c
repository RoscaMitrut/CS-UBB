#include <stdlib.h>
#include "cheltuiala_service.h"
#include <string.h>
#include <assert.h>
#include <stdio.h>

int comparare(Cheltuiala c1, Cheltuiala c2) {
	if (c1.numar > c2.numar) {
		return 1;
	}
	else {
		return 0;
	}
}

int addCheltuiala(MyList* l, int numar, int suma, char* tip) {
	Cheltuiala c = createCheltuiala(numar, suma, tip);
	int succes = valideaza(c);

	if (succes != 0) {
		destroyCheltuiala(&c);
		return succes;
	}
	add(l, c);
	return 0;
}

int findCheltuiala(MyList* l, int suma, char* tip) {
	int i = 0;
	Cheltuiala c;
	while (i < size(l)) {
		c = get(l, i);
		if ((strcmp(c.tip, tip) == 0) && (c.suma == suma)) return i;
		i++;
	}
	return -1;
}

int findCheltuialaByNumber(MyList* l, int numar) {
	int i = 0;
	Cheltuiala c;
	while (i < size(l)) {
		c = get(l, i);
		if (c.numar == numar) return i;
		i++;
	}
	return -1;
}

int modificaCheltuiala(MyList* l,int nr,int sum,char* tip) {
	Cheltuiala c = createCheltuiala(nr, sum, tip);
	int validare = valideaza(c);
	if (validare==0) {
		int index = findCheltuialaByNumber(l,nr);
		if (index != -1) {
			c = setElem(l, index, c);
			destroyCheltuiala(&c);
			return 0;
		}
	}
	destroyCheltuiala(&c);
	return 1;
}

int deleteCheltuiala(MyList* l,int nr) {
	int index = findCheltuialaByNumber(l,nr);
	Cheltuiala c;
	if (index != -1) {
		c = delete(l, index);
		destroyCheltuiala(&c);
		return 0;
	}
	return 1;
}

int sortare(MyList* l,MyList* lista_sortata,int ce_sortam,int tip_sortare) {
	if (ce_sortam > 2 || ce_sortam < 1 || tip_sortare<1 || tip_sortare > 2) return 1;
	Cheltuiala c1, c2;
	*lista_sortata = copyList(l);
	int i, j;

	switch (ce_sortam){

	case(1)://suma
		switch(tip_sortare){
		case(1)://crescator
			for (i = 0; i < size(lista_sortata) - 1; i++)
				for (j = 0; j < size(lista_sortata) - 1; j++) {
					c1 = get(lista_sortata, j);
					c2 = get(lista_sortata, j + 1);
					if (c1.suma > c2.suma)
						swap(lista_sortata, j, j + 1);
				}
			break;

		case(2)://descrescator
			for (i = 0; i < size(lista_sortata) - 1; i++) 
				for (j = 0; j < size(lista_sortata) -1; j++) {
					c1 = get(lista_sortata, j);
					c2 = get(lista_sortata, j + 1);
					if (c1.suma < c2.suma) 
						swap(lista_sortata, j, j + 1);
				}
			break;
		}
		break;
	case(2)://tip
		switch (tip_sortare) {
		case(1)://crescator
			for (i = 0; i < size(lista_sortata) - 1; i++) 
				for (j = 0; j < size(lista_sortata)-1; j++) {
					c1 = get(lista_sortata, j);
					c2 = get(lista_sortata, j + 1);
					if (strcmp(c1.tip,c2.tip)>0)
						swap(lista_sortata, j, j + 1);
				}
			break;
		
		case(2)://descrescator
			for (i = 0; i < size(lista_sortata) - 1; i++) 
				for (j = 0; j < size(lista_sortata)-1; j++) {
					c1 = get(lista_sortata, j);
					c2 = get(lista_sortata, j + 1);
					if (strcmp(c1.tip, c2.tip) < 0)
						swap(lista_sortata, j, j + 1);
				}
			break;
		}
		break;
	}

	return 0;
}

int sortare2(MyList* l,MyList* lista_sortata, int tip_sortare, int (*f)(Cheltuiala,Cheltuiala)) {
	if (tip_sortare > 2 || tip_sortare < 1) return 1;
	*lista_sortata = copyList(l);
	Cheltuiala c1, c2;
	int i, j;
	switch (tip_sortare){
	case(1)://crescator
		for (i = 0; i < size(lista_sortata) - 1; i++)
			for (j = 0; j < size(lista_sortata) - 1; j++) {
				c1 = get(lista_sortata, j);
				c2 = get(lista_sortata, j + 1);
				if (f(c1,c2) ==1) {
					swap(lista_sortata, j, j + 1);
				}
			}
		break;
	case(2)://descrescator
		for (i = 0; i < size(lista_sortata) - 1; i++)
			for (j = 0; j < size(lista_sortata) - 1; j++) {
				c1 = get(lista_sortata, j);
				c2 = get(lista_sortata, j + 1);
				if (f(c1,c2) == 0) {
					swap(lista_sortata, j, j + 1);
				}
			}
		break;
	}
	return 0;
}

MyList filtrare_tip(MyList* l,char* substring){
	MyList rez = createEmpty();
	for (int i = 0;i < size(l);i++) {
		Cheltuiala c = get(l, i);
			if (strstr(c.tip, substring) != NULL) {
				add(&rez, copyCheltuiala(&c));
			}
	}
	return rez;
}

MyList filtrare_suma(MyList* l,int tip_filtrare, int numar) {
	MyList rez = createEmpty();
	switch (tip_filtrare){
	case (1)://mai mare
		for (int i = 0;i < size(l);i++) {
			Cheltuiala c = get(l, i);
			if (c.suma>numar){
				add(&rez, copyCheltuiala(&c));
			}
		}
		break;
	case (2)://mai mic
		for (int i = 0;i < size(l);i++) {
			Cheltuiala c = get(l, i);
			if (c.suma < numar) {
				add(&rez, copyCheltuiala(&c));
			}
		}
		break;
	}
	return rez;
}

MyList filtrare_numar(MyList* l, int tip_filtrare, int numar) {
	MyList rez = createEmpty();
	switch (tip_filtrare) {
	case (1)://mai mare
		for (int i = 0;i < size(l);i++) {
			Cheltuiala c = get(l, i);
			if (c.numar > numar) {
				add(&rez, copyCheltuiala(&c));
			}
		}
		break;
	case (2)://mai mic
		for (int i = 0;i < size(l);i++) {
			Cheltuiala c = get(l, i);
			if (c.numar < numar) {
				add(&rez, copyCheltuiala(&c));
			}
		}
		break;
	}
	return rez;
}

void test_service_full() {
	MyList l = createEmpty();
	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));
	add(&l, createCheltuiala(3, 323, "apa"));
	add(&l, createCheltuiala(13, 49, "canal"));
	add(&l, createCheltuiala(7, 492, "apa"));

	assert(addCheltuiala(&l, 6, 200, "canal") == 0);
	assert(addCheltuiala(&l, -1, 50, "apa") != 0);

	assert(findCheltuiala(&l, 250, "gaze") == 1);
	assert(findCheltuiala(&l, 400, "apa") == -1);

	assert(findCheltuialaByNumber(&l, 13) == 3);
	assert(findCheltuialaByNumber(&l, 20) == -1);

	assert(modificaCheltuiala(&l, -1, 200, "apa") != 0);
	assert(modificaCheltuiala(&l, 1, 1500, "incalzire") == 0);

	assert(deleteCheltuiala(&l, 7) == 0);
	assert(deleteCheltuiala(&l, 69) != 0);

	destroy(&l);
}

void testfiltrari() {
	
	MyList l = createEmpty();
	MyList filtrata = createEmpty();

	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));
	add(&l, createCheltuiala(3, 323, "apa"));
	add(&l, createCheltuiala(13, 49, "canal"));
	add(&l, createCheltuiala(7, 492, "apa"));

	destroy(&filtrata);
	filtrata = filtrare_tip(&l, "bla");
	assert(filtrata.lg == 0);
	
	destroy(&filtrata);
	filtrata = filtrare_tip(&l, "apa");
	assert(filtrata.lg == 2);

	destroy(&filtrata);
	filtrata = filtrare_numar(&l, 1, 5);
	assert(filtrata.lg == 2);
	
	destroy(&filtrata);
	filtrata = filtrare_numar(&l,  2, 5);
	assert(filtrata.lg == 3);

	destroy(&filtrata);
	filtrata = filtrare_numar(&l,  3, 5);
	assert(filtrata.lg == 0);
	
	destroy(&filtrata);
	filtrata = filtrare_suma(&l, 1, 100);
	assert(filtrata.lg == 4);
	
	destroy(&filtrata);
	filtrata = filtrare_suma(&l,  2, 100);
	assert(filtrata.lg == 1);
	
	destroy(&filtrata);
	filtrata = filtrare_suma(&l, 3, 100);
	assert(filtrata.lg == 0);
	
	destroy(&filtrata);
	destroy(&l);
		
}

void testsortare() {
	MyList l = createEmpty();
	MyList sortata = createEmpty();
	add(&l, createCheltuiala(1, 150, "incalzire"));
	add(&l, createCheltuiala(2, 250, "gaze"));
	add(&l, createCheltuiala(3, 323, "apa"));
	add(&l, createCheltuiala(13, 49, "canal"));
	add(&l, createCheltuiala(7, 492, "apa"));
	assert(sortare(&l, &sortata, 5, 1) == 1);
	destroy(&sortata);
	assert(sortare(&l, &sortata, 1, 1) == 0);
	destroy(&sortata);
	assert(sortare(&l, &sortata, 1, 2) == 0);
	destroy(&sortata);
	assert(sortare(&l, &sortata, 2, 1) == 0);
	destroy(&sortata);
	assert(sortare(&l, &sortata, 2, 2) == 0);
	destroy(&sortata);
	assert(sortare2(&l, &sortata, 1, comparare) == 0);
	destroy(&sortata);
	assert(sortare2(&l, &sortata, 2, comparare) == 0);
	destroy(&sortata);

	destroy(&l);

}

