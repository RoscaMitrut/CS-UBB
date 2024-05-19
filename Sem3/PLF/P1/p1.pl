%
/*
a. sa se scrie un predicat care intoarce diferenta a doua multimi.
b. sa se scrie un predicat care adauga intr-o lista dupa fiecare element par valoarea 1.


apare(E: int, L: lista)
model flux: (i,i)
E - elementul cautat
L - lista in care cautam E
*/
apare(E, []):- false. %caz lista goala
apare(E, [E|_]):- !. %caz in care E apare in lista
apare(E, [_|L]):- apare(E, L). %"sarim" peste primul element si continuam cu urmatorul

/*
dif(L: lista, K: lista, R: lista)
modele de flux: (i,i,o)
L - lista 1
K - lista 2
R - lista rezultata (L-K)
*/

dif([], _, []). % cazul in care Prima lista ajunge sa fie goala
dif([H|T], K, [H|R]):- % cazul in care elementul nu apare in lista 2 => il adaugam in lista rezultat
	not(apare(H, K)),
	!,
	dif(T, K, R).
dif([_|T], K, R):-% "sarim" peste primul element din lista
	dif(T, K, R).



/*
adaugaPar(L: lista, R: lista)
model de flux: (i,o)
L - lista initiala
R - lista rezultata
*/

adaugaPar([], []). % caz de baza in care prima lista e vida => lista rezultata vida
adaugaPar([H|T], [H,1|R]):- % cazul in care elementul actual este par => adaugam elementul (+) 1 in lista rezultata 
		H mod 2 =:= 0,
		!,
		adaugaPar(T,R).
adaugaPar([H|T], [H|R]):-% cazul in care elementul nu e par -> adaugam elementul in lista rezultata
		adaugaPar(T, R).  