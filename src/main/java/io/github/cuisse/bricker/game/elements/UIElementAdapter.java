package io.github.cuisse.bricker.game.elements;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.FontMetrics;

import io.github.cuisse.bricker.game.UIElement;

/**
 * Adapter for UI elements
 * <p>
 * This class is used to make it easier to create UI elements.
 * It implements the UIElement interface and provides default
 * implementations for all methods.
 * <p>
 * This class is used by all UI elements.
 *
 * @see PlayAgain
 * @see PlayButton
 * @see Quit
 * @see Slider
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public abstract class UIElementAdapter extends Rectangle implements UIElement {

    public UIElementAdapter(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D graphics) { }

    @Override
    public void mouseClicked(int x, int y) { }

    @Override
    public void mouseMoved(int x, int y) { }

    @Override
    public void mouseEnter(int x, int y) { }

    @Override
    public void mouseExit(int x, int y) { }

    protected int middleX(FontMetrics metrics, String text) {
        return (x + width  / 2) - metrics.stringWidth(text) / 2;
    }

    protected int middleY(FontMetrics metrics) {
        return y + height / 3 + metrics.getHeight() / 2;
    }
    
}
