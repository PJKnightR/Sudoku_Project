import java.util.*;

public class ACThreeSolver {
    public Sudoku curSudokuBoard, tempSudokuBoard;
    public Sudoku.Square tempSquare;

    public ACThreeSolver(Sudoku board) {
        this.curSudokuBoard = board;
    }

    public boolean performAC3(Sudoku board, int curRow, int curColumn) {
        //find first entry without a set value, ignored if there is an assigned value

        while (board.getBoard()[curRow][curColumn].getValue() != 0) {
            //the entry only had one value in its domain. Thus, that is the only value it can have

            if (board.getBoard()[curRow][curColumn].getValue() != 0) {
                if(curColumn < 8) {
                    curColumn++;
                } else {
                    curColumn = 0;
                    curRow++;
                }

                if(curRow > 8) { //we succeeded, every node has been given a valid value
                    System.out.println("Final Board:");
                    board.printSudokuBoard();
                    return true;
                }
            }
        }

        //loop through plugging each value into the first entry
        for(int currentGuess = 1; currentGuess < 10; currentGuess++) {
            boolean inDomain = false;
            for (int x : board.getBoard()[curRow][curColumn].getDomain()) {
                if (x == currentGuess) {
                    inDomain = true;
                }
            }

            if (inDomain) {
                board.getBoard()[curRow][curColumn].setValue(currentGuess);

                //if this node succeeds, we continue to the next
                if(ACThree(board, board.getBoard()[curRow][curColumn])) {
                    if(curColumn < 8) {
                        curColumn++;
                    } else {
                        curColumn = 0;
                        curRow++;
                    }

                    if(curRow > 8) { //we succeeded, every node has been given a valid value
                        System.out.println("Final Board:");
                        board.printSudokuBoard();
                        return true;
                    }

                    //continue to next node with recursive call
                    if (!performAC3(tempSudokuBoard, curRow, curColumn)) {
                        //we failed and have to reset some values
                        if (board.getBoard()[curRow][curColumn].getDomain().size() == 0) {
                            return false;
                        } else {
                            if (board.getBoard()[curRow][curColumn].getDomain().get(0) != 0) {
                                board.getBoard()[curRow][curColumn].setValue(0);
                            }
                        }

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

        if (curColumn == 0 && curRow == 0) {
            System.out.println("Final Board:");
            board.printSudokuBoard();
        }

        return false; //we failed to find a valid number based on remaining Domain, this path was not valid
        //if the first node fails, the given puzzle was invalid
    }

    //constrain all values, then start plugging in and perform AC3 again
    public boolean ACThree(Sudoku board, Sudoku.Square startSquare) {
        //Iterate through cube entries
        LinkedList<Sudoku.Square> squareQueue = new LinkedList<Sudoku.Square>();
        squareQueue.add(startSquare);

        while (!squareQueue.isEmpty()) {
            Sudoku.Square currentSquare = squareQueue.pop();

            boolean goodEntry = false;
            while (!squareQueue.isEmpty() && !goodEntry) {
                if(currentSquare.getDomain().size() == 0) {
                    return false;
                }
                if(currentSquare.getDomain().get(0) == 0) {
                    currentSquare = squareQueue.pop();
                } else {
                    goodEntry = true;
                }
            }

            if (reviseConstraints(currentSquare)) {
                currentSquare = tempSquare; //has new constraints
                if (currentSquare.getDomain().size() == 0 && currentSquare.getValue() == 0) { //figure out what we want to use when there is no value
                    return false;
                }

                //add each neighbor to the queue in the row
                Sudoku.Square[] addToQueue = currentSquare.getRow();

                for (Sudoku.Square x: addToQueue) {
                    boolean inQueue = false;
                    for (int p = 0; p < squareQueue.size(); p++) {
                        if (squareQueue.get(p) == x) {
                            inQueue = true;
                        }
                    }
                    if (!inQueue) {
                        squareQueue.add(x);
                    }
                }

                //add each neighbor to the queue in the column
                addToQueue = currentSquare.getColumn();

                for (Sudoku.Square x: addToQueue) {
                    boolean inQueue = false;
                    for (int p = 0; p < squareQueue.size(); p++) {
                        if (squareQueue.get(p) == x) {
                            inQueue = true;
                        }
                    }
                    if (!inQueue) {
                        squareQueue.add(x);
                    }
                }

                //add each neighbor to the queue in the square
                addToQueue = currentSquare.getSurroundingInSquare();

                for (Sudoku.Square x: addToQueue) {
                    boolean inQueue = false;
                    for (int p = 0; p < squareQueue.size(); p++) {
                        if (squareQueue.get(p) == x) {
                            inQueue = true;
                        }
                    }
                    if (!inQueue) {
                        squareQueue.add(x);
                    }
                }
            }
        }

        tempSudokuBoard = board;
        return true;
    }

    public boolean reviseConstraints(Sudoku.Square square) {
        boolean revised = false;
        ArrayList <Integer> valArrayOriginal = square.getDomain();
        ArrayList <Integer> foundValues = new ArrayList<Integer>();

        for (int x = 0; x < valArrayOriginal.size(); x++) {
            boolean found = false;
            //we are updating the constraints of square if it has values it cannot

            int[] valArrayRow = getValues(square.getRow());

            for (int y = 0; y < valArrayRow.length && !found; y++) {
                if (valArrayRow[y] == valArrayOriginal.get(x)) {
                    found = true;
                    foundValues.add(valArrayOriginal.get(x));
                }
            }

            int[] valArrayColumn = getValues(square.getColumn());

            for (int y = 0; y < valArrayColumn.length && !found; y++) {
                if (valArrayColumn[y] == valArrayOriginal.get(x)) {
                    found = true;
                    foundValues.add(valArrayOriginal.get(x));
                }
            }

            int[] valArraySquare = getValues(square.getSurroundingInSquare());

            for (int y = 0; y < valArraySquare.length && !found; y++) {
                if (valArraySquare[y] == valArrayOriginal.get(x)) {
                    found = true;
                    foundValues.add(valArrayOriginal.get(x));
                }
            }
        }

        while (foundValues.size() > 0) {
            square = removeFromDomain(square, foundValues.get(0));
            foundValues.remove(0);
            revised = true;
        }

        if(square.getDomain().size() == 1) {
            square.setValue(square.getDomain().get(0));
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
