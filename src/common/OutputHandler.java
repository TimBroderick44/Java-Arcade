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
    
        // Print column labels on top (1, 2, 3, ...)
        System.out.print("   "); 
        for (int j = 0; j < columns; j++) {
            System.out.printf("%3d ", j + 1);
        }
        System.out.println();
    
        // Print top border
        System.out.print("   +");
        for (int j = 0; j < columns; j++) {
            System.out.print("---+");
        }
        System.out.println();
    
        // Print row labels on the left (A, B, C, ...)
        for (int i = 0; i < rows; i++) {
            System.out.printf("%2c |", 'A' + i);
            for (int j = 0; j < columns; j++) {
                String cell = game.getCell(i, j);
                // Check if cell is flagged
                boolean isFlagged = game.isFlagged(i, j);
                if (isFlagged) {
                    // Print F for flagged cell
                    System.out.print(" F |"); 
                } else {
                    // Print cell content
                    System.out.print(" " + cell + " |"); 
                }
            }
            System.out.println();
    
            // Print row separator
            System.out.print("   +");
            // Print separator between cells
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
        System.out.print("  "); 
        for (int i = 0; i < rows; i++) {
            System.out.print(" " + (i + 1) + "  "); 
        }
        System.out.println();
        System.out.println("   ------------"); 
    
        // Print board
        for (int j = 0; j < columns; j++) {
            // Print column label on the left (A, B, C, ...)
            System.out.print((char) ('A' + j) + " |"); 
            for (int i = 0; i < rows; i++) {
                // Print cell content (X, O, or empty)
                char cell = ticTacToe.getCell(i, j).charAt(0); 
                System.out.print((cell == '\u0000' ? "   |" : " " + cell + " |"));
            }
            System.out.println();
            System.out.println("   ------------");
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