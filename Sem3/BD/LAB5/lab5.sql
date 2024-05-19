USE MagazinElectronice
GO
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER FUNCTION [dbo].[Validate_Produs](
	@denumire_produs NVARCHAR(50), -- != NULL
	@id_c INT, -- FK Categorii(id_c)
	@descriere NVARCHAR(300),
	@pret INT -- != NULL
	) RETURNS INT AS BEGIN

	DECLARE @categorii_count INT;
	SELECT @categorii_count=COUNT(*) FROM Categorii WHERE id_c = @id_c;

	IF (@denumire_produs is NULL OR @pret IS NULL OR (@id_c IS NOT NULL AND @categorii_count = 0))
		RETURN 0;
	RETURN 1;
END
GO
SELECT [dbo].[Validate_Produs](NULL, NULL, NULL, NULL); --RETURNEAZA 0
GO
SELECT [dbo].[Validate_Produs]('asds', 2, NULL, 200); --RETURNEAZA 1
GO
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER PROCEDURE [dbo].[CRUD_Produse]
	@denumire_produs NVARCHAR(50),
	@id_c INT,
	@descriere NVARCHAR(300),
	@pret INT
AS BEGIN	
	DECLARE @validation_status INT;
	SELECT @validation_status = [dbo].[Validate_Produs](@denumire_produs, @id_c, @descriere, @pret);

	IF (@validation_status = 0)
	BEGIN
		RAISERROR(N'Parametrii invalizi (CRUD_Produse)', 16, 1);
		RETURN;
	END
	-- CREATE
	INSERT INTO Produse(denumire_produs, id_c, descriere, pret)
	VALUES (@denumire_produs, @id_c, @descriere, @pret);
	DECLARE @id_p INT=@@IDENTITY;
	PRINT(N'Produs creat cu identifier ' + CAST(@id_p AS VARCHAR(20)) + '!');
	-- READ
	SELECT * FROM Produse
	WHERE id_p = @id_p;
	PRINT(N'Printat Produs cu identifier ' + CAST(@id_p AS VARCHAR(20)) + '!');
	-- UPDATE
	UPDATE Produse
	SET denumire_produs = 'Updated' + @denumire_produs
	WHERE id_p = @id_p;
	PRINT(N'Updatat Produs cu identifier ' + CAST(@id_p AS VARCHAR(20)) + '!');
	-- DELETE
	DELETE FROM Produse
	WHERE id_p = @id_p;
	PRINT(N'Sters Produs cu identifier ' + CAST(@id_p AS VARCHAR(20)) + '!');
END
GO
EXEC [dbo].[CRUD_Produse] 'dfjhjjkjnhbg', 3, 'aretsfjhgdkiuy', 80000
GO
/*
EXEC [dbo].[CRUD_Produse] NULL, NULL, NULL, NULL
GO
*/
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER FUNCTION [dbo].[Validate_Locatie](
	@oras NVARCHAR(50),-- != NULL
	@strada NVARCHAR(50),-- != NULL
	@nr INT
	) RETURNS INT AS
BEGIN
	IF (@oras IS NULL or @strada IS NULL)
		RETURN 0;
	RETURN 1;
END
GO
SELECT [dbo].[Validate_Locatie](NULL, NULL, NULL);--RETURNEAZA 0
GO
SELECT [dbo].[Validate_Locatie]('asds', 'aasgsadgfasd', 200);--RETURNEAZA 1
GO
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER PROCEDURE [dbo].[CRUD_Locatii]
	@oras NVARCHAR(50),-- != NULL
	@strada NVARCHAR(50),-- != NULL
	@nr INT
AS BEGIN
	DECLARE @validation_status INT;
	SELECT @validation_status = [dbo].[Validate_Locatie](@oras,@strada,@nr);

	IF (@validation_status = 0)
	BEGIN
		RAISERROR(N'Parametrii invalizi (CRUD_Locatii)', 16, 1);
		RETURN;
	END
	-- CREATE
	INSERT INTO Locatii(oras,strada,nr)
	VALUES (@oras,@strada,@nr);
	DECLARE @id_l INT=@@IDENTITY;
	PRINT(N'Locatie creata cu identifier ' + CAST(@id_l AS VARCHAR(20)) + '!');
	-- READ
	SELECT * FROM Locatii
	WHERE id_l = @id_l;
	PRINT(N'Printat Locatie cu identifier ' + CAST(@id_l AS VARCHAR(20)) + '!');
	-- UPDATE
	UPDATE Locatii
	SET strada = 'Updated' + @strada
	WHERE id_l = @id_l;
	PRINT(N'Updatat Locatie cu identifier ' + CAST(@id_l AS VARCHAR(20)) + '!');
	-- DELETE
	DELETE FROM Locatii
	WHERE id_l = @id_l;
	PRINT(N'Sters Locatie cu identifier ' + CAST(@id_l AS VARCHAR(20)) + '!');
