import random

def insertion_sort_recursiv(array,key=lambda x:x,reverse=False):
	"""
	functie care sorteaza prin insertie recursiva o lista de liste
	array: lista pe care dorim sa o sortam
	key = elementul dupa care dorim sa sortam
	reverse = ordinea in care dorim sa sortam (crescatoare/descrescatoare)
	"""
	n = len(array)

	if n<=1:
		return
	insertion_sort_recursiv(array[:-1],key= key,reverse=reverse)

	last = array[n-1]
	j = n-2
	while (j>=0 and key(array[j]) < key(last)):
		array[j+1] = array[j]
		j = j-1
	array[j+1]=last

	if not(reverse):
		array.reverse()

def comb_sort(array,key= lambda x:x,reverse=False):
	"""
	functie care sorteaza o lista de liste prin comb sort
	array: lista pe care dorim sa o sortam
	key = elementul dupa care dorim sa sortam
	reverse = ordinea in care dorim sa sortam (crescatoare/descrescatoare)
	"""
	n = len(array)
	gap = n	
	swapped = True

	while gap !=1 or swapped == 1:
		temp = (gap*10)//13
		if temp<1:
			temp = 1
		gap = temp
		swapped = False
		for i in range(0, n-gap):
			if key(array[i]) > key(array[i + gap]):
				array[i], array[i + gap]=array[i + gap], array[i]
				swapped = True
	if reverse:
		array.reverse()

class console:
	def __init__(self,srv):
		self.__srv = srv
#---------------------------------------------------------------------------------------------------	
	def __add_student(self):
		'''
		adauga un student citit de la tastatura
		'''
		try:
			id = int(input("ID Student:"))
		except ValueError:
			print('ID-ul trebuie sa fie un numar')
			return

		nume = input("Nume student:")

		try:
			grup = int(input("Grupa student:"))
		except ValueError:
			print("Grupa trebuie sa fie un numar")
			return

		try:
			self.__srv.add_student(id,nume,grup)
			print('Studentul' , nume , 'cu ID' , id , 'din grupa' , grup , 'a fost adaugat cu succes.')
		except ValueError as ve:
			print(str(ve))

	def __add_problem(self):
		'''
		adauga o problema citita de la tastatura
		'''
		try:
			numar = int(input("Numarul problemei: "))
		except ValueError:
			print('Numarul problemei trebuie sa fie un numar')
			return
		
		descriere = input("Descriere: ")

		try:
			deadline = int(input("Deadline: "))
		except ValueError:
			print('Deadline-ul trebuie sa fie un numar')
			return
		
		try:
			self.__srv.add_pb(numar,descriere,deadline)
			print('Problema', numar, "Cu descrierea:", descriere,"Cu deadline:" ,deadline,'a fost adaugata cu succes.')
		except ValueError as ve:
			print(str(ve))

	def __random_student(self):
		'''
		adaugam un student random
		'''
		switch = 1
		while (switch == 1):
			switch = 0
			id = random.randint(1,1000)
			nume = random.choice(['Andrei','Mihai','Ion','Vasile','Dan','Claudiu','Grigore','Florin','Dumitru','Ionut'])
			grup = random.randint(101,1000)
			try:
				self.__srv.add_student(id,nume,grup)
				print('Studentul' , nume , 'cu ID' , id , 'din grupa' , grup , 'a fost adaugat cu succes.')
			except ValueError as ve:
				switch = 1

	def __notare(self):
		'''
		adaugam o nota
		'''
		try:
			id = int(input("ID Student:"))
			numar = int(input("Numarul problemei: "))
			nota = int(input("Nota:"))
		except ValueError:
			print('input-urile trebuie sa fie numere')
			return
		try:	
			self.__srv.create_grade(id,numar,nota)
		except ValueError:
			print("Notarea NU a fost efectuata cu succes")
#---------------------------------------------------------------------------------------------------
	def __delete_student(self):
		'''
		stergem un student pe baza unui id dat de la tastatura
		'''
		try:
			id = int(input("ID Student:"))
		except ValueError:
			print('ID-ul trebuie sa fie un numar')
			return
				
		try:
			self.__srv.delete_student(id)
			print('Studentul cu id ',id,'a fost sters')
		except ValueError as ve:
			print(str(ve))

	def __delete_problem(self):
		'''
		stergem o problema pe baza unui nr dat de la tastatura
		'''
		try:
			numar = int(input("Numar problema: "))
		except ValueError:
			print('Numarul problemei trebuie sa fie un numar')
			return

		try:
			self.__srv.delete_problem(numar)
			print("Problema cu numarul",numar,'a fost stearsa')
		except ValueError as ve:
			print(str(ve))
