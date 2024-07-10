#pragma once
#include "cheltuiala.h"
typedef Cheltuiala ElemType;
typedef struct {
	ElemType* elems;
	int lg;
	int capacitate;
}MyList;

/*
Creare lista goala
return MyList:lista creata
*/
MyList createEmpty();

/*
Distrugere o lista data
MyList l = lista pe care o distrugem
*/
void destroy(MyList* l);

/*
getter cheltuiala aflata pe o anumita pozitie din lista
MyList l = lista in care cautam cheltuiala
poz = pozitia pe care se afla cheltuiala cautata
return ElemType: cheltuiala respectiva
*/
ElemType get(MyList* l, int poz);

/*
setter cheltuiala pe o pozitie din lista
MyList l = lista in care facem modificarea
poz = pozitia cheltuielii pe care o inlocuim
ElemType ele = cheltuiala cu care inlocuim cheltuiala din lista
return ElemType: elementul care a fost inlocuit ( nu se dealoca memoria )
*/
ElemType setElem(MyList* l, int poz, ElemType el);

/*
returneaza numarul de cheltuieli dintr-o lista
MyList l = lista de cheltuieli
return: numar de cheltuieli din lista
*/
int size(MyList* l);

/*
adaugare cheltuiala in lista
MyList l = lista in care adaugam o cheltuiala
ElemType el = cheltuiala pe care dorim sa o adaugam
*/
void add(MyList* l,ElemType el);

/*
copiaza o lista
MyList l = lista pe care dorim sa o copiem
return MyList: copia unei liste
*/
MyList copyList(MyList* l);

/*
stergere cheltuiala de pe o anumita pozitie din lista
MyList l = lista din care stergem o cheltuiala
poz = pozitia cheltuielii pe care o "stergem"
return ElemType: cheltuiala care a fost "stearsa" ( nu se dealoca memoria )
*/
ElemType delete(MyList* l, int poz);

/*
swap intre 2 cheltuieli aflate pe pozitiile i si j
MyList l = Lista in care facem swap
i = pozita 1
j = pozitia 2
*/
void swap(MyList* l, int i, int j);


void testCreateList();
void testIterateList();
void testCopyList();
void testSetElem();
void testdelete();
void testswap();