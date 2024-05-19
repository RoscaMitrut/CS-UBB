USE MagazinElectronice

select * from logs

-- Dirty Reads --
BEGIN TRAN;
UPDATE Angajati SET nume = 'Dirty' WHERE nume = 'Maria'
INSERT INTO logs VALUES ('UPDATE','Angajati',CURRENT_TIMESTAMP)
WAITFOR DELAY '00:00:05';
ROLLBACK TRAN;
-- Unrepeatable Reads --
BEGIN TRAN;
WAITFOR DELAY '00:00:05';
UPDATE Angajati SET nume = 'Unrepeatable' WHERE nume = 'Maria';
INSERT INTO logs VALUES ('UPDATE','Angajati',CURRENT_TIMESTAMP)
COMMIT TRAN;

UPDATE Angajati SET nume = 'Maria' WHERE nume = 'Unrepeatable'
select* from Angajati
-- Phantom Reads --
BEGIN TRAN;
WAITFOR DELAY '00:00:05';
INSERT INTO Angajati VALUES ('aaaaa','aaaaa',11111,1);
INSERT INTO logs VALUES ('INSERT','Angajati',CURRENT_TIMESTAMP)
COMMIT TRAN;

DELETE FROM Angajati WHERE nume = 'aaaaa';
SELECT * FROM Angajati;
-- Deadlock --
GO
CREATE OR ALTER PROC deadlock_transaction1 AS
BEGIN
	--SET DEADLOCK_PRIORITY NORMAL; -- deadlock
	--SET DEADLOCK_PRIORITY LOW; -- solutie deadlock
	BEGIN TRAN;
	-- lock Locatii
	UPDATE Locatii SET oras = 'DEADLOCK1' WHERE id_l = 9111;
	INSERT INTO logs VALUES ('UPDATE','Locatii',CURRENT_TIMESTAMP)
	WAITFOR DELAY '00:00:05';
	-- accesare Distribuitori
	UPDATE Distribuitori SET nume = 'DEADLOCK1' WHERE id_d = 103;
	INSERT INTO logs VALUES ('UPDATE','Distribuitori',CURRENT_TIMESTAMP)
	COMMIT TRAN;
END;
GO

EXEC deadlock_transaction1;

--INSERT INTO Distribuitori VALUES ('deadlock',999);
--INSERT INTO Locatii VALUES ('deadlock','deadlock',999);

UPDATE Distribuitori SET nume='deadlock' WHERE id_d=103;
UPDATE Locatii SET oras = 'deadlock' WHERE id_l=9111;
select * from Distribuitori
select * from Locatii

DROP PROCEDURE deadlock_transaction1