from ui import *
from utils import *
from adaugare import *
from stergere import *
from cautare import *
from raport import *
from filtrare import *
from undo import *
from teste import *
'''
def generate_data():
	#functie pentru generarea datelor initiale
	return [{'ziua': 7, 'suma': 15, 'tipul': 'mancare'},
			{'ziua': 12, 'suma': 145, 'tipul': 'telefon'},
			{'ziua': 6, 'suma': 79, 'tipul': 'altele'},
			{'ziua': 5, 'suma': 25, 'tipul': 'imbracaminte'},
			{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'},
			]
'''
def generate_data():
	
	#functie pentru generarea datelor initiale
	
	return [[7,15,'mancare'],
			[12,145,'telefon'],
			[6,79,'altele'],
			[5,25,'imbracaminte'],
			[27,40,'intretinere'],
			]

def start():
	'''
	functie principala in care se apeleaza restul functiilor (functia main)
	'''
	lista_cheltuieli_curente = generate_data()		

	lista_undo =[]
	lista_undo.append(lista_cheltuieli_curente.copy())

	contor_undo = 0 
	switch = False
	while not switch:

		input_interfata_principala = afisare_interfata_principala()

		if input_interfata_principala == '1':
			input_interfata_secundara = get_user_input(mesaj_interfata_adaugare)
			if input_interfata_secundara == '1':
				lista_cheltuieli_curente = adauga_cheltuiala_1(lista_cheltuieli_curente)
				lista_undo,contor_undo = undo_add(lista_cheltuieli_curente,lista_undo,contor_undo)
			elif input_interfata_secundara == '2':
				lista_cheltuieli_curente = adauga_cheltuiala_2(lista_cheltuieli_curente)
				lista_undo,contor_undo = undo_add(lista_cheltuieli_curente,lista_undo,contor_undo)

			else:
				afisare_input_gresit()
		elif input_interfata_principala=='2':
			input_interfata_secundara = get_user_input(mesaj_interfata_stergere)
			if input_interfata_secundara == '1':
				lista_cheltuieli_curente = stergere_1(lista_cheltuieli_curente)
				lista_undo,contor_undo = undo_add(lista_cheltuieli_curente,lista_undo,contor_undo)
			elif input_interfata_secundara == '2':
				lista_cheltuieli_curente = stergere_2(lista_cheltuieli_curente)
				lista_undo,contor_undo = undo_add(lista_cheltuieli_curente,lista_undo,contor_undo)
			elif input_interfata_secundara == '3':
				lista_cheltuieli_curente = stergere_3(lista_cheltuieli_curente)
				lista_undo,contor_undo = undo_add(lista_cheltuieli_curente,lista_undo,contor_undo)
			else:
				afisare_input_gresit()

		elif input_interfata_principala=='3':
			input_interfata_secundara = get_user_input(mesaj_interfata_cautare)
			if input_interfata_secundara == '1':
				cautari_1(lista_cheltuieli_curente)
			elif input_interfata_secundara == '2':
				cautari_2(lista_cheltuieli_curente)
			elif input_interfata_secundara == '3':
				cautari_3(lista_cheltuieli_curente)
			else:
				afisare_input_gresit()

		elif input_interfata_principala=='4':
			input_interfata_secundara = get_user_input(mesaj_interfata_raport)
			if input_interfata_secundara == '1':
				rapoarte_1(lista_cheltuieli_curente)
			elif input_interfata_secundara == '2':
				rapoarte_2(lista_cheltuieli_curente)
			elif input_interfata_secundara == '3':
				rapoarte_3(lista_cheltuieli_curente)
			elif input_interfata_secundara == '4':
				rapoarte_4(lista_cheltuieli_curente)
			else:
				afisare_input_gresit()

		elif input_interfata_principala=='5':
			input_interfata_secundara = get_user_input(mesaj_interfata_filtrare)
			if input_interfata_secundara == '1':
				filtrare_1(lista_cheltuieli_curente)
			elif input_interfata_secundara == '2':
				filtrare_2(lista_cheltuieli_curente)
			else:
				afisare_input_gresit()

		elif input_interfata_principala == '6':
			if contor_undo > 0:
				lista_cheltuieli_curente = lista_undo[contor_undo-1].copy()
				contor_undo -= 1
				lista_undo.pop()
			else:
				print('nu se poate da undo')

		elif input_interfata_principala == '7':
			switch = True

		elif input_interfata_principala == '8':
			afisare_lista(lista_cheltuieli_curente)

