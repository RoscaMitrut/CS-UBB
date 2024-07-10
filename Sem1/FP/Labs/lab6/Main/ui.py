'''
mesajele pe care le afisam in meniuri si in cazul unui input gresit
'''
mesaj_interfata_principala = "\n1.Adauga Cheltuiala\n2.Stergere\n3.Cautari\n4.Rapoarte\n5.Filtrare\n6.Undo\n7.Iesire\nInput: "
mesaj_interfata_adaugare = "Ce adaugam? \n1.O noua cheltuiala\n2.Actualizam o cheltuiala\n"
mesaj_interfata_stergere = "Ce stergem? \n 1.Toate cheltuielile pentru o zi data \n 2.Cheltuielile pentru un interval de timp(se da ziua de inceput si ziua de sfarsit) \n 3.Toate cheltuielile de un anumit tip \n"
mesaj_interfata_cautare = "Ce Cautam?\n1.Cheltuieli mai mari decat o suma data\n2.Toate cheltuielile efectuate inainte de o zi data si mai mici decat o suma\n3.Toate cheltuielile de un anumit tip\n"
mesaj_interfata_raport = "Ce raportam?\n1.Suma totala pentru un anumit tip de cheltuiala\n2.Ziua in care suma cheltuita este maxima\n3.Toate cheltuielile ce au o anumita suma\n4.Cheltuielile sortate dupa tip\n"
mesaj_interfata_filtrare = "Ce filtram?\n1.Toate Cheltuielile de un anumit tip\n2.Toate cheltuielile mai mici decat o suma data\n"
mesaj_input_gresit = "Input introdus gresit."


def afisare_interfata_principala():
	'''
	functie care afiseaza interfata principala si ia inputul utilizatorului
	'''
	input_user = input(mesaj_interfata_principala)
	return input_user


def get_user_input(text_shown_to_user):
	'''
	functie care ia inputul utilizator si il returneaza
	text_shown_to_user = mesajul aratat utilizatorului pentru inputul care urmeaza sa fie luat
	'''
	user_input = input(text_shown_to_user)
	return user_input


def afisare_input_gresit():
	'''
	functie care tipareste un mesaj in cazul unui input gresit
	'''
	print(mesaj_input_gresit)


def afisare_lista(lista):
	'''
	functie care tipareste o lista
	lista = lista pe care dorim sa o tiparim
	'''
	print(lista)