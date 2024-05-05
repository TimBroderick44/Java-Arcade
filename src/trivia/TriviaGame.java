package trivia;

import common.Game;
import trivia.TriviaQuestions.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriviaGame extends Game {
    private List<Pair> triviaQuestions;
    private int currentQuestionIndex;
    private static final String FILENAME = "trivia_results.txt";

    public TriviaGame(String playerName) {
        super("Trivia Game");
        this.playerName = playerName;
        setupGame();
    }

    @Override
    public void play() {
        while (!isGameOver()) {
            displayTitle();
            displayOutput(); 
            String playerAnswer = inputHandler.getUserInput();

            if (playerAnswer.equalsIgnoreCase("exit")) {
                exit();
                return;
            }

            if (!isValidMove(playerAnswer)) {
                outputHandler.clearScreen();
                outputHandler.displayMessage("Invalid input. Please type 't' for true or 'f' for false.");
                continue;
            }

            outputHandler.clearScreen();
            handleInput(playerAnswer);

        }
        endGame();
    }

    public boolean isValidMove(String input) {
        String trimmedInput = input.trim().toLowerCase();
        return trimmedInput.equals("t") || trimmedInput.equals("f");
    }

    @Override
    public void endGame() {
        if (!gameOver)
            return;

        gameOver = true;
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        outputHandler.displayMessage("");
        outputHandler.displayMessage("Game Over! You got " + score + " correct!!");
        outputHandler.displayMessage("");
        outputHandler.displayMessage("Calculating results...");
        pauseForDramaticEffect(2);
        if(score == 0) {
            outputHandler.displayMessage("You didn't get any questions correct.... I'm embarassed for the both of us.");
        }
        else if (score > 1 && score < 5 ){
            outputHandler.displayMessage("You got a few questions correct... Pretty good... for a kid in primary school. AI burn!!");
        }
        else if (score >= 5 && score < 10) {
            outputHandler.displayMessage("You didn't do too bad! My guess is you have a social life and couldn't care less about trivia");
        }
        else if (score >= 10 && score < 20) {
            outputHandler.displayMessage("So close to joining the trivia elite.. Don't worry though you've got something they'lll never have... friends");
        }
        else if (score >= 20 && score < 30) {
            outputHandler.displayMessage("You know your stuff!... This is a good effort!");
        }
        else if (score >= 30 && score < 40) {
            outputHandler.displayMessage("Learn to use ChatGPT better...!");
        }
        else if (score >= 40 && score < 50) {
            outputHandler.displayMessage("So close!... How frustated are you right now? You were only " + (50 - score) + " away from getting all of the questions correct!");
        }
        else if (score == 50) {
            outputHandler.displayMessage("You got all of the questions correct! You are a God amongst insects! Now get outside and get some sun! ");
        }
        outputHandler.displayMessage("");
        // if score is the same as the number of questions, the player has 'won'.
        recordResult(playerName, score == triviaQuestions.size(), score, duration);
        outputHandler.displayMessage((score < 50 ? "Why not try again? You didn't embarrass yourself that much!" : "I wonder if you're as good at Minesweeper as Trivia?..."));
        outputHandler.displayMessage("Press any key to head back to the Main Menu...");
        inputHandler.getUserInput();
    }

    @Override
    public void handleInput(String input) {
        TriviaQuestions.Pair currentQuestion = triviaQuestions.get(currentQuestionIndex);
        if (input.equalsIgnoreCase(currentQuestion.answer)) {
            score++;
            outputHandler.displayMessage("Correct! Total correct answers: " + score);
            currentQuestionIndex++;
            if (currentQuestionIndex >= triviaQuestions.size()) {
                gameOver = true;
                outputHandler.displayMessage("You've answered all questions correctly!");
            }
        } else {
            gameOver = true;
            displayTitle();
            outputHandler.displayMessage("");
            outputHandler.displayMessage("Question: " + currentQuestion.question);
            outputHandler.displayMessage("Incorrect! The correct answer was: " + currentQuestion.answer);
        }
    }

    @Override
    public void displayOutput() {
        if (!gameOver) {
            Pair currentQuestion = triviaQuestions.get(currentQuestionIndex);
            outputHandler.displayMessage("");
            outputHandler.displayMessage("Question: " + currentQuestion.question);
            outputHandler.displayMessage("Type 't' for true or 'f' for false:");
            outputHandler.displayMessage("");
        }
    }

    @Override
    public void recordResult(String name, boolean won, int score, Duration duration) {
        String result = String.format("%s %s! They took %d seconds to get %d correct!",
                playerName, won ? "got all of them correct!" : "didn't get through all of the questions!",
                duration.getSeconds(), score);
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME, true))) {
            out.println(result);
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public void setupGame() {
        outputHandler.clearScreen();
        triviaQuestions = new ArrayList<>(TriviaQuestions.getQuestions());
        Collections.shuffle(triviaQuestions);
        currentQuestionIndex = 0;
        score = 0;
        gameOver = false;
        startTime = Instant.now();
    }

    @Override
    public void displayTitle() {
        outputHandler.displayMessage("");
        outputHandler.displayMessage(
                "████████╗██████╗ ██╗   ██╗███████╗     ██████╗ ██████╗     ███████╗ █████╗ ██╗     ███████╗███████╗██████╗ \r\n"
                        + //
                        "╚══██╔══╝██╔══██╗██║   ██║██╔════╝    ██╔═══██╗██╔══██╗    ██╔════╝██╔══██╗██║     ██╔════╝██╔════╝╚════██╗\r\n"
                        + //
                        "   ██║   ██████╔╝██║   ██║█████╗      ██║   ██║██████╔╝    █████╗  ███████║██║     ███████╗█████╗    ▄███╔╝\r\n"
                        + //
                        "   ██║   ██╔══██╗██║   ██║██╔══╝      ██║   ██║██╔══██╗    ██╔══╝  ██╔══██║██║     ╚════██║██╔══╝    ▀▀══╝ \r\n"
                        + //
                        "   ██║   ██║  ██║╚██████╔╝███████╗    ╚██████╔╝██║  ██║    ██║     ██║  ██║███████╗███████║███████╗  ██╗   \r\n"
                        + //
                        "   ╚═╝   ╚═╝  ╚═╝ ╚═════╝ ╚══════╝     ╚═════╝ ╚═╝  ╚═╝    ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝  ╚═╝   \r");
    }
}