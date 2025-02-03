# Lab 1
## Enunt
- Eficienta executiei paralele in contextul calcularii unei matrici de convolutie.

## Distributia datelor
- Am ales ca distributia pe linii/coloane sa se faca in functie de dimensiunile matricilor. (luam linii intregi in caz ca numarul de linii e mai mare decat cel de coloane).

## Metoda de rezolvare
- Workload-ul se imparte in **p** threaduri.
- Se incearca impartirea cantitatii de calcul intr-un mod cat mai echilibrat (se imparte numarul de linii/coloane la numarul de threaduri si se retine restul, iar apoi se calculeaza 2 pozitii: start si end, care se updateaza la fiecare pas de initializare a unui thread).

## Timpi de executie
- In cazul matricilor de dimensiuni reduse (10x10, 10x10000, 10000x10, 1000x1000), timpii de executie din C++ sunt superiori, insa in cazul matricilor de dimensiuni mai mari (10000x10000), Java este superior.
- De asemenea, timpii de executie sunt mai imprevizibili in Java
- Lucrul cu fisierele (input/output/...) este semnificativ mai rapid in Java.
- Testele au fost facute cu aproximativ aceleasi procese ruland in fundal
