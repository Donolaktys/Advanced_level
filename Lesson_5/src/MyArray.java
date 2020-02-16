public class MyArray{
    private static int size = 10000000;
    private static final int h = size / 2;
    private static float[] arr = new float[size];;

//Конструктор
    public MyArray() { }

//Присвоить всем элементам значение 1
    private static void initArray() { for (int i = 0; i < size; i++) { arr[i] = 1f; } }

//Возвращает массив
    private static float[] getArr() {
        return arr;
    }

    /**
     *  Вычислить каждый элемент массива по формуле
     *  recount для полного массива или первой части разделенного
     *  recount2 для второй части разделенного
     */

    private static void recount(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void recount2(float[] array) {
        int i = h;
        for (int j = 0; j < array.length; j++, i++) {
            array[j] = (float)(array[j] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
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

//разбить массив на 2 массива
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        System.out.println("Время на разбивку " + (System.currentTimeMillis() - a));

//обработать 2 массива в разных потоках
        Thread rec1 = new Thread(() -> {
            long b = System.currentTimeMillis();
            recount(a1);
            System.out.println("Время на пересчет первого потока " + (System.currentTimeMillis() - b));
        });

        Thread rec2 = new Thread(() -> {
            long c = System.currentTimeMillis();
            recount2(a2);
            System.out.println("Время на пересчет второго потока " + (System.currentTimeMillis() - c));
        });

        rec1.start();
        rec2.start();

        try {
            rec1.join();
            rec2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//объединить 2 полученных массива
        long d = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        System.out.println("Время на склейку " + (System.currentTimeMillis() - d));

        System.out.println("Время на пересчет массива вторым методом " + (System.currentTimeMillis() - all));

    }
}
