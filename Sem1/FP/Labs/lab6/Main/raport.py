from utils import *


def rapoarte_1(lista):
	'''
	functie care tipareste suma totala pentru un anumit tip de cheltuiala
	lista = lista in care avem stocate cheltuielile
	'''
	tip = get_user_input_tip()
	suma = suma_cheltuieli_de_un_tip(lista,tip)
	print('Suma: ',suma)


def rapoarte_2(lista):
	'''
	functie care tipareste ziua in care suma cheltuita este maxima
	lista = lista in care avem stocate cheltuielile
	'''
	zi_suma_maxima = cheltuiala_maxima(lista)
	print("Ziua: ",zi_suma_maxima)


def rapoarte_3(lista):
	'''
	functie care tipareste toate cheltuielile ecare au o anumita suma
	lista = lista in care avem stocate cheltuielile
	'''
	suma_data = get_user_input_suma()
	cheltuieli_de_anumita_suma(lista,suma_data)


def rapoarte_4(lista):
	'''
	functie care tipareste cheltuielile sortate dupa tipul acestora
	lista = lista in care avem stocate cheltuielile
	'''
	sorteaza_lista(lista)

