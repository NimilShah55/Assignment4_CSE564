import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * City Decorator is the abstract class that adds decorations to an existing city.
 * @author Gianni Consiglio
 */
public abstract class CityDecorator extends City{
    
    protected City city;
    
    /**
     * adds the base city to a the decorator.
     * @param c - city to add.
     */
    public void setCity(City c) {
        city = c;
    }
    
    /**
     * City Decorator- constructor for city calls the super constructor.
     * @param x - position
     * @param y - position
     * @param name - city name
     * @param color - city color
     * @param size - city size
     */
    public CityDecorator(int x, int y, String name, Color color, String size) {
        super(x, y, name, color, size);
    }
    
    /**
     * draw - draws the added city.
     */
    @Override
    public void draw(Graphics g) {
        if(city != null) {
            city.draw(g);            
        }
    }   
    
    /**
     * move - moves the added city.
     */
    @Override
    public void move(int x, int y) {
        if(city != null) {
            city.move(x, y);            
        }
    }
    
    /**
     * contains checks the bounds of the added city.
     */
    @Override
    public boolean contains(int x, int y) {
        if(city != null) {
            return city.contains(x, y);            
        }
        return false;
    }
    
    /**
     * gets x location of the added city.
     * 
     * @return x - position
     */
    @Override
    public int getX() {
        if(city != null) {
            return city.getX();
        }
        return 0;
    }

    /**
     * gets y location of the added city.
     * 
     * @return y -position
     */
    @Override
    public int getY() {
        if(city != null) {
            return city.getY();
        }
        return 0;
    }
    
    /**
     * gets the center of the base city.
     */
    @Override
    public Point center() {
        return city.center();
    }
    
    /**
     * gets the city that has the decoration added.
     * @return
     */
    @Override
    public City getCity() {
        if(city != null) {
            return city;
        }
        return null;
    }
}
