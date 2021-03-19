import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) {
        try {
            Sudoku board = new Sudoku("SudokuMaker.csv", true);

            board.printSudokuBoard();

            Sudoku.Square[] squares = board.getBoard()[0][0].getRow();
            for(int i = 0; i < 8; i++) {
                System.out.print(" " + squares[i].getValue() + " ");
            }
            System.out.print("\n");

            squares = board.getBoard()[0][0].getColumn();
            for(int i = 0; i < 8; i++) {
                System.out.print(" " + squares[i].getValue() + " ");
            }
            System.out.print("\n");

            squares = board.getBoard()[0][0].getSurroundingInSquare();
            for(int i = 0; i < 8; i++) {
                if (squares[i] == null) {
                    System.out.print(" N ");
                } else {
                    System.out.print(" " + squares[i].getValue() + " ");
                }
            }
            System.out.print("\n");

            ACThreeSolver solver = new ACThreeSolver(board);
            solver.ACThree(board, board.getBoard()[0][0]);
            System.out.println(solver.performAC3(board, 0, 0));

            //solver.ACThree(board);
            //solver.tempSudokuBoard.getBoard()[0][3].printDomain();

        } catch (FileNotFoundException flarp) {
            System.out.println("Could not find indicated CSV file");
            System.out.println(flarp.getMessage());
        }

        //create sudoku board
        //create solver

        //solve most constrained
        //solve simple backtrack
    }
}
