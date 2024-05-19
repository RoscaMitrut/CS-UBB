%cerinta
%prolog 17
%sa se sorteze o lista cu eliminarea dublurilor
% [4 2 6 2 3 4] -> [2 3 4 6]


%apartine(e-int,L-lista)
%model flux (i,i)
%verifica daca un elemente apartine unei liste
apartine(E,[E|T]):-!.
apartine(E,[H|T]):-apartine(E,T).


%elim_dubluri(L-lista,R-lista)
%model flux (i,o)
%elimina dublurile dintr-o lista
elim_dubluri([],[]).
elim_dubluri([H|T],[H|R]):-not(apartine(H,T)),
						!,
						elim_dubluri(T,R).
elim_dubluri([H|T],R):-elim_dubluri(T,R).


%min(L-lista,E-int)
%model flux(i,o)
%ia minimul dintr-o lista
min([E],E):-!.
min([H|T],H):-min(T,R),
				H<R,
				!.
min([H|T],R):-min(T,R).


%elim_elem(E-int,L-lista,R-lista)
%model flux (i,i,o)
%elimina un anumit element dat dintr-o lista
elim_elem(_,[],[]).
elim_elem(E,[E|T],R):-elim_elem(E,T,R),!.
elim_elem(E,[H|T],[H|R]):-elim_elem(E,T,R).


%sortare(L-lista,R-lista)
%model flux(i,o)
%sorteaza o lista
sortare([],[]):-!.
sortare(L,[R|R1]):-min(L,R),
						elim_elem(R,L,L1),
						sortare(L1,R1).

%wrapper(L-lista,R-lista)
%model flux(i,o)
%wrapper pentru cerinta
wrapper(L,R1):-elim_dubluri(L,R),
				sortare(R,R1).