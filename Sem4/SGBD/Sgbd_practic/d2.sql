GO
CREATE OR ALTER PROC deadlock_transaction2 AS
BEGIN
	--SET DEADLOCK_PRIORITY LOW
	SET DEADLOCK_PRIORITY HIGH
	BEGIN TRAN;
	-- lock Distribuitori
	UPDATE Fructe SET PretMediu = 1111 WHERE Fid = 3;

	WAITFOR DELAY '00:00:05';
	-- accesare Locatii
	UPDATE SucuriNaturale SET Pret = 1111 WHERE Sid = 5;
	COMMIT TRAN;
END;

EXEC deadlock_transaction2;

insert into Fructe values('Mere', 'verzi', 'septembrie', 5, 2)

select * from Fructe