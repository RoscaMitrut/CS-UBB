USE MagazinElectronice
go

create or alter function dbo.validare_Text (@data nvarchar(100))
returns bit
as
begin
	declare @return bit
	set @return = 1
	if @data is null or @data = ''
		set @return=0
	return @return
end
go

create or alter function dbo.validate_Numar (@data int)
returns bit
as
begin
	declare @return bit
	set @return = 1
	if @data is null or @data = '' or @data not like '[0-9]%'
		set @return=0
	return @return
end
go

create or alter function dbo.validare_Data (@data nvarchar(100))
returns bit
as
begin
	declare @return bit
	set @return = 1
	if @data is null or @data = '' or @data not like '[0-3][0-9]-[0-1][0-9]-[0-9][0-9][0-9][0-9]'
		set @return=0
	return @return
end
go

create or alter procedure AddLocatie
	@oras nvarchar(50), @strada nvarchar(50), @numar int as
begin
begin tran
	begin try
		if dbo.validare_Text(@oras) <> 1
			begin
				print 'Oras'
				raiserror('Oras invalid',14,1);
			end
		if dbo.validare_Text(@strada) <> 1
			begin
				print 'Strada'
				raiserror('Strada invalid',14,1);
			end
		if dbo.validate_Numar(@numar) <> 1
			begin
				print 'Numar'
				raiserror('Numar invalid',14,1);
			end

		INSERT INTO Locatii(oras,strada,nr)
		values
		(@oras,@strada,@numar)

		print 'Locatie adaugata'

		commit tran
		print 'Transaction committed'
	end try

	begin catch
		rollback tran
		print ERROR_MESSAGE();
		print 'Transaction rollbacked'
		return 0
	end catch

	return 1
end
go

create or alter procedure AddDistribuitor
	@nume nvarchar(50), @durata_medie_livrare int as
begin
begin tran
	begin try

		if dbo.validare_Text(@nume) <> 1
			begin
				print 'Nume'
				raiserror('Nume',14,1)
			end

		if dbo.validate_Numar(@durata_medie_livrare) <> 1
			begin
				print 'Durata Medie Livrare'
				raiserror('Durata Medie Livrare',14,1)
			end

		INSERT INTO Distribuitori(nume,durata_medie_livrare)
		values
		(@nume,@durata_medie_livrare)

		print 'Distribuitor adaugat'

		commit tran
		print 'Transaction committed'
	end try

	begin catch
		rollback tran
		print ERROR_MESSAGE();
		print 'Transaction rollbacked'
		return 0
	end catch

	return 1
end
go

create or alter procedure AddComanda
	@oras nvarchar(50), @strada nvarchar(50), @numar int,
	@nume nvarchar(50), @durata_medie_livrare int,
	@data nvarchar(20) as
begin
	declare @locatie_added bit;
	declare @ditribuitor_added bit;

	EXECUTE @locatie_added = AddLocatie @oras,@strada,@numar
	EXECUTE @ditribuitor_added = AddDistribuitor @nume,@durata_medie_livrare

	if @locatie_added <> 1
		begin
			print 'Locatie not added so we cannot add Comanda'
			return 0
		end

	if @ditribuitor_added <> 1
		begin
			print 'Distribuitor not added so we cannot add Comanda'
			return 0
		end

	if dbo.validare_Data(@data) <> 1
		begin
			print 'Format Data incorrect'
			return 0
		end

	declare @id_loc int;
	declare @id_dis int;

	SELECT TOP 1 @id_loc = L.id_l
	FROM dbo.Locatii as L
	WHERE L.oras = @oras and L.strada = @strada and L.nr = @numar;

	SELECT TOP 1 @id_dis = D.id_d
	FROM dbo.Distribuitori as D
	WHERE D.nume = @nume and D.durata_medie_livrare = @durata_medie_livrare;

	INSERT INTO Comenzi(id_l,id_d,data_efectuare)
	values
	(@id_loc,@id_dis,@data)

	print 'Comanda added'
end

--AddComanda ( oras , strada , numar     ,     nume , durata     ,     data )


Select * from Locatii;
Select * from Comenzi;
Select * from Distribuitori;

EXECUTE AddComanda 'Sarmas', 'MihaiEminescu', 72,
				   '', 7,
				   '12-04-2023';

Select * from Locatii;
Select * from Comenzi;
Select * from Distribuitori;


EXECUTE AddComanda 'Sarmas', 'MihaiEminescu', 72,
				   'asdf', 7,
				   '12-04-2023';

Select * from Locatii;
Select * from Comenzi;
Select * from Distribuitori;

delete from Comenzi where data_efectuare = '12-04-2023';
delete from Locatii where oras='Sarmas';
delete from	Distribuitori where nume='DanyTrans';