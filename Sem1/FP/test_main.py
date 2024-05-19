from domain.validators import *
from domain.entities import *
from repository.repo import *
from service.service import *
from ui.console import *

def test_add_student():
	#repo = InMemoryRepository()
	repo = InMemoryRepository('studenti.txt','probleme.txt','note.txt')

	validator = Validatorul()
	test_srv = Service(repo,validator)
	student_adaugat = test_srv.add_student(1,'Ion',216)

	assert(student_adaugat.get_studentID()==1)
	assert(student_adaugat.get_nume()=='Ion')
	assert(student_adaugat.get_grup()==216)

	print("Test trecut cu succes!")

def test_add_pb():
	repo = InMemoryRepository()
	validator = Validatorul()
	test_srv = Service(repo,validator)
	pb_adaugata = test_srv.add_pb(1,'Calculati 2*5',16)

	assert(pb_adaugata.get_nr_lab_pb()==1)
	assert(pb_adaugata.get_descriere()=='Calculati 2*5')
	assert(pb_adaugata.get_deadline()==16)

	print("Test trecut cu succes!")

def test_get_all_student():
	repo = InMemoryRepository()
	validator = Validatorul()
	test_srv = Service(repo,validator)
	test_srv.add_student(1,'Ion',216)
	test_srv.add_student(2,'Vasile',214)

	assert(type(test_srv.get_all_students())==list)
	assert(len(test_srv.get_all_students())==2)

	print("Test trecut cu succes!")

def test_get_all_problems():
	repo = InMemoryRepository()
	validator = Validatorul()
	test_srv = Service(repo,validator)
	test_srv.add_pb(1,'Calculati 2*5',16)
	test_srv.add_pb(2,'Calculati 7*8',13)

	assert(type(test_srv.get_all_problems())==list)
	assert(len(test_srv.get_all_problems())==2)

	print("Test trecut cu succes!")


def test_delete_student():
	repo = InMemoryRepository()
	validator = Validatorul()
	test_srv = Service(repo,validator)

	test_srv.add_student(1,'Ion',216)
	test_srv.delete_student(1)

	assert(len(test_srv.get_all_students())==0)

	print("Test trecut cu succes!")

def test_delete_problem():
	repo = InMemoryRepository()
	validator = Validatorul()
	test_srv = Service(repo,validator)

	test_srv.add_pb(1,'Calculati 2*5',16)
	test_srv.delete_problem(1)

	assert(len(test_srv.get_all_problems())==0)

	print("Test trecut cu succes!")

def test_get_student_by_id():
	repo = InMemoryRepository()
	validator = Validatorul()
	test_srv = Service(repo,validator)

	test_srv.add_student(1,'Ion',216)
	test_srv.add_student(2,'Vasile',214)

	student = test_srv.get_student_by_id(2)
	assert(student.get_studentID()==2)
	assert(student.get_nume()=='Vasile')
	assert(student.get_grup()==214)

	print("Test trecut cu succes!")

def test_get_problem_by_number():
	repo = InMemoryRepository()
	validator = Validatorul()
	test_srv = Service(repo,validator)

	test_srv.add_pb(1,'Calculati 2*5',16)
	test_srv.add_pb(2,'Calculati 7*8',13)

	prob = test_srv.get_problem_by_number(2)
	assert(prob.get_nr_lab_pb()==2)
	assert(prob.get_descriere()=='Calculati 7*8')
	assert(prob.get_deadline()==13)

	print("Test trecut cu succes!")

def test_create_student():
	student1 = student(1,'Ion',216)
	assert(student1.get_studentID()==1)
	assert(student1.get_nume()=='Ion')
	assert(student1.get_grup()==216)

	student1.set_studentID(2)
	student1.set_nume('Vasile')
	student1.set_grup(214)

	assert(student1.get_studentID()==2)
	assert(student1.get_nume()=='Vasile')
	assert(student1.get_grup()==214)

	print("Test trecut cu succes!")

def test_create_problem():
	problem1= pb_lab(1,'Calculati 2*5',16)
	assert(problem1.get_nr_lab_pb()==1)
	assert(problem1.get_descriere()=='Calculati 2*5')
	assert(problem1.get_deadline()==16)

	problem1.set_nr_lab_pb(2)
	problem1.set_descriere('Calculati 7*8')
	problem1.set_deadline(13)

	assert(problem1.get_nr_lab_pb()==2)
	assert(problem1.get_descriere()=='Calculati 7*8')
	assert(problem1.get_deadline()==13)

	print("Test trecut cu succes!")

def test_create_note():
	nota1 = note_lab(1,1,9)
	assert(nota1.get_id_student()==1)
	assert(nota1.get_nr_problema()==1)
	assert(nota1.get_nota()==9)

	nota1.set_id_student(2)
	nota1.set_nr_problema(2)
	nota1.set_nota(6)

	assert(nota1.get_id_student()==2)
	assert(nota1.get_nr_problema()==2)
	assert(nota1.get_nota()==6)

	print("Test trecut cu succes!")