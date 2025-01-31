from service.service import serv
from domain.entities import tractor
from repo.repo import repo
from console.ui import ui


def test_entities_tractor():
	tractor1=tractor("123","animale","5500","v456","27:01:2023")
	assert(tractor1.get_id()=="123")
	assert(tractor1.get_denumire()=="animale")
	assert(tractor1.get_pret()=="5500")
	assert(tractor1.get_model()=="v456")
	assert(tractor1.get_data()=="27:01:2023")
	print("Test entitate1 trecut cu succes!")
	tractor2=tractor("1","marfa","500","v6","29:01:2023")
	assert(tractor2.get_id()=="1")
	assert(tractor2.get_denumire()=="marfa")
	assert(tractor2.get_pret()=="500")
	assert(tractor2.get_model()=="v6")
	assert(tractor2.get_data()=="29:01:2023")
	print("Teste entitate2 tractor trecut cu succes!")

test_entities_tractor()

def test_stocare1_repo():
	nume_fisier = 'test.txt'
	repozitor = repo(nume_fisier)
	tractor1=tractor("123","animale","5500","v456","27:01:2023")
	tractor2=tractor("1","marfa","500","v6","29:01:2023")	
	lista = [tractor1,tractor2]
	repozitor.stocare_lista_tractoare(lista)
	print("test stocare1 repo trecut cu succes")

def test_stocare2_repo():
	nume_fisier = "test.txt"
	repozitor = repo(nume_fisier)
	repozitor.stocare_tractor("54","marfa","5605","v8","29:01:2023")
	print("Teste stocare2 repo trecut cu succes")


test_stocare1_repo()
test_stocare2_repo()
print("")
def test_repo_getter():
	nume_fisier = 'test.txt'
	repozitor = repo(nume_fisier)
	tractoare = repozitor.get_lista_tractoare()
	print("ID-uri din fisier:")
	for el in tractoare:
		print(f"id:{el.get_id()}")
	print("Test repo getter trecut cu succes!")

test_repo_getter()
print("")
def test_service():
	repozit = repo('test.txt')
	service = serv(repozit)
	assert(service.cerinta2(6)==1)
	assert(service.cerinta2(5)==2)
	print("Test service trecut cu succes!")

test_service()

def test_service2():
	repozit = repo('test.txt')
	service = serv(repozit)
	service.cerinta3('',-1)
	print('test2 service trecut cu succes')

test_service2()