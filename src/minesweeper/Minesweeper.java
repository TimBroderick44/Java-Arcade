package minesweeper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

import common.Game;
import common.BoardGame;

public class Minesweeper extends Game implements BoardGame {
    private int[][] board;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int rows;
    private int columns;
    private static final String FILENAME = "minesweeper.txt";

    public Minesweeper(String playerName) {
        super("MineSweeper");
        this.playerName = playerName;
        setupGame();
    }

    @Override
    public void setupGame() {
        outputHandler.clearScreen();
        this.rows = promptForBoardSize();
        this.columns = this.rows;
        this.board = new int[this.rows][this.columns];
        this.mines = new boolean[this.rows][this.columns];
        this.revealed = new boolean[this.rows][this.columns];
        this.flagged = new boolean[this.rows][this.columns];
        int minesCount = promptForMineCount(this.rows);
        outputHandler.clearScreen();
        setupBoard(this.rows, this.columns, minesCount);
        setupMines(this.rows, this.columns, minesCount);
        startTime = Instant.now();
    }

    private int promptForBoardSize() {
        // init size so that the while loop runs at least once
        int size = -1;
        while (size < 1 || size > 25) {
            outputHandler.displayMessage("Enter board size (1-25): ");
            try {
                size = Integer.parseInt(inputHandler.getUserInput());
                if (size < 1 || size > 25) {
                    outputHandler.displayMessage("Invalid size. Please enter a number between 1 and 25.");
                }
            } catch (NumberFormatException e) {
                outputHandler.displayMessage("Invalid input. Please enter a number.");
            }
        }
        return size;
    }

    private int promptForMineCount(int size) {
        // init mines so that the while loop runs at least once
        int mines = -1;
        // can't all be mines
        int maxMines = size * size - 1;
        if (size == 1) {
            return mines = 0;
        } else {
            while (mines < 1 || mines > maxMines) {
                outputHandler.displayMessage("Enter number of mines (1-" + (maxMines) + "): ");
                try {
                    mines = Integer.parseInt(inputHandler.getUserInput());
                    if (mines < 1 || mines > maxMines) {
                        outputHandler
                                .displayMessage("Invalid number of mines. Must be positive and less than " + maxMines);
                    }
                } catch (NumberFormatException e) {
                    outputHandler.displayMessage("Invalid input. Please enter a number.");
                }
            }
            return mines;
        }
    }

    @Override
    public void setupBoard(int rows, int columns, int mines) {
        // Silly dramatic effect
        // Curious about 'Thread.sleep()'
        outputHandler.displayMessage("Creating a board now.....  It will be " + rows + " x " + columns + " with " + mines
                + " mines.");
        pauseForDramaticEffect(2);
        outputHandler.displayMessage("Wait.... Before we start, " + playerName + "...");
        pauseForDramaticEffect(2);
        outputHandler.displayMessage("I don't think you understand what you're getting into...");
        pauseForDramaticEffect(2);
        outputHandler.displayMessage("If you lose, something really, really bad is going to happen... ");
        pauseForDramaticEffect(2);
        outputHandler.displayMessage("You've been warned....");
        pauseForDramaticEffect(2);
        this.rows = rows;
        this.columns = columns;
        board = new int[rows][columns];
        revealed = new boolean[rows][columns];
        outputHandler.clearScreen();
    }

