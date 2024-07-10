from ui import *

avem_liste_sau_dictionare = 'liste'
#avem_liste_sau_dictionare = 'dictionare'

def lista_sau_dictionar(lista):
	if type(lista[0])==list:
		zi = 0
		suma = 1
		tip = 2
	else:
		zi = 'ziua'
		suma = 'suma'
		tip = 'tipul'
	return zi,suma,tip		

if avem_liste_sau_dictionare == 'liste':
	zi_ = 0
	suma_ = 1
	tip_ = 2
elif avem_liste_sau_dictionare == 'dictionare':
	zi_ = 'ziua'
	suma_ = 'suma'
	tip_ = 'tipul'




def adauga_cheltuiala_la_lista(lista,zi,suma,tip):
	'''
	functie care adauga o cheltuiala la lista de cheltuieli
	lista = lista de cheltuieli in care dorim sa adaugam cheltuiala
	zi,suma,tip = cheltuiala pe care dorim sa o adaugam in lista de cheltuieli
	'''
	if avem_liste_sau_dictionare == 'liste':
		lista_noua = lista
		lista_noua.append([zi,suma,tip])
	elif avem_liste_sau_dictionare == 'dictionare':
		lista_noua = lista
		lista_noua.append({'ziua':zi,'suma':suma,'tipul':tip})
	return lista_noua

def reinoire_lista(lista,zi_cautata,suma_cautata,tip_cautat,zi_noua,suma_noua,tip_nou):
	'''
	functie care actualizeaza o cheltuiala dintr-o lista de cheltuieli
	lista = lista de cheltuieli in care dorim sa facem o modificare
	zi_cautata,suma_cautata,tip_cautat = cheltuiala pe care dorim sa o inlocuim
	zi_noua,suma_noua,tip_nou = cheltuiala cu care inlocuim cheltuiala veche
	'''
	if avem_liste_sau_dictionare == 'liste':
		lista_noua = [zi_noua,suma_noua,tip_nou]
	elif avem_liste_sau_dictionare == 'dictionare':
		lista_noua = {'ziua':zi,'suma':suma,'tipul':tip}
	for i in lista:
		if i[zi_]==zi_cautata and i[suma_]==suma_cautata and i[tip_]==tip_cautat:
			i[zi_]=lista_noua[zi_]
			i[suma_]=lista_noua[suma_]
			i[tip_]=lista_noua[tip_]
	return lista

def stergere_cheltuieli_zi(lista_de_modificat,zi):
	'''
	functie care sterge cheltuielile dintr-o zi data dintr-o lista de cheltuieli
	lista_de_modifica = lista de cheltuieli din care dorim sa stergem cheltuielile dintr-o anumita zi
	zi = ziua din care dorim sa stergem cheltuielile din lista de cheltuieli
	''' 
	zi_,suma_,tip_=lista_sau_dictionar(lista_de_modificat)
	return [i for i in lista_de_modificat if i[zi_]!=zi]

def stergere_cheltuieli_interval(lista_de_modificat,zi_inceput,zi_sfarsit):
	'''
	functie care sterge din lista de cheltuieli cheltuielile facute intr-un interval de timp
	lista_de_modificat = lista de cheltuieli in care facem modificari
	zi_inceput,zi_sfarsit = intervalul din care dorim sa stergem cheltuieli
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista_de_modificat)
	return [i for i in lista_de_modificat if i[zi_]<zi_inceput or i[zi_]>zi_sfarsit]

def stergere_cheltuieli_tip(lista_de_modificat,tip):
	'''
	functie care sterge din lista de cheltuieli cheltuielile de un anumit tip
	lista_de_modificat = lista de cheltuieli pe care o modificam
	tip = tipul cheltuielilor pe care dorim sa le stergem
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista_de_modificat)
	return [i for i in lista_de_modificat if i[tip_]!=tip]

def cautare_mM_decat_suma(lista,suma):
	'''
	functie care returneaza cheltuielile din lista de cheltuieli mai mari decat o suma
	lista = lista de cheltuieli in care cautam cheltuielile mai mari decat o suma
	suma = suma pe care trebuie sa o depaseasca suma cheltuielilor
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista)
	return [i for i in lista if i[suma_]>suma]

def cautare_inainte_de_zi_si_mm_decat_suma(lista,zi,suma):
	'''
	functie care returneaza cheltuielile din lista de cheltuieli care au fost facute inainte de o zi si au o suma mai mica decat o suma data
	lista = lista de cheltuieli in care cautam
	zi = ziua pe care nu trebuie sa o depaseasca cheltuielile
	suma = suma pe care nu trebuie sa o depaseasca cheltuielile
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista)
	return [i for i in lista if i[suma_]<suma and i[zi_]<zi]

