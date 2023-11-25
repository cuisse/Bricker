package io.github.cuisse.bricker.game;

import java.awt.Graphics2D;
import java.awt.Shape;

/**
 * Interface for all UI elements
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public interface UIElement extends Shape {

    /**
     * Draws the UI element.
     *
     * @param graphics The graphics object to draw with.
     */
    void draw(Graphics2D graphics);

    /**
     * Called when the mouse is clicked.
     *
     * @param x The x coordinate of the mouse.
     * @param y The y coordinate of the mouse.
     */
    void mouseClicked(int x, int y);

    /**
     * Called when the mouse is moved.
     *
     * @param x the x coordinate of the mouse.
     * @param y the y coordinate of the mouse.
     */
    void mouseMoved(int x, int y);

    /**
     * Called when the mouse enters the UI element.
     *
     * @param x the x coordinate of the mouse.
     * @param y the y coordinate of the mouse.
     */
    void mouseEnter(int x, int y);

    /**
     * Called when the mouse exits the UI element.
     *
     * @param x the x coordinate of the mouse.
     * @param y the y coordinate of the mouse.
     */
    void mouseExit(int x, int y);
    
}
