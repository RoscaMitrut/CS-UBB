from finite_state_computer import FiniteStateMachine
from transition import Transition

class UI:

	def __init__(self, filename:str="fisier.txt" ) -> None:
		self.filename = filename
		self.finite_state_machine = None

  
	def split_line(self,words, line):
		while line:
			word, _, line = line.partition(',')
			words.append(word)


	def read_from_file(self):
		alphabet = []
		states = []
		initial_state = ""
		final_states = []
		transitions = []

		with open(self.filename, "r") as fin:
			self.split_line(alphabet, fin.readline().strip())
			self.split_line(states, fin.readline().strip())
			initial_state = fin.readline().strip()
			self.split_line(final_states, fin.readline().strip())

			for line in fin:
				source_state, line = line.split(",", 1)
				destination_state, value = line.split(",", 1)
				transitions.append(Transition(source_state, destination_state, value.strip()))

		return alphabet, states, initial_state, final_states, transitions


	def read_from_cmd(self):
		alphabet = input("Alfabetul: ").split(",")
		states = input("Starile: ").split(",")
		initial_state = input("Starea initiala: ")
		final_states = input("Starile finale: ").split(",")

		transitions = []
		n = int(input("Numarul de tranzitii: "))

		for _ in range(n):
			line = input("Sursa, destinatia si valoarea tranzitiei: ")
			source_state, line = line.split(",", 1)
			destination_state, value = line.split(",", 1)
			transitions.append(Transition(source_state, destination_state, value.strip()))

		return alphabet, states, initial_state, final_states, transitions


	def print_states(self):
		print("Starile:", " ".join(self.finite_state_machine.get_states()))


	def print_alphabet(self):
		print("Alfabetul:", " ".join(self.finite_state_machine.get_alphabet()))


	def print_transitions(self):
		print("Tranzitile:")
		for transition in self.finite_state_machine.get_transitions():
			print("   ", transition.get_source_state(), transition.get_destination_state(), transition.get_value())


	def print_final_states(self):
		print("Starile finale:", " ".join(self.finite_state_machine.get_final_states()))


	def check_sequence(self):
		sequence = input("Secventa de verificat: ")
		if self.finite_state_machine.verificare_secventa(sequence):
			print("Secventa valida")
		else:
			print("Secventa invalida")


	def print_longest_prefix(self):
		sequence = input("Secventa: ")
		prefix = self.finite_state_machine.cel_mai_lung_prefix(sequence)
		print(type(prefix))
		if prefix==None:
			print("Nu exista")
		elif prefix=="":
			print("-")
		else:
			print(prefix)        


	def print_read_commands(self):
		print("   0 - Iesire")
		print("   1 - Citire din fisier")
		print("   2 - Citire din cmd\n")


	def print_commands(self):
		print()
		print("Options:")
		print("   0 - Iesire")
		print("   1 - Starile")
		print("   2 - Alfabetul")
		print("   3 - Tranzitile")
		print("   4 - Starile finale6")
		print("   5 - Verifica validitatea secventei")
		print("   6 - Cel mai lung prefix")
		return input("> ")
		

	def run(self) -> None:
		command = input("Alege:\n1 - Citeste din fisier\n2 - Citeste din cmd\n0 - Iesire\n> ")
		if command == "0":
			exit(0)
		elif command == "1":
			alphabet, states, initial_state, final_states, transitions = self.read_from_file()
		elif command == "2":
			alphabet, states, initial_state, final_states, transitions = self.read_from_cmd()
		else:
			print("Invalid!")

		self.finite_state_machine = FiniteStateMachine(alphabet, states, initial_state, transitions, final_states)
 
		while True:
			option = self.print_commands()
			if option == "0":
				exit(0)
			elif option == "1":
				self.print_states()
			elif option == "2":
				self.print_alphabet()
			elif option == "3":
				self.print_transitions()
			elif option == "4":
				self.print_final_states()
			elif option == "5":
				self.check_sequence()
			elif option == "6":
				self.print_longest_prefix()
			else:
				print("Invalid!")
