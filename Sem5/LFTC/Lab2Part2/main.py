class HashMap:
	def __init__(self, capacity=14):
		self.capacity = capacity  # Initial size of the hash table
		self.map = [None] * capacity  # Array to store key-value pairs

	def hash_function(self, key):
		hash_value = sum(ord(char) for char in str(key)) % self.capacity  # Sum ASCII values of key characters
		return hash_value

	def insert(self, key):
		hash_value = self.hash_function(key)
		index = hash_value

		# Handle collisions
		while self.map[index] is not None:
			index = (index + 1) % self.capacity

		self.map[index] = key

	def get_index(self, key):
		hash_value = self.hash_function(key)
		index = hash_value

		# Handle collisions
		while self.map[index] is not None:
			if self.map[index] == key:
				return index
			index = (index + 1) % self.capacity

		return None  # Key not found

	def get_by_index(self, index):
		return self.map[index]

	def print_map(self):
		for i in range(self.capacity):
			print(f"{i} : {self.map[i]}")
   
	def write_to_file(self):
		with open("ts.txt", "w") as f:
			for i in range(self.capacity):
				f.write(f"{i} : {self.map[i]} \n")
  
class FiniteStateMachine:
	def __init__(self, alphabet, states, initial_state, transitions, final_states):
		self.alphabet = alphabet
		self.states = states
		self.initial_state = initial_state
		self.transitions = transitions
		self.final_states = final_states

	def get_alphabet(self):
		return self.alphabet

	def get_transitions(self):
		return self.transitions

	def get_final_states(self):
		return self.final_states

	def get_states(self):
		return self.states
	
	def isDeterministic(self):
		# Dictionary to track seen transitions per state and symbol
		transition_map = {}

		for transition in self.transitions:
			source_state = transition.get_source_state()
			symbol = transition.get_value()

			# Check for ε-transitions (empty transitions), which are non-deterministic
			if symbol == "":
				return False

			# Initialize state entry in transition map if not present
			if source_state not in transition_map:
				transition_map[source_state] = set()

			# If a transition for this symbol already exists in this state, it's non-deterministic
			if symbol in transition_map[source_state]:
				return False

			# Mark this symbol as seen for this state
			transition_map[source_state].add(symbol)

		return True

	def verificare_secventa(self, sequence):
		if (self.isDeterministic()==False):
			print("Nu e AFD")
			return
		
		prefix = ""
		current_state = self.initial_state

		while sequence:
			found = False
			for transition in self.transitions:
				if (
					transition.get_source_state() == current_state
					and transition.get_value() == sequence[: len(transition.get_value())]
				):
					prefix += transition.get_value()
					sequence = sequence[len(transition.get_value()) :]
					current_state = transition.get_destination_state()
					found = True
					break
			if not found:
				return False

		if current_state in self.final_states:
			return True

		return False

	def cel_mai_lung_prefix(self, sequence):
		if (self.isDeterministic()==False):
			print("Nu e AFD")
			return
		prefix = None
		current_state = self.initial_state
		longest_final_prefix = None
  
		while sequence:
			valid_transition = None

			for transition in self.transitions:
				if (
					transition.get_source_state() == current_state
					and sequence.startswith(transition.get_value())
				):
					valid_transition = transition
					break

			if valid_transition is None:
				return longest_final_prefix

			prefix = prefix or ""
			prefix += valid_transition.get_value()

			sequence = sequence[len(valid_transition.get_value()):]
			current_state = valid_transition.get_destination_state()

			if current_state in self.final_states:
				longest_final_prefix = prefix
	
		if longest_final_prefix==None and self.initial_state in self.final_states:
			return "epsilon"
		
		return longest_final_prefix
	
class Transition:
	def __init__(self, source_state, destination_state, value):
		self.value = value
		self.source_state = source_state
		self.destination_state = destination_state

	def get_value(self):
		return self.value

	def get_source_state(self):
		return self.source_state

	def get_destination_state(self):
		return self.destination_state
	
def getKeywords(filename: str):
	keywords = {}
	with open(filename, 'r', encoding='utf-8') as f:
		lines = f.readlines()
		for line in lines:
			line = line.replace('\n', '')
			key, val = line.rsplit(':', 1)
			keywords[key] = val
	return keywords

def split_line(words, line):
	while line:
		word, _, line = line.partition(',')
		words.append(word)

