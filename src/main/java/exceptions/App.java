package exceptions;

public class App {
    private static final int X_SIZE = 4;
    private static final int Y_SIZE = 4;

    public static void main(String[] args) {
        String[][] arrCorrect = getArrCorrect();
        String[][] arrIncorrect1 = getArrIncorrect1();
        String[][] arrIncorrect2 = getArrIncorrect2();
        String[][] arrIncorrect3 = getArrIncorrect3();

        checkAndSumElement(arrCorrect);
    }

    private static void checkAndSumElement(String[][] arr) {
        if (arr.length != Y_SIZE) {
            throw new MyArraySizeException("Size arr don't 4X4 (Y coordinate not equal 4) ");
        }
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i].length != X_SIZE) {
                    throw new MyArraySizeException("Size arr don't 4X4 (X coordinate not equal 4) ");
                }
                try {
                    sum += Integer.parseInt(arr[j][i]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Error in cell with coordinates " + i + "-" + j);
                }
                System.out.print(arr[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("sum = " + sum + "\n");
    }

    private static String[][] getArrCorrect() {
        return new String[][]{
                {"21", "22", "23", "24"},
                {"25", "26", "27", "28"},
                {"29", "30", "31", "32"},
                {"33", "34", "35", "36"}
        };
    }

    private static String[][] getArrIncorrect1() {
        return new String[][]{
                {"21", "22", "23"},
                {"20", "30", "40", "50"},
                {"25", "26", "27", "28"},
                {"29", "30", "31", "32"}
        };
    }

    private static String[][] getArrIncorrect2() {
        return new String[][]{
                {"21", "22", "23", "24"},
                {"20", "30", "40", "50"},
                {"25", "26", "27", "28"},
                {"29", "30", "31", "32"},
                {"33", "34", "35", "36"}
        };
    }

    private static String[][] getArrIncorrect3() {
        return new String[][]{
                {"21", "22", "23", "24"},
                {"25", "26", "27", "28"},
                {"29", "30asdf", "31", "32"},
                {"33", "34", "35", "36"}
        };
    }
}
