#pragma once
#include "lista.h"

typedef struct {
	Lista* toateCheltuielile;
	Lista* listaUndo;
}ListaCompleta;

/*
creeaza o ListaCompleta goala
return: listaCompleta creeata
*/
ListaCompleta createListaCompleta();

/*
distruge o ListaCompleta data ca parametru
*/
void destroyListaCompleta(ListaCompleta* listacompleta);


/*
* adauga o cheltuiala care nu exista in lista, sau adauga noua suma la cheltuiala deja existenta
*
* param v: lista in care se adauga (Lista)
* param zi: data in care e efectuata cheltuiala (int)
* param suma: suma cheltuielii (int)
* param tip: tipul chestuielii (string)
*
* returns: 1 daca cheltuiala a fost adaugata cu succes, 0 altfel
*/
int adauga_cheltuiala(ListaCompleta* v, int zi, int suma, char* tip);

/*
* cauta o cheltuiala din lista daca ea exista
*
* param v: lista in care se cauta (Lista)
* param zi: data in care e efectuata cheltuiala (int)
* param tip: tipul chestuielii (string)
*
* returns: -1 daca cheltuiala nu exista in lista, altfel returneaza pozitia pe care se afla
*/
int cauta_cheltuiala(Lista* v, int zi, char* tip);

/*
* sterge o cheltuiala din lista daca ea exista
*
* param v: lista din care se sterge (Lista)
* param zi: data in care e efectuata cheltuiala (int)
* param tip: tipul chestuielii (string)
*
* returns: 1 daca cheltuiala a fost stearsa cu succes, 0 altfel
*/
int sterge_cheltuiala(ListaCompleta* v, int zi, char* tip);

/*
* modifica o cheltuiala care exista in lista, actualizand suma
*
* param v: lista in care se adauga (Lista)
* param zi: data in care e efectuata cheltuiala (int)
* param suma: suma noua a cheltuielii (int)
* param tip: tipul chestuielii (string)
*
* returns: 1 daca cheltuiala a fost modificata cu succes, 0 altfel
*/
int modifica_cheltuiala(ListaCompleta* v, int zi, int suma_noua, char* tip);

/*
* filtreaza cheltuielile afisand doar cele care indeplinesc conditia data, in functie de zi, suma sau tip
*
* param v: lista in care se aplica filtrul (Lista)
* param optiune: indica tipul filtrului prin valoarea sa: 1 pt zi, 2 pt suma, 3 pt tip (int)
* param zi_sau_suma: retine ziua sau suma dupa care se filtreaza, in functie de optiune (int)
* param tip: tipul dupa care se filtreaza (string)
*
* returns: lista filtrata, sau lista intiala daca filtrele sunt vide (Lista)
*/
Lista* filtreaza_cheltuieli(ListaCompleta* v, int optiune, int zi_sau_suma, char* tip);

/*
filtreaza cheltuielile si le afiseaza doar pe cale care au suma mai mica decat o suma data

param v: ListaCompleta pe care o filtram
param suma_max: suma maxima admisa
*/
Lista* filtreaza_suma_max(ListaCompleta* v,int suma_max);

/*
afiseaza suma cheltuielilor dintr-o zi data

param v: ListaCompleta din care luam cheltuielile
param ziua: ziua a carei suma de cheltuieli o printam

return: suma cheltuielilor zilei date
*/
int filtraza_suma_zi(ListaCompleta* v,int ziua);

/*
* sorteaza crescator lista de cheltuieli
*
* param v: lista in care se sorteaza (Lista)
* param optiune: indica tipul filtrului prin valoarea sa: 1 pt suma, 2 pt tip (int)
*
* returns: lista sortata
*/
Lista* sorteaza_cheltuieli(ListaCompleta* v, int optiune);


/*
functie de undo

param v: ListaCompleta pe care dorim sa facem undo
*/
int undo(ListaCompleta* v);


void test_service_adauga_sterge();
void test_service_cauta();
void test_service_modifica();
void test_service_filtreaza();
void test_service_sorteaza();
void test_undo();
void test_filtrare_suma_suma_max();