#---------------------------------------------------------------------------------------------------
	def __modify_student(self):
		'''
		modificam un student
		'''
		try:
			id_curent = int(input("ID-ul studentului:"))
			ce_facem = input("Ce modificam? 1.ID  2.Nume  3.Grup   ")
			if ce_facem == '1':
				date_noi = int(input("ID nou:"))
			elif ce_facem == '2':
				date_noi = input("Nume nou: ")
			elif ce_facem =='3':
				date_noi = int(input("Grupa noua:"))
			else:
				raise(ValueError)
		except ValueError:
			print("introduceti date valide")
			return
		student_actual = self.__srv.get_student_by_id(id_curent)
		try:
			if ce_facem == '1':
				self.__srv.add_student(date_noi,student_actual.get_nume(),student_actual.get_grup())
				print("ID modificat cu succes")
				self.__srv.delete_student(id_curent)
			elif ce_facem =='2':
				self.__srv.add_student(student_actual.get_studentID(),date_noi,student_actual.get_grup())
				print("Nume modificat cu succes")
				self.__srv.delete_student(id_curent)
			elif ce_facem =='3':
				self.__srv.add_student(student_actual.get_studentID(),student_actual.get_nume(),date_noi)
				print("Grupa modificata cu succes")
				self.__srv.delete_student(id_curent)
		except ValueError as ve:
			print("Modificarea NU a fost efectuata cu succes \n",ve)

	def __modify_problem(self):
		'''
		modificam o problema
		'''
		try:
			nr_curent = int(input("nr-ul problemei:"))
			ce_facem = input("Ce modificam? 1.Nr  2.Descriere  3.Deadline   ")
			if ce_facem == '1':
				date_noi = int(input("Nr nou:"))
			elif ce_facem == '2':
				date_noi = input("Descriere noua: ")
			elif ce_facem =='3':
				date_noi = int(input("Deadline nou:"))
			else:
				raise(ValueError)
		except ValueError:
			print("introduceti date valide")
			return
		problema_actuala = self.__srv.get_problem_by_number(nr_curent)
		try:
			if ce_facem == '1':
				self.__srv.add_pb(date_noi,problema_actuala.get_descriere(),problema_actuala.get_deadline())
				print("Nr modificat cu succes")
				self.__srv.delete_problem(nr_curent)
			elif ce_facem =='2':
				self.__srv.add_pb(problema_actuala.get_nr_lab_pb(),date_noi,problema_actuala.get_deadline())
				print("Descriere modificata cu succes")
				self.__srv.delete_problem(nr_curent)
			elif ce_facem =='3':
				self.__srv.add_pb(problema_actuala.get_nr_lab_pb(),problema_actuala.get_descriere(),date_noi)
				print("Deadline modificat cu succes")
				self.__srv.delete_problem(nr_curent)
		except ValueError as ve:
			print("Modificarea NU a fost efectuata cu succes \n", ve)
#---------------------------------------------------------------------------------------------------
	def __print_students(self,student_list):
		'''
		printam studentii
		'''
		if len(student_list) == 0:
			print("Nu avem studenti")
		else:
			print("Studenti:")
			for student in student_list:
				print('ID:', student.get_studentID() , 'Nume:', student.get_nume(), 'Grup:', student.get_grup(), '\n')

	def __print_problems(self,problem_list):
		'''printam problemele
		'''
		if len(problem_list) == 0:
			print('Nu avem probleme')
		else:
			print("Probleme:")
			for problema in problem_list:
				print('Nr:', problema.get_nr_lab_pb(), 'Descriere:', problema.get_descriere(), 'Deadline:',problema.get_deadline(),'\n')
#---------------------------------------------------------------------------------------------------
	def __cauta_student(self):
		'''
		printam un student care are un id specific
		'''
		try:
			id = int(input("ID Student:"))
		except ValueError:
			print('ID-ul trebuie sa fie un numar')
			return
		student_actual = self.__srv.get_student_by_id(id)
		print("Date pentru student cu ID",id,':\nNume:',student_actual.get_nume(),'\nGrupa:',student_actual.get_grup())

	def __cauta_problema(self):
		'''
		printam o problema care are un nr specific
		'''
		try:
			numar = int(input("Numarul problemei: "))
		except ValueError:
			print('Numarul problemei trebuie sa fie un numar')
			return
		problema_actuala = self.__srv.get_problem_by_number(numar)
		print("Date pentru problema cu Nr",numar,':\nDescriere:',problema_actuala.get_descriere,'\nDeadline:',problema_actuala.get_deadline())
