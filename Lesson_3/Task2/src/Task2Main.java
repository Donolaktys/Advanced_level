import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2Main {

    private static class Phonebook {
        private static Map<String, String> pb = new HashMap<>();

        private static void add(String surname, String number) {
            pb.put(number, surname);
        }

        private static List<String> getNumber(String surname){
            List<String> phoneNumber = new ArrayList<>();
            for (Map.Entry<String, String> entry :
                    pb.entrySet()) {
                if(entry.getValue() == surname) {
                    phoneNumber.add(entry.getKey());
                }
            }
            return phoneNumber;
        }
    }

    public static void main(String[] args) {
        Phonebook.add("Ivanov", "79258741236");
        Phonebook.add("Petrov", "79258741237");
        Phonebook.add("Ivanov", "79258741238");

        System.out.println(Phonebook.pb);

        System.out.println("Номера в справочнике для фамилии Ivanov " + Phonebook.getNumber("Ivanov"));
    }
}

/**
 * Результат выполнения:
 * {79258741238=Ivanov, 79258741237=Petrov, 79258741236=Ivanov}
 * Номера в справочнике для фамилии Ivanov [79258741238, 79258741236]
 */