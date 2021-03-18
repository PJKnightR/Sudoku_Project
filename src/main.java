import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) {
        try {
            Sudoku board = new Sudoku("SudokuMaker.csv", true);

            board.printSudokuBoard();
            //board.getBoard()[0][0].getRow();
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
