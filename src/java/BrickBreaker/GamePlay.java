package BrickBreaker;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 30;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -10;
    private int ballYdir = -10;

    private MapGenerator bricksMapGenerator;

    public GamePlay() {
        bricksMapGenerator = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(1, 1, 692, 594);

        bricksMapGenerator.draw((Graphics2D) graphics);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 3, 594);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 1, 3, 594);

        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(playerX, 550, 100, 8);

        graphics.setColor(Color.GREEN);
        graphics.fillOval(ballposX, ballposY, 20, 20);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("sans", Font.BOLD, 25));
        graphics.drawString("Score: " + score, 350, 30);

        if(totalBricks <= 0) {
            play = false;
            timer.stop();
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("sans", Font.BOLD, 50));
            graphics.drawString("You Win", 350, 300);
        }

        if (ballposY > 592) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            ballposX = 120;
            ballposY = 350;
            timer.stop();

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("sans", Font.BOLD, 50));
            graphics.drawString("Game Over", 200, 300);

            graphics.setFont(new Font("sans", Font.BOLD, 20));
            graphics.drawString("Press Enter to play again", 200, 350);

        }

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            // If ball collides, reverse the direction of the ball.
            // This is done by multiplying the direction by -1.

            if (new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) { // if ball collides with the player
                ballYdir = -ballYdir;
            }

            // If ball collides with the blocks, the block disappear and the ball bounces.
            for (int i = 0; i < bricksMapGenerator.map.length; i++) {
                for (int j = 0; j < bricksMapGenerator.map[0].length; j++) {
                    if (bricksMapGenerator.map[i][j] > 0) {
                        int brickX = j * bricksMapGenerator.brickWidth + 80;
                        int brickY = i * bricksMapGenerator.brickHeight + 50;
                        int brickWidth = bricksMapGenerator.brickWidth;
                        int brickHeight = bricksMapGenerator.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRectTop = new Rectangle(brickX, brickY - ballYdir, brickWidth, ballYdir);
                        Rectangle brickRectBottom = new Rectangle(brickX, brickY + brickHeight, brickWidth, ballYdir);
                        Rectangle brickRectLeft = new Rectangle(brickX - ballXdir, brickY, ballXdir, brickHeight);
                        Rectangle brickRectRight = new Rectangle(brickX + brickWidth, brickY, ballXdir, brickHeight);


                        if (brickRect.intersects(ballRect) || brickRectTop.intersects(ballRect) || brickRectBottom.intersects(ballRect) || brickRectLeft.intersects(ballRect) || brickRectRight.intersects(ballRect)) {
                            bricksMapGenerator.setBrickValue(i, j, 0);
                            totalBricks--;
                            score += 5;
                            ballXdir = -ballXdir;
                        }
                    }
                }
            }


            // If ball collides with the top wall, reverse the direction of the ball.
            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }

            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }

            if (ballposX > 670) { // If the ball collides with the right wall, reverse the direction of the ball.
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 60;
    }

    public void moveLeft() {
        play = true;
        playerX -= 60;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
