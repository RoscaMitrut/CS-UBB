import datetime

class ui:
	'''
	clasa pentru ui
	'''
	def __init__(self,serv):
		'''
		functie initializare cu service
		'''
		self.__serv = serv

	def cerinta1(self):
		'''
		functie care ia de la user un id o denumire un pret un model si o data de expirare revizie si creaza un tractor cu datele respective, pe care il stocheaza in fisier
		'''
		try:
			idd = int(input("ID tractor: "))
			idd = str(idd)
		except ValueError:
			print("ID introdus gresit")

		denumire = input("Denumire tractor:")
		try:
			pret = int(input("pret tractor: "))
			pret = str(pret)
		except ValueError:
			print("pret introdus gresit")

		model = input("model tractor: ")

		data = input("data expirare revizie (dd:mm:yyyy): ")

		self.__serv.cerinta1(idd,denumire,pret,model,data)

	def cerinta2(self):
		'''
		funtie care ia ca input de la user o cifra si o transmite in service functiei2 din service
		'''
		try:
			cifra = int(input("Cifra: "))
			if cifra>9 or cifra < 0:
				raise ValueError
		except ValueError:
			print("Cifra introdusa gresit")

		nr_sterse = self.__serv.cerinta2(cifra)
		print("Numar tractoare sterse: ",nr_sterse)
	def cerinta3(self):
		'''
		functie care ia ca input de la user un filtru text si un filtru numar si le transmite la service in functia service3 
		'''
		string = input("filtru text:")
		try:
			numar = int(input("filtru numar:"))
		except ValueError:
			print("Numarul nu este un numar :/")
		self.__serv.cerinta3(string,numar)

	def afisare(self):
		'''
		"functia main" a UI-ului
		''' 
		while True:
			cmd = input("Comanda (comenzi valabile: cerinta1, cerinta2, cerinta3): ")
			if cmd == "cerinta1":
				self.cerinta1()
			elif cmd == "cerinta2":
				self.cerinta2()
			elif cmd == "cerinta3":
				self.cerinta3()
			elif cmd == "exit":
				break
			else:
				pass