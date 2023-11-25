package io.github.cuisse.bricker.game.screens;

import io.github.cuisse.bricker.game.elements.PlayButton;
import io.github.cuisse.bricker.game.elements.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;

/**
 * Start screen.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class StartScreen extends AbstractScreen {

    private List<Rectangle> stars;

    private static final String TITLE = "Brick Breaker";
    private static final String BALL_SPEED = "Ball speed";
    private static final Color BACKGROUND_COLOR = new Color(0, 2, 32);
    private static final Color TITLE_COLOR = Color.ORANGE;
    private static final Color BALL_COLOR = Color.WHITE;
    private static final Color STAR_COLOR = Color.WHITE;
    private static final Color BOX_COLOR = new Color(32, 32, 32, 200);

    @Override
    protected void initialize() {
        addElement(new PlayButton(width / 2 - 50, height / 2 - 50, 100, 50));
        addElement(new Slider(width / 2 - 50, height / 2 + 50, 100, 10));
        stars = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            stars.add(
                    randomStar()
            );
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        // Space 
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, width, height);

        // Stars
        graphics.setColor(STAR_COLOR);
        for (int i = 0; i < 100; i++) {
            Rectangle star = stars.get(i);
            if (ThreadLocalRandom.current().nextInt(0, 1000) == 0) { // Animation effect
                star = randomStar();
                stars.set(i, star);
            } else {
                star.y += 1;
                if (star.y > height) {
                    star.y = 0;
                }
            }
            graphics.fill(star);
        }

        // Menu box
        graphics.setColor(BOX_COLOR);
        graphics.fillRoundRect(width / 2 - 200, height / 2 - 200, width / 2, height, 50, 50);

        // Buttons
        super.draw(graphics);

        // Title
        Font font = graphics.getFont();
        graphics.setFont(font.deriveFont(Font.BOLD, 50));
        graphics.setColor(TITLE_COLOR);
        graphics.drawString(TITLE, width / 2 - 195, height / 5);
        graphics.setFont(font);

        // Ball speed slider
        graphics.setColor(BALL_COLOR);
        graphics.drawString(BALL_SPEED, width / 2 - 75, height / 2 + 40);
    }

    private Rectangle randomStar() {
        return new Rectangle(
                ThreadLocalRandom.current().nextInt(0, width),
                ThreadLocalRandom.current().nextInt(0, height),
                ThreadLocalRandom.current().nextInt(2, 6),
                ThreadLocalRandom.current().nextInt(2, 6)
        );
    }

    @Override
    public void update() { /* Unused */ }

    @Override
    public void keyPressed(int keyCode) { /* Unused */ }
    
}