#--------------------------------------------------------------------------------------------------
	def __crescator(self,lista_note):
		'''
		printam notele crescator
		lista_note: lista de note din care sa luam notele
		'''
		if len(lista_note) == 0:
			print("Nu avem note")
		else:
			print("Note:")
			note = []
			for nota in lista_note:
				note.append([nota.get_id_student(),nota.get_nr_problema(),nota.get_nota()])
			note.sort(key=lambda x:x[2])
			for i in note:
				print("Nume:",self.__srv.get_student_by_id(i[0]).get_nume(),'Problema nr:',i[1],'Nota:',i[2])

	def __alfabetic(self,lista_note):
		'''
		printam notele alfabetic (numele studentilor)
		lista_note: lista de note din care sa luam notele
		'''
		if len(lista_note) == 0:
			print("Nu avem note")
		else:
			print("Note:")
			note = []
			for nota in lista_note:
				note.append([nota.get_id_student(),nota.get_nr_problema(),nota.get_nota()])
			note.sort(key=lambda x:x[0])
			for i in note:
				print("Nume:",self.__srv.get_student_by_id(i[0]).get_nume(),'Problema nr:',i[1],'Nota:',i[2])

	def __descrescator(self,lista_note):
		'''
		printam notele descrescator
		lista_note: lista de note din care sa luam notele
		'''
		if len(lista_note) == 0:
			print("Nu avem note")
		else:
			print("Note:")
			note = []
			for nota in lista_note:
				note.append([nota.get_id_student(),nota.get_nr_problema(),nota.get_nota()])
			note.sort(key=lambda x:x[2],reverse=True)
			for i in note:
				print("Nume:",self.__srv.get_student_by_id(i[0]).get_nume(),'Problema nr:',i[1],'Nota:',i[2])

	def __sub5(self,lista_note):
		'''
		printam notele sub 5
		lista_note: lista de note din care sa luam notele
		'''
		if len(lista_note) == 0:
			print("Nu avem note")
		else:
			print("Note:")
			note = []
			for nota in lista_note:
				note.append([nota.get_id_student(),nota.get_nr_problema(),nota.get_nota()])
			note.sort(key=lambda x:x[0])
			for i in note:
				if(i[2]<5):
					print("Nume:",self.__srv.get_student_by_id(i[0]).get_nume(),'Problema nr:',i[1],'Nota:',i[2])	
#---------------------------------------------------------------------------------------------------

	def __insertion(self,lista_note,key=0,reverse=False):
		"""
		sortam lista de note prin insertion sort recursiv
		lista_note: lista pe care dorim sa o sortam
		key = elementul dupa care dorim sa sortam
		reverse = ordinea in care dorim sa sortam (crescatoare/descrescatoare)
		"""
		if len(lista_note) == 0:
			print("Nu avem note")
		else:
			print("Note:")
			note = []
			for nota in lista_note:
				note.append([nota.get_id_student(),nota.get_nr_problema(),nota.get_nota()])
			insertion_sort_recursiv(note,key=key,reverse=reverse)
			for i in note:
				print("ID Student:",self.__srv.get_student_by_id(i[0]).get_studentID(),'Problema nr:',i[1],'Nota:',i[2])

	def __comb(self,lista_note,key=0,reverse=False):
		"""
		sortam lista de note prin comb sort
		lista_note: lista pe care dorim sa o sortam
		key = elementul dupa care dorim sa sortam
		reverse = ordinea in care dorim sa sortam (crescatoare/descrescatoare)
		"""
		if len(lista_note) == 0:
			print("Nu avem note")
		else:
			print("Note:")
			note = []
			for nota in lista_note:
				note.append([nota.get_id_student(),nota.get_nr_problema(),nota.get_nota()])
			comb_sort(note,key=key,reverse=reverse)
			for i in note:
				print("ID Student:",self.__srv.get_student_by_id(i[0]).get_studentID(),'Problema nr:',i[1],'Nota:',i[2])
#---------------------------------------------------------------------------------------------------
	def program_ui(self):
		'''
		main
		complexitate liniara 
		se ruleaza de cate ori dam un input
		'''
		print('Comenzi disponibile: adauga_student, adauga_problema, sterge_student, sterge_problema, modifica_student, modifica_problema, cautare_student, cautare_problema, notare_laborator, note_crescator, note_descrescator, note_alfabetic, note_sub_5, exit, afisare_studenti, afisare_probleme, random')
		cmd = input('Comanda este:')
		cmd = cmd.lower().strip()
		
		if cmd == 'adauga_student':
			self.__add_student()

		elif cmd == 'sterge_student':
			self.__delete_student()
	
		elif cmd == 'adauga_problema':
			self.__add_problem()
		
		elif cmd == 'sterge_problema':
			self.__delete_problem()
		
		elif cmd == 'modifica_student':
			self.__modify_student()
		
		elif cmd == 'modifica_problema':
			self.__modify_problem()
		
		elif cmd == 'cautare_student':
			self.__cauta_student()
		
		elif cmd == 'cautare_problema':
			self.__cauta_problema()
		
		elif cmd == 'notare_laborator':
			self.__notare()

		elif cmd == 'note_crescator':
			self.__crescator(self.__srv.get_all_grades())

		elif cmd == 'note_descrescator':
			self.__descrescator(self.__srv.get_all_grades())

		elif cmd == 'note_alfabetic':
			self.__alfabetic(self.__srv.get_all_grades())

		elif cmd == 'note_sub_5':
			self.__sub5(self.__srv.get_all_grades())

		elif cmd == 'afisare_studenti':
			self.__print_students(self.__srv.get_all_students())

		elif cmd == 'afisare_probleme':
			self.__print_problems(self.__srv.get_all_problems())

		elif cmd == 'random':
			self.__random_student()
			
		elif cmd == 'exit':
			return
			
		elif cmd == 'insertion':
			self.__insertion(self.__srv.get_all_grades())
			
		elif cmd == 'comb':
			self.__comb(self.__srv.get_all_grades(),key=1)
		else:
			print('Comanda invalida.')

		self.program_ui()