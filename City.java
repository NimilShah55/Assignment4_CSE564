import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Movable city that holds positional values to represent location and a name.
 * @author Nate Robinson
 */
public class City {
    
    public Rectangle bounds;
    public final String name;
    private static final int SIZE = 10;
    
    /**
     * Constructs City instance.
     * @param x X location of city
     * @param y Y location of city
     * @param name
     */
    public City(int x, int y, String name) {
        this.bounds = new Rectangle(x, y, SIZE, SIZE);
        this.name = name;
    }
    
    /**
     * @return x location of city
     */
    public int getX() {
        return bounds.x;
    }

    /**
     * @return y location of city
     */
    public int getY() {
        return bounds.y;
    }
    
    /**
     * Check if city bounds intersects coordinates.
     * @param x The X location of the city
     * @param y The Y location of the city
     * @return true if it is contained, otherwise false
     */
    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }
    
    /**
     * Move the city to the given location
     * @param x The X location of the city
     * @param y The Y location of the city
     */
    public void move(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }
    
    /**
     * Return the precise center of the city.
     * @return The center of the bounds, represented as a point.
     */
    public Point center() {
        return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
    }
}
