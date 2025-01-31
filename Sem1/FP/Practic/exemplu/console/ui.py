class ui:
	def __init__(self,serv):
		self.__serv = serv
	def rulare_initiala(self):
		self.__serv.prima_afisare()

	def adauga_examen(self):
		self.__serv.adaugare_examen()
	def cerinta2(self):
		self.__serv.cerinta2()
	def cerinta3(self):
		self.__serv.cerinta3()
	def main(self):
		while True:
			cmd = input("Comanda: (cerinta1/cerinta2/cerinta3/exit)")
			if cmd == "cerinta1":
				self.adauga_examen()
			if cmd == "cerinta2":
				self.cerinta2()
			if cmd == "cerinta3":
				self.cerinta3()
			if cmd == "exit":
				break