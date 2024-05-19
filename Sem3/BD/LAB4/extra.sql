CREATE OR ALTER VIEW ViewLocatii
AS
	SELECT L.oras as 'Locatie'
	FROM Locatii L
GO

CREATE OR ALTER VIEW ViewCategoriiProduse
AS
	SELECT C.denumire_categorie AS 'Categorie',P.denumire_produs AS 'Produs'
	FROM Categorii C
	INNER JOIN Produse P
	ON C.id_c = P.id_c

GO

CREATE OR ALTER VIEW ViewLocatiiNrProduse
AS
	SELECT L.oras AS 'Locatie', COUNT(LP.id_p) AS 'NrCategoriiProduse'
	FROM Locatii L
	INNER JOIN LocatiiProduse LP
	ON L.id_l = LP.id_l
	GROUP BY L.oras
GO



SET IDENTITY_INSERT Tables ON;
DELETE FROM Tables;
INSERT INTO Tables(TableID, Name)
VALUES (1, 'Locatii'), (2, 'Produse'), (3, 'LocatiiProduse');
SET IDENTITY_INSERT Tables OFF;

SET IDENTITY_INSERT Views ON;
DELETE FROM Views;
INSERT INTO Views(ViewID, Name)
VALUES (1, 'ViewLocatii'), (2, 'ViewCategoriiProduse'), (3, 'ViewLocatiiNrProduse');
SET IDENTITY_INSERT Views OFF;

SET IDENTITY_INSERT Tests ON;
DELETE FROM Tests;
INSERT INTO Tests(TestID, Name)
VALUES (1, 'selectView'),
		(2, 'insertLocatii'), (3, 'deleteLocatii'),
		(4, 'insertProduse'), (5, 'removeProduse'),
		(6, 'insertLocatiiProduse'), (7, 'deleteLocatiiProduse');
SET IDENTITY_INSERT Tests OFF;

DELETE FROM TestViews;
INSERT INTO TestViews(TestID, ViewID)
VALUES (1, 1), (1, 2), (1, 3);

DELETE FROM TestTables;
INSERT INTO TestTables(TestID, TableID, NoOfRows, Position)
VALUES (2, 1, 1000, 1), (4, 2, 1000, 2), (6, 3, 1000, 3), (7, 3, 1000, 3), (5, 2, 1000, 2), (3, 1, 1000, 1)
