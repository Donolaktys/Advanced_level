import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Task1Main {

    public static void main(String[] args) throws Exception {

        List<String> wordsArray = new ArrayList<>();
        wordsArray.add("долго"); wordsArray.add("ль"); wordsArray.add("мне");
        wordsArray.add("гулять"); wordsArray.add("на"); wordsArray.add("свете");
        wordsArray.add("то"); wordsArray.add("в"); wordsArray.add("коляске");
        wordsArray.add("то"); wordsArray.add("верхом");
        wordsArray.add("то"); wordsArray.add("в"); wordsArray.add("кибитке");
        wordsArray.add("то"); wordsArray.add("в"); wordsArray.add("карете");
        wordsArray.add("то"); wordsArray.add("в"); wordsArray.add("телеге");
        wordsArray.add("то"); wordsArray.add("пешком");

        System.out.println(wordsArray);

        HashMap<String, Integer> uniqueWordsArray = new HashMap<>();
        Integer counter;
        for (String word: wordsArray) {

            counter = uniqueWordsArray.get(word);
            uniqueWordsArray.put(word, counter == null ? 1 : counter + 1);
        }

        System.out.println(uniqueWordsArray);
    }

}

/**
 * Результат выполнения:
 * [долго, ль, мне, гулять, на, свете, то, в, коляске, то, верхом, то, в, кибитке, то, в, карете, то, в, телеге, то, пешком]
 * {карете=1, долго=1, мне=1, телеге=1, пешком=1, коляске=1, кибитке=1, ль=1, в=4, на=1, гулять=1, верхом=1, то=6, свете=1}
 */
