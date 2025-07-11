import java.awt.*;
import javax.swing.*;

public class Cell extends JButton {
    // 0 for unfilled, 1 for black, -1 for white
    private int state; 

    public Cell(int init_state) {
        super(); 
        this.state = init_state; 
    }

    public void setState(int state) {
        this.state = state; 
        if (state == -1) {
            this.setIcon(new DiscIcon(Color.WHITE, 60));
        } else if (state == 1) {
            this.setIcon(new DiscIcon(Color.BLACK, 60));
        }
    }

    public int getState() {
        return this.state; 
    }

}

class DiscIcon implements Icon {
    private final int size;
    private final Color color;

    public DiscIcon(Color color, int size) {
        this.color = color;
        this.size = size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}