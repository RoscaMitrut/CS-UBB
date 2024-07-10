import datetime
class serv:
	'''
	clasa pentru service
	'''
	def __init__(self,repo):
		'''
		functie initializare cu repository
		'''
		self.__repo = repo

	def cerinta1(self,idul,denumire,pret,model,data):
		'''
		functie care preia datele unui tractor din consola si le transmite la repository pentru a fi stocate intr-un fisier
		id = idul pe care dorim sa il folosim la crearea noului tractor
		denumire = denumirea pe care dorim sa o folosim la crearea noului tractor
		pret = pretul pe care dorim sa il folosim la crearea noului tractor
		model = modelul pe care dorim sa il folosim la crearea noului tractor
		data = data de expirare a reviziei pe care dorim sa o folosim la crearea noului tractor
		'''
		self.__repo.stocare_tractor(idul,denumire,pret,model,data)

	def cerinta2(self,cifra):
		'''
		functie care sterge din fisier tractoarele care au o anumita cifra in pret
		cifra= cifra pe care o au tractoarele care urmeaza sa fie sterse
		'''
		lista_tractoare = self.__repo.get_lista_tractoare()
		nr_sterse = 0
		cifra = str(cifra)
		lista_tractoare_noua = []
		for tractor in lista_tractoare:
			pret = tractor.get_pret()
			if cifra in pret:
				nr_sterse += 1
				continue
			lista_tractoare_noua.append(tractor)
		
		self.__repo.stocare_lista_tractoare(lista_tractoare_noua)
		return nr_sterse


	def cerinta3(self,text,numar):
		'''
		functie care printeaza lista de tractoare care respecta filtrele:
		text = text care trebuie sa se afle in denumirea tractorului
		numar = numar care trebuie sa fie mai mare decat pretul tractoarelor
		'''
		lista_tractoare_noua = []
		lista_tractoare_noua2 = []
		lista_finala = []
		avem_text = False
		avem_numar = False
		lista_tractoare = self.__repo.get_lista_tractoare()
		
		if text != "":
			avem_text = True
			for tractor in lista_tractoare:
				denumire = tractor.get_denumire()
				if text in denumire:
					lista_tractoare_noua.append(tractor)

		if numar != -1:
			avem_numar = True
			for tractor in lista_tractoare:
				pret = tractor.get_pret()
				pret = int(pret)
				if pret<numar:
					lista_tractoare_noua2.append(tractor)
		
		if avem_text and avem_numar:
			for elem in lista_tractoare:
				if elem in lista_tractoare_noua and elem in lista_tractoare_noua2:
					lista_finala.append(elem)
			lista_finala = lista_finala

		elif avem_text:
			lista_finala = lista_tractoare_noua
		
		elif avem_numar:
			lista_finala = lista_tractoare_noua2
		
		else:
			lista_finala = lista_tractoare

		data_azi = datetime.date.today()
		data_azi = str(data_azi)
		data_azi = data_azi[8:]

		for tractor in lista_finala:
			if int(data_azi)>int(tractor.get_data()[:2]):
				string = f"{tractor.get_id()}  * {tractor.get_denumire()}  {tractor.get_pret()}  {tractor.get_model()}  {tractor.get_data()[:-1]}"
				print(string)
			else:
				string = f"{tractor.get_id()}  {tractor.get_denumire()}  {tractor.get_pret()}  {tractor.get_model()}  {tractor.get_data()[:-1]}"
				print(string)