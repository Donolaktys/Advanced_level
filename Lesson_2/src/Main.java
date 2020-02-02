public class Main {

    public static void main(String[] args) {
        String[][] array = {
                {"123", "234", "345", "4567"},
                {"5647", "5647", "234", "345"},
                {"234", "987", "uiy", "254"},
                {"457", "8547", "8549", "54"}
        };

        try {
        System.out.println("Сумма целочисленных элементов массива = " + calculationArray(array));
        } catch (MyArraySizeException se) {
            System.out.println(se.getMessage());
        } catch (MyArrayDataException de){
            System.out.println("значение ячейки [" + de.getRow() + "][" + de.getColumn() + "] нельзя преобразовать в число");
        }
    }

    public static int calculationArray(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;

        if (arr.length != 4) {
            throw new MyArraySizeException("Неверный размер массива");
        } else {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length != 4) {
                    throw new MyArraySizeException("Количество элементов в строке " + i + " не равно 4");
                }
                for (int j = 0; j < arr[i].length; j++) {
                    try {
                        sum += Integer.parseInt(arr[i][j]);
                    } catch (NumberFormatException e) {
                        throw new MyArrayDataException(i, j);
                    }
                }
            }
        }

        return sum;
    }
}
