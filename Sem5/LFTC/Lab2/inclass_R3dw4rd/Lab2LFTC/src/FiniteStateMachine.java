import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FiniteStateMachine {
    ArrayList<String> alphabet = new ArrayList<>();
    ArrayList<String> states = new ArrayList<>();
    ArrayList<String> end_state = new ArrayList<>();
    ArrayList<Transition> transitions = new ArrayList<>();
    String start_state;
    public FiniteStateMachine() {}
    public FiniteStateMachine(ArrayList<String> alphabet, ArrayList<String> states,
                              String start_state, ArrayList<String> end_state,
                              ArrayList<Transition> transitions) {
        this.alphabet = alphabet;
        this.states = states;
        this.start_state = start_state;
        this.end_state = end_state;
        this.transitions = transitions;
    }

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }
    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public void setStates(ArrayList<String> states) {
        this.states = states;
    }

    public ArrayList<String> getEnd_state() {
        return end_state;
    }

    public void setEnd_state(ArrayList<String> end_state) {
        this.end_state = end_state;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public String getStart_state() {
        return start_state;
    }

    public void setStart_state(String start_state) {
        this.start_state = start_state;
    }


    public boolean checkCompliance(String sequence) {
        String prefix = "";
        String currentState = this.start_state;

        while (!sequence.isEmpty()) {
            boolean found = false;
            for (Transition transition : this.transitions) {
                String transitionValue = transition.getValue();
                if (transition.getInitialState().equals(currentState) && sequence.startsWith(transitionValue)) {
                    prefix += transitionValue;
                    sequence = sequence.substring(transitionValue.length());
                    currentState = transition.getFinalState();
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return this.end_state.contains(currentState);// to see if the sequence reached final state
    }

    public boolean isDeterminist() {
        /**
         * if two transitions have:
         * 1. the same initialState
         * 2. same Value
         * 3. Different final State
         * => non-determinist */
        for (Transition transition : transitions) {
            for (Transition transition1 : transitions) {
                if (transition1.getInitialState().equals(transition.getInitialState()) &&
                        transition1.getValue().equals(transition.getValue()) &&
                        !transition1.getFinalState().equals(transition.getFinalState())
                ) {
                    return false;
                }
            }
        }
        return true;

    }

    public String getLongestPrefix(String sequence) {
        String curr_prefix = "";
        String final_prefix = "";
        String currentState = this.start_state;

        // Loop through the sequence to process it character by character
        while (!sequence.isEmpty()) {
            boolean foundTransition = false;
            for (Transition transition : this.transitions) {
                String transitionValue = transition.getValue();

                if (transition.getInitialState().equals(currentState) && sequence.startsWith(transitionValue)) {
                    curr_prefix += transitionValue;
                    sequence = sequence.substring(transitionValue.length());
                    currentState = transition.getFinalState();
                    foundTransition = true;

                    // Check if the final state is a valid accepting state
                    for (String finalState : end_state) {
                        if (currentState.equals(finalState)) {
                            final_prefix = curr_prefix;  // Update the final prefix
                        }
                    }
                }
            }

            // If no valid transition was found, break the loop
            if (!foundTransition) {
                break;
            }
        }

        if (final_prefix.isEmpty()) {
            // If the initial state is also a final state, return "epsilon"
            for (String finalState : end_state) {
                if (Objects.equals(start_state, finalState)) {
                    return "epsilon";
                }
            }
        }

        return final_prefix;
    }
}
