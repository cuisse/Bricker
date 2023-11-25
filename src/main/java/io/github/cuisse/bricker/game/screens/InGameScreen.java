package io.github.cuisse.bricker.game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.github.cuisse.bricker.game.UserInterface;
import io.github.cuisse.bricker.game.elements.Settings;

/**
 * In game screen.
 *
 * @author Brayan Roman
 * @since  1.0.0
 */
public class InGameScreen extends AbstractScreen {

    private int points;
    private List<Brick> bricks;
    private Rectangle bounds;
    private Rectangle paddle;
    private Rectangle ball;
    private Color paddleColor = Color.BLACK;
    private int paddleHitDirection = PADDLE_TOP;
    private final Point ballVelocity = new Point(1, 1);
    private ScheduledFuture<?> changeColorTask;
    private ScheduledExecutorService service;

    // Used to change the direction of the ball when the paddle is moving
    private int paddleVelocityX;
    private int paddleLastX;

    // Used to store the velocity of the ball before the game is paused
    private int tempVelocityX;
    private int tempVelocityY;

    // Used to prevent multiple collisions with the paddle
    private long lastPaddleCollision;

    private static final int PADDLE_TOP = 1;
    private static final int PADDLE_BOTTOM = 2;
    private static final int PADDLE_LEFT = 3;
    private static final int PADDLE_RIGHT = 4;

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BALL_COLOR = Color.BLACK;
    private static final Color BOUNDS_COLOR = Color.BLACK;

    @Override
    protected void initialize() {
        bounds  = new Rectangle(90, 50, width - 180, height - 100);
        paddle  = new Rectangle(bounds.x + bounds.width / 2 - 50, bounds.y + bounds.height - 20, 60, 10);
        ball    = new Rectangle(bounds.x + bounds.width / 2 - 5, bounds.y + bounds.height - 35, 10, 10);
        bricks  = new ArrayList<>();
        service = Executors.newScheduledThreadPool(1);
        int brickWidth = 50;
        int brickHeight = 20;
        int brickPadding = 5;
        int brickOffsetTop = 10;
        int brickOffsetLeft = 10;
        int brickColumns = 11;
        int brickRows = 10;
        for (int i = 0; i < brickColumns; i++) {
            for (int j = 0; j < brickRows; j++) {
                int x = i * (brickWidth + brickPadding) + brickOffsetLeft;
                int y = j * (brickHeight + brickPadding) + brickOffsetTop;
                bricks.add(
                        new Brick(bounds.x + x, bounds.y + y, brickWidth, brickHeight)
                );
            }
        }
    }

    @Override
    public void update() {
        for (int index = 0; index < bricks.size(); index++) {
            if (handleBrickCollisionWith(index)) {
                break;
            }
        }

        checkPaddleCollision();

        if (ball.y < bounds.y) {
            changeYVelocity();
        }
        if (ball.x < bounds.x || (ball.x + ball.width) > bounds.x + bounds.width) {
            changeXVelocity();
        }
        if (bricks.isEmpty() || (ball.y + ball.height) > bounds.y + bounds.height) {
            ballVelocity.y = 0;
            ballVelocity.x = 0;
            callGameOver();
        } else {
            ball.x += ballVelocity.x;
            ball.y += ballVelocity.y;
        }

        paddleVelocityX = 0;
    }

