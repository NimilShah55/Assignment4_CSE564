import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BaseCity extends City{

    public BaseCity(int x, int y, String name, Color color, String size) {
        super(x, y, name, color, size);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.height, bounds.width);     
        g.setColor(this.color);
        g.fillRect(bounds.x+1, bounds.y+1, bounds.height-1, bounds.width-1);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.PLAIN, 14));
        g.drawString(name, bounds.x + bounds.width, bounds.y);
    }
    @Override
    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }
    @Override
    public void move(int x, int y) {
        super.move(x, y);
    }
}
