import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame("Snake game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGamePanel gamePanel = new SnakeGamePanel();

        window.setSize(gamePanel.width , gamePanel.height);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        window.setVisible(true);
        window.add(gamePanel);
        window.pack();
        gamePanel.requestFocus();
    }
}
