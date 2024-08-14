import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class SnakeGamePanel extends JPanel implements ActionListener {
    int tilePerWidth = 34;
    int tilePerHeight = 24;
    int tileSize = 32;
    int width = tilePerWidth*tileSize;
    int height = tilePerHeight*tileSize;
    Tile snakeHead;
    Tile food;
    Random random;
    Timer gameLoop;
    GameKeyListener keyListener = new GameKeyListener();

    public SnakeGamePanel() {
        this.setPreferredSize(new Dimension(tilePerWidth*tileSize, tilePerHeight*tileSize));
        this.setBackground(Color.GRAY);
        setFocusable(true);

        random = new Random();

        snakeHead = new Tile(5, 5);
        placeFood();

        this.addKeyListener(keyListener);

        gameLoop = new Timer(70, this);
        gameLoop.start();

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        //drawing the snake
        g.setColor(Color.BLACK);
        g.fillRect(snakeHead.x*snakeHead.size, snakeHead.y*snakeHead.size, snakeHead.size, snakeHead.size);

        //drawing the food
        g.setColor(Color.red);
        g.fillRect(food.x*food.size, food.y*food.size, food.size, food.size);
    }

    public void placeFood() {
        food = new Tile(random.nextInt(this.tilePerWidth), random.nextInt(this.tilePerHeight));
    }

    public void movesnake() {
        snakeHead.x += keyListener.xDirection;
        snakeHead.y += keyListener.yDirection;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        movesnake();
        repaint();
    }
}