def cautare_cheltuieli_de_un_tip(lista,tip):
	'''
	functia returneaza lista de cheltuieli care au un anumit tip
	lista = lista de cheltuieli in care cautam
	tip = tipul cautat 
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista)
	return [i for i in lista if i[tip_]==tip]

def suma_cheltuieli_de_un_tip(lista,tip):
	'''
	functia returneaza suma cheltuielilor de un anumit tip din lista de cheltuieli
	lista = lista de cheltuieli in care cautam
	tip = tipul a caror sume le cautam
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista)
	suma = 0
	for elem in lista:
		if elem[tip_]==tip:
			suma += elem[suma_]
	return suma

def cheltuiala_maxima(lista):
	'''
	functia returneaza ziua in care cheltuiala a fost maxima in o lista de cheltuieli
	lista = lista in care cautam ziua in care s-a facut cheltuiala maxima
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista)
	suma = 0
	for element in lista:
		if element[suma_]>suma:
			suma = element[suma_]
			zi_suma_maxima = element[zi_]
	return zi_suma_maxima

def cheltuieli_de_anumita_suma(lista,suma):
	'''
	functia returneaza cheltuielile care au o anumita suma dintr-o lista de cheltuieli
	lista = lista de cheltuieli in care cautam cheltuielile de o anumita suma
	suma = suma pe care trebuie sa o aiba cheltuielile
	'''
	for element in lista:
		if element[suma_]==suma:
			print(element)

def sorteaza_lista(lista):
	'''
	functia afiseaza lista de cheltuieli sortata dupa tipul cheltuielilor
	lista = lista de cheltuieli pe care o sortam
	'''
	print(sorted(lista, key=lambda lista: lista[tip_]))

def cheltuieli_fara_un_tip(lista,tip_dat):
	'''
	functia returneaza lista de cheltuieli din care scoatem cheltuielile care au un anumit tip
	lista - lista de cheltuieli din care scoatem anumite cheltuieli
	tip_dat = tipul cheltuielilor pe care le scoatem din lista
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista)
	return [i for i in lista if i[tip_]!=tip_dat]

def cheltuieli_mm_decat_o_suma(lista,suma_data):
	'''
	functia returneaza lista de cheltuieli din care scoatem cheltuielile mai mici decat o suma
	lista = lista de cheltuieli din care scoatem anumite cheltuieli
	suma_data suma pe care nu trebuie sa o depasteasca cheltuielile
	'''
	zi_,suma_,tip_=lista_sau_dictionar(lista)
	return [i for i in lista if i[suma_]<suma_data]
#-----------------------------------------------------------------------------
def get_user_input_zi():
	'''
	functie care ia ca input ziua unei cheltuieli de la user
	'''
	switch = True
	while switch:
		zi_input = input("Ziua:")
		switch = False
		try:
			zi_input=int(zi_input)
		except:
			afisare_input_gresit()
			switch = True
	return zi_input


def get_user_input_suma():
	'''
	functie care ia ca input suma unei cheltuieli de la user
	'''
	switch = True
	while switch:
		suma_input = input("Suma:")
		switch = False
		try:
			suma_input=int(suma_input)
		except:
			afisare_input_gresit()
			switch = True
	return suma_input


def get_user_input_tip():
	'''
	functie care ia ca input tipul unei cheltuieli de la user
	'''
	switch = True
	while switch:
		tip_input = input("Tip:\n1.mancare\n2.telefon\n3.altele\n4.imbracaminte\n5.intretinere\n")
		switch = False
		if tip_input == "1":
			tip_input = "mancare"
		elif tip_input == "2":
			tip_input = "telefon"
		elif tip_input == "3":
			tip_input = "altele"
		elif tip_input == "4":
			tip_input = "imbracaminte"
		elif tip_input == "5":
			tip_input = "intretinere"
		else:
			afisare_input_gresit()
			switch=True
	return tip_input


