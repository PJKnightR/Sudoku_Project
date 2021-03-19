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
            Sudoku board1AC3 = new Sudoku("test1.csv", true);
            Sudoku board2AC3 = new Sudoku("test2.csv", true);
            Sudoku board3AC3 = new Sudoku("test3.csv", true);
            Sudoku board4AC3 = new Sudoku("test4.csv", true);
            Sudoku board5AC3 = new Sudoku("test5.csv", true);
            Sudoku board6AC3 = new Sudoku("worldsHardest.csv", true);

            long start;
            long end;

            ACThreeSolver AC3Solver;
            Sudoku fin;

            start = System.nanoTime();
            AC3Solver = new ACThreeSolver(board1AC3);
            AC3Solver.ACThree(board1AC3, board1AC3.getBoard()[0][0]);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            AC3Solver = new ACThreeSolver(board2AC3);
            AC3Solver.AC3Guesser(board2AC3, 0, 0);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            AC3Solver = new ACThreeSolver(board3AC3);
            AC3Solver.AC3Guesser(board3AC3, 0, 0);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            AC3Solver = new ACThreeSolver(board4AC3);
            AC3Solver.AC3Guesser(board4AC3, 0, 0);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            AC3Solver = new ACThreeSolver(board5AC3);
            AC3Solver.ACThree(board5AC3, board5AC3.getBoard()[0][0]);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            AC3Solver = new ACThreeSolver(board6AC3);
            AC3Solver.ACThree(board6AC3, board6AC3.getBoard()[0][0]);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");


            System.out.println("\n");

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
            Sudoku board5 = new Sudoku("test5.csv", true);
            Sudoku board6 = new Sudoku("worldsHardest.csv", true);

            long start;
            long end;

            ForwardCheckingSolver fw;
            Sudoku fin;

            start = System.nanoTime();
            fw = new ForwardCheckingSolver(board1);
            fin = fw.solve(board1);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            fw = new ForwardCheckingSolver(board2);
            fin = fw.solve(board2);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            fw = new ForwardCheckingSolver(board3);
            fin = fw.solve(board3);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            fw= new ForwardCheckingSolver(board4);
            fin = fw.solve(board4);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            fw= new ForwardCheckingSolver(board5);
            fin = fw.solve(board5);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

            start = System.nanoTime();
            fw= new ForwardCheckingSolver(board6);
            fin = fw.solve(board6);
            end = System.nanoTime();
            System.out.println("Execution time is: " + (end - start)/1000000.0 + " miliseconds");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /*advanced dfs
        /*try {
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
