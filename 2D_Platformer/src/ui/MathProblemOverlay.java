package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import gamestates.Playing;
import main.Game;

public class MathProblemOverlay {
    private Playing playing;
    private String question;
    private int answer;
    private boolean isVisible = false;
    private boolean isSolved = false;  // Track if the current problem has been solved
    private StringBuilder playerInput = new StringBuilder();
    private Random random = new Random();

    public MathProblemOverlay(Playing playing) {
        this.playing = playing;
        generateNewProblem();
    }

    public void generateNewProblem() {
        int a = random.nextInt(10) + 1;  // Numbers from 1 to 10
        int b = random.nextInt(10) + 1;  // Numbers from 1 to 10
        answer = a + b;
        question = "What is " + a + " + " + b + "?";
        playerInput.setLength(0); // Clear previous answers
        isSolved = false;  // Reset solved status when generating a new problem
    }

    public void update() {
    	 if (!isVisible) return;

    	    try {
    	        int inputAnswer = Integer.parseInt(playerInput.toString());
    	        if (inputAnswer == answer) {
    	            isSolved = true;
    	            isVisible = false; // Ensure visibility is toggled off when the answer is correct.
    	            playing.setLevelCompleted(true); // This should trigger the next level or confirmation of completion.
    	        }
    	    } catch (NumberFormatException e) {
    	        // Optionally clear playerInput here to allow the user to start over if they enter non-numeric input.
    	        playerInput.setLength(0);
    	    }
    }

    public void draw(Graphics g) {
        if (!isVisible) return;
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.WHITE);
        g.drawString(question, Game.GAME_WIDTH / 2 - 100, Game.GAME_HEIGHT / 2);
        g.drawString(playerInput.toString(), Game.GAME_WIDTH / 2 - 100, Game.GAME_HEIGHT / 2 + 30);
    }

    public void keyPressed(int keyCode) {
        if (!isVisible) return;

        if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
            playerInput.append((char) (keyCode - KeyEvent.VK_0 + '0'));
        } else if (keyCode == KeyEvent.VK_BACK_SPACE && playerInput.length() > 0) {
            playerInput.deleteCharAt(playerInput.length() - 1);
        } else if (keyCode == KeyEvent.VK_ENTER) {
            update();
        }
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
        if (visible) {
            isSolved = false; // Reset solve status each time the overlay is shown.
            generateNewProblem();
        }
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isSolved() {
        return isSolved;
    }
}
