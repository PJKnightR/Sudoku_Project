import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Sudoku {
    private Square board[][] = new Square[9][9];

    public void Sudoku(String filePath, boolean withDomains) throws FileNotFoundException {
        Scanner file = new Scanner(new File("../boards/"+filePath));

        if(withDomains) {
            for (int i = 0; i < 9; i++) {//row
                Scanner scan = new Scanner(file.nextLine());
                scan.useDelimiter(",");
                for (int j = 0; j < 9; j++) { //column
                    String s = scan.next();

                    if(!s.equals("")){
                        int v = Character.getNumericValue( s.toCharArray()[0]);
                        ArrayList<Integer> domain = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

                        board[i][j] = new Square(v, domain, i, j);
                    } else {
                        //0 is a magic number that means there is no value
                        int v = 0;
                        ArrayList<Integer> domain = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

                        board[i][j] = new Square(v, domain, i, j);
                    }

                }
            }
        } else {
            for (int i = 0; i < 9; i++) {//row
                Scanner scan = new Scanner(file.nextLine());
                scan.useDelimiter(",");
                for (int j = 0; j < 9; j++) { //column
                    String s = scan.next();

                    if(!s.equals("")) {
                        int v = Character.getNumericValue(s.toCharArray()[0]);

                        board[i][j] = new Square(v, i, j);
                    }
                }
            }
        }


    }

    //copy constructor
    public Sudoku(Sudoku board){
        Square in[][] = board.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = in[i][j];
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
        Square(int val, int row, int column){
            value = val;
            this.row = row;
            this.column = column;
        }

        //with domain
        Square(int val, ArrayList<Integer> dom, int row, int column){
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
        
        public Square[] getSurroundingInSquare() {
            Square squares[] = new Square[8];
            int curPos = 0;

            //start going up in current column if valid
            for(int i = row - 1; i > -1; i--) {
                //we've gone into another square
                if (i == 2 || i == 5) {
                    i = -1;
                } else {
                    squares[curPos] = board[i][column];
                    curPos++;
                }
            }

            //going down in current column if valid
            for(int i = row + 1; i < 9; i++) {
                //we've gone into another square
                if (i == 3 || i == 6) {
                    i = 9;
                } else {
                    squares[curPos] = board[i][column];
                    curPos++;
                }
            }

            //go to the left columns if valid
            for(int i = column - 1; i > -1; i--) {
                //we've gone into another square
                if (i == 2 || i == 5) {
                    i = -1;
                } else {
                    squares[curPos] = board[row][i];
                    curPos++;
                }

                //check up and down in column
                //start going up in current column if valid
                for(int j = row - 1; j > -1; j--) {
                    //we've gone into another square
                    if (j == 2 || j == 5) {
                        j = -1;
                    } else {
                        squares[curPos] = board[j][i];
                        curPos++;
                    }
                }

                //going down in current column if valid
                for(int j = row + 1; j < 9; j++) {
                    //we've gone into another square
                    if (j == 3 || j == 6) {
                        j = 9;
                    } else {
                        squares[curPos] = board[j][i];
                        curPos++;
                    }
                }
            }

            //go to the right columns if valid
            for(int i = column + 1; i < 9; i++) {
                //we've gone into another square
                if (i == 3 || i == 6) {
                    i = 9;
                } else {
                    squares[curPos] = board[row][i];
                    curPos++;
                }

                //check up and down in column
                //start going up in current column if valid
                for(int j = row - 1; j > -1; j--) {
                    //we've gone into another square
                    if (j == 2 || j == 5) {
                        j = -1;
                    } else {
                        squares[curPos] = board[j][i];
                        curPos++;
                    }
                }

                //going down in current column if valid
                for(int j = row + 1; j < 9; j++) {
                    //we've gone into another square
                    if (j == 3 || j == 6) {
                        j = 9;
                    } else {
                        squares[curPos] = board[j][i];
                        curPos++;
                    }
                }
            }

            return squares;
        }
    }
    //get row
    //get column
    //get square


}
