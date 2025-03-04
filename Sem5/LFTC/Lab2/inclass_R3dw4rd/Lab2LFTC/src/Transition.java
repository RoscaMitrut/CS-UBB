public class Transition {
    String initialState;
    String finalState;
    String value;
    public Transition(String initialState, String finalState, String value) {
        this.initialState = initialState;
        this.finalState = finalState;
        this.value = value;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getFinalState() {
        return finalState;
    }

    public void setFinalState(String finalState) {
        this.finalState = finalState;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "initialState='" + initialState + '\'' +
                ", finalState='" + finalState + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
