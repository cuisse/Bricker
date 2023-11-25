package io.github.cuisse.bricker.game.elements;

import java.awt.Color;
import java.awt.Graphics2D;

import io.github.cuisse.bricker.game.UserInterface;
import io.github.cuisse.bricker.game.screens.InGameScreen;

/**
 * Play button
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class PlayButton extends UIElementAdapter {

    private Color color = Color.WHITE;
    private static final String TEXT = "Play";

    public PlayButton(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.drawString(TEXT, middleX(graphics.getFontMetrics(), TEXT), middleY(graphics.getFontMetrics()));
    }

    @Override
    public void mouseClicked(int x, int y) {
        UserInterface.currentScreen(
                new InGameScreen()
        );
    }

    @Override
    public void mouseEnter(int x, int y) {
        if (color == Color.WHITE) {
            color = Color.RED;
        }
    }
    
    @Override
    public void mouseExit(int x, int y) {
        if (color == Color.RED) {
            color = Color.WHITE;
        }
    }
    
}
