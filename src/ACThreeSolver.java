import java.util.*;

public class ACThreeSolver {
    public Sudoku curSudokuBoard, tempSudokuBoard;
    public Sudoku.Square tempSquare;

    public ACThreeSolver(Sudoku board) {
        this.curSudokuBoard = board;
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

    public boolean AC3Guesser(Sudoku board, int curRow, int curColumn) {
        if (!ACThree(board, board.getBoard()[curRow][curColumn])) {
            return false;
        }
        Sudoku newBoard = new Sudoku(tempSudokuBoard);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if(newBoard.getBoard()[r][c].getValue() == 0) {

                    for (int g: newBoard.getBoard()[r][c].getDomain()) {
                        //temp board to try the change
                        Sudoku temp = new Sudoku(newBoard);
                        ArrayList<Integer> oldDomain = temp.getBoard()[r][c].getDomain();
                        temp.getBoard()[r][c].setValue(g);

                        //if forward checking is valid
                        if (ACThree(temp, temp.getBoard()[r][c])){
                            temp = new Sudoku(tempSudokuBoard);

                            //move on
                            if(c < 8) {
                                c++;
                            } else {
                                c = 0;
                                r++;
                            }

                            if(r > 8) {
                                return true;
                            }

                            //if we found a solution
                            if(AC3Guesser(temp, r, c)){
                                return true;
                            } else {
                                temp.getBoard()[r][c].setValue(0);
                                temp.getBoard()[r][c].setDomain(oldDomain);
                            }
                        }
                        //if forward checking is invalid try the next value in the domain
                    }
                }
            }
        }

        /*Sudoku b = new Sudoku(board);

        for (int guess = 1; guess < 10; guess++) {
            b.getBoard()[curRow][curColumn].setValue(guess);
            //ACThree(b, b.getBoard()[curRow][curColumn]);

            if(ACThree(b, b.getBoard()[curRow][curColumn])) {
                board.getBoard()[curRow][curColumn].setValue(guess);
                return true;
            }

            board.getBoard()[curRow][curColumn].setValue(0);
        }*/


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
