package BrickBreaker;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePlay gamePlay = new GamePlay();
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Brick Breaker");
        frame.add(gamePlay);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Never add elements to the frame after setting it to visible.
        frame.setVisible(true);
    }
}
