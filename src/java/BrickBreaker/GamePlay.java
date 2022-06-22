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
    private int ballXdir = -2;
    private int ballYdir = -2;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(1, 1, 692, 592);

        graphics.setColor(Color.yellow);
        graphics.fillRect(0,0,3, 592);
        graphics.fillRect(0,0,692, 3);
        graphics.fillRect(691,1,3, 592);

        graphics.setColor(Color.blue);
        graphics.fillRect(playerX,550,100,8);

        graphics.setColor(Color.green);
        graphics.fillOval(ballposX, ballposY,20, 20);
        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            // If ball collides, reverse the direction of the ball.
            // This is done by multiplying the direction by -1.

            if(new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 560, 100, 8))) { // if ball collides with the player
                ballYdir = -ballYdir;
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
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
