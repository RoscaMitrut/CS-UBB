CREATE TABLE Piete
(
	id_pi INT PRIMARY KEY IDENTITY(1,1),
	denumire NVARCHAR(50),
	cartier NVARCHAR(50),
	localitate NVARCHAR(50)
)

CREATE TABLE Pesti
(
	id_pe INT PRIMARY KEY IDENTITY(1,1),
	denumire NVARCHAR(50),
	pret REAL,
	cantitate REAL
)

CREATE TABLE PietePesti
(
	id_pi INT FOREIGN KEY REFERENCES Piete(id_pi),
	id_pe INT FOREIGN KEY REFERENCES Pesti(id_pe),
	suma_totala	REAL, 
	nr_clienti INT,
	CONSTRAINT pk_PietePesti PRIMARY KEY (id_pi, id_pe)
)

CREATE TABLE Standuri
(
	id_s INT PRIMARY KEY IDENTITY(1,1),
	descriere NVARCHAR(50),
	nr_disponibile INT,
	ora_deschidere INT,
	ora_inchidere INT,
	id_pi INT FOREIGN KEY REFERENCES Piete(id_pi)
)

CREATE TABLE Clienti
(
	id_c INT PRIMARY KEY IDENTITY(1,1),
	nume NVARCHAR(50),
	prenume NVARCHAR(50),
	gen NVARCHAR(5),
	varsta INT,
	id_pi INT FOREIGN KEY REFERENCES Piete(id_pi)
)

--------------------------------------------------------

INSERT INTO Piete(denumire,cartier,localitate)
VALUES ('PiataMare','Magherus','Toplita'),
		('PiataMica','Moglanesti','Toplita'),
		('PiataMare','Targului','TarguMures'),
		('PiataMare','Manastur','Cluj-Napoca'),
		('PiataMica','Marasti','Cluj-Napoca')

INSERT INTO Pesti(denumire,pret,cantitate)
VALUES ('Pastrav',52.5,12.24),
		('Cod',32.1,123.12),
		('Crap',32.15,123.12),
		('Podut',25.12,73.34),
		('Clean',66.12,19.26)


DELETE FROM PietePesti

INSERT INTO PietePesti(id_pi,id_pe,suma_totala,nr_clienti)
VALUES (1,1,100.12,2),
		(3,4,64.12,2),
		(2,2,65.51,1)

INSERT INTO Standuri(descriere,nr_disponibile,ora_deschidere,ora_inchidere,id_pi)
VALUES ('LaBalta',2,7,18,1),
		('LaLac',5,8,17,2),
		('LaBalta',3,9,16,1),
		('LaLac',1,7,15,3),
		('PeRau',1,10,12,1)

INSERT INTO Clienti(nume,prenume,gen,varsta,id_pi)
VALUES('Pop','Paul','M',27,1),
		('Constantinescu','Mihaela','F',85,3),
		('Pop','Daniela','F',27,2),
		('Iliescu','Paul','M',34,3),
		('Ionescu','Laura','F',13,1)


SELECT * FROM Piete
SELECT * FROM Pesti
SELECT * FROM PietePesti
SELECT * FROM Standuri
SELECT * FROM Clienti

----------------------------------------------------------

go
CREATE OR ALTER PROCEDURE adaugare @id_pi INT,@id_pe INT,@suma REAL,@nr_c INT AS
BEGIN
	IF(NOT EXISTS(SELECT * FROM PietePesti WHERE id_pi=@id_pi AND id_pe=@id_pe))
	BEGIN
		INSERT INTO PietePesti(id_pi,id_pe,suma_totala,nr_clienti) VALUES
		(@id_pi,@id_pe,@suma,@nr_c)
	END
	ELSE
	BEGIN
		UPDATE PietePesti SET suma_totala = @suma, nr_clienti = @nr_c WHERE
		id_pi=@id_pi AND id_pe=@id_pe
	END
END

SELECT * FROM PietePesti
EXEC adaugare 1,1,100.12,2
EXEC adaugare 1,1,999.99,2
EXEC adaugare 1,2,122.12,3



CREATE OR ALTER VIEW vw_cerinta3 AS
SELECT C.nume,C.prenume FROM Clienti C
LEFT JOIN PietePesti P ON C.id_pi=P.id_pi
GROUP BY C.id_c,C.nume,C.prenume
HAVING sum(P.nr_clienti)=count(P.id_pi)

SELECT * FROM vw_cerinta3