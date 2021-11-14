import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CircleCity extends CityDecorator{
    private int x;
    private int y;
    
    public CircleCity(int x, int y, String name, Color colorCircle, String size) {
        super(x, y, name, colorCircle, size);
        int shapeSize;
        try {
            shapeSize = Integer.parseInt(size);
        }catch(Exception e) {
            shapeSize = 16;
        }
        this.x = x;
        this.y = y;
        this.size = shapeSize;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(this.color);
        g.fillOval(x+3, y+3, size-5, size-5);
        g.setColor(Color.BLACK);
        g.drawOval(x+3, y+3, size-5, size-5);   
    }
    
    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
        super.move(x, y);
    }
    
    @Override
    public boolean contains(int x, int y) {
        boolean center;
        center = super.contains(x, y);
        if(center) {
            return true;
        }else {
            return false;
        }
    }
    
    /**
     * @return x location of city
     */
    @Override
    public int getX() {
        return super.getX();
    }

    /**
     * @return y location of city
     */
    @Override
    public int getY() {
        return super.getY();
    }

}
