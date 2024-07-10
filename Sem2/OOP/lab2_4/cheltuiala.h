#pragma once

typedef struct {
	int numar;
	int suma;
	char* tip;
} Cheltuiala;

/*
returneaza o copie a unei cheltuieli date
Cheltuiala c = cheltuiala data
return Cheltuiala = copia cheltuielii
*/
Cheltuiala copyCheltuiala(Cheltuiala* c);

/*
creare cheltuiala noua
numar = numarul cheltuielii
suma = suma cheltuielii
tip = tipul cheltuielii
return Cheltuiala = cheltuiala creata
*/
Cheltuiala createCheltuiala(int numar, int suma, char* tip);

/*
dealocare memorie ocupata de o cheltuiala
Cheltuiala c = cheltuiala pe care dorim sa o dealocam
*/
void destroyCheltuiala(Cheltuiala* c);

/*
validare cheltuiala
return:
1 :numar<0
2 :suma<0
3 :tip==NULL
0 :totu' ok
*/
int valideaza(Cheltuiala c);





void testCreateDestroyValidate();