import java.awt.event.*;

public class GameKeyListener implements KeyListener {

    int xDirection = 0;
    int yDirection = 0;

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && yDirection != 1) {
            this.xDirection = 0;
            this.yDirection = -1;
        }

        else if(e.getKeyCode() == KeyEvent.VK_DOWN && yDirection != -1) {
            this.xDirection = 0;
            this.yDirection = 1;
        }

        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && xDirection != -1) {
            this.xDirection = 1;
            this.yDirection = 0;
        }

        else if(e.getKeyCode() == KeyEvent.VK_LEFT && xDirection != 1) {
            this.xDirection = -1;
            this.yDirection = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