    @Override
    public void draw(Graphics2D graphics) {
        // Background
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(0, 0, width, height);

        // Bounds
        graphics.setColor(BOUNDS_COLOR);
        graphics.draw(bounds);

        // Bricks
        for (Brick brick : bricks) {
            graphics.setColor(brick.color);
            graphics.fillRoundRect(brick.getBounds().x, brick.getBounds().y, brick.getBounds().width, brick.getBounds().height, 5, 5);

            Font font = graphics.getFont();
            graphics.setFont(font.deriveFont(7f));
            graphics.setColor(Color.BLACK);
            graphics.drawString(Integer.toString(brick.points), brick.getBounds().x + brick.getBounds().width / 2 - 3, brick.getBounds().y + brick.getBounds().height / 2 + 3);
            graphics.setFont(font);
        }

        // Paddle
        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect(paddle.getBounds().x, paddle.getBounds().y, paddle.getBounds().width, paddle.getBounds().height, 5, 5);
        if (paddleColor.equals(Color.RED)) {
            graphics.setColor(Color.RED);
            switch (paddleHitDirection) {
                case PADDLE_TOP    -> graphics.fillRoundRect(paddle.getBounds().x, paddle.getBounds().y, paddle.getBounds().width, 2, 5, 5);
                case PADDLE_BOTTOM -> graphics.fillRoundRect(paddle.getBounds().x, paddle.getBounds().y + paddle.getBounds().height - 2, paddle.getBounds().width, 2, 5, 5);
                case PADDLE_LEFT   -> graphics.fillRoundRect(paddle.getBounds().x, paddle.getBounds().y, 2, paddle.getBounds().height, 5, 5);
                case PADDLE_RIGHT  -> graphics.fillRoundRect(paddle.getBounds().x + paddle.getBounds().width - 2, paddle.getBounds().y, 2, paddle.getBounds().height, 5, 5);
            }
        }

        // Ball
        graphics.setColor(BALL_COLOR);
        graphics.fillOval(ball.getBounds().x, ball.getBounds().y, ball.getBounds().width, ball.getBounds().height);

        // Points
        graphics.setColor(Color.BLACK);
        if (ballVelocity.x == 0 && ballVelocity.y == 0) {
            graphics.drawString("Press SPACE to start", 10, 20);
        } else {
            graphics.drawString("Points: " + points , 10, 20);
        }
    }

    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == 27) { // ESC
            callGameOver();
        } else if (keyCode == 32) { // SPACE
            if (ballVelocity.x == 0 && ballVelocity.y == 0) {
                ballVelocity.x = tempVelocityX;
                ballVelocity.y = tempVelocityY;
            } else {
                tempVelocityX = ballVelocity.x;
                tempVelocityY = ballVelocity.y;
                ballVelocity.x = 0;
                ballVelocity.y = 0;
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (paddleLastX == 0) {
            paddleLastX = x;
        }
        paddleVelocityX = Integer.compare(x, paddleLastX);
        if (ballVelocity.x != 0 && ballVelocity.y != 0) { // If the game is not paused
            super.mouseMoved(x, y);
            paddle.x = x - paddle.width / 2;
            if (paddle.x < bounds.x) {
                paddle.x = bounds.x;
            } else if (paddle.x > bounds.x + bounds.width - paddle.width) {
                paddle.x = bounds.x + bounds.width - paddle.width;
            }
        }
        paddleLastX = x;
    }

    // Handles the collision with a brick
    private boolean handleBrickCollisionWith(int index) {
        Brick brick = bricks.get(index);
        if (ball.intersects(brick)) {
            if (ball.x + ball.width > brick.x && ball.x < brick.x + brick.width) {
                changeYVelocity();
            } else {
                changeXVelocity();
            }
            brick.lives -= 1;
            if (brick.lives <= 0) {
                bricks.remove(index);
                points += brick.points;
            } else {
                brick.color = brick.color.darker();
            }
            return true;
        }
        return false;
    }

    // Handles the collision with the paddle
    private void checkPaddleCollision() {
        if (ball.intersects(paddle)) {
            var now = System.currentTimeMillis();
            if (now - lastPaddleCollision < 100) {
                // Prevent multiple collisions with the paddle
                return;
            }
            lastPaddleCollision = now;
            int side = findSideCollision(ball, paddle);
            if (side == 0) {
                return;
            }
            if (side == PADDLE_TOP || side == PADDLE_BOTTOM) {
                changeYVelocity();
                if (paddleVelocityX != 0) {
                    ballVelocity.x = paddleVelocityX;
                }
            } else if (side == PADDLE_LEFT || side == PADDLE_RIGHT) {
                changeXVelocity();
            } else {
                changeYVelocity();
                changeXVelocity();
            }
            if (paddleColor.equals(Color.BLACK)) {
                paddleColor = Color.RED;
            }
            if (changeColorTask != null) {
                changeColorTask.cancel(true);
            }
            paddleHitDirection = side;
            changeColorTask = service.scheduleWithFixedDelay(() -> {
                paddleColor = Color.BLACK;
            }, 500, 500, TimeUnit.MILLISECONDS);
        }
    }

    private int findSideCollision(Rectangle ball, Rectangle paddle) {
        int ballX = ball.x + ball.width / 2;
        int ballY = ball.y + ball.height / 2;
        int paddleX = paddle.x + paddle.width / 2;
        int paddleY = paddle.y + paddle.height / 2;
        int paddleWidth = paddle.width / 2;
        int paddleHeight = paddle.height / 2;

        if (ballX < paddleX - paddleWidth ) return PADDLE_LEFT;
        if (ballX > paddleX + paddleWidth ) return PADDLE_RIGHT;
        if (ballY < paddleY - paddleHeight) return PADDLE_TOP;
        if (ballY > paddleY + paddleHeight) return PADDLE_BOTTOM;

        return 0;
    }

    private void changeYVelocity() {
        ballVelocity.y *= -1;
    }

    private void changeXVelocity() {
        ballVelocity.x *= -1;
    }

    private void callGameOver() {
        service.shutdownNow();
        UserInterface.currentScreen(
                new GameOverScreen()
        );
    }

    private static class Brick extends Rectangle {

        private int lives = Settings.difficulty() == Settings.MAX_DIFFICULTY ? 2 : 1;
        private final int points = Math.random() > 0.8 ? 1 : 2;
        private Color color = randomColor();

        private Brick(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        private Color randomColor() {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            return new Color(r, g, b);
        }

    }
    
}
