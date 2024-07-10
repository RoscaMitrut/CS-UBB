from utils import *


def cautari_1(lista):
	'''
	functia tipareste toate cheltuielile mai mari decat o suma data
	lista = lista in care se cauta cheltuielile
	'''
	suma = get_user_input_suma()
	lista_noua = cautare_mM_decat_suma(lista,suma)
	afisare_lista(lista_noua)


def cautari_2(lista):
	'''
	functia tipareste toate cheltuielile efectuate inainte de o zi si mai mici decat o suma
	lista = lista de cheltuieli in care se cauta cheltuielile respective
	'''
	zi = get_user_input_zi()
	suma = get_user_input_suma()
	lista_noua = cautare_inainte_de_zi_si_mm_decat_suma(lista,zi,suma)
	afisare_lista(lista_noua)


def cautari_3(lista):
	'''
	functia tipareste toate cheltuielile de un anumit tip
	lista = lista de cheltuieli din care se printeaza cheltuielile de tipul dat
	'''
	tip = get_user_input_tip()
	afisare_lista(cautare_cheltuieli_de_un_tip(lista,tip))
