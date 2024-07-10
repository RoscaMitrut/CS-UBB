#pragma once
#include "cheltuiala.h"

typedef void* ElemType;

typedef void (*DestroyFct)(ElemType);

typedef ElemType(*CopyFct)(ElemType);

typedef struct {
	ElemType* elems;
	int lungime;
	int capacitate;
	DestroyFct dfnc;
}Lista;

/*
* creeaza o lista vida (de lungime 0)
* returns: lista vida (Lista)
*/
Lista* creeaza_lista_goala(DestroyFct f);


/*
* distruge lista, transformand-o intr-o lista de lungime 0
*
* param v: lista care se sterge (Lista)
*/
void distruge_lista(Lista* v);

/*
* returneaza elementul de pe pozitia data in lista
*
* param v: lista in care se cauta elementul (Lista)
* param poz: pozitia elementului cautat (int)
*
* returns: elementul de pe pozitia data (ElemType)
*/
ElemType get(Lista* v, int poz);

/*
* inlocuieste elementul de pe pozitia data in lista cu alt element
*
* param v: lista in care se cauta elementul (Lista)
* param poz: pozitia elementului de inlocuit (int)
* param elem: elementul cu care se inlocuieste cel existent (ElemType)
*
* returns: elementul care a fost inlocuit (ElemType)
*/
ElemType set(Lista* v, int poz, ElemType elem);

/*
* calculeaza lungimea listei date
*
* param v: lista pt care se calculeaza lungimea (Lista)
*
* returns: lungimea listei (int)
*/
int size(Lista* v);





/*
* adauga un element in lista si creste lungimea listei
*
* param v: lista in care se adauga elementul (v)
* param elem: elementul de adaugat (ElemType)
*/
void adauga_elem(Lista* v, ElemType elem);

/*
sterge elementul de pe ultima pozitie din lista si scade lungimea
param v: lista din care se sterge elementul
return: elementul sters de pe ultima pozitie
*/
ElemType removeLast(Lista* v);

/*
* sterge elementul de pe pozitia data in lista si scade lungimea listei
*
* param v: lista din care se sterge elementul (Lista)
* param poz: pozitia elementului de sters
*
* returns: elementul sters de pe pozitia data (ElemType)
*/
ElemType sterge_elem(Lista* v, int poz);

/*
* copiaza lista data
*
* param: lista de copiat (Lista)
*
* retruns: copia listei (Lista)
*/
Lista* copiaza_lista(Lista* v, CopyFct cf);


//////////////////////////////////////////////////////////////////////////////////////////

void test_creeaza_distruge_lista();
void test_adauga_sterge_elem();
void test_copiaza_lista();
void testListaDeListe();