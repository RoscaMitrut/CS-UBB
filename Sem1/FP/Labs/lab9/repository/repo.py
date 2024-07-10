from domain.entities import student,pb_lab,note_lab
"""
class InMemoryRepository:

	def __init__(self):
		self.__students = []
		self.__probleme = []
		self.__note = []
#---------------------------------------------------------------------------------------------------
	def find_student_id(self,id):
		'''
		cauta studentul cu un id dat
		'''
		for student in self.__students:
			if student.get_studentID()== id:
				return student
		return None

	def find_problem(self,numar):
		'''
		cauta problema cu un nr dat
		'''
		for problema in self.__probleme:
			if problema.get_nr_lab_pb()==numar:
				return problema
		return None
#---------------------------------------------------------------------------------------------------
	def store_student(self,student):
		'''
		stocam un student
		'''
		if self.find_student_id(student.get_studentID()) is not None:
			raise ValueError('Exista deja student cu acest id')
		self.__students.append(student)

	def store_pb(self,problema):
		'''
		stocam o problema
		'''
		if self.find_problem(problema.get_nr_lab_pb()) is not None:
			raise ValueError("Exista deja problema asta")
		self.__probleme.append(problema)
#---------------------------------------------------------------------------------------------------
	def get_all_students(self):
		'''
		returnam toti studentii
		'''
		return self.__students

	def get_all_problems(self):
		'''
		returnama toate problemele
		'''
		return self.__probleme
#---------------------------------------------------------------------------------------------------
	def delete_student_by_id(self,id):
		'''
		stergem un student pe baza unui id
		'''
		student = self.find_student_id(id)
		if student is None:
			raise ValueError("Nu exista student cu acest id")
		self.__students.remove(student)
		return student

	def delete_problem_by_number(self,numar):
		'''
		stergem o problema pe baza unui numar
		'''
		problema = self.find_problem(numar)
		if problema is None:
			raise ValueError("Nu exista problema cu acest numar")
		self.__probleme.remove(problema)
		return problema
#---------------------------------------------------------------------------------------------------
	def store_nota(self,notare):
		'''
		stocam o nota
		'''
		self.__note.append(notare)

	def get_all_grades(self):
		'''
		returnam toate notele
		'''
		return self.__note

"""


class InMemoryRepository:
	def __init__(self,filename_s,filename_p,filename_g):
		self.__filename_s = filename_s
		self.__filename_p = filename_p
		self.__filename_g = filename_g

	def __load_from_file_student(self):
		"""
		incarcam studentii din fisier
		"""
		try:
			f = open(self.__filename_s,'r')
		except IOError:
			print('Probleme la deschidere fisier')

		studenti = []
		lines = f.readlines()
		for line in lines:
			student_id,student_name,student_group = [token.strip() for token in line.split(';')]
			s = student(int(student_id),student_name,int(student_group))
			studenti.append(s)
		f.close()
		return studenti

	def __save_to_file_student(self,lista_studenti):
		"""
		salvam studentii in fisier
		"""
		with open(self.__filename_s, 'w') as f:
			for student in lista_studenti:
				string_studenti = str(student.get_studentID()) + ';' + student.get_nume() + ';' + str(student.get_grup()) + '\n'
				f.write(string_studenti)

	def __load_from_file_problem(self):
		"""
		incarcam problemele din fisier
		"""
		try:
			f = open(self.__filename_p,'r')
		except IOError:
			print('Probleme la deschidere fisier')

		probleme = []
		lines = f.readlines()
		for line in lines:
			problem_nr, problem_descriere, problem_deadline = [token.strip() for token in line.split(';')]
			p = pb_lab(int(problem_nr),problem_descriere,int(problem_deadline))
			probleme.append(p)
		f.close()
		return probleme

	def __save_to_file_problem(self,lista_probleme):
		"""
		salvam problemele in fisier
		"""
		with open(self.__filename_p, 'w') as f:
			for problema in lista_probleme:
				string_probleme = str(problema.get_nr_lab_pb()) + ';' + problema.get_descriere() + ';' + str(problema.get_deadline()) + '\n'
				f.write(string_probleme)

	def __load_from_file_grade(self):
		"""
		incarcam notele din fisier
		"""
		try:
			f = open(self.__filename_g,'r')
		except IOError:
			print('Probleme la deschidere fisier')

		note = []
		lines = f.readlines()
		for line in lines:
			grade_id,grade_nr,grade_grade = [token.strip() for token in line.split(';')]
			nota = note_lab(int(grade_id),int(grade_nr),int(grade_grade))
			note.append(nota)
		f.close()
		return note

	def __save_to_file_grade(self,lista_note):
		"""
		salvam notele in fiisier
		"""
		with open(self.__filename_g, 'w') as f:
			for nota in lista_note:
				string_note = str(nota.get_id_student()) + ';' + str(nota.get_nr_problema()) + ';' + str(nota.get_nota()) + '\n'
				f.write(string_note)

	def find_student_id(self,id):
		'''
		cauta studentul cu un id dat
		'''
		studenti = self.__load_from_file_student()
		for student in studenti:
			if student.get_studentID() == id:
				return student
		return None

	def find_problem(self,numar):
		'''
		cauta problema cu un nr dat
		'''
		probleme = self.__load_from_file_problem()
		for problema in probleme:
			if problema.get_nr_lab_pb() == numar:
				return problema
		return None

	def store_student(self,student):
		'''
		stocam un student
		'''
		studenti = self.__load_from_file_student()
		if student in studenti:
			print("Studenti duplicat")
			return
		studenti.append(student)
		self.__save_to_file_student(studenti)

	def store_pb(self,problema):
		'''
		stocam o problema
		'''
		probleme = self.__load_from_file_problem()
		if problema in probleme:
			print("Problema duplicata")
			return
		probleme.append(problema)
		self.__save_to_file_problem(probleme)

	def get_all_students(self):
		'''
		returnam toti studentii
		'''
		return self.__load_from_file_student()

	def get_all_problems(self):
		'''
		returnama toate problemele
		'''
		return self.__load_from_file_problem()

	def delete_student_by_id(self,id):
		'''
		stergem un student pe baza unui id
		'''
		studenti = self.__load_from_file_student()
		student_de_sters = self.__find_student_id(id)
		if student_de_sters is None:
			raise ValueError("Nu exista student cu acest id")
		studenti.remove(student_de_sters)
		self.__save_to_file_student(studenti)
		return student__de_sters

	def delete_problem_by_number(self,numar):
		'''
		stergem o problema pe baza unui numar
		'''
		probleme = self.__load_from_file_problem()
		problema_de_sters = self.__find_problem(numar)
		if problema_de_sters is None:
			raise ValueError("Nu exista problema cu nr asta")
		probleme.remove(problema_de_sters)
		self.__save_to_file_problem(probleme)
		return problema_de_sters

	def store_nota(self,notare):
		'''
		stocam o nota
		'''
		note = self.__load_from_file_grade()
		if notare in note:
			print("Nota duplicata")
			return
		note.append(notare)
		self.__save_to_file_grade(note)

	def get_all_grades(self):
		'''
		returnam toate notele
		'''
		return self.__load_from_file_grade()