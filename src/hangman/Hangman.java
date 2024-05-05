package hangman;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import common.Game;
import util.WordGenerator;


public class Hangman extends Game {
    private String wordToGuess;
    private StringBuilder currentGuess;
    private int attemptsLeft;
    private int numberOfGuesses;
    private Set<Character> guessedLetters;
    private static final String FILENAME = "hangman_results.txt";

    public Hangman(String playerName) {
        super("Hangman");
        this.playerName = playerName;
        setupGame();
    }

    public void setupGame() {
        outputHandler.clearScreen();
        wordToGuess = WordGenerator.getNewWord();
        currentGuess = initializeCurrentGuess();
        attemptsLeft = 6;
        numberOfGuesses = 0;
        gameOver = false;
        guessedLetters = new HashSet<>();
        startTime = Instant.now();
    }

    private StringBuilder initializeCurrentGuess() {
        StringBuilder initialGuess = new StringBuilder();
        for (int i = 0; i < wordToGuess.length(); i++) {
            initialGuess.append("_");
        }
        return initialGuess;
    }

    @Override
    public void play() {
        while (!isGameOver()) {
            displayTitle();
            displayOutput();
            outputHandler.displayMessage("Enter your guess (a - z): ");
            String guess = inputHandler.getUserInput();

            if (guess.equalsIgnoreCase("exit")) {
                exit();
                return;
            }

            if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                outputHandler.clearScreen();
                outputHandler.displayMessage("Please enter a single letter.");
                continue;
            }

            else if (guessedLetters.contains(guess.charAt(0))) {
                outputHandler.clearScreen();
                outputHandler.displayMessage("You have already guessed that letter. Try a different one!");
                continue;
            }
            
            outputHandler.clearScreen();
            handleInput(guess);
        }
        endGame();
    }

    @Override
    public void handleInput(String input) {
        char guessedLetter = input.toLowerCase().charAt(0);
        if (!guessedLetters.contains(guessedLetter)) {
            guessedLetters.add(guessedLetter);
            numberOfGuesses++;
            boolean isCorrectGuess = false;

            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == guessedLetter && currentGuess.charAt(i) == '_') {
                    currentGuess.setCharAt(i, guessedLetter);
                    isCorrectGuess = true;
                }
            }

            if (!isCorrectGuess) {
                attemptsLeft--;
            }

            if (currentGuess.toString().equals(wordToGuess) || attemptsLeft <= 0) {
                gameOver = true;
            }
        } else {
            outputHandler.displayMessage("You have already guessed that letter. Try a different one!");
        }
    }

    @Override
    public void endGame() {
        displayTitle();
        displayHangmanFigure();
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);

        if (currentGuess.toString().equals(wordToGuess)) {
            outputHandler.displayMessage("Congratulations! You guessed the word correctly!");
        } else {
            outputHandler.displayMessage("Game Over! The word was: " + wordToGuess);
        }

        recordResult(playerName, currentGuess.toString().equals(wordToGuess), numberOfGuesses, duration);
        outputHandler.displayMessage("Take some time to " + (currentGuess.toString().equals(wordToGuess) ? "enjoy your victory" : "lament over your loss") + " and then we'll head back to the Main Menu.");
        outputHandler.displayMessage("");
        outputHandler.displayMessage("Press any key to head back to the Main Menu...");
        inputHandler.getUserInput();
    }

    @Override
    public void recordResult(String name, boolean won, int numberOfGuesses, Duration duration) {
        String result = String.format("%s %s %s %d seconds and made %d guesses.",
                                    playerName, won ? "guessed the word correctly!" : "failed to guess the word!", won ? "They got it in" : "They took",
                                    duration.getSeconds(), numberOfGuesses);
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME, true))) {
            out.println(result);
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    @Override
    public void displayOutput() {
        outputHandler.displayMessage("Current word to guess: " + currentGuess.toString());
        outputHandler.displayMessage("Attempts remaining: " + attemptsLeft);
        outputHandler.displayMessage("Guessed letters: " + guessedLetters.toString());
        displayHangmanFigure();
    }
    
    private void displayHangmanFigure() {
        String[] hangmanStates = new String[] {
            // State for 0 attempts left (complete figure)
            "    +---+\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|\\  |\n" +
            "   / \\  |\n" +
            "       ===",
    
            // State for 1 attempt left
            "    +---+\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|\\  |\n" +
            "   /    |\n" +
            "       ===",
    
            // State for 2 attempts left
            "    +---+\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|\\  |\n" +
            "        |\n" +
            "       ===",
    
            // State for 3 attempts left
            "    +---+\n" +
            "    |   |\n" +
            "    O   |\n" +
            "   /|   |\n" +
            "        |\n" +
            "       ===",
    
            // State for 4 attempts left
            "    +---+\n" +
            "    |   |\n" +
            "    O   |\n" +
            "    |   |\n" +
            "        |\n" +
            "       ===",
    
            // State for 5 attempts left
            "    +---+\n" +
            "    |   |\n" +
            "    O   |\n" +
            "        |\n" +
            "        |\n" +
            "       ===",
    
            // State for 6 attempts left (empty gallows)
            "    +---+\n" +
            "    |   |\n" +
            "        |\n" +
            "        |\n" +
            "        |\n" +
            "       ==="
        };
    
        System.out.println(hangmanStates[attemptsLeft]);
    }

    @Override
    public void displayTitle() {
        outputHandler.displayMessage("");

        outputHandler.displayMessage("    ██░ ██  ▄▄▄       ███▄    █   ▄████  ███▄ ▄███▓ ▄▄▄       ███▄    █ \r\n" + //
                        "   ▓██░ ██▒▒████▄     ██ ▀█   █  ██▒ ▀█▒▓██▒▀█▀ ██▒▒████▄     ██ ▀█   █ \r\n" + //
                        "   ▒██▀▀██░▒██  ▀█▄  ▓██  ▀█ ██▒▒██░▄▄▄░▓██    ▓██░▒██  ▀█▄  ▓██  ▀█ ██▒\r\n" + //
                        "   ░▓█ ░██ ░██▄▄▄▄██ ▓██▒  ████▒░▓█  ██▓▒██    ▒██ ░██▄▄▄▄██ ▓██▒  ████▒\r\n" + //
                        "   ░▓█▒░██▓ ▓█   ▓██▒▒██░   ▓██░░▒▓███▀▒▒██▒   ░██▒ ▓█   ▓██▒▒██░   ▓██░\r\n" + //
                        "    ▒ ░░▒░▒ ▒▒   ▓▒█░░ ▒░   ▒ ▒  ░▒   ▒ ░ ▒░   ░  ░ ▒▒   ▓▒█░░ ▒░   ▒ ▒ \r\n" + //
                        "    ▒ ░▒░ ░  ▒   ▒▒ ░░ ░░   ░ ▒░  ░   ░ ░  ░      ░  ▒   ▒▒ ░░ ░░   ░ ▒░\r\n" + //
                        "    ░  ░░ ░  ░   ▒      ░   ░ ░ ░ ░   ░ ░      ░     ░   ▒      ░   ░ ░ \r\n" + //
                        "    ░  ░  ░      ░  ░         ░       ░        ░         ░  ░         ░ \r");
    }
}
