from domain.entities import student
from domain.entities import pb_lab
from domain.entities import note_lab
class Validatorul:
	def validate_student(self,student):
		'''
		validator de studenti
		'''
		errors = []
		if student.get_studentID() < 0:
			errors.append('ID-ul trebuie sa fie mai mare decat 0')
		if len(student.get_nume())<2:
			errors.append('Numele trebuie sa aiba mai mult de 2 litere')
		if student.get_grup() < 100:
			errors.append('Grupa trebuie sa fie mai mare decat 100')

		if len(errors) > 0:
			errors_string = '\n.'.join(errors)
			raise ValueError(errors_string)

	def validate_problem(self,prob):
		'''
		validator de probleme
		'''
		errors = []
		if prob.get_nr_lab_pb() < 0:
			errors.append('Numarul problemei trebuie sa fie mai mare decat 0')
		if len(prob.get_descriere())<10:
			errors.append('Descrierea trebuie sa aiba mai mult de 10 caractere')
		if prob.get_deadline()<1 or prob.get_deadline()>31:
			errors.append('Deadline-ul trebuie sa fie intre 1 si 31')

		if len(errors) > 0:
			errors_string = '\n'.join(errors)
			raise ValueError(errors_string)

	def validate_grade(self,grade):
		'''
		validator de note
		'''
		errors=[]
		if grade.get_nota() < 1 or grade.get_nota()>10:
			errors.append("Nota trebuie sa fie intre 1 si 10")
		if len(errors) >0:
			errors_string = '\n',join(errors)
			raise ValueError(errors_string)