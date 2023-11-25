package io.github.cuisse.bricker.game.elements;

import java.awt.Font;

/**
 * Settings for the game.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public final class Settings {
    
    public static final int WIDTH  = 800;
    public static final int HEIGHT = 600;
    public static final int MIN_DIFFICULTY = 1;
    public static final int MAX_DIFFICULTY = 10;
    public static final Font FONT_MONOSPACED_24 = new Font("Monospaced", Font.BOLD, 24);
    private static int difficulty = MIN_DIFFICULTY;

    /**
     * Get the current difficulty.
     *
     * @return the current difficulty.
     */
    public static int difficulty() {
        return difficulty;
    }

    /**
     * Set the difficulty.
     *
     * @param difficulty the difficulty to set.
     */
    public static void difficulty(int difficulty) {
        if (difficulty < MIN_DIFFICULTY || difficulty > MAX_DIFFICULTY) {
            throw new IllegalArgumentException("Difficulty must be between " + MIN_DIFFICULTY + " and " + MAX_DIFFICULTY + ".");
        } else {
            Settings.difficulty = difficulty;
        }
    } 

    private Settings() {
        throw new IllegalStateException();
    }

}
