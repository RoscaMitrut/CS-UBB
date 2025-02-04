import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
    static ArrayList<String> alphabet = new ArrayList<>();
    static ArrayList<String> states = new ArrayList<>();
    static ArrayList<String> end_state = new ArrayList<>();
    static ArrayList<Transition> transitions = new ArrayList<>();
    static String start_state;


    public static void readFromFile(String filename) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            alphabet.addAll(Arrays.asList(bufferedReader.readLine().split(" ")));
            states.addAll(Arrays.asList(bufferedReader.readLine().split(" ")));
            start_state = bufferedReader.readLine();
            end_state.addAll(Arrays.asList(bufferedReader.readLine().split(" ")));
            String raw_transition = bufferedReader.readLine();
            while (raw_transition != null) {
                String[] splitted = raw_transition.split(" ");
                transitions.add(new Transition(splitted[0], splitted[1], splitted[2]));
                raw_transition = bufferedReader.readLine();
            }
            System.out.println("Read successfully from file" + filename);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void readFromConsole() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter alphabet(separate space): ");
        alphabet.addAll(Arrays.asList(scanner.nextLine().split(" ")));

        System.out.println("Enter state(separate space): ");
        states.addAll(Arrays.asList(scanner.nextLine().split(" ")));

        System.out.println("Enter start state ONE: ");
        start_state = scanner.nextLine();

        System.out.println("Enter end-state(separate space): ");
        end_state.addAll(Arrays.asList(scanner.nextLine().split(" ")));

        System.out.println("Enter transitionand than enter (pattern: start_state end_state value) ");
        String raw_transition = scanner.nextLine();
        while (raw_transition != null && !raw_transition.isEmpty()) {
            String[] splitted = raw_transition.split(" ");
            transitions.add(new Transition(splitted[0], splitted[1], splitted[2]));
            raw_transition = scanner.nextLine();
        }

        System.out.println("Read successfully from console");
    }

    public static void Menu() {
        System.out.println("----------Functionalities------------------");
        System.out.println("\t1. States");
        System.out.println("\t2. Input alphabet");
        System.out.println("\t3. Transitions");
        System.out.println("\t4. Final states");
        System.out.println("\t5. Check the compliance of a sequence");
        System.out.println("\t6. Determine the longest compliant offset");
        System.out.println("\t0. Exit");

    }

    public static void main(String[] args) {

        String option;
        System.out.println("1: read from file");
        System.out.println("2: read from console");
        System.out.println("3: exit");
        System.out.println("Enter a command : ");
        Scanner scanner = new Scanner(System.in);
        option = scanner.nextLine();
        switch (option) {
            case "1" -> readFromFile("./src/data/af1.txt");
            case "2" -> readFromConsole();

            case "3" -> {
                System.out.println("Exiting program...");
                return;
            }
            default -> System.out.println("Invalid option");
        }
        FiniteStateMachine af=new FiniteStateMachine(alphabet,states,start_state,end_state,transitions);
        boolean loop = true;

        while (loop) {
            Menu();
            option = scanner.nextLine();

            switch (option) {
                case "0"-> loop = false;
                case "1" -> System.out.println(af.getStates());
                case "2" -> System.out.println(af.getAlphabet());
                case "3" -> af.getTransitions().forEach(System.out::println);
                case "4" -> af.getEnd_state().forEach(System.out::println);
                case "5" -> {
                    if (!af.isDeterminist()) {
                        System.out.println("The FSM is not determinist. This option is not available!");
                        break;
                    }

                    System.out.println("Introduce the sequence: ");
                    String sequence = scanner.nextLine();
                    if (af.checkCompliance(sequence)) {
                        System.out.println("The sequence is compliant");
                    } else {
                        System.out.println("The sequence is not compliant");
                    }
                }
                case "6" -> {
                    if (!af.isDeterminist()) {
                        System.out.println("The FSM is not determinist. This option is not available!");
                        break;
                    }

                    System.out.println("Introduce the sequence: ");
                    String sequence = scanner.nextLine();
                    String result = af.getLongestPrefix(sequence);
                    System.out.println(result.isEmpty() ? "no match..." : result);
                }
                default -> System.out.println("Invalid option");

            }

        }
    }
}
