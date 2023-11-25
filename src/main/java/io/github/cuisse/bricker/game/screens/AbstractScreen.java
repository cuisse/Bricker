package io.github.cuisse.bricker.game.screens;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import io.github.cuisse.bricker.game.Screen;
import io.github.cuisse.bricker.game.UIElement;
import io.github.cuisse.bricker.game.elements.Settings;

/**
 * Abstract screen class
 * <p>
 * This class is used to create screens for the game.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public abstract class AbstractScreen extends Rectangle implements Screen {

    private final List<UIElement> elements = new ArrayList<>();

    public AbstractScreen() {
        super(0, 0, Settings.WIDTH, Settings.HEIGHT);
        initialize();
    }

    /**
     * Initialize the screen.
     */
    protected abstract void initialize();

    /**
     * Add an element to the screen.
     *
     * @param element the element to add.
     */
    protected void addElement(UIElement element) {
        elements.add(new Element(element));
    }

    @Override
    public void draw(Graphics2D graphics) {
        for (UIElement element : elements) {
            element.draw(graphics);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        for (UIElement element : elements) {
            if (element.contains(x, y)) {
                element.mouseClicked(x, y);
            }
        }
    }

    public void mouseMoved(int x, int y) {
        for (UIElement element : elements) {
            if (element.contains(x, y)) {
                if (element instanceof Element adapter && !adapter.mouseInside) {
                    adapter.mouseInside = true;
                    element.mouseEnter(x, y);
                }
            } else {
                if (element instanceof Element adapter &&  adapter.mouseInside) {
                    adapter.mouseInside = false;
                    element.mouseExit(x, y);
                }
            }
            element.mouseMoved(x, y);
        }
    }

    private static class Element extends Rectangle implements UIElement {
        final UIElement value;
        boolean mouseInside = false;

        public Element(UIElement value) {
            super(value.getBounds());
            this.value = value;
        }

        @Override public void draw(Graphics2D graphics)  { value.draw(graphics);     }
        @Override public void mouseClicked(int x, int y) { value.mouseClicked(x, y); }
        @Override public void mouseMoved(int x, int y)   { value.mouseMoved(x, y);   }
        @Override public void mouseEnter(int x, int y)   { value.mouseEnter(x, y);   }
        @Override public void mouseExit(int x, int y)    { value.mouseExit(x, y);    }
    }

    
}
