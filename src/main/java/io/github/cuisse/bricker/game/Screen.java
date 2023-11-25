package io.github.cuisse.bricker.game;

import java.awt.Graphics2D;

/**
 * Screen interface for the game.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface Screen {

    /**
     * Updates the screen.
     */
    void update();

    /**
     * Draws the screen.
     *
     * @param graphics the graphics object to draw with.
     */
    void draw(Graphics2D graphics);

    /**
     * Called when a key is pressed.
     *
     * @param keyCode the key code of the key pressed.
     */
    void keyPressed(int keyCode);

    /**
     * Called when mouse is clicked.
     *
     * @param x the x coordinate of the mouse.
     * @param y the y coordinate of the mouse.
     */
    void mouseClicked(int x, int y);

    /**
     * Called when mouse is moved.
     *
     * @param x the x coordinate of the mouse.
     * @param y the y coordinate of the mouse.
     */
    void mouseMoved(int x, int y);
    
}
