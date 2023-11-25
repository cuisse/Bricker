package io.github.cuisse.bricker.game;

/**
 * Application user interface.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public final class UserInterface {
    
    private static Screen currentScreen;

    /**
     * Updates the current screen.
     */
    public static void update() {
        if (currentScreen != null) {
            currentScreen.update();
        }
    }

    /**
     * Draws the current screen.
     *
     * @param graphics the graphics context.
     */
    public static void draw(java.awt.Graphics2D graphics) {
        if (currentScreen != null) {
            currentScreen.draw(graphics);
        }
    }

    /**
     * Sets the current screen.
     *
     * @param screen the screen to set.
     */
    public static void currentScreen(Screen screen) {
        currentScreen = screen;
    }

    /**
     * Delivers the key pressed event to the current screen.
     *
     * @param keyCode the key code.
     */
    public static void keyPressed(int keyCode) {
        if (currentScreen != null) {
            currentScreen.keyPressed(keyCode);
        }
    }

    /**
     * Delivers the mouse-clicked event to the current screen.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public static void mouseClicked(int x, int y) {
        if (currentScreen != null) {
            currentScreen.mouseClicked(x, y);
        }
    }

    /**
     * Delivers the mouse-moved event to the current screen.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public static void mouseMoved(int x, int y) {
        if (currentScreen != null) {
            currentScreen.mouseMoved(x, y);
        }
    }

    private UserInterface() { }

}
