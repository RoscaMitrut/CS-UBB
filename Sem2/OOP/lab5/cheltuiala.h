#pragma once
typedef struct {
	int zi;
	int suma;
	char* tip;
}Cheltuiala;

/*
* creeaza o cheltuiala noua
*
* param zi: data in care e efectuata cheltuiala (int)
* param suma: suma cheltuielii (int)
* param tip: tipul chestuielii (string)
*
* return: cheltuiala creata
*/
Cheltuiala* creeaza_cheltuiala(int zi, int suma, char* tip);

/*
*distuge o cheltuiala
* param Cheltuiala: cheltuiala pe care dorim sa o stergem
*/
void distruge_cheltuiala(Cheltuiala *c);

/*
*valideaza o cheltuiala
*
*cheltuiala este valida daca ziua este in intervalul [1,31], suma este un numar real
*pozitiv si tipul cheltuielii apartine multimii {mancare, transport, telefon&internet,imbracaminte,altele}
*
*param c:cheltuiala care trebuie validata
*
* return: 1 daca e valida, 0 altfel
*/
int valideaza_cheltuiala(Cheltuiala* c);

/*
* copiaza o cheltuiala
* 
* param c: cheltuiala care trebuie copiata
* 
* return: copia cheltuielii
*/
Cheltuiala* copiaza_cheltuiala(Cheltuiala* c);


//////////////////////////////////////////////////////////////////////////////////////////

void test_creeaza_distruge_cheltuiala();
void test_valideaza();
void test_copiaza();
