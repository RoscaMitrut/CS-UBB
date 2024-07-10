from utils import *

def stergere_1(lista_din_care_stergem):
	'''
	functie care sterge din lista de cheltuieli cheltuielile pentru o zi data
	lista_din_care_sterge = lista in care avem stocate cheltuielile,din care vrem sa stergem
	'''
	zi_stergere = get_user_input_zi()
	lista_noua = stergere_cheltuieli_zi(lista_din_care_stergem,zi_stergere)
	return lista_noua

def stergere_2(lista_din_care_stergem):
	'''
	functie care sterge din lista de cheltuieli toate cheltuielile dintr-un interval de timp
	lista_din_care_sterge = lista in care avem stocate cheltuielile,din care vrem sa stergem
	'''
	print("Inceput; ",end="")
	zi_inceput = get_user_input_zi()
	print("Sfarsit; ",end="")
	zi_sfarsit = get_user_input_zi()
	lista_noua = stergere_cheltuieli_interval(lista_din_care_stergem,zi_inceput,zi_sfarsit)
	return lista_noua


def stergere_3(lista_din_care_stergem):
	'''
	functie care sterge din lista de cheltuieli toate cheltuielile de un anumit tip
	lista_din_care_stergem = lista in care avem stocate cheltuielile,din care vrem sa stergem
	'''
	tip = get_user_input_tip()
	lista_noua = stergere_cheltuieli_tip(lista_din_care_stergem,tip)
	return lista_noua
