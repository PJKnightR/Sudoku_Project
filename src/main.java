import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        /*
        System.out.println("Please enter the filename you would like to test:");
        Scanner scan = new Scanner(System.in);
        String in = scan.nextLine();
         */

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

        //create sudoku board
        //create solver

        //solve most constrained
        //solve simple backtrack

        try {/*
            Sudoku board2 = new Sudoku("test1.csv", true);
            board2.printSudokuBoard();

            ForwardCheckingSolver fw;
            Sudoku fin;

            fw = new ForwardCheckingSolver(board2);
            fin = fw.solve(board2);

            fin.printSudokuBoard();
            */


            Sudoku board1 = new Sudoku("test1.csv", true);
            Sudoku board2 = new Sudoku("test2.csv", true);
            Sudoku board3 = new Sudoku("test3.csv", true);
            Sudoku board4 = new Sudoku("test4.csv", true);

            long start;
            long end;

            ForwardCheckingSolver fw;
            Sudoku fin;

            start = System.nanoTime();
            fw = new ForwardCheckingSolver(board1);
            fin = fw.solve(board1);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start) + " nanoseconds");

            start = System.nanoTime();
            fw = new ForwardCheckingSolver(board2);
            fin = fw.solve(board2);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start) + " nanoseconds");

            start = System.nanoTime();
            fw = new ForwardCheckingSolver(board3);
            fin = fw.solve(board3);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start) + " nanoseconds");

            start = System.nanoTime();
            fw= new ForwardCheckingSolver(board4);
            fin = fw.solve(board4);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start) + " nanoseconds");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /*advanced dfs
        try {
            Sudoku board = new Sudoku("SudokuMaker.csv", true);
            AdvancedDFS dfs = new AdvancedDFS(board);
            Sudoku fin = dfs.solve(board);
            fin.printSudokuBoard();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }*/

    }

}
