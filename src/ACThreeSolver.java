import java.util.*;

public class ACThreeSolver {
    public LinkedList<Sudoku.Square> squareQueue;
    public Sudoku curSudokuBoard, tempSudokuBoard;
    public Sudoku.Square tempSquare;

    public ACThreeSolver(Sudoku board) {
        this.squareQueue = new LinkedList<Sudoku.Square>();
        this.curSudokuBoard = board;
    }

    public boolean performAC3(Sudoku board, int curRow, int curColumn) {
        //find first entry without a set value, ignored if there is an assigned value
        while (board.getBoard()[curRow][curColumn].getValue() != 0) {
            //the entry only had one value in its domain. Thus, that is the only value it can have
            if (board.getBoard()[curRow][curColumn].getDomain().size() == 1) {
                board.getBoard()[curRow][curColumn].setValue(board.getBoard()[curRow][curColumn].getDomain().get(0));
            } else {
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
        }

        //loop through plugging each value into the first entry
        for(int currentGuess = 1; currentGuess < 10; currentGuess++) {
            if (Arrays.asList(board.getBoard()[curRow][curColumn].getDomain()).contains(currentGuess)) {
                board.getBoard()[curRow][curColumn].setValue(currentGuess);

                //if this node succeeds, we continue to the next
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

                    //continue to next node with recursive call
                    if (!performAC3(tempSudokuBoard, curRow, curColumn)) {
                        //we failed and have to reset some values
                        if (curColumn == 0) {
                            curColumn = 8;
                            curRow--;
                        } else {
                            curColumn--;
                        }
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

        while (!squareQueue.isEmpty()) {
            Sudoku.Square currentSquare = squareQueue.pop();

            if (reviseConstraints(currentSquare)) {
                currentSquare = tempSquare; //has new constraints
                if (currentSquare.getDomain().size() == 0 && currentSquare.getValue() == 0) { //figure out what we want to use when there is no value
                    return false;
                }

                //TO-DO: check if in queue

                //add each neighbor to the queue in the row
                Sudoku.Square[] addToQueue = currentSquare.getRow();

                for (Sudoku.Square x: addToQueue) {
                    squareQueue.add(x);
                }

                //add each neighbor to the queue in the column
                addToQueue = currentSquare.getColumn();

                for (Sudoku.Square x: addToQueue) {
                    squareQueue.add(x);
                }

                //add each neighbor to the queue in the square
                addToQueue = currentSquare.getSurroundingInSquare();

                for (Sudoku.Square x: addToQueue) {
                    squareQueue.add(x);
                }
            }
        }

        tempSudokuBoard = board;
        return true;
    }

    private boolean reviseConstraints(Sudoku.Square square) {
        boolean revised = false;
        for (int x: square.getDomain()) {
            //we are updating the constraints of square if it has values it cannot

            //see if row or column or 3x3 square contains value
            if(Arrays.asList(getValues(square.getRow())).contains(x)
                    || Arrays.asList(getValues(square.getColumn())).contains(x)
                    || Arrays.asList(getValues(square.getSurroundingInSquare())).contains(x)) {
                square = removeFromDomain(square, x);
                revised = true;
            }
        }

        tempSquare = square;
        return revised;
    }

    public Sudoku.Square removeFromDomain(Sudoku.Square square, int val) {
        for (int i = 0; i < square.getDomain().size(); i++) {
            if(square.getDomain().get(i) == val) {
                square.getDomain().remove(i);
                i--;
            }
        }

        return square;
    }

    public int[] getValues(Sudoku.Square[] s) {
        int[] values = new int[s.length];

        for(int i = 0; i < s.length; i++) {
            values[i] = s[i].getValue();
        }

        return values;
    }

}
