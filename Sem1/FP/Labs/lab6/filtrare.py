from utils import *


def filtrare_1(lista):
	'''
	functia afiseaza toate cheltuielile inafara de cele de un anumit tip
	lista = lista de cheltuieli din care se tipareste
	'''
	tip_dat = get_user_input_tip()
	lista_noua = cheltuieli_fara_un_tip(lista,tip_dat)
	afisare_lista(lista_noua)


def filtrare_2(lista):
	'''
	functia tipareste toate cheltuielile mai mici decat o suma data
	lista = lista de cheltuieli din care se tiparesc cheltuielili
	'''
	suma = get_user_input_suma()
	lista_noua = cheltuieli_mm_decat_o_suma(lista,suma)
	afisare_lista(lista_noua)
