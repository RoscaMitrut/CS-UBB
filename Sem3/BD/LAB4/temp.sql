GO
CREATE OR ALTER PROC insertLocatii AS
BEGIN
	DECLARE @rows INT = (SELECT TOP 1 TT.NoOfRows
	FROM TestTables TT INNER JOIN Tests T ON TT.TestID = T.TestID
	WHERE T.Name = 'insertLocatii');
	DECLARE @current INT =1;
	WHILE @current <= @rows
	BEGIN
		INSERT INTO Locatii(oras) VALUES ('testDataBasePerformance')
		SET @current = @current + 1
	END
END
GO
CREATE OR ALTER PROC deleteLocatii AS
BEGIN
	DELETE FROM Locatii
	WHERE oras = 'testDataBasePerformance';
END



GO
CREATE OR ALTER PROC insertProduse AS
BEGIN
	DECLARE @rows INT = (SELECT TOP 1 TT.NoOfRows
	FROM TestTables TT INNER JOIN Tests T ON TT.TestID = T.TestID
	WHERE T.Name = 'insertProduse');
	DECLARE @current INT=1;
	DECLARE @categorie INT = (SELECT id_c FROM Categorii WHERE Categorii.denumire_categorie='CATEGORIE GENERALA');
	DECLARE @int_max INT = (SELECT POWER(2., 31)-1);
	WHILE @current <= @rows
	BEGIN
		INSERT INTO Produse(denumire_produs,id_c,pret) VALUES ('testDataBasePerformance',@categorie,@int_max);
		SET @current = @current + 1
	END
END
GO
CREATE OR ALTER PROC deleteProduse AS
BEGIN
	DELETE FROM Produse
	WHERE denumire_produs = 'testDataBasePerformance';
END
GO



CREATE OR ALTER PROC insertLocatiiProduse AS
BEGIN
	DECLARE @rows INT = (SELECT TOP 1 TT.NoOfRows
	FROM TestTables TT INNER JOIN Tests T ON TT.TestID = t.TestID
	WHERE T.Name ='insertLocatiiProduse');
	DECLARE @current INT = 1;
	DECLARE @first_locatie INT = (SELECT TOP 1 id_l FROM Locatii WHERE oras = 'testDataBasePerformance');
	DECLARE @first_produs INT = (SELECT TOP 1 id_p FROM Produse WHERE denumire_produs = 'testDataBasePerformance');
	DECLARE @int_max INT = (SELECT POWER(2., 31)-1);
	WHILE @current <= @rows
	BEGIN
		DECLARE @current_locatie INT = (SELECT @first_locatie + @current - 1);
		DECLARE @current_produs INT = (SELECT @first_produs + @current - 1);
		INSERT INTO LocatiiProduse(id_l,id_p,stoc)
		VALUES (@current_locatie, @current_produs, @int_max);
		SET @current = @current + 1
	END
END
GO
CREATE OR ALTER PROC deleteLocatiiProduse AS
BEGIN
	DECLARE @int_max INT = (SELECT POWER(2., 31)-1);
	DELETE FROM LocatiiProduse
	WHERE stoc = @int_max;
END

-------------------------------------------------------------------------------------------------------------------------

GO
CREATE OR ALTER PROCEDURE testRunViewsProc AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @start DATETIME;
	DECLARE @end DATETIME;
	DECLARE @view INT;

	SELECT @view = ViewID FROM Views WHERE Name = 'ViewLocatii';
	SET @start = SYSDATETIME();
	SELECT * FROM ViewLocatii;
	SET @end = SYSDATETIME(); 
	PRINT ('ViewLocatii test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('ViewLocatii Test', @start, @end)
    INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (@@IDENTITY, @view, @start, @end);

	SELECT @view = ViewID FROM Views WHERE Name = 'ViewCategoriiProduse';
	SET @start = SYSDATETIME();
	SELECT * FROM ViewCategoriiProduse;
	SET @end = SYSDATETIME();
	PRINT ('ViewCategoriiProduse test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('ViewCategoriiProduse Test', @start, @end)
    INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (@@IDENTITY, @view, @start, @end);

	SELECT @view = ViewID FROM Views WHERE Name = 'ViewLocatiiNrProduse';
	SET @start = SYSDATETIME();
	SELECT * FROM ViewLocatiiNrProduse;
	SET @end = SYSDATETIME();
	PRINT ('ViewLocatiiNrProduse test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('ViewLocatiiNrProduse Test', @start, @end)
    INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (@@IDENTITY, @view, @start, @end);
END



GO
CREATE OR ALTER PROCEDURE testRunDeletesProc AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @start DATETIME;
	DECLARE @end DATETIME;
	DECLARE @table INT;

	SELECT @table = TableID FROM Tables WHERE Name = 'LocatiiProduse';
	SET @start = SYSDATETIME();
	EXEC deleteLocatiiProduse;
	SET @end = SYSDATETIME(); 
	PRINT ('deleteLocatiiProduse test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('deleteLocatiiProduse Test', @start, @end)
    INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@@IDENTITY, @table, @start, @end);

	SELECT @table = TableID FROM Tables WHERE Name = 'Produse';
	SET @start = SYSDATETIME();
	EXEC deleteProduse;
	SET @end = SYSDATETIME(); 
	PRINT ('deleteProduse test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('deleteProduse Test', @start, @end)
    INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@@IDENTITY, @table, @start, @end);

	SELECT @table = TableID FROM Tables WHERE Name = 'Locatii';
	SET @start = SYSDATETIME();
	EXEC deleteLocatii;
	SET @end = SYSDATETIME(); 
	PRINT ('deleteLocatii test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('deleteLocatii Test', @start, @end)
    INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@@IDENTITY, @table, @start, @end);
END



GO
CREATE OR ALTER PROCEDURE testRunInsertsProc AS
BEGIN
	SET NOCOUNT ON;
	DECLARE @start DATETIME;
	DECLARE @end DATETIME;
	DECLARE @table INT;

	SELECT @table = TableID FROM Tables WHERE Name = 'Locatii';
	SET @start = SYSDATETIME();
	EXEC insertLocatii;
	SET @end = SYSDATETIME(); 
	PRINT ('insertLocatii test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('insertLocatii Test', @start, @end)
    INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@@IDENTITY, @table, @start, @end);

	SELECT @table = TableID FROM Tables WHERE Name = 'Produse';
	SET @start = SYSDATETIME();
	EXEC insertProduse;
	SET @end = SYSDATETIME(); 
	PRINT ('insertProduse test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('insertProduse Test', @start, @end)
    INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@@IDENTITY, @table, @start, @end);

	SELECT @table = TableID FROM Tables WHERE Name = 'LocatiiProduse';
	SET @start = SYSDATETIME();
	EXEC insertLocatiiProduse;
	SET @end = SYSDATETIME(); 
	PRINT ('insertLocatiiProduse test time: ' + CAST(DATEDIFF(ms, @start, @end) AS VARCHAR) + ' ms')
	INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES ('insertLocatiiProduse Test', @start, @end)
    INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@@IDENTITY, @table, @start, @end);
END
GO


EXEC testRunDeletesProc;
EXEC testRunInsertsProc;
EXEC testRunViewsProc;
