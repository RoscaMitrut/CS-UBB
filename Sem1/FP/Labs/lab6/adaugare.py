from utils import *


def adauga_cheltuiala_1(lista):
	'''
	functia adauga o cheltuiala noua in lista in care avem cheltuielile curente
	lista = lista in care dorim sa adaugam cheltuiala
	'''
	zi = get_user_input_zi()
	suma = get_user_input_suma()
	tip = get_user_input_tip()
	lista_noua = adauga_cheltuiala_la_lista(lista,zi,suma,tip)
	return lista_noua

def adauga_cheltuiala_2(lista):
	'''
	functia reactualizeaza o cheltuiala
	lista = lista de cheltuieli, in care actualizam una dintre cheltuieli
	'''
	print("Date cautate:")
	zi_schimbare = get_user_input_zi()
	suma_schimbare = get_user_input_suma()
	tip_schimbare = get_user_input_tip()
	print("Date Noi:")
	zi_noua = get_user_input_zi()
	suma_noua = get_user_input_suma()
	tip_nou = get_user_input_tip()
	lista_noua = reinoire_lista(lista,zi_schimbare,suma_schimbare,tip_schimbare,zi_noua,suma_noua,tip_nou)
	return lista_noua