END
GO
EXEC [dbo].[CRUD_Locatii] 'fgfdefsad','asdfgadf',123
GO
/*
EXEC [dbo].[CRUD_Locatii] NULL, NULL, NULL
GO
*/
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER FUNCTION [dbo].[Validate_LocatieProdus](
	@id_l INT,--FK Locatii(id_l)
	@id_p INT,--FK Produse(id_p)
	@stoc INT-- >0
	) RETURNS INT AS
BEGIN
	DECLARE @locatii_count INT;
	SELECT @locatii_count=COUNT(*) FROM Locatii WHERE id_l = @id_l;
	DECLARE @produse_count INT;
	SELECT @produse_count=COUNT(*) FROM Produse WHERE id_p = @id_p;
	DECLARE @loc_prod_count INT;
	SELECT @loc_prod_count=COUNT(*) FROM LocatiiProduse WHERE id_l = @id_l AND id_p = @id_p;

	IF (@stoc <= 0 OR @locatii_count = 0 OR @produse_count = 0 OR @loc_prod_count != 0)
		RETURN 0;
	RETURN 1;
END
GO
SELECT [dbo].[Validate_LocatieProdus](NULL, NULL, NULL);--RETURNEAZA 0
GO
SELECT [dbo].[Validate_LocatieProdus](1, 2, 12);--RETURNEAZA 1
GO
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER PROCEDURE [dbo].[CRUD_LocatiiProduse]
	@id_l INT,
	@id_p INT,
	@stoc INT
AS BEGIN
	DECLARE @validation_status INT;
	SELECT @validation_status = [dbo].[Validate_LocatieProdus](@id_l,@id_p,@stoc);

	IF (@validation_status = 0)
	BEGIN
		RAISERROR(N'Parametrii invalizi (CRUD_LocatiiProduse)', 16, 1);
		RETURN;
	END
	-- CREATE
	INSERT INTO LocatiiProduse(id_l,id_p,stoc)
	VALUES (@id_l,@id_p,@stoc);
	PRINT(N'LocatieProdus creat cu identifier  (' + CAST(@id_l AS VARCHAR(20)) + ', ' + CAST(@id_p AS VARCHAR(20)) + ')!');
	-- READ
	SELECT * FROM LocatiiProduse
	WHERE id_l = @id_l AND id_p = @id_p;
	PRINT(N'Printat LocatieProdus cu identifier  (' + CAST(@id_l AS VARCHAR(20)) + ', ' + CAST(@id_p AS VARCHAR(20)) + ')!');
	-- UPDATE
	UPDATE LocatiiProduse
	SET stoc = stoc / 2 + 1
	WHERE id_l = @id_l AND id_p = @id_p;
	PRINT(N'Updatat LocatieProdus cu identifier  (' + CAST(@id_l AS VARCHAR(20)) + ', ' + CAST(@id_p AS VARCHAR(20)) + ')!');
	-- DELETE
	DELETE FROM LocatiiProduse
	WHERE id_l = @id_l AND id_p = @id_p;
	PRINT(N'Sters LocatieProdus cu identifier  (' + CAST(@id_l AS VARCHAR(20)) + ', ' + CAST(@id_p AS VARCHAR(20)) + ')!');
END
GO
EXEC [dbo].[CRUD_LocatiiProduse] 1,2,200
GO
/*
EXEC [dbo].[CRUD_LocatiiProduse] NULL, NULL, NULL
GO
*/
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER VIEW ViewLocatii_2
AS
	SELECT oras,strada,nr FROM Locatii
GO
---------------------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR ALTER VIEW ViewProduse
AS
	SELECT denumire_produs,pret FROM Produse
GO
---------------------------------------------------------------------------------------------------------------------------------------------------------
IF EXISTS(SELECT name FROM sys.indexes WHERE name ='N_idx_locatii_data')
	DROP INDEX N_idx_locatii_data ON Locatii;
CREATE NONCLUSTERED INDEX N_idx_locatii_data ON Locatii(oras,strada,nr);
---------------------------------------------------------------------------------------------------------------------------------------------------------
IF EXISTS(SELECT name FROM sys.indexes WHERE name ='N_idx_produse_data')
	DROP INDEX N_idx_produse_data ON Produse;
CREATE NONCLUSTERED INDEX N_idx_produse_data ON Produse(denumire_produs,pret)
---------------------------------------------------------------------------------------------------------------------------------------------------------
SELECT * FROM ViewProduse ORDER BY pret
SELECT * FROM ViewLocatii_2 ORDER BY nr
---------------------------------------------------------------------------------------------------------------------------------------------------------