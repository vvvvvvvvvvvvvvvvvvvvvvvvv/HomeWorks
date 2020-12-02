package com.company.Lesson2;

public class  Exceptions {
    static int i = 1;
    private static Object test;

    public static void main(String[] args) {
        final String[][] matrix = new String[][]{
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "b", "4"},
                {"1", "2", "3", "4"}
        };
        try {
            print4x4Matrix(matrix);

        }catch (MyArrayDataException e){
            System.out.println(e.row + " " + e.column);
        }catch (MyArraySizeException e) {
            System.out.println("Not 4x4");
        }
    }

    public static void print4x4Matrix(String[][] matrix){
       if(matrix.length != 4){
           throw new MyArraySizeException();
       }
        int summ = 0;
        for (int j = 0; j < matrix.length; j++) {
            if(matrix[j].length != 4){
                throw new MyArraySizeException();
            }

            for (int k = 0; k < matrix[j].length; k++) {
                try {
                    final int num = Integer.parseInt(matrix[j][k]);
                    summ += num;
                }catch (NumberFormatException e){
                    throw new MyArrayDataException(j, k);
                }


            }
        }
        System.out.println(summ);
    }


}
class MyArraySizeException extends RuntimeException {

}
class MyArrayDataException extends RuntimeException {
     int row;
     int column;
     MyArrayDataException(int row, int column){
         this.row = row;
         this.column = column;
     }

}

