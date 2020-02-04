public class MyArrayDataException extends Exception {
    int row, column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public MyArrayDataException(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
