public class MyArray{
    private static int size = 10000000;
    private static final int h = size / 2;
    private static float[] arr = new float[size];;

    public MyArray() {

    }

    private static void initArray() {
        for (int i = 0; i < size; i++) {
            arr[i] = 1f;
        }
    }
    private static float[] getArr() {
        return arr;
    }

    private static void recount(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    /**
     * Метод без разделения массива
     */
    public static void singleThread() {
        initArray();

        long a = System.currentTimeMillis();
        recount(getArr());
        System.out.println("Время на пересчет массива первым методом " + (System.currentTimeMillis() - a));
    }

    /**
     * Метод с разделением массива
     */
    public static void twoThreads() {
        long all = System.currentTimeMillis();
        float[] a1 = new float[h], a2 = new float[h];

        initArray();

//разбиваем массив на 2
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        System.out.println("Время на разбивку " + (System.currentTimeMillis() - a));

//обрабатываем 2 массива в разных потоках
        Thread rec1 = new Thread(() -> {
            long b = System.currentTimeMillis();
            recount(a1);
            System.out.println("Время на пересчет первого потока " + (System.currentTimeMillis() - b));
        });

        Thread rec2 = new Thread(() -> {
            long c = System.currentTimeMillis();
            recount(a2);
            System.out.println("Время на пересчет второго потока " + (System.currentTimeMillis() - c));
        });

        rec1.start();
        rec2.start();

//ждем конец работы потоков
        try {
            rec1.join();
            rec2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//объединяем 2 полученных массива
        long d = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        System.out.println("Время на склейку " + (System.currentTimeMillis() - d));

        System.out.println("Время на пересчет массива вторым методом " + (System.currentTimeMillis() - all));

    }

}



//        Значения получаются ~ одинаковые, но есть ли предпочтительное решение для вычисления времени работы потоков
//        Throw ... { ... sout}
//        или как в коде ниже
//        try { rec1.join(); sout; ... }
//        и есть ли вообще разница?


//        long b = System.currentTimeMillis();
//        Thread rec1 = new Thread(() -> {
//            recount(a1);
//        });
//
//        long c = System.currentTimeMillis();
//        Thread rec2 = new Thread(() -> {
//            recount(a2);
//        });
//
//        rec1.start();
//        rec2.start();
//
//        try {
//            rec1.join();
//            System.out.println("Время на пересчет первого потока " + (System.currentTimeMillis() - b));
//            rec2.join();
//            System.out.println("Время на пересчет второго потока " + (System.currentTimeMillis() - c));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
