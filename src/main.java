import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) {
        try {
            Sudoku board = new Sudoku("SudokuMaker.csv", true);

            System.out.println("Initial Board:");
            board.printSudokuBoard();

            ACThreeSolver solver = new ACThreeSolver(board);
            solver.ACThree(board, board.getBoard()[0][0]);
            System.out.println("Solvable Puzzle by AC3: " + solver.performAC3(board, 0, 0));


            Sudoku boardHard = new Sudoku("SudokuMaker (Hard).csv", true);

            System.out.println("Hard Board \nInitial Board:");
            boardHard.printSudokuBoard();

            ACThreeSolver solverHard = new ACThreeSolver(boardHard);
            solverHard.ACThree(boardHard, boardHard.getBoard()[0][0]);
            System.out.println("Solvable Puzzle by AC3: " + solverHard.performAC3(board, 0, 0));

        } catch (FileNotFoundException flarp) {
            System.out.println("Could not find indicated CSV file");
            System.out.println(flarp.getMessage());
        }
    }
}
