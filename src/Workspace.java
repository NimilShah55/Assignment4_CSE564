import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI panel that handles mouse events and interactions with the cities.
 * @author Nate Robinson
 */
public class Workspace extends JPanel implements MouseListener, 
        MouseMotionListener, IObserver {
    
    int preX, preY;
    boolean pressOut = false;
    boolean isAddingCity = false;
    private City selected = null;
    final NewCityHandler newCityHandler;

    /**
     * Instantiates Workspace.
     */
    public Workspace() {
        this.newCityHandler = new NewCityHandler();

        addMouseMotionListener(this);
        addMouseListener(this);
    }
    
    /**
     * Clear collection of cities and repaint.
     */
    public void reset() {
        CityDatabase.getInstance().clear();
        StatusBar.getInstance().setStatus("Cities cleared.");
        repaint();
    }
    
    /**
     * Clear collection of cities and load new cities.
     * @param newCities Cities to load.
     */
    public void loadCities(City[] newCities) {
        CityDatabase.getInstance().addCities(newCities);
        StatusBar.getInstance().setStatus("New cities loaded.");
        repaint();
    }
    
    /**
     * Prompts city model to draw itself.
     * @param g Graphics to draw on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        List<City> cities = CityDatabase.getInstance().cities;
        Map<City,City> paths = CityDatabase.getInstance().paths;
        
        Color prevColor = g.getColor();
        paintCities(g2, cities);
        paintPaths(g2, paths);
        g.setColor(prevColor);
    }
    
    private void paintCities(Graphics2D g, List<City> cities) {
        for (City city : cities) {
            g.setColor(Color.BLACK);
            int x = city.bounds.x, y = city.bounds.y,
                        h = city.bounds.height, w = city.bounds.width;
            g.drawRect(x, y, w, h);
            g.setColor(Color.WHITE);
            g.fillRect(x + 1, y + 1, w - 1, h - 1);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Courier", Font.PLAIN, 12));
            g.drawString(city.name, x + w, y);
        }
    }
    
    private void paintPaths(Graphics2D g, Map<City, City> paths) {
        if (paths != null) {
            g.setColor(Color.RED);
            for (City thisCity : paths.keySet()) {
                City otherCity = paths.get(thisCity);
                g.drawLine(thisCity.center().x, thisCity.center().y, 
                        otherCity.center().x, otherCity.center().y);
            }
        }
    }

    /**
     * Unused.
     * @param e ActionEvent, unused
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Create a city if empty spot, otherwise select and move city.
     * @param e Used to get the location of the mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        selected = CityDatabase.getInstance().findCityAt(e.getX(), e.getY());
        
        if (selected == null) {
            if (!isAddingCity) {
                isAddingCity = true;
                newCityHandler.promptAt(e.getX(), e.getY());
            }
        } else {
            preX = (int)(selected.getX() - e.getX());
            preY = (int)(selected.getY() - e.getY());
            CityDatabase.getInstance().moveCity(selected, preX + e.getX(), preY + e.getY());
            repaint();
        }
    }

    /**
     * Move the city to location of mouse release.
     * @param e Used to get the location of the mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        selected = CityDatabase.getInstance().findCityAt(e.getX(), getY());

        if (selected != null) {
            CityDatabase.getInstance().moveCity(selected, preX + e.getX(), preY + e.getY());
            repaint();
        } else {
            StatusBar.getInstance().setStatus("City moved.");
        }
    }

    /**
     * Unused.
     * @param e ActionEvent, unused
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Unused.
     * @param e ActionEvent, unused
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * If mouse is pressed down, move the selected city to new location and 
     * repaint.
     * @param e Unused
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if(!pressOut) {
            CityDatabase.getInstance().moveCity(selected, preX + e.getX(), preY + e.getY());
            StatusBar.getInstance().setStatus("Dragging city.");
            repaint();
        }
    }

    /**
     * Unused.
     * @param e ActionEvent, unused
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
    
    /**
     * Sets the city paths and redraw the map.
     * @param ob Paths as a map with key and value as cities
     */
    @Override
    public void update(Object ob) {
        repaint();
    }

    private class NewCityHandler implements ActionListener {
        int x, y;
        final JTextField pendingNameField;
        
        private NewCityHandler() {
            this.pendingNameField = new JTextField();
            pendingNameField.setFont(new Font("Courier", Font.PLAIN, 12));
            pendingNameField.setVisible(false);
            pendingNameField.addActionListener(this);
            Workspace.this.add(pendingNameField);
        }
        
        private void promptAt(int x, int y) {
            this.x = x;
            this.y = y;
            isAddingCity = true;
            pendingNameField.setText("");
            pendingNameField.setBounds(x, y, 60, 20);
            pendingNameField.setVisible(true);
            pendingNameField.requestFocus();
            
        }

        /**
         * Finish city creation and send data to generate city.
         * @param e ActionEvent, used to get entered name
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            pendingNameField.setVisible(false);
            String name = e.getActionCommand();
            CityDatabase.getInstance().createCity(x, y, name);
            isAddingCity = false;
            repaint();
        }
    }
}
