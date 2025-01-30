Comenzi

Pentru a inregistra comenzi, un restaurant a proiectat un sistem client-server dupa cum urmeaza.

Meniul oferit de restaurant este trimis de server catre clienti prin web socket (ws://localhost:3000).
Cand o aplicatie client se conecteaza prin web socket, severul trimite meniul oferit in JSON
folosind formatul [{ code, name, price }] unde code este un numar si name este sir de caractere,
de ex. [{"code": 1, "name": "p1", price: 11}, {"code": 2, "name": "p2", price: 12}].

Serverul permite aplicatiei client sa trimita elementele comandate de utilizator, via POST /item,
un element avand urmatoarele proprietati: {code, quantity, table} reprezentand dodul produsului,
cantitatea comandata si masa de la care s-a comandat.

Dezvoltati o aplicatie client mobila dupa cum urmeaza.

1. La prima pornire, aplicatia permite utilizatorului sa introduca masa la care se afla (table)
si sa declanseze un buton "Set table". Masa va fi salvata local iar in sesiunile de lucru urmatoare
nu se va putea modifica [1p].

2. La pornire, dupa ce masa a fost stabilita, aplicatia verifica daca meniul a fost primit de la
server in sesiunile de lucru anterioare. Daca meniul n-a fost primit anterior, aplicatia se conecteaza
prin web socket pentru a primi meniul. Pana cand primeste meniul, aplicatia prezinta un progress
indicator (loading) [1p].

3. Applicatia persista local meniul restaurantului si cantitatile introduse de utilizator.
La repornirea aplicatiei, interfata cu utilizatorul va afisa meniul si cantitatile introduse anterior [1p].

4. O lista prezinta meniul restaurantului impreuna cu cantitatile comandate, un element din lista prezentand
numele elementului din meniu (name), un text nemodificabil reprezentand cantitatea comandata de utilizator
- daca a fost comandata (quantity), pretul produsului (price) si pretul total (quantity * price) [1p].

5. Cand utilizatorul selecteaza (face click pe) un element din lista, textul care reprezinta cantitatea va fi inlocuit
cu un element de intrare care-i va permite utilizatorului sa introduca un numar (quantity).
Dupa introducerea cantitatii si confirmarea ei (prin enter sau un buton), se va afisa din nou un text readonly [1p].

6. Utilizatorul poate filtra lista, optand pentru afisarea tuturor elementelor, sau doar a celor comandate (cele care
au o cantitate introdusa) [1p].

7. Cand utilizatorul declanseaza un buton 'Submit' (prezentat sub lista), aplicatia va trimite toate cantitatile
comandate executand in paralel operatii POST /item, pentru fiecare element ce are cantitate introdusa,
incluzand in corpul cererii { code, quantity, table } [1p].

8. Daca anumite operatii POST esueaza (de ex. cantitate negativa), utilizatorul va fi informat prin prezentarea
cantitatii respective cu font rosu. Butonul 'Submit' poate fi declansat de mai multe ori si va trimite
la server elementele care n-au fost trimise anterior sau cele pentru care trimiterea a esuat  [1p].

9. Atunci cand operatiile POST sunt in curs de executie, un indicator de progres va fi prezentat de catre
elementul din lista pentru care se incearca trimiterea catre server. Toate erorile IO (lipsa conectivitate,
server indisponibil) vor fi raportate utilizatorului folosind notificari [1p].
