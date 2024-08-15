import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class SnakeGamePanel extends JPanel implements ActionListener {
    Color primaryColor = Color.GRAY;
    int tilePerWidth = 34;
    int tilePerHeight = 24;
    int tileSize = 32;
    int width = tilePerWidth * tileSize;
    int height = tilePerHeight * tileSize;
    Tile snakeHead;
    Tile food;
    Random random;
    Timer gameLoop;
    GameKeyListener keyListener = new GameKeyListener();
    ArrayList<Tile> snakeBody;
    boolean gameOver = false;
    int score = 0;
    Image snakeHeadImage;
    Image snakeBodyImage;
    Image foodImage;
    Button playAgainButton;

    public SnakeGamePanel() throws IOException {
        this.setPreferredSize(new Dimension(tilePerWidth * tileSize, tilePerHeight * tileSize));
        this.setBackground(primaryColor);
        setFocusable(true);
        snakeBody = new ArrayList<Tile>();
        snakeHeadImage = ImageIO.read(new File("src\\Tiles\\snake_head.png"));
        snakeBodyImage = ImageIO.read(new File("src\\Tiles\\snake_body.png"));
        foodImage = ImageIO.read(new File("src\\Tiles\\apple.png"));
        random = new Random();
        snakeHead = new Tile(5, 5);
        placeFood();
        this.addKeyListener(keyListener);
        
        // Initialize the play again button
        this.playAgainButton = new Button("Play Again");
        this.add(playAgainButton);
        playAgainButton.setBounds(width / 2 - 80, height / 2 + 30, 100, 30);
        playAgainButton.setVisible(false);
        
        // Add action listener for the play again button
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgain();
            }
        });
        
        gameLoop = new Timer(80, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Drawing the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        if (!gameOver) {
            g.drawString("Score: " + score, 10, 30);
        } else {
            g.setColor(Color.RED);
            g.drawString("GAME OVER with the score of: " + score, width / 2 - 210, height / 2);
        }
        // Drawing the snake head
        g.setColor(primaryColor);
        g.fillRect(snakeHead.x * snakeHead.size, snakeHead.y * snakeHead.size, snakeHead.size, snakeHead.size);
        g.drawImage(snakeHeadImage, snakeHead.x * snakeHead.size, snakeHead.y * snakeHead.size, snakeHead.size, snakeHead.size, this);
        // Drawing the snake parts
        for (Tile snakePart : snakeBody) {
            g.setColor(Color.GRAY);
            g.fillRect(snakePart.x * snakePart.size, snakePart.y * snakePart.size, snakePart.size, snakePart.size);
            g.drawImage(snakeBodyImage, snakePart.x * snakePart.size, snakePart.y * snakePart.size, snakePart.size, snakePart.size, this);
        }
        // Drawing the food
        g.setColor(primaryColor);
        g.fillRect(food.x * food.size, food.y * food.size, food.size, food.size);
        g.drawImage(foodImage, food.x * food.size, food.y * food.size, food.size, food.size, this);
    }

    public void placeFood() {
        food = new Tile(random.nextInt(this.tilePerWidth), random.nextInt(this.tilePerHeight));
    }

    public void movesnake() {
        // Store the current position of the snake head
        int previousHeadX = snakeHead.x;
        int previousHeadY = snakeHead.y;
        // Update the snake's head position first
        snakeHead.x += keyListener.xDirection;
        snakeHead.y += keyListener.yDirection;
        // Check for collision with food
        if (checkCollision(snakeHead, food)) {
            // Add new segment to the snake's body
            snakeBody.add(new Tile(previousHeadX, previousHeadY)); // Store the previous head position
            score++;
            placeFood(); // Reposition the food
        }
        // Check for collision with itself
        for (Tile tile : snakeBody) {
            if (checkCollision(snakeHead, tile)) {
                gameOver = true;
                break; // Exit the loop if a collision is found
            }
        }
        // Check for collision with bounds
        if (snakeHead.x < 0 || snakeHead.x >= tilePerWidth || snakeHead.y < 0 || snakeHead.y >= tilePerHeight) {
            gameOver = true;
        }
        // Move the snake's body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                // Move the first body part to the position of the head
                snakePart.x = previousHeadX; // Use the stored previous head position
                snakePart.y = previousHeadY; // Use the stored previous head position
            } else {
                // Move each body part to the position of the previous part
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
    }

    public boolean checkCollision(Tile tile1, Tile tile2) {
        return (tile1.x == tile2.x && tile1.y == tile2.y);
    }

    public void playAgain() {
        gameOver = false;
        score = 0; // Reset score
        snakeBody.clear();
        snakeHead = new Tile(5, 5);
        placeFood();
        playAgainButton.setVisible(false); // Hide the 
        
        // Reset the direction of the snake
        keyListener.xDirection = 0;
        keyListener.yDirection = 0;

        gameLoop.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        movesnake();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            playAgainButton.setVisible(true);
        }
    }
}