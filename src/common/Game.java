package common;

import java.time.Duration;
import java.time.Instant;

public abstract class Game {
    protected String gameName;
    protected String playerName;
    protected boolean won;
    protected boolean gameOver;
    protected int score;
    protected Instant startTime;
    protected Instant endTime;
    protected Duration duration;
    protected OutputHandler outputHandler = new OutputHandler();
    protected InputHandler inputHandler = new InputHandler();

    public Game(String gameName) {
        this.gameName = gameName;
    }

    public abstract void setupGame();

    public abstract void play();

    public boolean isGameOver() {
        return gameOver;    
    };

    public abstract void endGame();

    public void exit() {
        outputHandler.displayMessage("....Too scared to keep playing?! Fine. Going back to the main menu.");
        gameOver = true;
        pauseForDramaticEffect(1.5);
    }

    public abstract void handleInput(String input);

    public abstract void displayOutput();

    public abstract void recordResult(String name, boolean won, int score, Duration duration);
    
    public abstract void displayTitle();

    // public abstract boolean isValidMove(String input);

    public void pauseForDramaticEffect(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

