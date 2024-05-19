/*
SELECT * from Clienti
SELECT * from Locatii
SELECT * from Angajati
SELECT * from Categorii
SELECT * from Produse
SELECT * from Distribuitori
SELECT * from Manageri
SELECT * from LocatiiProduse
SELECT * from Comenzi
SELECT * from Achizitii
SELECT * from RecenziiProduse
SELECT * from RecenziiLocatii
*/

-- Achizitiile de peste 1000Ron, facute de clienti care au numar de telefon
SELECT p.denumire_produs,p.descriere,p.pret,a.data_achizitie,c.nume,c.telefon
FROM Produse p 
INNER JOIN Achizitii a on p.id_p = a.id_p
INNER JOIN Clienti c on c.id_c=a.id_c
WHERE p.pret>1000 and c.telefon LIKE '_%'

-- Utilizatorii care au lasat cel putin o recenzie de peste 2 stele produselor din categoria "Telefon"
SELECT DISTINCT c.id_c,c.nume
From Produse p 
INNER JOIN RecenziiProduse r on p.id_p = r.id_p
INNER JOIN Clienti c on c.id_c=r.id_c
WHERE r.nota>=3 and p.id_c = 5

-- Mediile notelor lasate de fiecare client magazinelor
SELECT c.id_c,c.nume,AVG(r.nota) as medie_rating_locatii
From Locatii l 
INNER JOIN RecenziiLocatii r on l.id_l = r.id_l
INNER JOIN Clienti c on c.id_c=r.id_c
GROUP BY c.id_c,c.nume

-- Mediile salariilor pentru fiecare functie si sub 3000Ron
SELECT a.functie,AVG(a.salar) as medie_salar
FROM Angajati a
GROUP BY a.functie
HAVING AVG(a.salar) < 3000

-- Comenzile efectuate de distribuitori, in anul 2022
SELECT d.nume,c.data_efectuare,l.oras
FROM Distribuitori d 
INNER JOIN Comenzi c on d.id_d = c.id_d
INNER JOIN Locatii l on l.id_l=c.id_l
WHERE c.data_efectuare LIKE '%-2022'

-- Mediile notelor lasate de fiecare client produselor, care sa depaseasca 3 stele
SELECT c.id_c,c.nume,AVG(r.nota) as medie_rating_produse
From Produse p 
INNER JOIN RecenziiProduse r on p.id_p = r.id_p
INNER JOIN Clienti c on c.id_c=r.id_c
WHERE p.pret>1000
GROUP BY c.id_c,c.nume
HAVING AVG(r.nota)> 3

-- Produsele care au stocul mai mic decat 10
SELECT p.denumire_produs,SUM(lp.stoc) as stoc_total
FROM Produse p 
INNER JOIN LocatiiProduse lp on p.id_p = lp.id_p
INNER JOIN Locatii l on l.id_l=lp.id_l
GROUP BY p.denumire_produs
HAVING SUM(lp.stoc) < 10

-- Utilizatorii care au lasat cel putin o recenzie de peste 2 stele magazinului din Oradea
SELECT DISTINCT c.id_c,c.nume
From Locatii l 
INNER JOIN RecenziiLocatii r on l.id_l = r.id_l
INNER JOIN Clienti c on c.id_c=r.id_c
WHERE r.nota>=3 and l.oras='Oradea'

-- Valoarea totala a bunurilor de la fiecare magazin
SELECT l.oras,SUM(lp.stoc * p.pret) as valoare_totala
FROM Produse p 
INNER JOIN LocatiiProduse lp on p.id_p = lp.id_p
INNER JOIN Locatii l on l.id_l=lp.id_l
GROUP BY l.oras

-- Achizitiile facute in luna Septembrie
SELECT p.denumire_produs,p.descriere,p.pret,a.data_achizitie,c.nume,c.telefon
FROM Produse p 
INNER JOIN Achizitii a on p.id_p = a.id_p
INNER JOIN Clienti c on c.id_c=a.id_c
WHERE a.data_achizitie LIKE '%-09-%'
