import javax.swing.*;
import java.awt.*;

public class SnakeGamePanel extends JPanel {
    int tilePerWidth = 34;
    int tilePerHeight = 24;
    int tileSize = 32;
    int width = tilePerWidth*tileSize;
    int height = tilePerHeight*tileSize;

    public SnakeGamePanel() {
        this.setPreferredSize(new Dimension(tilePerWidth*tileSize, tilePerHeight*tileSize));
        this.setBackground(Color.BLACK);
    }
}
