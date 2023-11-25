package io.github.cuisse.bricker.game.screens;

import java.awt.Color;
import java.awt.Graphics2D;

import io.github.cuisse.bricker.game.elements.PlayAgain;
import io.github.cuisse.bricker.game.elements.Quit;

/**
 * Game over screen.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class GameOverScreen extends AbstractScreen {

    private static final String TITLE = "Game Over";
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BOX_COLOR = Color.BLACK;
    private static final Color TITLE_COLOR = Color.BLACK;

    @Override
    protected void initialize() {
        addElement(new PlayAgain(width / 2 - 105, height / 3 - 50, 150, 30));
        addElement(new Quit(width / 2 - 80, height / 3 - 10, 100, 30));
    }

    @Override
    public void draw(Graphics2D graphics) {
        // Background
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, width, height);

        // box
        graphics.setColor(BOX_COLOR);
        graphics.drawRect((width / 2) - 220, (int) (height / 5.3), width / 2, height / 2);

        // Title background
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(width / 2 - 105, height /  6, 150, 100);

        // Title
        graphics.setColor(TITLE_COLOR);
        graphics.drawString(TITLE, width / 2 - 95, height / 5);

        // Elements
        super.draw(graphics);
    }

    @Override
    public void update() { /* Empty */ }

    @Override
    public void keyPressed(int keyCode) { /* Empty */ }
    
}
