import java.util.ArrayList;
import java.util.Stack;

public class ForwardCheckingSolver {
    private Sudoku board;

    public ForwardCheckingSolver(Sudoku board){
        this.board = new Sudoku(board);
        if(!board.initDomains()){
            System.out.println("Invalid board");
            System.exit(1);
        }
    }

    public Sudoku solve(Sudoku state) {
        Sudoku.Square b[][] = state.getBoard();
        //loop through board and start filling in values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Sudoku.Square s = b[i][j];

                //if the square does not have a value already
                if (s.getValue() == 0){

                    //guess a number in the domain
                    for (int g: s.getDomain()) {
                        //temp board to try the change
                        Sudoku temp = new Sudoku(state);
                        temp.getBoard()[i][j].setValue(g);

                        //if forward checking is valid
                        if (temp.reduce(i, j)){
                            //move on
                            Sudoku solution = solve(temp);
                            //if we found a solution
                            if(solution != null){
                                return solution;
                            } //if we didn't find a solution we try the next value in the domain
                        }
                        //if forward checking is invalid try the next value in the domain
                    }//end foreach
                    //if we make it here without returning, we can't solve this branch
                    return null;
                }//end if
                //otherwise continue
            }
        }
        //if we iterate through the entire board and everything has a value, we have a solution
        return state;
    }


    /***
     * check if a potential value is valid for a specified position
     * @param x x coordinate of the square to check
     * @param y y coordiante of the square to check
     * @param testVal the value to check
     * @return true if value is valid option
     */
    private boolean isValid(int x, int y, int testVal){
        Sudoku.Square s = board.getBoard()[x][y];

        Sudoku.Square row[] = s.getRow();
        Sudoku.Square col[] = s.getColumn();
        Sudoku.Square bs[] = s.getSurroundingInSquare();

        for (Sudoku.Square t : row){
            //if something else in the row already has that value, its not a valid option
            if (t.getValue() == testVal){
                return false;
            }
        }

        for (Sudoku.Square t : col){
            //if something else in the column already has that value, its not a valid option
            if (t.getValue() == testVal){
                return false;
            }
        }

        for (Sudoku.Square t : bs){
            //if something else in the surrounding square already has that value, its not a valid option
            if (t.getValue() == testVal){
                return false;
            }
        }

        //if none of the above find it to be invalid, then its valid
        return true;
    }


}
