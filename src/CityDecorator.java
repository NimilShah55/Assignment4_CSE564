import java.awt.Color;
import java.awt.Graphics;

public abstract class CityDecorator extends City{
    
    protected City city;
    
    public void setCity(City c) {
        city = c;
    }
    
    public CityDecorator(int x, int y, String name, Color color, String size) {
        super(x, y, name, color, size);
    }
    @Override
    public void draw(Graphics g) {
        if(city != null) {
            city.draw(g);            
        }
    }   
    @Override
    public void move(int x, int y) {
        if(city != null) {
            city.move(x, y);            
        }
    }
    @Override
    public boolean contains(int x, int y) {
        if(city != null) {
            return city.contains(x, y);            
        }
        return false;
    }
}
