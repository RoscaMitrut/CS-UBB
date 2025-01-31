comb([H|_],1,[H]).
comb([_|T],K,C):-comb(T,K,C).
comb([H|T],K,[H|C]):-K>1,K1 is K-1,comb(T,K1,C).

subliste([],[]).
subliste([_|T],Rez):-subliste(T,Rez).
subliste([H|T],[H|Rez]):-subliste(T,Rez).

insereaza(E,L,[E|L]).
insereaza(E,[H|T],[H|Rez]):-insereaza(E,T,Rez).

aranj([H|_],1,[H]).
aranj([_|T],K,R):-aranj(T,K,R).
aranj([H|T],K,R):-K>1,K1 is K-1,
	aranj(T,K1,R1),
	insereaza(H,R1,R).

perm([], []).
perm([E|T],P):-perm(T, L),insereaza(E,L,P).