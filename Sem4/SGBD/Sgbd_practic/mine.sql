select * from TipuriFructe
select * from Fructe
select * from TipuriSucuriNaturale
select * from SucuriNaturale
select * from Achizitii

--CREATE INDEX index_blabla on SucuriNaturale(Gramaj);

SELECT Gramaj FROM SucuriNaturale;


SELECT Gramaj
FROM SucuriNaturale WITH (INDEX(0))