import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Sudoku {
    private Square board[][] = new Square[9][9];

    public Sudoku(String filePath, boolean withDomains) throws FileNotFoundException {
        Scanner file = new Scanner(new File( "boards/"+filePath));

        if(withDomains) {
            for (int i = 0; i < 9; i++) {//row
                String nl = file.nextLine();

                Scanner scan = new Scanner(nl);
                scan.useDelimiter(",");
                for (int j = 0; j < 9; j++) { //column
                    String s;
                    if(scan.hasNext()) {
                        s = scan.next();
                    } else { s = "";}

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

    public void printSudokuBoard() {
        for(int r = 0; r < 9; r++) {
            for(int c = 0; c < 9; c++) {
                System.out.print(" " + board[r][c].getValue() + " ");
                if (c == 2 || c == 5) {
                    System.out.print(" | ");
                }
            }
            System.out.print("\n");
            if (r == 2 || r == 5) {

                System.out.print(" -  -  -  |  -  -  -  |  -  -  -\n");
            }
        }
        System.out.print("\n");
    }

    //copy constructor
    public Sudoku(Sudoku board){
        Square in[][] = board.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = new Square(in[i][j]);
            }
        }
    }

    public Square[][] getBoard() {
        return this.board;
    }

    /***
     * initializes all the domains in the board
     * @return false if the board is invalid
     */
    public boolean initDomains(){
        //iterate through the board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Square s = board[i][j];

                //if this position has a value
                if(s.getValue() != 0){
                    //reduce it's dependencies
                    //if a dependecy reduces to empty, return false
                    if(!reduce(i,j)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /***
     * reduce the domains of squares affected by the square at x,y
     * @param x
     * @param y
     * @return false if any domain reduces to []
     */
    public boolean reduce(int x, int y){
        Sudoku.Square s = board[x][y];
        int val = s.getValue();

        Sudoku.Square row[] = s.getRow();
        Sudoku.Square col[] = s.getColumn();
        Sudoku.Square bs[] = s.getSurroundingInSquare();

        //update the row
        for (Sudoku.Square t : row){
            //if the domain contains our updated value, remove it
            if (t.getDomain().contains(val)){
                ArrayList<Integer> d = t.getDomain();

                d.remove(val);
                //if after removing, the domain is empty, return false
                if (d.size() == 0){
                    return false;
                }
            }
        }

        //update the column
        for (Sudoku.Square t : col){
            //if the domain contains our updated value, remove it
            if (t.getDomain().contains(val)){
                ArrayList<Integer> d = t.getDomain();

                d.remove(val);
                //if after removing, the domain is empty, return false
                if (d.size() == 0){
                    return false;
                }
            }
        }

        //update the square
        for (Sudoku.Square t : bs) {
            //if the domain contains our updated value, remove it
            if (t.getDomain().contains(val)) {
                ArrayList<Integer> d = t.getDomain();

                d.remove(val);
                //if after removing, the domain is empty, return false
                if (d.size() == 0) {
                    return false;
                }
            }
        }

        return true;
    }


    public class Square{
        private int value;
        private ArrayList<Integer> domain;
        public int row, column;


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

        //copy
        Square(Square s){
            value = s.value;
            domain = new ArrayList<Integer>(s.domain);
            row = s.row;
            column = s.column;
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

        public void printDomain() {
            System.out.print("\n");
            for (int x : domain) {
                System.out.print(x + ", ");
            }
        }

        public Square[] getRow() {
            Square squares[] = new Square[8];
            int currentPos = 0;

            for(int i = 0; i < 9; i++) {
                if(i != column) {
                    squares[currentPos] = board[row][i];
                    currentPos++;
                }
            }

            return squares;
        }

        public Square[] getColumn() {
            Square squares[] = new Square[8];
            int currentPos = 0;

            for(int i = 0; i < 9; i++) {
                if(i != row) {
                    squares[currentPos] = board[i][column];
                    currentPos++;
                }
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
                } else if(i != row) {
                    squares[curPos] = board[i][column];
                    curPos++;
                }
            }

            //going down in current column if valid
            for(int i = row + 1; i < 9; i++) {
                //we've gone into another square
                if (i == 3 || i == 6) {
                    i = 9;
                } else if(i != row) {
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
            }


            //go to the right columns if valid
            for(int i = column + 1; i < 9; i++) {
                //we've gone into another square
                if (i == 3 || i == 6) {
                    i = 9;
                } else {
                    squares[curPos] = board[row][i];
                    curPos++;

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
            }

            return squares;
        }
    }
    //get row
    //get column
    //get square


}
