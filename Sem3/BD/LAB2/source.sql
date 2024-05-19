USE master
DROP DATABASE MagazinElectronice
CREATE DATABASE MagazinElectronice
USE MagazinElectronice

alter database MagazinElectronice set single_user with rollback immediate

drop database MagazinElectronice


CREATE TABLE Clienti
	(
	id_c INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	nume NVARCHAR(50) NOT NULL,
	email NVARCHAR(50),
	telefon NVARCHAR(20)
	)
CREATE TABLE Locatii
	(
	id_l INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	oras NVARCHAR(50) NOT NULL,
	strada NVARCHAR(50),
	nr INT
	)
CREATE TABLE Angajati
	(
	id_a INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	nume NVARCHAR(50) NOT NULL,
	functie NVARCHAR(50) NOT NULL,
	salar INT NOT NULL CHECK(salar > 1000),
	id_l INT FOREIGN KEY REFERENCES Locatii(id_l)
	)
CREATE TABLE Categorii
	(
	id_c INT PRIMARY KEY NOT NULL,
	denumire_categorie NVARCHAR(50) NOT NULL
	)
CREATE TABLE Produse
	(
	id_p INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	denumire_produs NVARCHAR(50) NOT NULL,
	id_c INT FOREIGN KEY REFERENCES Categorii(id_c),
	descriere NVARCHAR(300),
	pret INT CHECK(pret>0) NOT NULL
	)
CREATE TABLE Distribuitori
	(
	id_d INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
	nume NVARCHAR(50) NOT NULL,
	durata_medie_livrare INT
	)


CREATE TABLE Manageri
	(
	id_m INT FOREIGN KEY REFERENCES Locatii(id_l),
	nume NVARCHAR(50) NOT NULL,
	experienta INT,
	CONSTRAINT pk_Manageri PRIMARY KEY(id_m)
	)
CREATE TABLE LocatiiProduse
	(
	id_l INT FOREIGN KEY REFERENCES Locatii(id_l),
	id_p INT FOREIGN KEY REFERENCES Produse(id_p),
	stoc INT NOT NULL CHECK(stoc >= 0),
	CONSTRAINT pk_LocatiiProduse PRIMARY KEY (id_l, id_p)
	)
CREATE TABLE Comenzi
	(
	id_l INT FOREIGN KEY REFERENCES Locatii(id_l),
	id_d INT FOREIGN KEY REFERENCES Distribuitori(id_d),
	data_efectuare NVARCHAR(20),
	CONSTRAINT pk_Comenzi PRIMARY KEY (id_l,id_d)
	)
CREATE TABLE Achizitii
	(
	id_c INT FOREIGN KEY REFERENCES Clienti(id_c),
	id_p INT FOREIGN KEY REFERENCES Produse(id_p),
	data_achizitie NVARCHAR(20),
	CONSTRAINT pk_Achizitii PRIMARY KEY (id_c,id_p)
	)
CREATE TABLE RecenziiProduse
	(
	id_c INT FOREIGN KEY REFERENCES Clienti(id_c),
	id_p INT FOREIGN KEY REFERENCES Produse(id_p),
	CONSTRAINT pk_RecenziiProduse PRIMARY KEY (id_c,id_p),
	nota INT NOT NULL CHECK(nota>0 AND nota <=5),
	recenzie NVARCHAR(300)
	)
CREATE TABLE RecenziiLocatii
	(
	id_c INT FOREIGN KEY REFERENCES Clienti(id_c),
	id_l INT FOREIGN KEY REFERENCES Locatii(id_l),
	CONSTRAINT pk_RecenziiLocatii PRIMARY KEY (id_c,id_l),
	nota INT NOT NULL CHECK(nota>0 AND nota <=5),
	recenzie NVARCHAR(300)
	)