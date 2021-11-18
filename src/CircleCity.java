import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Circle City is a decoration for a city.
 * 
 * @author Gianni Consiglio
 */
public class CircleCity extends CityDecorator {
    private int x;
    private int y;

    /**
     * this is the constructor for the Circle city.
     * 
     * @param x           - position
     * @param y           - position
     * @param name        - name of the city
     * @param colorCircle - color needed to add.
     * @param size        - size
     */
    public CircleCity(int x, int y, String name, Color colorCircle, String size) {
        super(x, y, name, colorCircle, size);
        int shapeSize;
        try {
            shapeSize = Integer.parseInt(size);
        } catch (Exception e) {
            shapeSize = 16;
        }
        this.x = x;
        this.y = y;
        this.size = shapeSize;
    }

    /**
     * draw for the new circle as well as the base city.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(this.color);
        g.fillOval(x + 3, y + 3, size - 5, size - 5);
        g.setColor(Color.BLACK);
        g.drawOval(x + 3, y + 3, size - 5, size - 5);
    }

    /**
     * move the city to a new x and y.
     */
    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        super.move(x, y);
    }

    /**
     * contains checks the bounds of the city.
     */
    @Override
    public boolean contains(int x, int y) {
        boolean center;
        center = super.contains(x, y);
        int radius = size / 2;
        boolean circle = radius > (x * this.x + y * this.y);
        if (circle || center) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * gets x location of the base city.
     * 
     * @return x -position
     */
    @Override
    public int getX() {
        return super.getX();
    }

    /**
     * gets the y location of the base city.
     * 
     * @return y - position
     */
    @Override
    public int getY() {
        return super.getY();
    }

    /**
     * gets the center of the base city.
     */
    @Override
    public Point center() {
        return super.center();
    }
}
