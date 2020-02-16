
public class Main {

    public static void main(String[] args) {
        MyArray.singleThread();
        System.out.println("______________________________");
        MyArray.twoThreads();
    }

}


/**
 * Время на пересчет массива первым методом 5803
 * ______________________________
 * Время на разбивку 6
 * Время на пересчет первого потока 1662
 * Время на пересчет второго потока 4101
 * Время на склейку 6
 * Время на пересчет массива вторым методом 4178
 *
 * Process finished with exit code 0
 */