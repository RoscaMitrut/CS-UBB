INSERT INTO Clienti(nume,email,telefon)
VALUES ('Marius','marius_123@yahoo.com','0767990161') ,
	   ('Vasile','vasileboss@gmail.com','') ,
	   ('Andrei','','0725311262') ,
	   ('Laurentiu','laurlaur@gmail.com','0712354321') ,
	   ('Alexandra','alexandra235@gmail.com','') ,
       ('Mihai','','') ,
	   ('Radu','radu_si_maria@yahoo.com','0766655523') ;

INSERT INTO Locatii(oras,strada,nr)
VALUES ('Cluj-Napoca','Dambovitei',85) ,
	   ('Targu-Mures','Sperantei',201) ,
	   ('Toplita','Eroilor',1543) ,
	   ('Oradea','Plopilor',99) ;

INSERT INTO Angajati(nume,functie,salar,id_l)
VALUES ('Marius','Vanzator',2200,'1'),
	   ('Rares','Vanzator',2300,'4') ,
	   ('Maria','Vanzator',2600,'3') ,
	   ('Mihaela','Vanzator',2400,'2') ;

INSERT INTO Categorii(id_c,denumire_categorie)
VALUES (1,'Laptop'),
	   (2,'PC'),
	   (3,'Cabluri'),
	   (4,'Display'),
	   (5,'Telefon'),
	   (6,'Periferice PC'),
	   (7,'Electrocasnice mici'),
	   (8,'Electrocasnice mari');

INSERT INTO Produse(denumire_produs,id_c,descriere,pret)
VALUES ('Laptop Asus ASD2134',1,'i5,8gb,gtx1060m',3000),
	   ('Laptop Lenovo GF124',1,'ryzen5,16gb,rtx3060',3500),
	   ('Desktop Dell',2,'i7,16gb,rtx2060',2600),
	   ('Cablu HDMI',3,'15m',100),
	   ('Monitor Dell',4,'4K,28"',700),
	   ('TV Samsung',4,'4K,80"',2500),
	   ('Samsung Galaxy A54',5,'8gb,5G,6"',1700),
	   ('Mouse Logitech G125',6,'800-3000DPI',300),
	   ('Aspirator Bosch',7,'2200W',700),
	   ('Masina de spalat vase Whirlpool',8,'10seturi,45cm',2000);

INSERT INTO Distribuitori(nume,durata_medie_livrare)
VALUES ('METATOOLS SRL',4),
	   ('ELECTRO SRL',5),
	   ('TRITON SRL',3),
	   ('EXPERT TOOLS SRL',3),
	   ('TONIS TRADE SRL',8);

INSERT INTO Manageri(id_m,nume,experienta)
VALUES ('1','Marius',14),
		('2','Mihai',11),
		('3','Mircea',17),
		('4','Vasile',22);

INSERT INTO LocatiiProduse(id_l,id_p,stoc)
VALUES (1,1,15),
		(3,7,1),
		(2,5,2),
		(4,4,6),
		(3,4,2),
		(2,2,11);

INSERT INTO Comenzi(id_l,id_d,data_efectuare)
VALUES (1,1,'17-03-2023'),
		(2,2,'17-04-2023'),
		(1,3,'12-03-2023'),
		(3,4,'01-11-2022'),
		(4,2,'22-09-2023');

INSERT INTO Achizitii(id_c,id_p,data_achizitie)
VALUES (1,1,'11-03-2023'),
		(2,2,'02-04-2023'),
		(7,7,'30-03-2023'),
		(5,9,'01-07-2022'),
		(4,4,'22-09-2023');

INSERT INTO RecenziiProduse(id_c,id_p,nota,recenzie)
VALUES (1,1,3,'Merge ok'),
		(2,2,5,'Bun'),
		(7,7,5,'Perfect'),
		(5,9,2,'Slabut'),
		(4,4,1,'Jale'),
		(1,3,4,'Bunicel');

INSERT INTO RecenziiLocatii(id_c,id_l,nota,recenzie)
VALUES (1,1,5,'Curat'),
		(2,4,4,'Ok'),
		(7,3,5,'Curat'),
		(5,2,3,'Putin murdar'),
		(4,4,4,'Acceptabil');

