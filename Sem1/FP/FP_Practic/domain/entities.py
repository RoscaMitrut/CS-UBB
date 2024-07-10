class tractor:
	'''
	clasa entitate pentru tractoare 
	'''
	def __init__(self,id,denumire,pret,model,data):
		'''
		functie de initializare tractor
		id = id-ul tractorului
		denumire = denumirea tractorului
		pret = pretul tractorului 
		model = modelul tractorului
		data = data in care expira revizia tractorului
		'''
		self.__id = id
		self.__denumire = denumire
		self.__pret = pret
		self.__model = model
		self.__data = data

	def get_id(self):
		'''
		returneaza id-ul unui tractor
		'''
		return self.__id

	def get_denumire(self):
		'''
		returneaza denumirea unui tractor
		'''
		return self.__denumire

	def get_pret(self):
		'''
		returneaza pretul unui tractor
		'''
		return self.__pret

	def get_model(self):
		''' 
		returneaza modelul unui tractor
		'''
		return self.__model

	def get_data(self):
		'''
		returneaza data in care expira revizia unui tractor
		'''
		return self.__data