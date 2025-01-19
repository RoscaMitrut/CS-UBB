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