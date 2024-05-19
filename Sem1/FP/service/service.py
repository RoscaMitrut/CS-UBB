from domain.entities import student,pb_lab,note_lab
from domain.validators import Validatorul
from repository.repo import InMemoryRepository

class Service:
	def __init__(self,repo,validator):
		self.__repo = repo
		self.__validator = validator
#---------------------------------------------------------------------------------------------------
	def add_student(self,id,nume,grup):
		'''
		stocheaza un student
		id = id student
		nume = nume student
		grup = grupa student
		'''
		s = student(id,nume,grup)
		self.__validator.validate_student(s)
		self.__repo.store_student(s)
		return s

	def add_pb(self,nr,descriere,deadline):
		'''
		stocheaza o problema
		nr = nr problema
		descriere = descriere problema
		deadline = deadline problema
		'''
		pb = pb_lab(nr,descriere,deadline)
		self.__validator.validate_problem(pb)
		self.__repo.store_pb(pb)
		return pb
#---------------------------------------------------------------------------------------------------
	def get_all_students(self):
		'''
		returneaza toti studentii
		'''
		return self.__repo.get_all_students()

	def get_all_problems(self):
		'''
		returneaza toate problemele
		'''
		return self.__repo.get_all_problems()
#---------------------------------------------------------------------------------------------------
	def delete_student(self,id):
		'''
		sterge un student
		id = idul studentului pe care dorim sa il stergem
		'''
		return self.__repo.delete_student_by_id(id)

	def delete_problem(self,numar):
		'''
		sterge o problema
		numar = numarul problemei pe care dorim sa il stergem
		'''
		return self.__repo.delete_problem_by_number(numar)
#---------------------------------------------------------------------------------------------------
	def get_student_by_id(self,id):
		'''
		returneaza un student pe baza unui id dat
		id= idul dat
		'''
		return self.__repo.find_student_id(id)
	def get_problem_by_number(self,nr):
		'''
		returneaza o problema pe baza unui nr dat
		nr = nr dat
		'''
		return self.__repo.find_problem(nr)
#---------------------------------------------------------------------------------------------------
	def create_grade(self,id,nr,grade):
		'''
		facem o nota
		id = idul studentuilui care a luat nota
		nr = nr problemei pe care a luat studentul nota
		grade = nota
		'''
		student = self.__repo.find_student_id(id)
		problem = self.__repo.find_problem(nr)
		if student is None:
			print('Studentul cu id',id,'nu exista')
			return
		if problem is None:
			print('Problema cu nr',nr,'nu exista')
			return
		notare = note_lab(id,nr,grade)
		self.__validator.validate_grade(notare)
		self.__repo.store_nota(notare)
		return notare

	def get_all_grades(self):
		'''
		returneaza toate notele
		'''
		return self.__repo.get_all_grades()