def read_from_file(filename):
	alphabet = []
	states = []
	initial_state = ""
	final_states = []
	transitions = []
	with open(filename, "r") as fin:
		split_line(alphabet, fin.readline().strip())
		split_line(states, fin.readline().strip())
		initial_state = fin.readline().strip()
		split_line(final_states, fin.readline().strip())
		for line in fin:
			source_state, line = line.split(",", 1)
			destination_state, value = line.split(",", 1)
			transitions.append(Transition(source_state, destination_state, value.strip()))
	return alphabet, states, initial_state, final_states, transitions



alphabet, states, initial_state, final_states, transitions = read_from_file("fsm_id.txt")
fsm_id = FiniteStateMachine(alphabet, states, initial_state, transitions, final_states)

alphabet2, states2, initial_state2, final_states2, transitions2 = read_from_file("fsm_whole.txt")
fsm_whole = FiniteStateMachine(alphabet2, states2, initial_state2, transitions2, final_states2)

alphabet3, states3, initial_state3, final_states3, transitions3 = read_from_file("fsm_real.txt")
fsm_real = FiniteStateMachine(alphabet3, states3, initial_state3, transitions3, final_states3)



def getTS(cod, keywords, fsm_list):
	ts = HashMap()

	i = 0
	tokens = []
	while i < len(cod):
		if cod[i].isspace():
			i += 1
			continue

		matched = False
		'''
		for keyword in keywords:
			if cod.startswith(keyword, i):
				tokens.append(keyword)
				i += len(keyword)
				matched = True
				break
		'''
		if not matched:
			for fsm in fsm_list:
				prefix = fsm.cel_mai_lung_prefix(cod[i:])
				if prefix:
					tokens.append(prefix)
					i += len(prefix)
					matched = True
					break

		if not matched:
			if cod[i] in keywords:
				tokens.append(cod[i])
				i+=1
				matched = True
			else:
				print(f"Unexpected character in source code: {cod[i]} at index {i}")
				i += 1
				#raise ValueError(f"Unexpected character in source code: {cod[i]} at index {i}")

	variables = []
	constants = []

	for token in tokens:
		if token in keywords:
			continue

		if (
			token not in variables
			and any(c.isalpha() for c in token)
			and fsm_id.cel_mai_lung_prefix(token) == token
		):
			variables.append(token)

		elif token not in constants:
			if (
				fsm_whole.cel_mai_lung_prefix(token) == token
				or fsm_real.cel_mai_lung_prefix(token) == token
			):
				constants.append(token)
			elif token.startswith("'") and token.endswith("'"):
				constants.append(token.strip("'"))

	for var in variables:
		ts.insert(var)

	for const in constants:
		ts.insert(const)

	return ts



def getFIP(cod,keywords,ts,fsm_list):
	fip = []
	exceptions = []
	
	i = 0
	line_nr = 1

	while i < len(cod):
		if cod[i] == "\n":
			line_nr += 1
			i += 1
			continue

		if cod[i].isspace():
			i += 1
			continue

		matched = False

		for keyword in keywords:
			if cod.startswith(keyword, i):
				fip.append((keyword, keywords[keyword]))
				i += len(keyword)
				matched = True
				break
		
		if not matched:
			for fsm in fsm_list:
				prefix = fsm.cel_mai_lung_prefix(cod[i:])
				if prefix:
					clean_word = prefix.strip("'\"")
					if ts.get_index(clean_word) is not None:
						index = ts.get_index(clean_word)
						if prefix[0].isalpha():
							fip.append((prefix, f"{keywords['ID']} | {index}"))
						else:
							fip.append((prefix, f"{keywords['CONST']} | {index}"))
					else:
						exceptions.append(
							f"The word '{prefix}' is neither a keyword, variable, nor a constant. Line {line_nr}"
						)
					i += len(prefix)
					matched = True
					break

		if not matched:
			exceptions.append(
				f"Unexpected character '{cod[i]}' at index {i}, Line {line_nr}"
			)
			i += 1

	exception_message = None if not exceptions else "\n".join(exceptions)
	return fip, exception_message


if __name__=="__main__":
	print("="*200)
	nume_fisier = "cerc.pas"
	keywords = getKeywords("keywords.csv")
	cod = ""
	with open("cerc.pas", "r", encoding="utf-8") as f:
		cod = f.read()

	symbol_table = getTS(cod, keywords, [fsm_id, fsm_real, fsm_whole])
	symbol_table.print_map()
	print("="*200)
	symbol_table.write_to_file()

	fip, exception2 = getFIP(cod, keywords, symbol_table, [fsm_id, fsm_real, fsm_whole])

	for el in fip:
		print(el)
	print('\n\n',exception2)

	with open("fip.txt", "w") as f:
		for el in fip:
			f.write(el[0] + " " + el[1] + "\n")
		if exception2 != None:
			f.write(exception2)
   
	print("="*200)