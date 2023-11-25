package io.github.cuisse.bricker;

import javax.swing.JFrame;

import java.awt.EventQueue;

/**
 * Application entry point.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class Application extends JFrame {

    public static void main( String[] args ) {
        EventQueue.invokeLater(() -> {
            (new Application()).setVisible(true);
        });
    }

    private Application() {
        add(new GameInstance());
        setTitle("Brick Breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

}
