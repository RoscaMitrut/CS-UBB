USE MagazinElectronice

-- Dirty Reads --
--SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED -- dirty reads
SET TRANSACTION ISOLATION LEVEL READ COMMITTED -- dirty reads solution
BEGIN TRAN;
SELECT * FROM Angajati
INSERT INTO logs VALUES ('SELECT','Angajati',CURRENT_TIMESTAMP)
WAITFOR DELAY '00:00:05';
SELECT * FROM Angajati
INSERT INTO logs VALUES ('SELECT','Angajati',CURRENT_TIMESTAMP)
COMMIT TRAN;

-- Unrepeatable Reads--
SET TRANSACTION ISOLATION LEVEL READ COMMITTED -- unrepeatable reads
-- SET TRANSACTION ISOLATION LEVEL REPEATABLE READ -- unrepeatable reads solution
BEGIN TRAN;
SELECT * FROM Angajati;
INSERT INTO logs VALUES ('SELECT','Angajati',CURRENT_TIMESTAMP)
WAITFOR DELAY '00:00:05';
SELECT * FROM Angajati;
INSERT INTO logs VALUES ('SELECT','Angajati',CURRENT_TIMESTAMP)
COMMIT TRAN;

-- Phantom Reads --
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ -- phantom reads
-- SET TRANSACTION ISOLATION LEVEL SERIALIZABLE -- phantom reads solutions
BEGIN TRAN;
SELECT * FROM Angajati;
INSERT INTO logs VALUES ('SELECT','Angajati',CURRENT_TIMESTAMP)
WAITFOR DELAY '00:00:05';
SELECT * FROM Angajati;
INSERT INTO logs VALUES ('SELECT','Angajati',CURRENT_TIMESTAMP)
COMMIT TRAN;

-- Deadlock --

GO
CREATE OR ALTER PROC deadlock_transaction2 AS
BEGIN
	SET DEADLOCK_PRIORITY LOW; -- deadlock
	--SET DEADLOCK_PRIORITY HIGH; -- solutie deadlock
	BEGIN TRAN;
	-- lock Distribuitori
	UPDATE Distribuitori SET nume = 'DEADLOCK2' WHERE id_d = 103;
	INSERT INTO logs VALUES ('UPDATE','Angajati',CURRENT_TIMESTAMP)
	WAITFOR DELAY '00:00:05';
	-- accesare Locatii
	UPDATE Locatii SET oras = 'DEADLOCK2' WHERE id_l = 9111;
	INSERT INTO logs VALUES ('UPDATE','Angajati',CURRENT_TIMESTAMP)
	COMMIT TRAN;
END;
GO

EXEC deadlock_transaction1;
EXEC deadlock_transaction2;


DROP PROCEDURE deadlock_transaction1