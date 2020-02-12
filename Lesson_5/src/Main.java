
public class Main {

    public static void main(String[] args) {
        MyArray.singleThread();
        System.out.println("______________________________");
        MyArray.twoThreads();
    }

}


/**

 Время на пересчет массива первым методом 5934
 ______________________________
 Время на разбивку 16
 Время на пересчет второго потока 1817
 Время на пересчет первого потока 1823
 Время на склейку 8
 Время на пересчет массива вторым методом 1900

 Process finished with exit code 0
