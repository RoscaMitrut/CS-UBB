from utils import *
"""
TESTE!! YAAAY!!
"""
lista_pentru_teste_1 = [{'ziua': 7, 'suma': 15, 'tipul': 'mancare'},{'ziua': 5, 'suma': 25, 'tipul': 'imbracaminte'},{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'}]
lista_pentru_teste_2 = [[17,115,'mancare'],[12,145,'telefon'],[6,79,'altele']]
lista_pentru_teste_3 = [[16,67,'telefon'],[24,156,'mancare'],[6,479,'intretinere']]


def test_stergere_cheltuieli_zi():
	assert(stergere_cheltuieli_zi(lista_pentru_teste_1,7)==[{'ziua': 5, 'suma': 25, 'tipul': 'imbracaminte'},{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'}])

	assert(stergere_cheltuieli_zi(lista_pentru_teste_3,24)==[[16,67,'telefon'],[6,479,'intretinere']])

	print('Test Passed')

def test_stergere_cheltuieli_interval():
	assert(stergere_cheltuieli_interval(lista_pentru_teste_1,1,10)==[{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'}])

	assert(stergere_cheltuieli_interval(lista_pentru_teste_2,1,10)==[[17,115,'mancare'],[12,145,'telefon']])

	print('Test Passed')

def test_stergere_cheltuieli_tip():
	assert(stergere_cheltuieli_tip(lista_pentru_teste_1,'mancare')==[{'ziua': 5, 'suma': 25, 'tipul': 'imbracaminte'},{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'}])

	assert(stergere_cheltuieli_tip(lista_pentru_teste_3,'intretinere')==[[16,67,'telefon'],[24,156,'mancare']])

	print('Test Passed')

def test_cautare_mM_decat_suma():
	assert(cautare_mM_decat_suma(lista_pentru_teste_1,35)==[{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'}])

	assert(cautare_mM_decat_suma(lista_pentru_teste_2,150)==[])

	print('Test Passed')

def test_cautare_inainte_de_zi_si_mm_decat_suma():
	assert(cautare_inainte_de_zi_si_mm_decat_suma(lista_pentru_teste_2,10,100)==[[6,79,'altele']])

	assert(cautare_inainte_de_zi_si_mm_decat_suma(lista_pentru_teste_3,25,200)==[[16,67,'telefon'],[24,156,'mancare']])

	print('Test Passed')

def test_cautare_cheltuieli_de_un_tip():
	assert(cautare_cheltuieli_de_un_tip(lista_pentru_teste_3,'mancare')==[[24,156,'mancare']])

	assert(cautare_cheltuieli_de_un_tip(lista_pentru_teste_1,'imbracaminte')==[{'ziua': 5, 'suma': 25, 'tipul': 'imbracaminte'}])
	
	print('Test Passed')

def test_suma_cheltuieli_de_un_tip():
	assert(suma_cheltuieli_de_un_tip(lista_pentru_teste_1,'intretinere')==40)
	
	assert(suma_cheltuieli_de_un_tip(lista_pentru_teste_3,'telefon')==67)
	
	print('Test Passed')

def test_cheltuiala_maxima():
	assert(cheltuiala_maxima(lista_pentru_teste_1)==27)
	
	assert(cheltuiala_maxima(lista_pentru_teste_2)==12)
	
	print('Test Passed')

def test_cheltuieli_fara_un_tip():
	assert(cheltuieli_fara_un_tip(lista_pentru_teste_1,'mancare')==[{'ziua': 5, 'suma': 25, 'tipul': 'imbracaminte'},{'ziua': 27, 'suma': 40, 'tipul': 'intretinere'}])
	
	assert(cheltuieli_fara_un_tip(lista_pentru_teste_3,'altele')==[[16,67,'telefon'],[24,156,'mancare'],[6,479,'intretinere']])
	
	print('Test Passed')

def test_cheltuieli_mm_decat_o_suma():
	assert(cheltuieli_mm_decat_o_suma(lista_pentru_teste_1,10)==[])
	
	assert(cheltuieli_mm_decat_o_suma(lista_pentru_teste_3,70)==[[16,67,'telefon']])
	
	print('Test Passed')

