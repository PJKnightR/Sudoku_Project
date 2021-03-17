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
        private int row, column;

        //no domain
        void Square(int val, int row, int column){
            value = val;
            this.row = row;
            this.column = column;
        }

        //with domain
        void Square(int val, ArrayList<Integer> dom, int row, int column){
            domain = dom;
            value = val;
            this.row = row;
            this.column = column;
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

        public Square[] getRow() {
            Square squares[] = new Square[8];

            for(int i = 0; i < 9; i++) {
                squares[i] = board[row][i];
            }

            return squares;
        }

        public Square[] getColumn() {
            Square squares[] = new Square[8];

            for(int i = 0; i < 9; i++) {
                squares[i] = board[i][column];
            }

            return squares;
        }
    }
    //get row
    //get column
    //get square


}
