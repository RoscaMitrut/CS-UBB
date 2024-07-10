#5,9,10
switch = 0

def citire_lista():
	global switch
	global lista_intrare
	n = input("Nr elemente: ")
	n = int(n)
	lista_intrare=[]
	if (n!=0):
		for i in range(n):
			print("a[",i,"]:",end='')
			temp = input()
			temp = int(temp)
			lista_intrare.append(temp)
	switch=1

def cerinta_1():
	contor =1
	max =1
	for i in range(len(lista_intrare)-1):
		if(lista_intrare[i]==lista_intrare[i+1]):
			contor +=1
			if max<contor:
				max = contor
		else:
			contor=1
			element = lista_intrare[i]
	if max==len(lista_intrare):
		element=lista_intrare[0]
	print("Secventa maxima cu elem egale:")
	for i in range(max):
		print(element,end="  ")
	print("")


def cerinta_2():
	contor =1
	max =1
	for i in range(len(lista_intrare)-1):
		if(lista_intrare[i]==lista_intrare[i+1]):
			contor +=1
			if max<contor:
				max = contor
		else:
			contor=1
			element = lista_intrare[i]
	if max==len(lista_intrare):
		element=lista_intrare[0]
	if max>=3:
		print("Secventa de cel putin 3 elem egale:")
		for i in range(max):
			print(element,end="  ")
		print("")
	elif(len(lista_intrare)==1):
		print("P=1")
	else:
		print("Nu avem secventa de cel putin 3 elem care sa se repete si nici p nu e 1")


def cerinta_3():
	if (len(lista_intrare)==1):
		print("P=1")
	else:
		for j in range(len(lista_intrare)-2):
			dif1 = lista_intrare[j+1]-lista_intrare[j]
			dif2 = lista_intrare[j+2]-lista_intrare[j+1]
			if len(lista_intrare)==1:
				print("P=1")
			elif dif1<0 and dif2>0:
				print("Diferentele a[",j+1,"] - a[",j,"] si a[",j+2,"] - a[",j+1,"] au semn contrar")
			elif dif1>0 and dif1<0:
				print("Diferentele a[",j+1,"] - a[",j,"] si a[",j+2,"] - a[",j+1,"] au semn contrar")
			else:
				print("Diferentele a[",j+1,"] - a[",j,"] si a[",j+2,"] - a[",j+1,"] nu au semn contrar")


def afisare_interfata():
	print("\n 1.Citire lista de numere intregi\n",
		"2.Gasire secventa maxima cu elem egale\n",
		"3.Gasire secventa cu cel putin 3 elem care se repeta sau daca p=1\n",
		"4.Verificam daca diferentele au semn contrar sau daca p=1\n",
		"5.Iesire\n",
		"Input: ",end="")


def start():
	while True:
		afisare_interfata()
		ce_facem = input()
		ce_facem = int(ce_facem)
		print("")
		if ce_facem==1:
			citire_lista()
	
		if ce_facem==2:
			if switch==1:
				cerinta_1()
			else:
				print("Nu avem lista definita")
	
		if ce_facem==3:
			if switch==1:
				cerinta_2()
			else:
				print("Nu avem lista definita")
	
		if ce_facem==4:
			if switch==1:
				cerinta_3()
			else:
				print("Nu avem lista definita")
	
		if ce_facem==5:
			break

start()