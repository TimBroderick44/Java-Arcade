package tictactoe;

import common.Game;
import common.BoardGame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Random;
import java.util.stream.IntStream;
import java.time.Instant;

public class TicTacToe extends Game implements BoardGame {
    private char[][] board;
    private Random random;
    private int moveCount;
    private boolean tie;
    private String result;
    private static final String FILENAME = "tictactoe.txt";

    public TicTacToe(String playerName) {
        super("Tic-Tac-Toe");
        this.playerName = playerName;
        setupGame();
    }

    @Override
    public void setupGame() {
        outputHandler.clearScreen();
        this.board = new char[3][3];
        this.gameOver = false;
        this.random = new Random();
        setupBoard(3, 3, 0);
    }

    @Override
    public void setupBoard(int rows, int columns, int extras) {
        board = new char[rows][columns];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\u0000';
            }
        }
        startTime = Instant.now();
        moveCount = 0;
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public int getColumns() {
        return 3;
    }

    @Override
    public String getCell(int row, int col) {
        return String.valueOf(board[row][col]);
    }

    public void play() {
        while (!isGameOver()) {
            displayOutput();
            outputHandler.displayMessage(playerName + ", enter your move (e.g. A1, B2, C3):");

            String input = inputHandler.getUserInput();

            if (input.equalsIgnoreCase("exit")) {
                exit();
                return;
            }

            while (!isValidMove(input)) {
                outputHandler.clearScreen();
                outputHandler.displayMessage("Invalid input. Please enter moves like 'A1', 'B2', etc.");
                displayOutput();
                outputHandler.displayMessage(playerName + ", enter your move (e.g. A1, B2, C3):");
                input = inputHandler.getUserInput();
                continue;
            }

            outputHandler.clearScreen();

            handleInput(input);
            displayOutput();
            checkGameState();

            computerMove();
            outputHandler.clearScreen();
            checkGameState();
        }
        displayTitle();
        displayOutput();
        if ((checkForWinner('X') == false) && tie) {
            outputHandler.displayMessage("You tied with a poorly programmed AI! How?!");
        } else {
            outputHandler.displayMessage(
                    checkForWinner('X') ? "Congratulations! You crushed the AI! What a win for humanity!"
                            : "You lose! AI reigns supreme!!");
        }
        endGame();
    }

    public boolean isValidMove(String input) {
        try {
            char colChar = Character.toUpperCase(input.charAt(0));
            int row = Integer.parseInt(input.substring(1)) - 1;
            int col = colChar - 'A';
            // Check an actual cell (i.e. within the boundaries of the board) and the cell
            // is empty
            return (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '\u0000');
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter moves like 'A1', 'B2', etc.");
            return false;
        }
    }

    private void checkGameState() {
        if (checkForWinner('X')) {
            gameOver = true;
        } else if (checkForWinner('O')) {
            gameOver = true;
        } else if (isBoardFull()) {
            tie = true;
            gameOver = true;
        }
    }

    private boolean checkForWinner(char player) {
        // If any row, columnn or diagonal has the same character, somebody has won
        return IntStream.range(0, 3).anyMatch(i ->
        // First two conditions check rows and columns, last two check diagonals
        (board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) ||
                ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                        (board[0][2] == player && board[1][1] == player && board[2][0] == player));
    }

    // Need to check if a tie
    // A tie would equal a full board
    private boolean isBoardFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == '\u0000') {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void endGame() {
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        boolean won = checkForWinner('X');
        recordResult(playerName, won, moveCount, duration);
        if (tie) {
            outputHandler.displayMessage(
                    "Take this time to come up with a better strategy for next time! Then, we'll head back to the Main Menu.");
        } else {
            outputHandler.displayMessage(
                    won ? "Take some time to enjoy your victory and then we'll head back to the Main Menu."
                            : "Take some time to lament over your loss and then we'll head back to the Main Menu.");
        }
        outputHandler.displayMessage("");
        outputHandler.displayMessage("Press any key to head back to the Main Menu...");
        inputHandler.getUserInput();
    }

    @Override
    public void handleInput(String input) {
        try {
            // Convert the input to a row and column
            // The first character is the column (A, B, C)
            // The second character is the row (1, 2, 3)
            char colChar = Character.toUpperCase(input.charAt(0));
            // Subtract 'A' to get the column index
            // Subtract 1 from the row to get the row index
            int col = colChar - 'A';
            int row = Integer.parseInt(input.substring(1)) - 1;

            // Same as above
            // Check if a cell is empty and within the boundaries of the board
            // If ok, place an 'x' and increment the move count
            if (row >= 0 && row < getRows() && col >= 0 && col < getColumns() && board[row][col] == '\u0000') {
                board[row][col] = 'X';
                moveCount++;
            } else {
                // otherwise, must be an invalid move
                System.out.println("Invalid move. The spot is already taken or out of bounds. Please try again.");
            }
        } catch (Exception e) {
            // For any other exception, print this
            System.out.println("Invalid input. Please enter 'A1', 'B2', etc.");
        }
    }

    private void computerMove() {
        if (gameOver) {
            return;
        }
        // If not empty and within the boundaries of the board, place an 'O' randomly
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '\u0000');

        outputHandler.displayMessage("Computer is thinking...");
        for (int i = 0; i < 3; i++) {
            pauseForDramaticEffect(1);
            System.out.print(".");
        }

        board[row][col] = 'O';
        System.out.println("Computer placed 'O' at " + (char) ('A' + col) + (row + 1));
    }

    @Override
    public void displayOutput() {
        displayTitle();
        outputHandler.displayBoard(this);
        outputHandler.displayMessage("");
    }

    @Override
    public void recordResult(String name, boolean won, int score, Duration duration) {
        if (tie) {
            result = String.format("%s tied with the AI! It took %d moves and %d seconds for a tie! How?!",
                    playerName, score, duration.getSeconds());
        } else {
            result = String.format("%s %s %d moves! It only took %d seconds %s",
                    playerName,
                    won ? "is a legend and crushed the AI! They won in"
                            : "lost to an AI that just randomly assigns 'O's. They lost in",
                    score, duration.getSeconds(), won ? "for them to win!" : "for them to be embarrassed by the AI!");
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME, true))) {
            out.println(result);
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    @Override
    public void displayTitle() {
        outputHandler.displayMessage("       (                                            )       \r\n" + //
                "  *   ))\\ )  (      *   )   (       (      *   ) ( /(       \r\n" + //
                "` )  /(()/(  )\\   ` )  /(   )\\      )\\   ` )  /( )\\()) (    \r\n" + //
                " ( )(_))(_)|((_)   ( )(_)|(((_)(  (((_)   ( )(_)|(_)\\  )\\   \r\n" + //
                "(_(_()|_)) )\\___  (_(_()) )\\ _ )\\ )\\___  (_(_())  ((_)((_)  \r\n" + //
                "|_   _|_ _((/ __| |_   _| (_)_\\(_|(/ __| |_   _| / _ \\| __| \r\n" + //
                "  | |  | | | (__    | |    / _ \\  | (__    | |  | (_) | _|  \r\n" + //
                "  |_| |___| \\___|   |_|   /_/ \\_\\  \\___|   |_|   \\___/|___| \r\n");
    }
}