    private void setupMines(int rows, int columns, int mineCount) {
        // Initialize mines
        mines = new boolean[rows][columns];
        // Randomly place mines
        Random rand = new Random();
        for (int i = 0; i < mineCount; i++) {
            // r = row, c = column
            int r, c;
            do {
                r = rand.nextInt(rows);
                c = rand.nextInt(columns);
            } while (mines[r][c]);
            mines[r][c] = true;
        }
        // Calculate the number of adjacent mines for each cell
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                board[r][c] = countAdjacentMines(r, c);
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        // -1 will be used to indicate that the cell is a mine
        if (mines[row][col])
            return -1;

        int count = 0;
        // Check all 8 adjacent cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;
                int r = row + i, c = col + j;
                // Check if the cell is within the bounds of the board and is a mine
                if (r >= 0 && r < rows && c >= 0 && c < columns && mines[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Recursive function to reveal cells
    public void revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= columns) {
            return;
        }
        // If the cell has already been revealed or is a mine, return
        if (revealed[row][col] || mines[row][col]) {
            return;
        }

        // Reveal the cell
        revealed[row][col] = true;

        // If the cell is not adjacent to any mines, reveal all adjacent cells
        if (board[row][col] == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0)
                        continue;
                    // Recursively reveal adjacent cells 
                    revealCell(row + i, col + j);
                }
            }
        }
    }

    private void showAllMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                revealed[i][j] = true;
            }
        }
    }

    @Override
    public void play() {
        while (!isGameOver()) {
            outputHandler.displayBoard(this);
            outputHandler.displayMessage("");
            outputHandler.displayMessage("Enter your move (e.g. A1, B6, etc.)");
            outputHandler.displayMessage("Use 'F' to flag a cell OR 'U' to remove a flag from a cell (e.g. FA1, FB6 OR UA1, UB6 etc.):");
            
            String input = inputHandler.getUserInput();

            if (input.equalsIgnoreCase("exit")) {
                exit();
                return;
            }

            while (!isValidMove(input)) {
                outputHandler.clearScreen();
                outputHandler.displayMessage("Invalid input. Please enter a valid cell reference, e.g., A1.");
                outputHandler.displayMessage("Use 'F' to flag a cell OR 'U' to remove a flag from a cell (e.g. FA1, FB6 OR UA1, UB6 etc.):");
                outputHandler.displayBoard(this);
                outputHandler.displayMessage("");
                outputHandler.displayMessage("Enter your move (e.g. A1, B6, etc.)");
                outputHandler.displayMessage("Use 'F' to flag a cell OR 'U' to remove a flag from a cell (e.g. FA1, FB6 OR UA1, UB6 etc.):");
                input = inputHandler.getUserInput();
                continue;
            }

            outputHandler.clearScreen();
            handleInput(input);
        }
        endGame();
    }

    private boolean isValidMove(String input) {
        if (input.length() < 2) {
            return false;
        }

        boolean isFlagging = input.toUpperCase().startsWith("F");
        boolean isUnflagging = input.toUpperCase().startsWith("U");

        if (isFlagging || isUnflagging) {
            input = input.substring(1); 
        }

        int row = input.toUpperCase().charAt(0) - 'A';
        int col = Integer.parseInt(input.substring(1)) - 1;

        if (row < 0 || row >= rows || col < 0 || col >= columns) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isGameOver() {
        if (gameOver) {
            displayOutput();
            outputHandler.displayMessage("");
            outputHandler.displayMessage("BOOOOOOMMM!!! You hit a mine. Game over!");
            outputHandler.displayMessage("");
            outputHandler.displayMessage("     _.-^^---....,,--       \r\n" + //
                                " _--                  --_  \r\n" + //
                                "<                        >)\r\n" + //
                                "|                         | \r\n" + //
                                " \\._                   _./  \r\n" + //
                                "    ```--. . , ; .--'''       \r\n" + //
                                "          | |   |             \r\n" + //
                                "       .-=||  | |=-.   \r\n" + //
                                "       `-=#$%&%$#=-'   \r\n" + //
                                "          | ;  :|     \r\n" + //
                                " _____.,-#%&$@%#&#~,._____");
            outputHandler.displayMessage("");
            return true;
        }

        // Check if all 'safe' cells have been revealed
        boolean allSafeRevealed = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!mines[i][j] && !revealed[i][j]) {
                    allSafeRevealed = false;
                    break;
                }
            }
            if (!allSafeRevealed)
                break;
        }

        // If all safe cells have been revealed, the player has won
        if (allSafeRevealed) {
            displayOutput();
            outputHandler.displayMessage("");
            outputHandler.displayMessage("Congratulations! You've cleared all the mines!");
            outputHandler.displayMessage("");
            won = true;
            gameOver = true;
            return true;
        }

        return false;
    }

    @Override
    public void handleInput(String input) {
        boolean isFlagging = input.toUpperCase().startsWith("F");
        boolean isUnflagging = input.toUpperCase().startsWith("U");

        if (isFlagging || isUnflagging) {
            input = input.substring(1); // Remove flag/unflag prefix
        }

        int row = input.toUpperCase().charAt(0) - 'A';
        int col = Integer.parseInt(input.substring(1)) - 1;

        if (isFlagging) {
            flagged[row][col] = true;
        } else if (isUnflagging) {
            flagged[row][col] = false;
        } else {
            if (mines[row][col]) {
                won = false;
                gameOver = true;
                showAllMines();
            } else if (!flagged[row][col]) {
                revealCell(row, col);
            } else {
                outputHandler.displayMessage("Cell is flagged. Unflag to reveal.");
            }
        }
    }

    @Override
    public void endGame() {
        gameOver = true;
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        if (won) {
            recordResult(playerName, true, rows, duration);
        } else {
            recordResult(playerName, false, rows, duration);
        }
        if (rows >= 20 && won && duration.getSeconds() > 300){
            outputHandler.displayMessage("You've won! But... You were playing for " + duration + " seconds! Go outside and get some fresh air!");
        }
        else if (rows >= 20 && won && duration.getSeconds() < 300){
            outputHandler.displayMessage("You've won! You are a Mineseeper God! Maybe have a crack at going outside?");
        }
        else if (rows >= 20 && won == false) {
            outputHandler.displayMessage("You've lost!... Maybe try playing on a board smaller than " + rows + "x" + rows + " next time.");
        }
        else if (rows >= 5 && rows < 20 && won) {
            outputHandler.displayMessage("You've won! Good effort. Maybe try playing on a larger board next time with more than just a few mines?");
        }
        else if (rows >= 5 && rows < 20 && won == false) {
            outputHandler.displayMessage("You've lost! Have another go and this time, try not to blow yourself up.");
        }
        else if (rows < 5 && won ){
            outputHandler.displayMessage("You've won! But really you've also lost... It wasn't exactly that large a board now, was it?");
        }
        else if (rows < 5 && won == false) {
            outputHandler.displayMessage("I'm not sure how this happened, but you've lost on a board " + rows + "x" + rows +"! Maybe try playing on a board larger next time.");
        }
        outputHandler.displayMessage("Take some time to " + (won ? "celebrate your win!" : "reflect on your loss. Then, I'll take you back to the Main Menu."));
        outputHandler.displayMessage("");
        outputHandler.displayMessage("Press any key to head back to the Main Menu...");
        inputHandler.getUserInput();
    }

    @Override
    public void displayOutput() {
        outputHandler.displayBoard(this);
    }

    private int countMines() {
        int mineCount = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (mines[i][j]) mineCount++;
            }
        }
        return mineCount;
    }

    @Override
    public void recordResult(String name, boolean won, int score, Duration duration) {
        String result = String.format("%s %s! They were playing on a board %dx%d with %d mines. It took them %d seconds %s",
                                    playerName, won ? "found all the mines!" : "thought it would be a good idea to play with live mines... They didn't make it", score, score, countMines(), duration.getSeconds(), won ? "to find them all." : "to blow themselves up.");
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME, true))) {
            out.println(result);
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    public boolean isFlagged(int row, int col) {
        return flagged[row][col];
    }

    @Override
    public String getCell(int row, int col) {
        if (flagged[row][col]) {
            return "F"; 
        } else if (!revealed[row][col]) {
            return "_";  
        } else if (mines[row][col]) {
            return "*";  
        } else {
            return String.valueOf(board[row][col]); 
        }
    }

    @Override
    public void displayTitle() {
        outputHandler.displayMessage(" __       __  __                                                                                               \r\n" + //
                        "/  \\     /  |/  |                                                                                              \r\n" + //
                        "$$  \\   /$$ |$$/  _______    ______    _______  __   __   __   ______    ______    ______    ______    ______  \r\n" + //
                        "$$$  \\ /$$$ |/  |/       \\  /      \\  /       |/  | /  | /  | /      \\  /      \\  /      \\  /      \\  /      \\ \r\n" + //
                        "$$$$  /$$$$ |$$ |$$$$$$$  |/$$$$$$  |/$$$$$$$/ $$ | $$ | $$ |/$$$$$$  |/$$$$$$  |/$$$$$$  |/$$$$$$  |/$$$$$$  |\r\n" + //
                        "$$ $$ $$/$$ |$$ |$$ |  $$ |$$    $$ |$$      \\ $$ | $$ | $$ |$$    $$ |$$    $$ |$$ |  $$ |$$    $$ |$$ |  $$/ \r\n" + //
                        "$$ |$$$/ $$ |$$ |$$ |  $$ |$$$$$$$$/  $$$$$$  |$$ \\_$$ \\_$$ |$$$$$$$$/ $$$$$$$$/ $$ |__$$ |$$$$$$$$/ $$ |      \r\n" + //
                        "$$ | $/  $$ |$$ |$$ |  $$ |$$       |/     $$/ $$   $$   $$/ $$       |$$       |$$    $$/ $$       |$$ |      \r\n" + //
                        "$$/      $$/ $$/ $$/   $$/  $$$$$$$/ $$$$$$$/   $$$$$/$$$$/   $$$$$$$/  $$$$$$$/ $$$$$$$/   $$$$$$$/ $$/       \r\n" + //
                        "                                                                                 $$ |                          \r\n" + //
                        "                                                                                 $$/                           \r\n");
    }
}
