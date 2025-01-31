from domain.entities import tractor
class repo:
	'''
	clasa pentru repository
	'''
	def __init__(self,nume_fisier):
		'''
		functie initializare cu numele fisierului
		'''
		self.__nume_fisier = nume_fisier

	def get_lista_tractoare(self):
		'''
		functie care returneaza sub forma unei liste tractoarele aflate in fisierul dat
		'''
		fis = open(self.__nume_fisier,'r')
		lines = fis.readlines()
		lista=[]
		for line in lines:
			lista2 = [x for x in line.split(";")]
			tractor_temp = tractor(lista2[0],lista2[1],lista2[2],lista2[3],lista2[4])
			lista.append(tractor_temp)

		return lista

	def stocare_tractor(self,idul,denumire,pret,model,data):
		'''
		functie de stocare a unui tractor in fisierul dat
		id = idul pe care dorim sa il folosim la crearea noului tractor
		denumire = denumirea pe care dorim sa o folosim la crearea noului tractor
		pret = pretul pe care dorim sa il folosim la crearea noului tractor
		model = modelul pe care dorim sa il folosim la crearea noului tractor
		data = data de expirare a reviziei pe care dorim sa o folosim la crearea noului tractor
		'''
		fis = open(self.__nume_fisier,'a')
		string = f"{idul};{denumire};{pret};{model};{data}\n"
		fis.write(string)

	def stocare_lista_tractoare(self,lista_tractoare):
		'''
		functie care stocheaza o lista de tractoare in fisierul dat
		lista_tractoare = lista in care se afla tractoarele pe care dorim sa le stocam
		'''
		fis = open(self.__nume_fisier,'w')
		for tractor in lista_tractoare:
			string = f"{tractor.get_id()};{tractor.get_denumire()};{tractor.get_pret()};{tractor.get_model()};{tractor.get_data()[:-1]}\n"
			fis.write(string)			