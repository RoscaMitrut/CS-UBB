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

create or alter procedure AddComanda1
	@oras nvarchar(50), @strada nvarchar(50), @numar int,
	@nume nvarchar(50), @durata_medie_livrare int,
	@data nvarchar(20) as
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
		if dbo.validare_Data(@data) <> 1
			begin
				print 'Data'
				raiserror('Data',14,1)
			end

		declare @id_loc int;
		declare @id_dis int;

		INSERT INTO Distribuitori(nume,durata_medie_livrare)
		values
		(@nume,@durata_medie_livrare)
		set @id_dis =SCOPE_IDENTITY()

		print 'Distribuitor adaugat'

		INSERT INTO Locatii(oras,strada,nr)
		values
		(@oras,@strada,@numar)
		SET @id_loc = SCOPE_IDENTITY()

		print 'Locatie adaugata'

		INSERT INTO Comenzi(id_l,id_d,data_efectuare)
		values
		(@id_loc,@id_dis,@data)

		commit tran
		select 'Transaction committed'
	end try

	begin catch
		rollback tran
		print ERROR_MESSAGE();
		select 'Transaction rollbacked'
	end catch
end

Select * from Locatii;
Select * from Comenzi;
Select * from Distribuitori;

EXECUTE AddComanda1 'Sarmas', 'MihaiEminescu', 72,
				   'DanyTrans', 7,
				   '12-04-2023';

Select * from Locatii;
Select * from Comenzi;
Select * from Distribuitori;

EXECUTE AddComanda1 '', 'MihaiEminescu', 72,
				   'DanyTrans', 7,
				   '12-04-2023';

Select * from Locatii;
Select * from Comenzi;
Select * from Distribuitori;

delete from Comenzi where data_efectuare = '12-04-2023';
delete from Locatii where oras='Sarmas';
delete from	Distribuitori where nume='DanyTrans';