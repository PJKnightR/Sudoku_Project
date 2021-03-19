# Sudoku Solver
### By Adam Cramer and Patrick Reagan

This project is intended to compare different methods of solving a sudoku board.
Three methods have been coded into this project, these being AC-3 with simple backtracking, Forward Checking, and an advanced Depth First Search which checks that each guess is valid instead of guessing randomly

Depth First Search has been commented out of the main method as it runs for an exceptionally long amount of time
## Usage
Adding a board to test can be done by placing a .csv into the boards file.
This csv should contain the sudoku board with 0's replacing any blank spaces.
Please reference any of the example boards for a more detailed syntax.

Running the program via the main method will prompt the user for a file name to run, the program will then output the timing of AC-3 and forward checking