CREATE PROCEDURE proc_1 AS
ALTER TABLE Manageri
ALTER COLUMN nume NVARCHAR(100) NOT NULL
PRINT 'S-a modificat coloana "nume" din tabela "Manageri"'
GO
--DROP PROCEDURE proc_1

CREATE PROCEDURE proc_2 AS
ALTER TABLE Manageri
ADD CONSTRAINT experienta_minima DEFAULT 1 FOR experienta
PRINT 'S-a adaugat constrangere pentru coloana "experienta" din tabela "Manageri"'
GO
--DROP PROCEDURE proc_2

CREATE PROCEDURE proc_3 AS
CREATE TABLE Traineri(
id_t INT NOT NULL PRIMARY KEY,
nume NVARCHAR(50)
)
PRINT 'S-a creat tabela "Traineri"'
GO
--DROP PROCEDURE proc_3

CREATE PROCEDURE proc_4 AS
ALTER TABLE Traineri
ADD id_ucenic INT NOT NULL
PRINT 'S-a adaugat campul "id_ucenic" in tabela "Traineri"'
GO
--DROP PROCEDURE proc_4

CREATE PROCEDURE proc_5 AS
ALTER TABLE Traineri
ADD CONSTRAINT fk_Traineri FOREIGN KEY(id_ucenic) REFERENCES Angajati(id_a)
PRINT 'S-a adaugat cheia straina'
GO
--DROP PROCEDURE proc_5

---------------------------------------------------

CREATE PROCEDURE undo_proc_1 AS
ALTER TABLE Manageri
ALTER COLUMN nume NVARCHAR(50) NOT NULL
PRINT 'S-a modificat inapoi la normal coloana "nume" din tabela "Manageri"'
GO
--DROP PROCEDURE undo_proc_1

CREATE PROCEDURE undo_proc_2 AS
ALTER TABLE Manageri
DROP CONSTRAINT experienta_minima
PRINT 'S-a sters constrangerea pentru coloana "experienta" din tabela "Manageri"'
GO
--DROP PROCEDURE undo_proc_2

CREATE PROCEDURE undo_proc_3 AS
DROP TABLE Traineri
PRINT 'S-a sters tabela "Traineri"'
GO
--DROP PROCEDURE undo_proc_3

CREATE PROCEDURE undo_proc_4 AS
ALTER TABLE Traineri
DROP COLUMN id_ucenic
PRINT 'S-a sters campul "id_ucenic" in tabela "Traineri"'
GO
--DROP PROCEDURE undo_proc_4

CREATE PROCEDURE undo_proc_5 AS
ALTER TABLE Traineri
DROP CONSTRAINT fk_Traineri
PRINT 'S-a sters cheia straina'
GO
--DROP PROCEDUDE undo_proc_5

--------------------------------------------
DROP TABLE IF EXISTS VersiuneBD

CREATE TABLE VersiuneBD(
	nr_ver INT
)

INSERT INTO VersiuneBD VALUES(0)


GO
CREATE PROCEDURE proc_main @ver_dorita INT
AS
BEGIN
	DECLARE @ver_actuala INT
	SELECT @ver_actuala=nr_ver from VersiuneBD

	IF @ver_dorita = @ver_actuala
		PRINT 'Versiunea actuala este deja cea curenta!'

	ELSE IF @ver_dorita < 0 OR @ver_dorita > 5
		PRINT 'Versiunea dorita nu este valida!'

	ELSE IF @ver_actuala < @ver_dorita
	BEGIN
		WHILE(@ver_actuala < @ver_dorita)
		BEGIN
			SET @ver_actuala = @ver_actuala + 1
			DECLARE @proc AS VARCHAR(20) = CONCAT('proc_', CAST(@ver_actuala AS VARCHAR))
			EXEC @proc
		END
	DELETE FROM VersiuneBD
	INSERT INTO VersiuneBD(nr_ver) VALUES (@ver_dorita)
	END
	ELSE
	BEGIN
		WHILE(@ver_actuala > @ver_dorita)
			BEGIN
			DECLARE @proc_undo AS VARCHAR(20) = CONCAT('undo_proc_', CAST(@ver_actuala AS VARCHAR))
			EXEC @proc_undo
			SET @ver_actuala = @ver_actuala - 1
		END
	DELETE FROM VersiuneBD
	INSERT INTO VersiuneBD(nr_ver) VALUES (@ver_dorita)
END
END
GO
--DROP PROCEDURE proc_main

exec proc_main 5