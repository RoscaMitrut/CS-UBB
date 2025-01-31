from domain.entities import examen

class repo:
	def __init__(self,nume_fisier):
		self.__numefisier = nume_fisier

	def get_examene(self):
		fis = open(self.__numefisier,'r')
		lines = fis.readlines()
		lista_examene = []
		for line in lines:
			lista = [item for item in line.split(",")]
			lista[3]=lista[3][:-1]
			examen_temp = examen(lista[0],lista[1],lista[2],lista[3])
			lista_examene.append(examen_temp)
		return lista_examene

	def store_examen(self,data,ora,materie,stare):
		fis = open(self.__numefisier,'a')
		string = f"{data},{ora},{materie},{stare}\n"
		fis.write(string)

	def cerinta3(self,nume_fisier,sir):
		fis = open(nume_fisier,"w")
		examene = self.get_examene()
		lista = []
		for examen in examene:
			lista.append([examen.get_data(),examen.get_ora(),examen.get_materie(),examen.get_stare()])

		lista = [x for x in lista if sir in x[2]]

		lista = sorted(lista,key=lambda x:x[0])
		for element in lista:
			for i in range(0,3):
				fis.write(element[i])
				fis.write(",")
			fis.write(element[3])
			fis.write("\n")