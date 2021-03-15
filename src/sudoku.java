import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class sudoku {
    private square board[][] = new square[9][9];

    public void sudoku(String filePath, boolean withDomains) throws FileNotFoundException {
        Scanner file = new Scanner(new File(filePath));

        if(withDomains) {
            for (int i = 0; i < 9; i++) {//row
                Scanner scan = new Scanner(file.nextLine());
                scan.useDelimiter(",");
                for (int j = 0; j < 9; j++) { //column
                    String s = scan.next();

                    if(!s.equals("")){
                        int v = Character.getNumericValue( s.toCharArray()[0]);
                        //board[i][j] = new square(v, new int[] = [v]);
                    }

                }
            }
        } else {
            for (int i = 0; i < 9; i++) {//row
                for (int j = 0; j < 9; j++) { //column

                }
            }
        }


    }

    private class square{
        private int value;
        private int[] domain;

        //no domain
        void square(int val){
            value = val;
        }

        //with domain
        void square(int val, int[] dom){
            domain = dom;
            value = val;
        }
    }
    //get row
    //get column
    //get square


}
