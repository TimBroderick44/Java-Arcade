import common.InputHandler;
import common.OutputHandler;
import minesweeper.Minesweeper;
import tictactoe.TicTacToe;
import hangman.Hangman;
import trivia.TriviaGame;

public class GameCollection {
    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        OutputHandler outputHandler = new OutputHandler();

        displayTitle();

        outputHandler.displayMessage("Please enter your name:");
        String playerName = inputHandler.getUserInput();

        outputHandler.clearScreen();

        while (true) {
            outputHandler.clearScreen();
            displayTitle();
            outputHandler.displayMessage(playerName + ", do you want to play a game?");
            outputHandler.displayMessage("");
            outputHandler.displayMessage("1. Minesweeper");
            outputHandler.displayMessage("2. Tic-Tac-Toe");
            outputHandler.displayMessage("3. Hangman");
            outputHandler.displayMessage("4. True or False? (A Game of Trivia)");
            outputHandler.displayMessage("");
            outputHandler.displayMessage("5. Show me some High Scores!");
            outputHandler.displayMessage("");
            outputHandler.displayMessage("Enter your choice (1-5), or 'exit' to quit:");

            String choice = inputHandler.getUserInput();

            switch (choice.toLowerCase()) {
                case "1":
                    Minesweeper minesweeper = new Minesweeper(playerName); 
                    minesweeper.play();
                    break;
                case "2":
                    TicTacToe ticTacToe = new TicTacToe(playerName);
                    ticTacToe.play();
                    break;
                case "3":
                    Hangman hangman = new Hangman(playerName);
                    hangman.play();
                    break;
                case "4":
                    TriviaGame triviaGame = new TriviaGame(playerName);
                    triviaGame.play();
                    break;
                case "5":
                    outputHandler.clearScreen();
                    outputHandler.displayHighScores();
                    outputHandler.displayMessage("==========================================================================");
                    outputHandler.displayMessage("Press any key to continue...");
                    inputHandler.getUserInput();
                    break;
                case "exit":
                    outputHandler.displayMessage("Exiting the Java Arcade. Thank you for playing!");
                    return;
                default:
                    outputHandler.displayMessage("Invalid choice. Please try again.\n");
                    break;
            }
        }
    }

    public static void displayTitle() {
        OutputHandler outputHandler = new OutputHandler();
        outputHandler.displayMessage("\n\n\n████████╗██╗  ██╗███████╗         ██╗ █████╗ ██╗   ██╗ █████╗      █████╗ ██████╗  ██████╗ █████╗ ██████╗ ███████╗\r\n" + //
                        "╚══██╔══╝██║  ██║██╔════╝         ██║██╔══██╗██║   ██║██╔══██╗    ██╔══██╗██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝\r\n" + //
                        "   ██║   ███████║█████╗           ██║███████║██║   ██║███████║    ███████║██████╔╝██║     ███████║██║  ██║█████╗  \r\n" + //
                        "   ██║   ██╔══██║██╔══╝      ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║    ██╔══██║██╔══██╗██║     ██╔══██║██║  ██║██╔══╝  \r\n" + //
                        "   ██║   ██║  ██║███████╗    ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║    ██║  ██║██║  ██║╚██████╗██║  ██║██████╔╝███████╗\r\n" + //
                        "   ╚═╝   ╚═╝  ╚═╝╚══════╝     ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝ ╚══════╝\r\n");
    }
}