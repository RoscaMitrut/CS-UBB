import datetime
class service:
	def __init__(self,repo):
		self.__repo = repo

	def prima_afisare(self):
		lista_examene = self.__repo.get_examene()
		lista = []
		data_azi = datetime.date.today()
		data_azi = str(data_azi)
		data_azi = data_azi[8:]
		data_azi = int(data_azi)
		
		for examen in lista_examene:
			data = examen.get_data()
			data = data[:2]
			data = int(data)
			if data == data_azi+1:
				lista.append([examen.get_data(),examen.get_ora(),examen.get_materie(),examen.get_stare()])

		lista = sorted(lista,key=lambda x:x[1])

		for examen in lista:
			print(examen)

	def adaugare_examen(self):
		try:
			data = input("data examen:")
		except ValueError:
			print("Eroare la citire data")

		try:
			ora = input("ora examen:")
		except ValueError:
			print("Eroare la citire ora")

		try:
			materie = input("materie examen:")
		except ValueError:
			print("Eroare la citire materie")

		try:
			stare = input("stare materie:")
		except ValueError:
			print("Eroare la citire stare")
		
		self.__repo.store_examen(data,ora,materie,stare)

	def cerinta2(self):
		
		try:
			data = input("data examen:")
		except ValueError:
			print("Eroare la citire data")
		data = data[:2]
		data = int(data)
		lista = []
		lista_examene = self.__repo.get_examene()
		for examen in lista_examene:
			data2 = examen.get_data()
			data2 = data2[:2]
			data2 = int(data2)
			if data+3 >= data2 and data2>data:
				lista.append([examen.get_data(),examen.get_ora(),examen.get_materie(),examen.get_stare()])


		for examen in lista:
			print(examen)

	def cerinta3(self):
		try:
			nume = input("nume fisier:")
		except ValueError:
			print("Eroare la citire nume fisier")
		try:
			sir = input("sir:")
		except ValueError:
			print("Eroare la citire sir")

		self.__repo.cerinta3(nume,sir)