import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * This is the base city in this case its a square type of city.
 * 
 * @author Gianni Consiglio
 */
public class BaseCity extends City {
    /**
     * constructs the city object.
     * 
     * @param x     -position
     * @param y     - position
     * @param name  - name of the city
     * @param color - color of the square
     * @param size  - size of the city
     */
    public BaseCity(int x, int y, String name, Color color, String size) {
        super(x, y, name, color, size);
    }

    /**
     * overrides the draw method of city as it needs to be implemented.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.height, bounds.width);
        g.setColor(this.color);
        g.fillRect(bounds.x + 1, bounds.y + 1, bounds.height - 1, bounds.width - 1);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.PLAIN, 14));
        g.drawString(name, bounds.x + bounds.width, bounds.y);
    }
    
    /**
     * returns itself since its the base city
     */
    @Override
    public City getCity() {
        return this;
    }
}
