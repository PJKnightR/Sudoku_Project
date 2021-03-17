import java.util.*;

public class sudokuSolver {
    public LinkedList<Sudoku.Square> squareQueue;
    public Sudoku curSudokuBoard, originalSudokuBoard, tempSudokuBoard;

    public sudokuSolver(Sudoku board) {
        this.squareQueue = new LinkedList<Sudoku.Square>();
        this.curSudokuBoard = board;
        this.originalSudokuBoard = board;
    }

    public boolean performAC3(Sudoku board, int curRow, int curColumn) {

        //find first entry without a set value, ignored if no value
        while (board.getBoard()[curRow][curColumn].getValue() != 0) {
            if(curColumn < 8) {
                curColumn++;
            } else {
                curColumn = 0;
                curRow++;
            }

            if(curRow > 8) { //we succeeded, every node has been given a valid value
                //TO-DO: Print square
                return true;
            }
        }

        //loop through plugging each value into the first entry
        for(int currentGuess = 1; currentGuess < 10; currentGuess++) {
            if (Arrays.asList(board.getBoard()[curRow][curColumn].getDomain()).contains(currentGuess)) {
                board.getBoard()[curRow][curColumn].setValue(currentGuess);

                if(ACThree(board)) {
                    if(curColumn < 8) {
                        curColumn++;
                    } else {
                        curColumn = 0;
                        curRow++;
                    }

                    if(curRow > 8) { //we succeeded, every node has been given a valid value
                        //TO-DO: Print square
                        return true;
                    }

                    //we succeeded, continue to next node with recursive call
                    if (!performAC3(tempSudokuBoard, curRow, curColumn)) {
                        //TO-DO:
                        //the node failed, we return to the beginning
                    }

                }
            }
        }

        return false; //we failed to find a valid number based on remaining Domain, this path was not valid
        //if the first node fails, the given puzzle was invalid
    }

    //constrain all values, then start plugging in and perform AC3 again
    public boolean ACThree(Sudoku board) {
        //Iterate through cube entries
        //if we find an entry with only one value in the domain, add its neighbors to queue
        //if we cannot find a domain of one, look for 2 and so on

        while (!squareQueue.isEmpty()) {
            Sudoku.Square currentSquare = squareQueue.pop();

            if (reviseConstraints(currentSquare)) {
                if (currentSquare.getDomain().length == 0 && currentSquare.getValue() == 0) { //figure out what we want to use when there is no value
                    return false;
                }
                //add each neighbor to the queue in the square
                //add each neighbor to the queue in the row
                //add each neighbor to the queue in the column
            }
        }

        tempSudokuBoard = board;
        return true;
    }

    private boolean reviseConstraints(Sudoku.Square square) {
        boolean revised = false;
        for (int x: square.getDomain()) {
            //compare to other values in square
            //compare to other values in row
            //compare to other values in column
        }

        return revised;
    }

}
