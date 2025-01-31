#ifndef MYSORT_H_
#define MYSORT_H_
#include "lista.h"

/*
* tipul de functie de comparare pentru doua elem
* param c1: primul elem de comparat (Cheltuiala)
* param c2: al doilea elem de comparat (Cheltuiala)
* returneaza 1 daca c1>c2, 0 daca c1=c2, -1 daca c1<c2
*/
typedef int(*functie_comparare)(Cheltuiala* c1, Cheltuiala* c2);

/*
* sorteazalista cu bubble sort
* param v: lista de sortat (Lista)
* param cmpf: relatie de sortare
*/
void sort(Lista* v, functie_comparare cmpf);
#endif