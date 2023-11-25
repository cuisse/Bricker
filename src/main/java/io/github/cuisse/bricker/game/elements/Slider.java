package io.github.cuisse.bricker.game.elements;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * This class is responsible for drawing the slider and changing the difficulty.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class Slider extends UIElementAdapter {

    private static final int steps = 10;

    public Slider(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect(x, y, width, height, 5, 5);
        graphics.setColor(difficultyColor());
        graphics.fillRoundRect(x, y, Settings.difficulty() * width / steps, height, 5, 5);
    }

    @Override
    public void mouseClicked(int x, int y) {
        Settings.difficulty(
            Math.min(Math.max((x - this.x) / (width / steps) + 1, Settings.MIN_DIFFICULTY), Settings.MAX_DIFFICULTY)
        );
    }

    public Color difficultyColor() {
        int percent = (int) ((double) Settings.difficulty() / (double) steps * 100);
        if (percent < 10) { return Color.GREEN.darker();  }
        if (percent < 20) { return Color.GREEN;           }
        if (percent < 30) { return Color.YELLOW.darker(); }
        if (percent < 40) { return Color.YELLOW;          }
        if (percent < 50) { return Color.ORANGE.darker(); }
        if (percent < 60) { return Color.ORANGE;          }
        if (percent < 70) { return Color.RED.darker();    }
        return Color.RED;
    }
    
}
