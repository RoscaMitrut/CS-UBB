GO
CREATE OR ALTER PROC deadlock_transaction1 AS
BEGIN
	--SET DEADLOCK_PRIORITY LOW
	--SET DEADLOCK_PRIORITY HIGH
	BEGIN TRAN;
	-- lock Locatii
	UPDATE SucuriNaturale SET Pret = 9999 WHERE Sid = 5;
	WAITFOR DELAY '00:00:05';
	-- accesare Distribuitori
	UPDATE Fructe SET PretMediu = 9999 WHERE Fid = 3;
	COMMIT TRAN;
END;

EXEC deadlock_transaction1;

insert into SucuriNaturale values('Fresh fresh', 'Fresh Fresh SRL', 333, 50, '11/07/2023', 2)

select * from SucuriNaturale