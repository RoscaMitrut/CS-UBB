% 13.
%a) Sa se adauge dupa fiecare element dintr-o lista divizorii elementului.
%b) Se da o lista eterogena, formata din numere intregi si liste de numere intregi.
%Se cere ca in fiecare sublista sa se adauge dupa fiecare element divizorii elementului.\
%[1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2] =>
%[1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]

% a
% insert_div(N:number, Div:number, L:list, R:list)
% model flux : (i, i, i, o)
% N - numarul ai carui divizori ii vrem
% Div - divizorul curent (se incepe la 2 si urca...)
% L - lista in care dorim sa adaugam divizorii lui N
% R - lista rezultata dupa adaugarea divizorilor lui N in L
insert_div(N, _, L, L) :- N =< 2.
insert_div(N, N, L, L):-!.
insert_div(N, Div, L, [Div|R]) :- N mod Div =:= 0,
    !,
    NDiv is Div + 1,
    insert_div(N, NDiv, L, R).
insert_div(N, Div, L, R) :-
    NDiv is Div + 1,
    insert_div(N, NDiv, L, R).

% divizori(L:list, R:list)
% model flux : (i, o)
% L - lista in care dorim sa adaugam divizorii fiecaraui numar, pozitionati dupa acesta
% R - lista rezultata
divizori([], []).
divizori([H|T], [H|R]) :-
    divizori(T, RD),
    insert_div(H, 2, RD, R).

% b
% eter_list(L:list, R:list)
% model flux : (i, o)
% L - lista eterogena in care dorim sa adaugam dupa fiecare numar din interiorul unei liste divizorii acestuia, pozitionati dupa acesta
% R - lista eterogena rezultata
eter_list([], []).
eter_list([H|T], [HR|R]) :-
    is_list(H),
    !,
    divizori(H, HR),
    eter_list(T, R).
eter_list([H|T], [H|R]) :-
    eter_list(T, R).