def start_2():
	'''
	functie principala in care se apeleaza restul functiilor (functia main)
	'''
	tipuri_posibile = ['mancare','intretinere','telefon','altele','imbracaminte']
	lista_cheltuieli_curente = generate_data()		

	lista_undo =[]
	lista_undo.append(lista_cheltuieli_curente.copy())

	contor_undo = 0 
	switch = False
	while not switch:
		ce_facem = []
		while not(ce_facem):
			ce_facem = input()
		ce_facem_ = ce_facem.split()

		if ce_facem_[0]=='tipareste':
			afisare_lista(lista_cheltuieli_curente)

		elif ce_facem_[0]=='adauga':
			if len(ce_facem_)==4:
				try:
					zi = int(ce_facem_[1])
					suma = int(ce_facem_[2])
				except:
					continue		
				if ce_facem_[3] in tipuri_posibile:	
					tip = ce_facem_[3]
				else:
					continue
				lista_cheltuieli_curente = adauga_cheltuiala_la_lista(lista_cheltuieli_curente,zi,suma,tip)

		elif ce_facem_[0]=='sterge':
			if type(ce_facem_[1]==type('string')):
				if ce_facem_[1] in tipuri_posibile:	
					tip = ce_facem_[1]
				else:
					continue
				lista_cheltuieli_curente = stergere_cheltuieli_tip(lista_cheltuieli_curente,tip)
			elif len(ce_facem_)==2:
				try:
					zi = int(ce_facem_[1])
				except:
					continue
				lista_cheltuieli_curente = stergere_cheltuieli_zi(lista_cheltuieli_curente,zi)
			elif len(ce_facem_)==3:
				try:
					zi_inceput = int(ce_facem_[1])
					zi_sfarsit = int(ce_facem_[2])
				except:
					continue
				lista_cheltuieli_curente = stergere_cheltuieli_interval(lista_cheltuieli_curente,zi_inceput,zi_sfarsit)
	
		elif ce_facem_[0]=='schimba':
			if len(ce_facem_)==7:
				try:
					zi_schimbare = int(ce_facem_[1])
					suma_schimbare = int(ce_facem_[2])
					zi_noua = int(ce_facem_[4])
					suma_noua = int(ce_facem_[5])
				except:
					continue
				if (tip_schimbare in tipuri_posibile) and (tip_nou in tipuri_posibile):
					tip_schimbare = ce_facem_[3]
					ip_nou = ce_facem_[6]
				else:
					continue
				lista_cheltuieli_curente = reinoire_lista(lista_cheltuieli_curente,zi_schimbare,suma_schimbare,tip_schimbare,zi_noua,suma_noua,tip_nou)
		elif ce_facem_[0]=='help':
			print('\n tipareste : tipareste lista curenta cu cheltuieli \nadauga int int string : adauga o cheltuiala la lista\nschimba int int string int int string : schimba o cheltuiala din lista cu una noua\nsterge string : sterge cheltuielile de un anumit tip\nsterge int : sterge cheltuielile dintr-o anumita zi\nsterge int int : sterge cheltuielile dintr-un anumit interval de timp\nexit : iesire din program\n')
		elif ce_facem_[0]=='exit':
			switch=True
	
		else:
			pass

'''
TESTE
'''
test_stergere_cheltuieli_zi()

test_stergere_cheltuieli_interval()

test_stergere_cheltuieli_tip()

test_cautare_mM_decat_suma()

test_cautare_inainte_de_zi_si_mm_decat_suma()

test_cautare_cheltuieli_de_un_tip()

test_suma_cheltuieli_de_un_tip()

test_cheltuiala_maxima()

test_cheltuieli_fara_un_tip()

test_cheltuieli_mm_decat_o_suma()
'''
'''
start()