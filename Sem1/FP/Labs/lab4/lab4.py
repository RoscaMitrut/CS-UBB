def generate_data():
	return [{'ziua': 7, 'suma': 15, 'tipul': 'mancare'},
			{'ziua': 12, 'suma': 145, 'tipul': 'telefon'},
			{'ziua': 6, 'suma': 79, 'tipul': 'altele'},
			{'ziua': 5, 'suma': 25, 'tipul': 'imbracaminte'},
			{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'},
			]
#lista de liste ? 
def afisare_interfata():
	print("\n", 
		"1.Adauga Cheltuiala\n",
		"2.Stergere\n",
		"3.Cautari\n",
		"4.Rapoarte\n",
		"5.Filtrare\n",
		"6.Undo\n",
		"7.Iesire\n",
		"Input: ",end="")

def list_with_selected_type(lista,sir):
	return [i for i in lista if i['tipul']==sir]


def ce_adaug():
	ceva = input("Ce adaugam? \n1.O noua cheltuiala\n2.Actualizam o cheltuiala\n")
	return ceva
def adauga_cheltuiala_1(lista):
	zi = int(input("Ziua: "))
	suma = int(input("Suma: "))
	tip = input("Tipul: ")
	cheltuiala = {'ziua': zi, 'suma': suma, 'tipul': tip}
	lista.append(cheltuiala)
def adauga_cheltuiala_2(lista):
	zi = int(input("Ziua: "))
	suma = int(input("Suma: "))
	tip = input("Tipul: ")
	lista_noua = [i for i in lista if i['ziua']!=zi or i['suma']!=suma or i['tipul']!=tip]
	return lista_noua

def ce_sterg():
	ceva = input("Ce stergem? \n 1.Toate cheltuielile pentru o zi data \n 2.Cheltuielile pentru un interval de timp(se da ziua de inceput si ziua de sfarsit) \n 3. Toate cheltuielile de un anumit tip \n")
	return ceva
def stergere_1(lista):
	zi_stergere = int(input("Care este ziua? \n"))
	lista_noua = [i for i in lista if i['ziua']!=zi_stergere]
	return lista_noua
def stergere_2(lista):
	zi_inceput = int(input("Ziua de inceput: "))
	zi_sfarsit = int(input("Ziua de sfarsit: "))
	lista_noua = [i for i in lista if i['ziua']<zi_inceput or i['ziua']>zi_sfarsit]
	return lista_noua
def stergere_3(lista):
	tip = input("Tipul: ")
	lista_noua = [i for i in lista if i['tipul']!=tip]
	return lista_noua

def ce_afisez():
	ceva = input("Ce Cautam?\n1.Cheltuieli mai mari decat o suma data\n2.Toate cheltuielile efenctuate inainte de o zi data si mai mici decat o suma\n3.Toate cheltuielile de un anumit tip\n")
	return ceva
def cautari_1(lista):
	suma = int(input("Suma: "))
	lista_noua = [i for i in lista if i['suma']>suma]
	print(lista_noua)
def cautari_2(lista):
	zi = int(input("Zi: "))
	suma = int(input("Suma: "))
	lista_noua = [i for i in lista if i['suma']<suma and i['ziua']<zi]
	print(lista_noua)
def cautari_3(lista):
	ceva = input("1.mancare\n2.telefon\n3.altele\n4.imbracaminte\n5.intretinere\n")
	match ceva:
		case '1':
			print(list_with_selected_type(lista,'mancare'))
		case '2':
			print(list_with_selected_type(lista,'telefon'))
		case '3':
			print(list_with_selected_type(lista,'altele'))
		case '4':
			print(list_with_selected_type(lista,'imbracaminte'))
		case '5':
			print(list_with_selected_type(lista,'intretinere'))
		case default:
			print("Input introdus gresit")

def ce_raportez():
	ceva = input('Ce raportam?\n1.Suma totala pentru un anumit tip de cheltuiala\n2.Ziua in care suma cheltuita este maxima\n3.Toate cheltuielile ce au o anumita suma\n4.Cheltuielile sortate dupa tip\n')
	return ceva
def rapoarte_1(lista):
	ceva = input("1.mancare\n2.telefon\n3.altele\n4.imbracaminte\n5.intretinere\n")
	suma =0
	match ceva:
		case '1':
			lista_noua = list_with_selected_type(lista,'mancare')
		case '2':
			lista_noua = list_with_selected_type(lista,'telefon')
		case '3':
			lista_noua = list_with_selected_type(lista,'altele')
		case '4':
			lista_noua = list_with_selected_type(lista,'imbracaminte')
		case '5':
			lista_noua = list_with_selected_type(lista,'intretinere')
		case default:
			print("Input introdus gresit")
	for elem in lista_noua:
		suma += elem['suma']
		print('Suma este',suma)
def rapoarte_2(lista):
	suma=0
	for element in lista:
		if element['suma']>suma:
			suma = element['suma']
			zi_suma_maxima = element['ziua']
	print("Ziua in care s-a cheltuit suma maxima este ",zi_suma_maxima)
def rapoarte_3(lista):
	suma_data = int(input('Suma:'))
	for element in lista:
		if element['suma']==suma_data:
			print(element)
def rapoarte_4(lista):
	print(sorted(lista, key=lambda lista: lista['tipul']))

def ce_filtrez():
	ceva = input('Ce filtram?\n1.Toate Cheltuielile de un anumit tip\n2.Toate cheltuielile mai mici decat o suma data\n')
	return ceva
def filtrare_1(lista):
	ceva = input("1.mancare\n2.telefon\n3.altele\n4.imbracaminte\n5.intretinere\n")
	match ceva:
		case '1':
			lista_noua = [i for i in lista if i['tipul']!='mancare']
		case '2':
			lista_noua = [i for i in lista if i['tipul']!='telefon']
		case '3':
			lista_noua = [i for i in lista if i['tipul']!='altele']
		case '4':
			lista_noua = [i for i in lista if i['tipul']!='imbracaminte']
		case '5':
			lista_noua = [i for i in lista if i['tipul']!='intretinere']
		case default:
			print("Input introdus gresit")	
	print(lista_noua)
def filtrare_2(lista):
	ceva = int(input('Suma:'))
	lista_noua = [i for i in lista if i['suma']<ceva]
	print(lista_noua)


def start():
	lista_cheltuieli_curente = generate_data()
	switch = False
	while not switch:
		afisare_interfata()
		ce_facem = input()

		if ce_facem == '1':
			ce_adaugam = ce_adaug()
			if ce_adaugam == '1':
				adauga_cheltuiala_1(lista_cheltuieli_curente)
			elif ce_adaugam == '2':
				lista_cheltuieli_curente = adauga_cheltuiala_2(lista_cheltuieli_curente)
			else:
				print("Input introdus gresit.")

		elif ce_facem=='2':
			ce_stergem = ce_sterg()
			if ce_stergem == '1':
				lista_cheltuieli_curente = stergere_1(lista_cheltuieli_curente)
			elif ce_stergem == '2':
				lista_cheltuieli_curente = stergere_2(lista_cheltuieli_curente)
			elif ce_stergem == '3':
				lista_cheltuieli_curente = stergere_3(lista_cheltuieli_curente)
			else:
				print("Input introdus gresit.")

		elif ce_facem=='3':
			ce_afisam = ce_afisez()
			if ce_afisam == '1':
				cautari_1(lista_cheltuieli_curente)
			elif ce_afisam == '2':
				cautari_2(lista_cheltuieli_curente)
			elif ce_afisam == '3':
				cautari_3(lista_cheltuieli_curente)
			else:
				print('Input introdus gresit.')

		elif ce_facem=='4':
			ce_raportam = ce_raportez()
			if ce_raportam == '1':
				rapoarte_1(lista_cheltuieli_curente)
			elif ce_raportam == '2':
				rapoarte_2(lista_cheltuieli_curente)
			elif ce_raportam == '3':
				rapoarte_3(lista_cheltuieli_curente)
			elif ce_raportam == '4':
				rapoarte_4(lista_cheltuieli_curente)
			else:
				print('Input introdus gresit.')

		elif ce_facem=='5':
			ce_filtram = ce_filtrez()
			if ce_filtram == '1':
				filtrare_1(lista_cheltuieli_curente)
			elif ce_filtram == '2':
				filtrare_2(lista_cheltuieli_curente)
			else:
				print('Input introdus gresit.')

		elif ce_facem == '7':
			switch = True
		


start()