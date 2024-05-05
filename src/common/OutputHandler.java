package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import minesweeper.Minesweeper;
import tictactoe.TicTacToe;

public class OutputHandler {

    public void displayBoard(BoardGame game) {
        if (game instanceof Minesweeper) {
            displayMinesweeperBoard((Minesweeper) game);
        } else if (game instanceof TicTacToe) {
            displayTicTacToeBoard((TicTacToe) game);
        }
    }

    public void displayMinesweeperBoard(Minesweeper game) {
        int rows = game.getRows();
        int columns = game.getColumns();
        game.displayTitle();
    
        // Print column headers
        System.out.print("   "); // Spacing for row labels
        for (int j = 0; j < columns; j++) {
            System.out.printf("%3d ", j + 1);
        }
        System.out.println();
    
        // Print top border of the grid
        System.out.print("   +");
        for (int j = 0; j < columns; j++) {
            System.out.print("---+");
        }
        System.out.println();
    
        for (int i = 0; i < rows; i++) {
            // Print row label
            System.out.printf("%2c |", 'A' + i);
            for (int j = 0; j < columns; j++) {
                String cell = game.getCell(i, j);
                boolean isFlagged = game.isFlagged(i, j); // Assuming the Minesweeper class has an isFlagged method
                if (isFlagged) {
                    System.out.print(" F |"); // Display 'F' for flagged cells
                } else {
                    System.out.print(" " + cell + " |"); // Display cell content
                }
            }
            System.out.println();
    
            // Print row divider
            System.out.print("   +");
            for (int j = 0; j < columns; j++) {
                System.out.print("---+");
            }
            System.out.println();
        }
    }

    private void displayTicTacToeBoard(TicTacToe ticTacToe) {
        int rows = ticTacToe.getRows();
        int columns = ticTacToe.getColumns();
    
        // Print row labels on top
        System.out.print("  "); // Leading spaces for alignment
        for (int i = 0; i < rows; i++) {
            System.out.print(" " + (i + 1) + "  "); // Print row numbers 1, 2, 3 across the X axis
        }
        System.out.println();
        System.out.println("   ------------"); // Adjust the line length as needed
    
        for (int j = 0; j < columns; j++) {
            System.out.print((char) ('A' + j) + " |"); // Print column labels A, B, C down the Y axis
            for (int i = 0; i < rows; i++) {
                char cell = ticTacToe.getCell(i, j).charAt(0); // Use getCell(row, column)
                System.out.print((cell == '\u0000' ? "   |" : " " + cell + " |"));
            }
            System.out.println();
            System.out.println("   ------------"); // Adjust the line length as needed
        }
    }
    
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayHighScores() {
        displayFileContents("minesweeper.txt");
        displayFileContents("tictactoe.txt");
        displayFileContents("hangman_results.txt");
        displayFileContents("trivia_results.txt");
    }

    public void displayFileContents(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            displayMessage("");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        displayMessage("");
        displayMessage("");
    }
}