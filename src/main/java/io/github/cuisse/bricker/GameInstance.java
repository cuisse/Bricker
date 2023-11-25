package io.github.cuisse.bricker;

import io.github.cuisse.bricker.game.UserInterface;
import io.github.cuisse.bricker.game.elements.Settings;
import io.github.cuisse.bricker.game.screens.StartScreen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This class is responsible for handling the game loop and rendering the game.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class GameInstance extends JLabel implements ActionListener {

    public GameInstance() {
        init();
    }

    private void init() {
        setFont(Settings.FONT_MONOSPACED_24);
        setPreferredSize(new Dimension(Settings.WIDTH, Settings.HEIGHT));
        setSize(Settings.WIDTH, Settings.HEIGHT);
        setFocusable(true);
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                UserInterface.keyPressed(event.getKeyCode());
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                UserInterface.mouseClicked(event.getX(), event.getY());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                UserInterface.mouseMoved(event.getX(), event.getY());
            }
        });
        UserInterface.currentScreen(new StartScreen());
        Timer timer = new Timer(1, this);
        timer.setDelay(1);
        timer.start();
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        for (int i = 0; i < Settings.difficulty(); i++) {
            UserInterface.update();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        render((Graphics2D) graphics);
    }

    private void render(Graphics2D graphics) {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHints(hints);
        UserInterface.draw(graphics);
    }
    
}
