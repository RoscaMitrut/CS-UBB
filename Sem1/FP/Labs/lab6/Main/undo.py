def undo_add(lista_noastra,lista_undo,contor_undo):
	'''
	functia adauga in o lista ultima stare a listei de cheltuieli si creste un contor care semnifica numarul de schimbari facute pana in acel punct
	lista noastra = lista de cheltuieli pe care dorim sa o adaugam in lista cu ultimele stari ale listei
	lista_undo = lista in care adaugam lista de cheltuieli precizata mai sus
	contor_undo = contorul care semnifica numarul de modificari suferite de lista de cheltuieli
	'''
	lista_undo.append(lista_noastra)
	contor_undo += 1
	return lista_undo,contor_undo


