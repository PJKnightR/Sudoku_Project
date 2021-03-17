import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sudoku {
    private Square board[][] = new Square[9][9];

    public void Sudoku(String filePath, boolean withDomains) throws FileNotFoundException {
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

    public Square[][] getBoard() {
        return this.board;
    }

    public class Square{
        private int value;
        private ArrayList<Integer> domain;

        //no domain
        void Square(int val){
            value = val;
        }

        //with domain
        void Square(int val, ArrayList<Integer> dom){
            domain = dom;
            value = val;
        }

        public int getValue() {
            return this.value;
        }

        public void setValue(int v) {
            this.value = v;
        }

        public ArrayList<Integer> getDomain() {
            return this.domain;
        }

        public void setDomain(ArrayList<Integer> d) {
            this.domain = d;
        }

        public Square[] getRow(int r) {
            Square squares[] = new Square[8];

            for(int i = 0; i < 9; i++) {
                squares[i] = board[r][i];
            }

            return squares;
        }

        public Square[] getColumn(int c) {
            Square squares[] = new Square[8];

            for(int i = 0; i < 9; i++) {
                squares[i] = board[i][c];
            }

            return squares;
        }
    }
    //get row
    //get column
    //get square


}
